package gogagner.goldenbrainsithub.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import utility.Constants;
import utility.Helper;
import networkcommunication.NetworkCommunicationHelper;
import webAPIModel.LoginAccountModel;

public class LoginActivity extends Activity implements View.OnClickListener{
    public static String TAG = LoginActivity.class.getSimpleName();
    Button btnLogin;
    EditText edMobileNumber,edPassword;
    TextView tvCreateAccount,tvForgotPassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.setFullScreen(LoginActivity.this);
        setContentView(R.layout.activity_login_layout);
        initView();
    }

    public void initView(){
        try
        {
            edMobileNumber = (EditText) findViewById(R.id.edMobileNumber);
            edPassword = (EditText) findViewById(R.id.edPassword);

            edMobileNumber.setHint(getResources().getString(R.string.lbl_mobile_number).toUpperCase());
            edPassword.setHint(getResources().getString(R.string.lbl_password).toUpperCase());

            btnLogin = (Button) findViewById(R.id.btnLogin);
            btnLogin.setOnClickListener(this);

            tvCreateAccount = (TextView) findViewById(R.id.tvCreateAccount);
            String first = "Don't have an account with GoGagner ? ";
            String next = " <font color='#EE0000'>Create Account</font>";
            tvCreateAccount.setText(Html.fromHtml(first + next));

            tvCreateAccount.setOnClickListener(this);

            tvForgotPassword = (TextView) findViewById(R.id.tvForgotPwd);
            tvForgotPassword.setOnClickListener(this);
        }
        catch (Exception e){

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                if (validateUserPWD()) {
                    if (new Helper().isNetworkAvailable(getApplication())) {
                        String webAPI = Helper.getSharedPrefValStr(LoginActivity.this, Constants.sharedPref.s_BASE_URL)
                                .concat(Constants.webAPI.apiLogin);
                        String requestBody = generateLoginBody();
                        NetworkCommunicationHelper networkCommunicationHelper = new NetworkCommunicationHelper();

                        //For Access Token
                        networkCommunicationHelper.sendPostAccessTokenRequest(getApplication(), webAPI, requestBody,
                                new NetworkCommunicationHelper.OnResponseReceived() {
                                    @Override
                                    public void onSuccess(final String res) {

                                        Helper.updatedSharedPrefValBoolean(LoginActivity.this,
                                                Constants.login.isLoginSuccess, true);
                                        Helper.updateSharedPrefValStr(LoginActivity.this,
                                                Constants.sharedPref.userName,edMobileNumber.getText().toString());
                                        Helper.showToast(LoginActivity.this, getResources().getString(
                                                R.string.login_success
                                        ));
                                        startActivity(new Intent(
                                                LoginActivity.this,
                                                BuyerSellerDashBoardActivity.class
                                        ));
                                        finish();

                                    }

                                    @Override
                                    public void onFailure(final String err) {
                                        Helper.showToast(LoginActivity.this, ""+Helper.getServerErroMessage(err));

                                        switch (Helper.getServerErroCode(err)) {
                                            case Constants.serverErroCode.code_406:
                                                startActivity(new Intent(
                                                        LoginActivity.this,
                                                        VerifyOTPActivity.class
                                                ).putExtra(Intent.EXTRA_STREAM, Constants.webAPI.signFlag));
                                                finish();
                                                break;
                                            case Constants.serverErroCode.code_409:
                                                break;

                                        }
                                       /* */
                                    }
                                });
                    } else
                        Helper.showToast(getApplication(), getResources().getString(R.string.lbl_no_internet));
                } else {

                }

                break;

            case R.id.tvCreateAccount :
                startActivity(new Intent(
                        LoginActivity.this,
                        SignupActivity.class
                ));
                finish();
                break;

            case R.id.tvForgotPwd:
                startActivity(new Intent(
                        LoginActivity.this,
                        RequestOTP.class
                ));
                finish();
                break;
        }
    }


    public String generateLoginBody(){
        LoginAccountModel loginAccountModel = new LoginAccountModel();
        loginAccountModel.setUsername(edMobileNumber.getText().toString());
        loginAccountModel.setPassword(edPassword.getText().toString());
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();
        Type type = new TypeToken<LoginAccountModel>() {
        }.getType();
        return gson.toJson(loginAccountModel, type);
    }


    public boolean validateUserPWD(){
        if (TextUtils.isEmpty(edMobileNumber.getText().toString().trim())) {
            Helper.showToast(this, getString(R.string.enter_valid_email_mobilenumber));
            return false;
        } else if (TextUtils.isEmpty(edPassword.getText().toString().trim())){
            Helper.showToast(this, getString(R.string.enter_valid_pwd));
            return false;
        }
        else
            return true;

       /* if (edMobileNumber.getText().toString().matches("") ) {
            Helper.showToast(this, getString(R.string.enter_valid_email_mobilenumber));
            return false;
        } else if (edPassword.getText().toString().matches("")){
            Helper.showToast(this, getString(R.string.enter_valid_pwd));
            return false;
        } else {
            return true;
        }*/


    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Helper.exitPopUp(LoginActivity.this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
