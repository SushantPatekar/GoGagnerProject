package gogagner.goldenbrainsithub.com;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

import dbModel.User;
import dbModel.UserModel;
import utility.Constants;
import utility.Helper;
import utility.NetworkCommunicationHelper;
import utility.UriHelper;
import webAPIModel.RegisterAccountModel;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SignupActivity extends Activity implements View.OnClickListener {
    public static String TAG = SignupActivity.class.getSimpleName();
    Button btnSignUp;
    EditText edFirstName,
            edLastName,
            edMobileNumber, edEmail, edPassword, edConifirmPass;

ImageView imgAvatar;
    Spinner spState, spCity, spLocality;
    TextView tvIagree;
    TextView tvLogin;
    CheckBox chIagree;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.setFullScreen(SignupActivity.this);
        setContentView(R.layout.activity_signup_layout);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initView();

        Helper.hideKeyboard(SignupActivity.this);
    }

    public void initView() {
        try {

            edConifirmPass = (EditText) findViewById(R.id.edConfirmPassword);
            edEmail = (EditText) findViewById(R.id.edEmail);
            edFirstName = (EditText) findViewById(R.id.edFirstName);
            edLastName = (EditText) findViewById(R.id.edLastName);
            edMobileNumber = (EditText) findViewById(R.id.edMobileNumber);
            edPassword = (EditText) findViewById(R.id.edPassword);
            imgAvatar  = (ImageView) findViewById(R.id.imgAvatar);
            imgAvatar.setOnClickListener(this);

            edConifirmPass.setHint(getResources().getString(R.string.lbl_cofirm_password).toUpperCase());
            edEmail.setHint(getResources().getString(R.string.lbl_email).toUpperCase());
            edFirstName.setHint(getResources().getString(R.string.lbl_first_name).toUpperCase());
            edLastName.setHint(getResources().getString(R.string.lbl_last_name).toUpperCase());
            edMobileNumber.setHint(getResources().getString(R.string.lbl_mobile_number).toUpperCase());
            edPassword.setHint(getResources().getString(R.string.lbl_password).toUpperCase());
            btnSignUp = (Button) findViewById(R.id.btnRegister);
            btnSignUp.setOnClickListener(this);
            chIagree =(CheckBox) findViewById(R.id.checkbox);
            tvIagree = (TextView) findViewById(R.id.tvIagree);

            tvLogin = (TextView) findViewById(R.id.tvLogin);

            tvLogin.setOnClickListener(this);
            String first = "Already have an account with GoGaner ? ";
            String next = "<font color='#EE0000'>Log In</font>";
            tvLogin.setText(Html.fromHtml(first + next));

            spCity = (Spinner) findViewById(R.id.spCity);
            spLocality = (Spinner) findViewById(R.id.spCityArea);
            spState = (Spinner) findViewById(R.id.spState);

            localcities = new ArrayList<>();
            states = new ArrayList<>();
            cities = new ArrayList<>();

            fetchStates();

            spState.setOnItemSelectedListener(state_listener);
            spCity.setOnItemSelectedListener(city_listener);
            spLocality.setOnItemSelectedListener(localCity_listener);
        } catch (Exception e) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                validate();

                break;

            case R.id.tvLogin:
                startActivity(new Intent(SignupActivity.this,
                        LoginActivity.class));
                finish();
                break;

            case R.id.imgAvatar:
                checkWriteExternalPermission();
                break;
        }
    }

    public void validate() {
        String mobStr = edMobileNumber.getText().toString().trim();
        String pwdStr = edPassword.getText().toString().trim();
        String emailStr = edEmail.getText().toString().trim();
        String firstName = edFirstName.getText().toString().trim();
        String lastName = edLastName.getText().toString().trim();

        if (mobStr.equals("") || mobStr.length() < 10) {
            Helper.showToast(this, getString(R.string.enter_mob_no));
        }
        else if (edFirstName.getText().toString().matches("")) {
            Helper.showToast(this, getString(R.string.signup_first_error));
        }
        else if (edLastName.getText().toString().matches("")) {
            Helper.showToast(this, getString(R.string.signup_last_error));
        }
        else if (edPassword.getText().toString().matches("")) {
            Helper.showToast(this, getString(R.string.signup_pwd_error));
        }
        else if (edConifirmPass.getText().toString().matches("")) {
            Helper.showToast(this, getString(R.string.signup_pwd_error));
        }
        else if (emailStr.equals("") || !emailStr.matches(Constants.UserDetails.EMAIL_REGEX)) {
            Helper.showToast(this, getString(R.string.enter_valid_email));
        }
        else if(edPassword.getText().toString().matches(edConifirmPass.getText().toString())){
            Helper.showToast(this, getString(R.string.enter_valid_confirm_pwd));

        }
        else {

            //TODO register api integration
            if (new Helper().isNetworkAvailable(getApplication())) {
                if(chIagree.isChecked()){
                    String webAPI = Helper.getSharedPrefValStr(SignupActivity.this, Constants.sharedPref.s_BASE_URL)
                            .concat(Constants.webAPI.apiRegister);
                    String requestBody = generateSignupBody();
                    Log.i(TAG,""+requestBody);
                    NetworkCommunicationHelper networkCommunicationHelper = new NetworkCommunicationHelper();

                    networkCommunicationHelper.sendPostRequest(getApplication(), webAPI, requestBody,
                            new NetworkCommunicationHelper.OnResponseReceived() {
                                @Override
                                public void onSuccess(final String res) {
                                    Helper.updatedSharedPrefValBoolean(SignupActivity.this,
                                            Constants.login.isSignupSuccess, true);
                                    startActivity(new Intent(SignupActivity.this,
                                            VerifyOTPActivity.class).putExtra(Intent.EXTRA_STREAM,Constants.webAPI.signFlag));
                                    addUserInDB();
                                    finish();
                                }

                                @Override
                                public void onFailure(final String err) {
                                    Helper.showToast(SignupActivity.this, ""+Helper.getServerErroMessage(err));

                                }
                            });

                }
                else {
                    Helper.showToast(SignupActivity.this,getResources().getString(R.string.i_agree_error));
                }



            }
            else
                Helper.showToast(getApplication(),getResources().getString(R.string.lbl_no_internet));
        }

    }

    public String generateSignupBody() {
        RegisterAccountModel registerAccountModel = new RegisterAccountModel();
        registerAccountModel.setCityId(spCity.getSelectedItemPosition()+1);
       registerAccountModel.setCountryId(1);
        registerAccountModel.setEmail(edEmail.getText().toString());
        registerAccountModel.setFirstName(edFirstName.getText().toString());
        registerAccountModel.setLastName(edLastName.getText().toString());
        registerAccountModel.setLocalityId(spLocality.getSelectedItemPosition()+1);
        registerAccountModel.setMobile(edMobileNumber.getText().toString());
        registerAccountModel.setStateId(spState.getSelectedItemPosition()+1);
        registerAccountModel.setUserType(1);
        registerAccountModel.setPassword(edPassword.getText().toString());
        registerAccountModel.setStatus(1);

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();
        Type type = new TypeToken<RegisterAccountModel>() {
        }.getType();
        return gson.toJson(registerAccountModel, type);
    }

    private ArrayAdapter<State> stateArrayAdapter;
    private ArrayAdapter<City> cityArrayAdapter;
    private ArrayAdapter<LocalCity> locaCityArrayAdapter;

    private ArrayList<State> states;
    private ArrayList<City> cities;
    private ArrayList<LocalCity> localcities;

    private AdapterView.OnItemSelectedListener state_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //  if (position > 0) {
            changeCityAdapter();
            //}

            chnageLocality();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void chnageLocality() {

        String webAPI = Helper.getSharedPrefValStr(SignupActivity.this, Constants.sharedPref.s_BASE_URL)
                .concat(Constants.webAPI.apiLocality);

        NetworkCommunicationHelper networkCommunicationHelper = new NetworkCommunicationHelper();
        networkCommunicationHelper.sendSynchronousGetRequest(getApplication(), webAPI,
                new NetworkCommunicationHelper.OnResponseReceived() {
                    @Override
                    public void onSuccess(final String res) {


                       try{
                           String responseString = Helper.fetchResponse(res);
                           Gson gson = new GsonBuilder()
                                   .serializeNulls()
                                   .create();
                           Type type = new TypeToken<ArrayList<LocalCity>>() {
                           }.getType();

                           localcities = gson.fromJson(responseString, type);

                           locaCityArrayAdapter = new ArrayAdapter<LocalCity>(getApplicationContext(), R.layout.simple_spinner_dropdown_item
                                   , localcities);
                           locaCityArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                           spLocality.setAdapter(locaCityArrayAdapter);
                       }
                       catch (Exception e){

                       }


                    }

                    @Override
                    public void onFailure(final String err) {
                    }
                });

        cityArrayAdapter = new ArrayAdapter<City>(getApplicationContext(), R.layout.simple_spinner_dropdown_item, new ArrayList<City>());
        cityArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spLocality.setAdapter(cityArrayAdapter);
    }

    private void changeCityAdapter() {

        String webAPI = Helper.getSharedPrefValStr(SignupActivity.this, Constants.sharedPref.s_BASE_URL)
                .concat(Constants.webAPI.apiCity);

        NetworkCommunicationHelper networkCommunicationHelper = new NetworkCommunicationHelper();
        networkCommunicationHelper.sendSynchronousGetRequest(getApplication(), webAPI,
                new NetworkCommunicationHelper.OnResponseReceived() {
                    @Override
                    public void onSuccess(final String res) {

                   try{
                       String responseString = Helper.fetchResponse(res);
                       Gson gson = new GsonBuilder()
                               .serializeNulls()
                               .create();
                       Type type = new TypeToken<ArrayList<State>>() {
                       }.getType();

                       states = gson.fromJson(responseString, type);
                       stateArrayAdapter = new ArrayAdapter<State>(getApplicationContext(), R.layout.simple_spinner_dropdown_item, states);
                       stateArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                       spCity.setAdapter(stateArrayAdapter);
                       chnageLocality();
                   }
                   catch (Exception e){

                   }





                    }

                    @Override
                    public void onFailure(final String err) {
                    }
                });

    }

    private AdapterView.OnItemSelectedListener city_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {
                chnageLocality();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener localCity_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void fetchStates() {

        String webAPI = Helper.getSharedPrefValStr(SignupActivity.this, Constants.sharedPref.s_BASE_URL)
                .concat(Constants.webAPI.apiState);

        NetworkCommunicationHelper networkCommunicationHelper = new NetworkCommunicationHelper();
        networkCommunicationHelper.sendSynchronousGetRequest(getApplication(), webAPI,
                new NetworkCommunicationHelper.OnResponseReceived() {
                    @Override
                    public void onSuccess(final String res) {

                       try{
                           String responseString = Helper.fetchResponse(res);

                           Gson gson = new GsonBuilder()
                                   .serializeNulls()
                                   .create();
                           Type type = new TypeToken<ArrayList<State>>() {
                           }.getType();

                           states = gson.fromJson(responseString, type);
                           stateArrayAdapter = new ArrayAdapter<State>(getApplicationContext(), R.layout.simple_spinner_dropdown_item, states);
                           stateArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                           spState.setAdapter(stateArrayAdapter);
                       }
                       catch (Exception e){

                       }


                    }

                    @Override
                    public void onFailure(final String err) {
                    }
                });

    }



    private class LocalCity implements Comparable<LocalCity> {

        private int id;
        private String name;
        private String slug;

        public LocalCity(int id, String name, String slug) {
            this.id = id;
            this.name = name;
            this.slug = slug;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public int compareTo(LocalCity another) {
            return this.getId() - another.getId();//ascending order
        }
    }

    private class State implements Comparable<State> {

        private int id;
        private String name;
        private String slug;

        public State(int id, String name, String slug) {
            this.id = id;
            this.name = name;
            this.slug = slug;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public int compareTo(State another) {
            return this.id - another.getId();//ascending order
//            return another.getCityID() - this.cityID;//descending order
        }

    }

    private class City implements Comparable<City> {
        private int id;
        private String name;
        private String slug;

        public City(int id, String name, String slug) {
            this.id = id;
            this.name = name;
            this.slug = slug;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public int compareTo(City another) {
            return this.id - another.getId();//ascending order
        }
    }



    public void addUserInDB(){
        Helper.updateSharedPrefValStr(getApplication(),Constants.sharedPref.userName,
                edMobileNumber.getText().toString());
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setCityId(spCity.getSelectedItemPosition()+1);
        user.setCountryId(1);
        user.setEmail(edEmail.getText().toString());
        user.setFirstName(edFirstName.getText().toString());
        user.setLastName(edLastName.getText().toString());
        user.setLocalityId(spLocality.getSelectedItemPosition()+1);
        user.setMobile(edMobileNumber.getText().toString());
        user.setStateId(""+spState.getSelectedItem().toString());
        user.setUserType(1);
        user.setPassword(edPassword.getText().toString());
        user.setProfileURL(profilePicPath);
        user.setStatus(1);
        new UserModel().addUser(getApplication(), user);


    }

    private void checkWriteExternalPermission() {
        if (!checkEachPermission(WRITE_EXTERNAL_STORAGE)) {
            requestContactPermission();
        }else {
            selectProfileImage();
        }
    }

    private boolean checkEachPermission(String permission) {
        return ContextCompat.checkSelfPermission(getApplicationContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }
    private void requestContactPermission() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case WRITE_EXTERNAL_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    try {
                        boolean writeExternalPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                        if (writeExternalPermission) {
//                            isPermissionAllowed = true;
                            selectProfileImage();

                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
                                    showMessageOKCancel("Please allow permissions.",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                        requestContactPermission();
                                                    }
                                                }
                                            });
                                    return;
                                } else {

                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        nbutton.setTextColor(Color.BLACK);

    }
    private void selectProfileImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Attachment"), PICK_IMAGE);
    }

    public static final int PICK_IMAGE = 6845;
    private static final int WRITE_EXTERNAL_PERMISSION_REQUEST_CODE = 2564;
    private String profilePicPath;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                profilePicPath = UriHelper.getPath(this, uri);

                showImage(profilePicPath);
            }
        }
    }


    private void showImage(String imagePath) {
//        ivProfilePic.setBackground(null);
        Glide.with(this).load(imagePath)
//                .apply(RequestOptions.circleCropTransform())
                .into(imgAvatar);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }



}












