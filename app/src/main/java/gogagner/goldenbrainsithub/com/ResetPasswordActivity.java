package gogagner.goldenbrainsithub.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import dbModel.User;
import dbModel.UserModel;
import gogagner.goldenbrainsithub.com.R;

import utility.Constants;
import utility.Helper;
import utility.NetworkCommunicationHelper;
import webAPIModel.LoginAccountModel;

public class ResetPasswordActivity  extends Activity implements View.OnClickListener{
    public static String TAG = ResetPasswordActivity.class.getSimpleName();
    Button btnResetPassword;
    EditText edConfirmPassword,edNewPassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.setFullScreen(ResetPasswordActivity.this);
        setContentView(R.layout.activity_reset_password);
        initView();
    }

    public void initView(){
        try
        {
            edConfirmPassword = (EditText) findViewById(R.id.edConfirmPassword);
            edNewPassword = (EditText) findViewById(R.id.edNewPassword);

            edConfirmPassword.setHint(getResources().getString(R.string.lbl_cofirm_password).toUpperCase());
            edNewPassword.setHint(getResources().getString(R.string.lbl_new_password).toUpperCase());

            btnResetPassword = (Button) findViewById(R.id.btnResetPwd);
            btnResetPassword.setOnClickListener(this);


        }
        catch (Exception e){

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnResetPwd:
                if(validate()){
                if(new Helper().isNetworkAvailable(getApplication()))   {

                    String webAPI = Helper.getSharedPrefValStr(ResetPasswordActivity.this, Constants.sharedPref.s_BASE_URL)
                            .concat(Constants.webAPI.apiResetPWD);
                    String requestBody = resetPWDBody();
                    NetworkCommunicationHelper networkCommunicationHelper = new NetworkCommunicationHelper();

                    networkCommunicationHelper.sendPostRequest(getApplication(), webAPI, requestBody,
                            new NetworkCommunicationHelper.OnResponseReceived() {
                                @Override
                                public void onSuccess(final String res) {

                                    startActivity(new Intent(
                                            getApplication(),
                                            LoginActivity.class
                                    ));
                                    finish();

                                  /*  //TODO check for correct OTP
                                    Helper.showToast(ResetPasswordActivity.this, res);
                                    Helper.updatedSharedPrefValBoolean(ResetPasswordActivity.this,
                                            Constants.login.isLoginSuccess, true);
                                    Helper.showToast(VerifyOTPActivity.this, getResources().getString(
                                            R.string.login_success
                                    ));
                                    startActivity(new Intent(
                                            VerifyOTPActivity.this,
                                            BuyerSellerDashBoardActivity.class
                                    ));
                                    finish();*/

                                }

                                @Override
                                public void onFailure(final String err) {
                                    Helper.showToast(getApplication(), getResources().getString(R.string.lbl_error_valid_otp));
                                }


                            });
                }
                else {
                    Helper.showToast(ResetPasswordActivity.this,getResources().getString(R.string.lbl_no_internet));
                }
                }
                else {

                }

              /*  startActivity(new Intent(
                        ResetPasswordActivity.this,
                        LoginActivity.class
                ));
                finish();*/

                break;


        }
    }

    public boolean validate(){
        if (edNewPassword.getText().toString().matches("") ||
                edConfirmPassword.getText().toString().matches("") ) {
            Helper.showToast(this, getString(R.string.enter_valid_pwd));
            return false;
        } else if (! edConfirmPassword.getText().toString().matches(edNewPassword.getText().toString())){
            Helper.showToast(this, getString(R.string.enter_valid_pwd_mismatch));
            return false;
        } else {
            return true;
        }
    }

    public String resetPWDBody(){
        String username = Helper.getSharedPrefValStr(this, Constants.sharedPref.userName);
       String mobileNumber = Helper.getSharedPrefValStr(ResetPasswordActivity.this,
                Constants.sharedPref.userName);
        LoginAccountModel loginAccountModel = new LoginAccountModel();
        loginAccountModel.setUsername(""+mobileNumber);
        loginAccountModel.setPassword(edNewPassword.getText().toString());
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();
        Type type = new TypeToken<LoginAccountModel>() {
        }.getType();
        return gson.toJson(loginAccountModel, type);
    }

}
