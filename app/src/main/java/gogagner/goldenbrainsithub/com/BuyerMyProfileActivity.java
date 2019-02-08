package gogagner.goldenbrainsithub.com;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;
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
import java.util.Calendar;

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

    Spinner spState, spCity, spLocality;
    RelativeLayout editProfileChangePwd, editProfilChangeImg, editProfileDetails;
    int sizeInDp = 12;
    //
    Button btnResetPwd, btnSaveChanges;

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
        registerAccountModel.setCityId(1);
        registerAccountModel.setEmail(edEmail.getText().toString());
        registerAccountModel.setFirstName(edFirstName.getText().toString());
        registerAccountModel.setLastName(edLastName.getText().toString());
        registerAccountModel.setLocalityId(2);
        registerAccountModel.setAadhaarCardNo(edAdharNumber.getText().toString());
        registerAccountModel.setOccupation("service");
        registerAccountModel.setStateId(1);
        registerAccountModel.setStateId(1);
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
}
