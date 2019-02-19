package gogagner.goldenbrainsithub.com;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbModel.City;
import dbModel.CityModel;
import dbModel.Locality;
import dbModel.LocalityModel;
import dbModel.State;
import dbModel.StateModel;
import networkcommunication.VolleyMultipartRequest;
import utility.Constants;
import utility.Helper;
import networkcommunication.NetworkCommunicationHelper;
import utility.UriHelper;
import webAPIModel.RegisterAccountModel;
import webAPIModel.SignupMedia;
import webAPIModel.SignupMobile;

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
    private RequestQueue rQueue;

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
            mProgressDialog = new ProgressDialog(getApplication());
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
            String first = "Already have an account with GoGagner ? ";
            String next = "<font color='#EE0000'>Log In</font>";
            tvLogin.setText(Html.fromHtml(first + next));

            spCity = (Spinner) findViewById(R.id.spCity);
            spLocality = (Spinner) findViewById(R.id.spCityArea);
            spState = (Spinner) findViewById(R.id.spState);

            states = new ArrayList<>();

            fetchStates(Constants.webAPI.firstIndex);
            changeCityAdapter(Constants.webAPI.firstIndex);
            chnageLocality(Constants.webAPI.firstIndex);

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
        else  if(!Helper.isPasswordPolicy(edPassword)){
            Helper.showToast(this, getString(R.string.pwd_policy_error));

        }
        else  if(!Helper.isPasswordPolicy(edConifirmPass)){
            Helper.showToast(this, getString(R.string.pwd_policy_error));

        }
        else if (edConifirmPass.getText().toString().matches("")) {
            Helper.showToast(this, getString(R.string.signup_pwd_error));
        }
        else if (emailStr.equals("") || !emailStr.matches(Constants.UserDetails.EMAIL_REGEX)) {
            Helper.showToast(this, getString(R.string.enter_valid_email));
        }
        else if(!edPassword.getText().toString().matches(edConifirmPass.getText().toString())){
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
                    new Helper().showDialog(mProgressDialog,getResources().getString(R.string.popup_messege));

                    NetworkCommunicationHelper networkCommunicationHelper = new NetworkCommunicationHelper();

                    networkCommunicationHelper.sendPostRequest(getApplication(), webAPI, requestBody,
                            new NetworkCommunicationHelper.OnResponseReceived() {
                                @Override
                                public void onSuccess(final String res) {
                                    //Save Access TOken for uses
                                    try {
                                        new Helper().hideDialog(mProgressDialog);
                                        String data = Helper.fetchResponseasObject(res);
                                        JSONObject jsonData = new JSONObject(data);
                                        String token = jsonData.getString("token");
                                        Helper.updateSharedPrefValStr(SignupActivity.this,
                                                Constants.sharedPref.userName, edMobileNumber.getText().toString());
                                        Helper.updateSharedPrefValStr(SignupActivity.this,
                                                Constants.login.x_access_token, token);
                                        startActivity(new Intent(SignupActivity.this,
                                                VerifyOTPActivity.class).putExtra(Intent.EXTRA_STREAM, Constants.webAPI.signFlag));
                                        finish();
                                    } catch (Exception e) {

                                    }

                                    Helper.updatedSharedPrefValBoolean(SignupActivity.this,
                                            Constants.login.isSignupSuccess, true);

                                }

                                @Override
                                public void onFailure(final String err) {
                                    Helper.showToast(SignupActivity.this, ""+Helper.getServerMessage(err));
                                    new Helper().hideDialog(mProgressDialog);
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
        registerAccountModel.setCityId(mSelectedCityID);
        registerAccountModel.setCountryId(1);
        registerAccountModel.setEmail(edEmail.getText().toString());
        registerAccountModel.setFirstName(edFirstName.getText().toString());
        registerAccountModel.setLastName(edLastName.getText().toString());
        registerAccountModel.setLocalityId(mSelectedLocalityID);
        registerAccountModel.setMobile(edMobileNumber.getText().toString());
        registerAccountModel.setStateId(mSelectedStateID);
        registerAccountModel.setUserType(1);
        registerAccountModel.setPassword(edPassword.getText().toString());
        registerAccountModel.setStatus(1);

        SignupMobile signupMobile = new SignupMobile();
        signupMobile.setMedium(profilePicMediumPath);
        signupMobile.setSmall(profilePicSmallPath);

        SignupMedia signupMedia = new SignupMedia();
        signupMedia.setSignupMobile(signupMobile);

        registerAccountModel.setMediaModel(signupMedia);

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();
        Type type = new TypeToken<RegisterAccountModel>() {
        }.getType();
        return gson.toJson(registerAccountModel, type);
    }

    private ArrayAdapter<State> stateArrayAdapter;

    private ArrayList<State> states;
    String mSelectedStateName;
    int mSelectedStateID;

    String mSelectedCityName;
    int mSelectedCityID;

    String mSelectedLocalityName;
    int mSelectedLocalityID;


    private void chnageLocality(int mPosition) {
        String getBody = "cityId="+mPosition;
        String webAPI = Helper.getSharedPrefValStr(SignupActivity.this, Constants.sharedPref.s_BASE_URL)
                .concat(Constants.webAPI.apiLocality).concat(getBody);

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
                           Type type = new TypeToken<List<dbModel.Locality>>() {
                           }.getType();
                           List<dbModel.Locality> _mLocalityyList = gson.fromJson(responseString, type);

                           if(_mLocalityyList.size()>0){
                               addFirstLocality();
                               new LocalityModel().addAllLocality(getApplication(),
                                       _mLocalityyList);

                               List<dbModel.Locality> mcityList=new LocalityModel().getAllLocality(getApplication());
                               setLocalityAdapter(mcityList);
                           }
                           else {
                               List<dbModel.Locality> mcityList=new LocalityModel().getAllLocality(getApplication());
                               new LocalityModel().deleteAllLocality(getApplication(),mcityList);
                               addFirstLocality();
                                mcityList=new LocalityModel().getAllLocality(getApplication());
                               setLocalityAdapter(mcityList);
                           }


                       }
                       catch (Exception e){

                       }


                    }

                    @Override
                    public void onFailure(final String err) {
                    }
                });


    }

    private void setLocalityAdapter(List<Locality> mcityList) {
        spLocality.setAdapter(new LocalityAdapter(getApplicationContext(),
                R.layout.simple_spinner_dropdown_item,mcityList));
    }

    private void addFirstLocality() {
        dbModel.Locality firstLocality = new dbModel.Locality();
        firstLocality.setId(0);
        firstLocality.setName(getResources().getString(R.string.locality));
        firstLocality.setSlug(getResources().getString(R.string.locality));

        new LocalityModel().addLocality(getApplication(),firstLocality);
    }

    private void changeCityAdapter(int mPosition) {
        String getBody = "stateId="+mPosition;
        String webAPI = Helper.getSharedPrefValStr(SignupActivity.this, Constants.sharedPref.s_BASE_URL)
                .concat(Constants.webAPI.apiCity).concat(getBody);

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
                       Type type = new TypeToken<List<dbModel.City>>() {
                       }.getType();
                       List<dbModel.City> cityList = gson.fromJson(responseString, type);
                       List<dbModel.City> mcityList;
if(cityList.size()>0){
    addFirstCity();
    new CityModel().addAllCity(getApplication(),
            cityList);
     mcityList=new CityModel().getAllCity(getApplication());
    setCityAdpater(mcityList);

}
else {
    mcityList=new CityModel().getAllCity(getApplication());
    new CityModel().deleteAllCity(getApplication(),mcityList);
    addFirstCity();
    mcityList = new CityModel().getAllCity(getApplication());
    setCityAdpater(mcityList);

}

                   }
                   catch (Exception e){

                   }


                    }

                    @Override
                    public void onFailure(final String err) {
                    }
                });

    }

    private void setCityAdpater(List<City> mcityList) {
        spCity.setAdapter(new CityAdapter(getApplicationContext(),
                R.layout.simple_spinner_dropdown_item,mcityList));
    }

    private void addFirstCity() {
        dbModel.City firstCity = new dbModel.City();
        firstCity.setId(0);
        firstCity.setName(getResources().getString(R.string.city));
        firstCity.setSlug(getResources().getString(R.string.city));
        new CityModel().addCity(getApplication(),firstCity);
    }

    private AdapterView.OnItemSelectedListener state_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {
                dbModel.State _state=  new StateModel().getState(getApplication(),position);
                mSelectedStateName = _state.getName();
                mSelectedStateID = _state.getId();
                changeCityAdapter(position);
                chnageLocality(position);
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private AdapterView.OnItemSelectedListener city_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            if (position > 0) {
                dbModel.City _city=  new CityModel().getCity(getApplication(),position);
                mSelectedCityName = _city.getName();
                mSelectedCityID = _city.getId();
                chnageLocality(position);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener localCity_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {
                dbModel.Locality _locality=  new LocalityModel().getLocality(getApplication(),position);
                mSelectedLocalityName = _locality.getName();
                mSelectedLocalityID = _locality.getId();

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void fetchStates(int mPosition) {

        String getBody = "countryId="+mPosition;
        String webAPI = Helper.getSharedPrefValStr(SignupActivity.this, Constants.sharedPref.s_BASE_URL)
                .concat(Constants.webAPI.apiState).concat(getBody);

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

                           Type type = new TypeToken<List<dbModel.State>>() {
                           }.getType();
                           List<dbModel.State> statesList = gson.fromJson(responseString, type);

                           if(statesList.size()>0){
                               addFirstState();
                               new StateModel().addAllState(getApplication(),
                                       statesList);

                               List<dbModel.State> stateList=new StateModel().getAllState(getApplication());
                               setStateAdapter(stateList);
                           }
                           else {

                               new StateModel().deleteAll(getApplication());
                               addFirstState();
                               List<dbModel.State> stateList=new StateModel().getAllState(getApplication());
                               setStateAdapter(stateList);

                           }


                       }
                       catch (Exception e){
                        Log.e(TAG,""+e.getLocalizedMessage());
                       }


                    }

                    @Override
                    public void onFailure(final String err) {
                    }
                });

    }

    private void setStateAdapter(List<State> stateList) {
        spState.setAdapter(new StateAdapter(getApplicationContext(),
                R.layout.simple_spinner_dropdown_item,stateList));
    }

    private void addFirstState() {
        State firstStae = new State();
        firstStae.setId(0);
        firstStae.setName(getResources().getString(R.string.state));
        firstStae.setSlug(getResources().getString(R.string.state));
        new StateModel().addState(getApplication(),firstStae);
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
        /*Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);*/

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Attachment"), PICK_IMAGE);
    }

    public static final int PICK_IMAGE = 6845;
    private static final int WRITE_EXTERNAL_PERMISSION_REQUEST_CODE = 2564;
    private String profilePicMediumPath,profilePicSmallPath;
    Bitmap bitmap;
    ProgressDialog mProgressDialog;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
               try
               {
                   uri = resultData.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                  // profilePicPath = UriHelper.getPath(this, uri);
//TODO upload Image

                   String webAPI = Constants.webAPI.IMAGE_UPLOAD_BASE_URL.concat(Constants.webAPI.uploadImage);
                   new Helper().showDialog(mProgressDialog,getResources().getString(R.string.popup_messege));
                   VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, webAPI,
                           new Response.Listener<NetworkResponse>() {
                               @Override
                               public void onResponse(NetworkResponse response) {
                                   rQueue.getCache().clear();
                                   try {
                                       JSONObject jsonObject = new JSONObject(new String(response.data));

                                       Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                       profilePicMediumPath = jsonObject.getJSONObject("images").getJSONObject("mobile").getString("medium");
                                       profilePicSmallPath = jsonObject.getJSONObject("images").getJSONObject("mobile").getString("small");
                                       new Helper().hideDialog(mProgressDialog);
                                       showImage(profilePicMediumPath);

                                   } catch (JSONException e) {
                                       e.printStackTrace();
                                   }
                               }
                           },
                           new Response.ErrorListener() {
                               @Override
                               public void onErrorResponse(VolleyError error) {
                                   new Helper().hideDialog(mProgressDialog);
                                   Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           }) {

                       /*
                        * If you want to add more parameters with the image
                        * you can do it here
                        * here we have only one parameter with the image
                        * which is tags
                        * */
                       @Override
                       protected Map<String, String> getParams() throws AuthFailureError {
                           Map<String, String> params = new HashMap<>();
                           // params.put("tags", "ccccc");  add string parameters
                           return params;
                       }

                       /*
                        *pass files using below method
                        * */
                       @Override
                       protected Map<String, DataPart> getByteData() {
                           Map<String, DataPart> params = new HashMap<>();
                           long imagename = System.currentTimeMillis();
                           params.put("filename", new VolleyMultipartRequest.DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                           return params;
                       }
                   };


                   volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                           0,
                           DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                           DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                   rQueue = Volley.newRequestQueue(SignupActivity.this);
                   rQueue.add(volleyMultipartRequest);


               }
               catch (Exception e){

               }
            }
        }
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    private void showImage(String imagePath) {
        imgAvatar.setBackground(null);
        Glide.with(this).load(imagePath)
                .apply(RequestOptions.circleCropTransform())
                .into(imgAvatar);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }


///// for State Adapter

    public class StateAdapter extends ArrayAdapter<dbModel.State>{
        List<dbModel.State> mList ;
        public StateAdapter(@NonNull Context context, int resource, @NonNull List<dbModel.State> objects) {
            super(context, resource, objects);
            mList = objects;
        }

       /* public StateAdapter(Context context, int textViewResourceId,
                               String[] objects) {
            super(context, textViewResourceId, objects);
            // TODO Auto-generated constructor stub
        }*/

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            // TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            //return super.getView(position, convertView, parent);

            LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.simple_spinner_dropdown_item, parent, false);
            TextView label=(TextView)row.findViewById(R.id.idText);
            label.setText(""+mList.get(position).getName());



            return row;
        }
    }
/////

/////
    //City Adapter
public class CityAdapter extends ArrayAdapter<dbModel.City>{
    List<dbModel.City> mList ;
    public CityAdapter(@NonNull Context context, int resource, @NonNull List<dbModel.City> objects) {
        super(context, resource, objects);
        mList = objects;
    }



    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        // TODO Auto-generated method stub
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        //return super.getView(position, convertView, parent);

        LayoutInflater inflater=getLayoutInflater();
        View row=inflater.inflate(R.layout.simple_spinner_dropdown_item, parent, false);
        TextView label=(TextView)row.findViewById(R.id.idText);
        label.setText(""+mList.get(position).getName());



        return row;
    }
}

/////


    //Locality Adapter
    public class LocalityAdapter extends ArrayAdapter<dbModel.Locality>{
        List<dbModel.Locality> mList ;
        public LocalityAdapter(@NonNull Context context, int resource, @NonNull List<dbModel.Locality> objects) {
            super(context, resource, objects);
            mList = objects;
        }

       /* public StateAdapter(Context context, int textViewResourceId,
                               String[] objects) {
            super(context, textViewResourceId, objects);
            // TODO Auto-generated constructor stub
        }*/

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            // TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            //return super.getView(position, convertView, parent);

            LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.simple_spinner_dropdown_item, parent, false);
            TextView label=(TextView)row.findViewById(R.id.idText);
            label.setText(""+mList.get(position).getName());



            return row;
        }
    }

/////
}












