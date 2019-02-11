package gogagner.goldenbrainsithub.com;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dbModel.City;
import dbModel.CityModel;
import dbModel.Locality;
import dbModel.LocalityModel;
import dbModel.State;
import dbModel.StateModel;
import slidermenu.BaseDrawerActivity;
import utility.Constants;
import utility.Helper;
import networkcommunication.NetworkCommunicationHelper;
import webAPIModel.ChangePWDModel;
import webAPIModel.RegisterAccountModel;

public class BuyerMyProfileActivity extends BaseDrawerActivity implements View.OnClickListener {

    ImageView imvPannel_one, imvPannel_two, imvPannel_three;
    ImageView imline_one, imline_two, imline_three;
    EditText edOldPWD, edNewPWD, edCOnfirmPWD;

    EditText edFirstName,
            edLastName,
            edMobileNumber, edEmail, edDOB, edAdharNumber;

    Spinner spState, spCity, spLocality,spOccupation;
    RelativeLayout editProfileChangePwd, editProfilChangeImg, editProfileDetails;
    int sizeInDp = 12;
    //
    Button btnResetPwd, btnSaveChanges;

    private ArrayList<State> states;
    String mSelectedStateName;
    int mSelectedStateID;

    String mSelectedCityName;
    int mSelectedCityID;

    String mSelectedLocalityName;
    int mSelectedLocalityID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_myprofile);
        initView();
    }

    private void initView() {
        editProfileChangePwd = (RelativeLayout) findViewById(R.id.editProfileChangePwd);
        editProfilChangeImg = (RelativeLayout) findViewById(R.id.editProfilChangeImg);
        editProfileDetails = (RelativeLayout) findViewById(R.id.editProfileDetails);

        editProfilChangeImg.setVisibility(View.VISIBLE);
        editProfileChangePwd.setVisibility(View.GONE);
        edOldPWD = (EditText) findViewById(R.id.edOldPassword);
        edNewPWD = (EditText) findViewById(R.id.edNewPassword);
        edCOnfirmPWD = (EditText) findViewById(R.id.edConfirmPassword);

        edCOnfirmPWD.setHint(getResources().getString(R.string.lbl_cofirm_password).toUpperCase());
        edNewPWD.setHint(getResources().getString(R.string.lbl_new_password).toUpperCase());
        edOldPWD.setHint(getResources().getString(R.string.lbl_oldPwd).toUpperCase());

        imvPannel_one = (ImageView) findViewById(R.id.imvPannel_one);
        imvPannel_two = (ImageView) findViewById(R.id.imvPannel_two);
        imvPannel_three = (ImageView) findViewById(R.id.imvPannel_three);

        imline_one = (ImageView) findViewById(R.id.imline_one);
        imline_two = (ImageView) findViewById(R.id.imvline_two);
        imline_three = (ImageView) findViewById(R.id.imvline_three);

        imline_one.setVisibility(View.INVISIBLE);
        imline_two.setVisibility(View.VISIBLE);
        imline_three.setVisibility(View.INVISIBLE);

        imvPannel_one.setBackground(getDrawable(R.drawable.selector_row_buyer_menu));
        imvPannel_two.setBackground(getDrawable(R.drawable.bg_edittext_icon));
        imvPannel_three.setBackground(getDrawable(R.drawable.selector_row_buyer_menu));

        imvPannel_one.setImageResource(R.drawable.password);
        imvPannel_two.setImageResource(R.drawable.profile);
        imvPannel_three.setImageResource(R.drawable.edit_pencil);


        int dpAsPixels = getDpAsPixels(sizeInDp);
        imvPannel_one.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
        imvPannel_three.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
        imvPannel_two.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);

        imvPannel_one.setOnClickListener(this);
        imvPannel_two.setOnClickListener(this);
        imvPannel_three.setOnClickListener(this);

        //Event related
        btnResetPwd = (Button) findViewById(R.id.btnResetPwd);
        btnResetPwd.setOnClickListener(this);
        btnSaveChanges = (Button) findViewById(R.id.btnSaveChanges);
        btnSaveChanges.setOnClickListener(this);

        edAdharNumber = (EditText) findViewById(R.id.edAdharCard);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edFirstName = (EditText) findViewById(R.id.edFirstName);
        edLastName = (EditText) findViewById(R.id.edLastName);
        edMobileNumber = (EditText) findViewById(R.id.edMobileNumber);
        edDOB = (EditText) findViewById(R.id.edDOB);

        edAdharNumber.setHint(getResources().getString(R.string.lbl_aadhar_number).toUpperCase());
        edEmail.setHint(getResources().getString(R.string.lbl_email).toUpperCase());
        edFirstName.setHint(getResources().getString(R.string.lbl_first_name).toUpperCase());
        edLastName.setHint(getResources().getString(R.string.lbl_last_name).toUpperCase());
        edMobileNumber.setHint(getResources().getString(R.string.lbl_mobile_number).toUpperCase());
        edDOB.setHint(getResources().getString(R.string.lbl_dob).toUpperCase());
        edDOB.setOnClickListener(this);


        spCity = (Spinner) findViewById(R.id.spCity);
        spLocality = (Spinner) findViewById(R.id.spCityArea);
        spState = (Spinner) findViewById(R.id.spState);
        spOccupation = (Spinner)findViewById(R.id.spOccupation);

        states = new ArrayList<>();

        fetchStates(Constants.webAPI.firstIndex);
        changeCityAdapter(Constants.webAPI.firstIndex);
        chnageLocality(Constants.webAPI.firstIndex);
        setOccupation(Constants.webAPI.firstIndex);

        spState.setOnItemSelectedListener(state_listener);
        spCity.setOnItemSelectedListener(city_listener);
        spLocality.setOnItemSelectedListener(localCity_listener);
    }

    private int getDpAsPixels(int sizeInDp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (sizeInDp * scale + 0.5f);
    }

    @Override
    public void onDrawerProfileClick(View view) {

    }

    @Override
    public void onClick(View view) {
        int dpAsPixels = getDpAsPixels(sizeInDp);
        switch (view.getId()) {
            case R.id.imvPannel_one:
                enablePasswordView(dpAsPixels);
                break;

            case R.id.imvPannel_two:
                enableProfilePicView(dpAsPixels);
                break;

            case R.id.imvPannel_three:
                enableEditProfileView(dpAsPixels);
                break;

            case R.id.btnResetPwd:
                fun_ChnagePWD();
                break;

            case R.id.btnSaveChanges:
                fun_SaveChanges();
                break;

            case R.id.edDOB:
                edDOB.setInputType(InputType.TYPE_NULL);
                setDateFromCalender();
                break;
        }
    }

    private void enableEditProfileView(int dpAsPixels) {
        imvPannel_one.setBackground(getDrawable(R.drawable.selector_row_buyer_menu));
        imvPannel_two.setBackground(getDrawable
                (R.drawable.selector_row_buyer_menu));
        imvPannel_three.setBackground(getDrawable(R.drawable.bg_edittext_icon));
        imline_one.setVisibility(View.INVISIBLE);
        imline_two.setVisibility(View.INVISIBLE);
        imline_three.setVisibility(View.VISIBLE);
        editProfilChangeImg.setVisibility(View.GONE);
        editProfileChangePwd.setVisibility(View.GONE);
        editProfileDetails.setVisibility(View.VISIBLE);

        imvPannel_one.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
        imvPannel_three.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
        imvPannel_two.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
    }

    private void enableProfilePicView(int dpAsPixels) {
        imvPannel_one.setBackground(getDrawable(R.drawable.selector_row_buyer_menu));
        imvPannel_two.setBackground(getDrawable(R.drawable.bg_edittext_icon));
        imvPannel_three.setBackground(getDrawable(R.drawable.selector_row_buyer_menu));
        imline_one.setVisibility(View.INVISIBLE);
        imline_two.setVisibility(View.VISIBLE);
        imline_three.setVisibility(View.INVISIBLE);

        editProfilChangeImg.setVisibility(View.VISIBLE);
        editProfileChangePwd.setVisibility(View.GONE);
        editProfileDetails.setVisibility(View.GONE);

        imvPannel_one.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
        imvPannel_three.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
        imvPannel_two.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
    }

    private void enablePasswordView(int dpAsPixels) {
        imvPannel_one.setBackground(getDrawable(R.drawable.bg_edittext_icon));
        imvPannel_two.setBackground(getDrawable(R.drawable.selector_row_buyer_menu));
        imvPannel_three.setBackground(getDrawable(R.drawable.selector_row_buyer_menu));
        imline_one.setVisibility(View.VISIBLE);
        imline_two.setVisibility(View.INVISIBLE);
        imline_three.setVisibility(View.INVISIBLE);

        editProfilChangeImg.setVisibility(View.GONE);
        editProfileChangePwd.setVisibility(View.VISIBLE);
        editProfileDetails.setVisibility(View.GONE);


        imvPannel_one.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
        imvPannel_three.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
        imvPannel_two.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
    }

    private void fun_ChnagePWD() {
        try {
            if (changePWDValidate()) {
                fun_webAPIforChangePWD();
            }
        } catch (Exception e) {

        }
    }

    private void fun_SaveChanges() {
        try {
            if (sawChangeValidate()) {
                fun_webAPIforSaveChanes();
            }
        } catch (Exception e) {

        }
    }

    private boolean sawChangeValidate() {
        boolean isFormFilledUp = false;


        if (edFirstName.getText().toString().trim().matches("")) {
            Helper.showToast(this, getString(R.string.signup_first_error));
        } else if (edLastName.getText().toString().trim().matches("")) {
            Helper.showToast(this, getString(R.string.signup_last_error));
        } else if (edMobileNumber.getText().toString().trim().matches("")) {
            Helper.showToast(this, getString(R.string.enter_mob_no));
        } else if (edDOB.getText().toString().trim().matches("")) {
            Helper.showToast(this, getString(R.string.dob_error));
        } else if (edEmail.getText().toString().trim().matches("")) {
            Helper.showToast(this, getString(R.string.enter_valid_email));
        } else if (edAdharNumber.getText().toString().trim().matches("")) {
            Helper.showToast(this, getString(R.string.adhar_error));

        } else {
            isFormFilledUp = true;
        }
        return isFormFilledUp;
    }

    private boolean changePWDValidate() {
        boolean isValid = false;
        String oldPWD = edOldPWD.getText().toString().trim();
        String newPWD = edNewPWD.getText().toString().trim();
        String confirmPWD = edCOnfirmPWD.getText().toString().trim();
        if (oldPWD.matches("")) {
            isValid = false;
            Helper.showToast(this, getString(R.string.lbl_empty_oldPwd));
        } else if (newPWD.matches("")) {
            isValid = false;
            Helper.showToast(this, getString(R.string.lbl_empty_newPwd));
        } else if (confirmPWD.matches("")) {
            isValid = false;
            Helper.showToast(this, getString(R.string.lbl_empty_newPwd));
        } else if (!newPWD.matches(confirmPWD)) {
            isValid = false;
            Helper.showToast(this, getString(R.string.lbl_pwd_mismatch));

        } else if (newPWD.matches(oldPWD) || oldPWD.matches(confirmPWD)) {
            Helper.showToast(this, getString(R.string.lbl_old_newPwd));
            return false;
        } else {
            isValid = true;
        }
        return isValid;
    }

    private void fun_webAPIforChangePWD() {
        if (new Helper().isNetworkAvailable(getApplication())) {

            String webAPI = Helper.getSharedPrefValStr(BuyerMyProfileActivity.this, Constants.sharedPref.s_BASE_URL)
                    .concat(Constants.webAPI.changePassword);
            String requestBody = resetPWDBody();
            NetworkCommunicationHelper networkCommunicationHelper = new NetworkCommunicationHelper();

            networkCommunicationHelper.sendUserPostRequest(getApplication(), webAPI, requestBody,
                    new NetworkCommunicationHelper.OnResponseReceived() {
                        @Override
                        public void onSuccess(final String res) {
                            try {
                                Toast.makeText(getApplicationContext(), "" + Helper.getServerSuccessMessage(res), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {

                            }

                        }

                        @Override
                        public void onFailure(final String err) {
                            Helper.showToast(BuyerMyProfileActivity.this, "" + Helper.getServerMessage(err));
                        }


                    });
        } else {
            Helper.showToast(BuyerMyProfileActivity.this, getResources().getString(R.string.lbl_no_internet));
        }
    }

    public String resetPWDBody() {
        ChangePWDModel changePWDModel = new ChangePWDModel();
        changePWDModel.setOldPassword(edOldPWD.getText().toString());
        changePWDModel.setPassword(edNewPWD.getText().toString());
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();
        Type type = new TypeToken<ChangePWDModel>() {
        }.getType();
        return gson.toJson(changePWDModel, type);
    }

    private void fun_webAPIforSaveChanes() {
        if (new Helper().isNetworkAvailable(getApplication())) {

            String webAPI = Helper.getSharedPrefValStr(BuyerMyProfileActivity.this, Constants.sharedPref.s_BASE_URL)
                    .concat(Constants.webAPI.saveChanges);
            String requestBody = saveChangesBody();
            NetworkCommunicationHelper networkCommunicationHelper = new NetworkCommunicationHelper();

            networkCommunicationHelper.sendUserPostRequest(getApplication(), webAPI, requestBody,
                    new NetworkCommunicationHelper.OnResponseReceived() {
                        @Override
                        public void onSuccess(final String res) {
                            try {
                                Toast.makeText(getApplicationContext(), "" + Helper.getServerSuccessMessage(res), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {

                            }

                        }

                        @Override
                        public void onFailure(final String err) {
                            Helper.showToast(BuyerMyProfileActivity.this, "" + Helper.getServerMessage(err));
                        }


                    });
        } else {
            Helper.showToast(BuyerMyProfileActivity.this, getResources().getString(R.string.lbl_no_internet));
        }
    }

    public String saveChangesBody() {

        RegisterAccountModel registerAccountModel = new RegisterAccountModel();
        registerAccountModel.setCityId(mSelectedCityID);
        registerAccountModel.setEmail(edEmail.getText().toString());
        registerAccountModel.setFirstName(edFirstName.getText().toString());
        registerAccountModel.setLastName(edLastName.getText().toString());
        registerAccountModel.setLocalityId(mSelectedLocalityID);
        registerAccountModel.setAadhaarCardNo(edAdharNumber.getText().toString());
        registerAccountModel.setOccupation("service");
        registerAccountModel.setStateId(mSelectedStateID);
        registerAccountModel.setCountryId(1);
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();
        Type type = new TypeToken<RegisterAccountModel>() {
        }.getType();
        return gson.toJson(registerAccountModel, type);
    }

    public void setDateFromCalender() {
        int mYear, mMonth, mDay;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        edDOB.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void fetchStates(int mPosition) {

        String getBody = "countryId="+mPosition;
        String webAPI = Helper.getSharedPrefValStr(BuyerMyProfileActivity.this, Constants.sharedPref.s_BASE_URL)
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

                            Type type = new TypeToken<List<State>>() {
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
                R.layout.simple_spinner_white_dropdown,stateList));
    }

    private void addFirstState() {
        State firstStae = new State();
        firstStae.setId(0);
        firstStae.setName(getResources().getString(R.string.state));
        firstStae.setSlug(getResources().getString(R.string.state));
        new StateModel().addState(getApplication(),firstStae);
    }

    ///// for State Adapter

    public class StateAdapter extends ArrayAdapter<State> {
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
            View row=inflater.inflate(R.layout.simple_spinner_white_dropdown, parent, false);
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
            View row=inflater.inflate(R.layout.simple_spinner_white_dropdown, parent, false);
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
            View row=inflater.inflate(R.layout.simple_spinner_white_dropdown, parent, false);
            TextView label=(TextView)row.findViewById(R.id.idText);
            label.setText(""+mList.get(position).getName());



            return row;
        }
    }
    private void chnageLocality(int mPosition) {
        String getBody = "cityId="+mPosition;
        String webAPI = Helper.getSharedPrefValStr(BuyerMyProfileActivity.this, Constants.sharedPref.s_BASE_URL)
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
                R.layout.simple_spinner_white_dropdown,mcityList));
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
        String webAPI = Helper.getSharedPrefValStr(BuyerMyProfileActivity.this, Constants.sharedPref.s_BASE_URL)
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
                R.layout.simple_spinner_white_dropdown,mcityList));
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

    private void setOccupation(int
                               mPosition){


        dbModel.Locality firstLocality;
        ArrayList<Locality> mcityList = new ArrayList<Locality>();
        try{
                            firstLocality= new dbModel.Locality();
                            firstLocality.setId(0);
                            firstLocality.setName("OCCUPATION");
                            firstLocality.setSlug(getResources().getString(R.string.locality));
            mcityList.add(firstLocality);

                      firstLocality = new dbModel.Locality();
                            firstLocality.setId(1);
                            firstLocality.setName("STUDENT");
                            firstLocality.setSlug(getResources().getString(R.string.locality));
            mcityList.add(firstLocality);
                             firstLocality = new dbModel.Locality();
                            firstLocality.setId(2);
                            firstLocality.setName("RETAILER");
                            firstLocality.setSlug(getResources().getString(R.string.locality));
            mcityList.add(firstLocality);

                             firstLocality = new dbModel.Locality();
                            firstLocality.setId(3);
                            firstLocality.setName("EMPLOYEE");
                            firstLocality.setSlug(getResources().getString(R.string.locality));
            mcityList.add(firstLocality);


                            spOccupation.setAdapter(new LocalityAdapter(getApplicationContext(),
                                    R.layout.simple_spinner_white_dropdown,mcityList));
                        }
                        catch (Exception e){

                        }



    }

}
