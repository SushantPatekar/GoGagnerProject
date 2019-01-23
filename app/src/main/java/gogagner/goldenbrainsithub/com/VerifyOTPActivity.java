package gogagner.goldenbrainsithub.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import dbModel.User;
import dbModel.UserModel;
import utility.Constants;
import utility.Helper;
import utility.NetworkCommunicationHelper;
import webAPIModel.LoginAccountModel;
import webAPIModel.VerifyOTPModel;

public class VerifyOTPActivity extends Activity implements View.OnClickListener {
    TextView tvResendOtp;
    TextView tvOtpTimer;
    Button btnSubmitOTP;

    private EditText otp_a, otp_b, otp_c, otp_d, otp_e, otp_f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.setFullScreen(VerifyOTPActivity.this);
        Helper.setFullScreen(VerifyOTPActivity.this);
        setContentView(R.layout.activity_forgot_password_layout);
        initView();
    }

    public void initView() {
        tvResendOtp = (TextView) findViewById(R.id.tvResendOtp);
        String first = "Haven't recieved OTP ? ";
        String next = "<font color='#EE0000'>Resend</font>";
        tvResendOtp.setText(Html.fromHtml(first + next));
        tvOtpTimer = (TextView) findViewById(R.id.tvOtpTimer);

        otp_a = (EditText) findViewById(R.id.otp_a);
        otp_b = (EditText) findViewById(R.id.otp_b);
        otp_c = (EditText) findViewById(R.id.otp_c);
        otp_d = (EditText) findViewById(R.id.otp_d);
        otp_e = (EditText) findViewById(R.id.otp_e);
        otp_f = (EditText) findViewById(R.id.otp_f);

        btnSubmitOTP = (Button) findViewById(R.id.btnSubmitOTP);
        btnSubmitOTP.setOnClickListener(this);

        tvResendOtp.setOnClickListener(this);
        startTimer();
    }


    public void startTimer() {
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvOtpTimer.setText(getResources().getString(R.string.lbl_otp_reminder) + " " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                tvOtpTimer.setText("Please Try Again");
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmitOTP:

              if(new Helper().isNetworkAvailable(getApplication())){
                  if (isvalidateOTPEmpty()) {
                      Helper.showToast(VerifyOTPActivity.this, getResources().getString(R.string.lbl_enter_valid_otp));
                  } else {
                      String webAPI = Helper.getSharedPrefValStr(VerifyOTPActivity.this, Constants.sharedPref.s_BASE_URL)
                              .concat(Constants.webAPI.verifyOTP);
                      String requestBody = generateVerifyOTPBody();
                      NetworkCommunicationHelper networkCommunicationHelper = new NetworkCommunicationHelper();

                      networkCommunicationHelper.sendPostRequest(getApplication(), webAPI, requestBody,
                              new NetworkCommunicationHelper.OnResponseReceived() {
                                  @Override
                                  public void onSuccess(final String res) {

                                      //TODO check for correct OTP
                                     /* Helper.updatedSharedPrefValBoolean(VerifyOTPActivity.this,
                                              Constants.login.isLoginSuccess, true);*/
                                      startActivity(new Intent(
                                              VerifyOTPActivity.this,
                                              ResetPasswordActivity.class
                                      ));
                                      finish();

                                  }

                                  @Override
                                  public void onFailure(final String err) {
                                      Helper.showToast(VerifyOTPActivity.this, ""+Helper.getServerErroMessage(err));

                                  }


                              });


                  }
              }
              else
                  Helper.showToast(VerifyOTPActivity.this,getResources().getString(R.string.lbl_no_internet));
                break;

            case R.id.tvResendOtp:

                if(new Helper().isNetworkAvailable(getApplication())){

                        String webAPI = Helper.getSharedPrefValStr(VerifyOTPActivity.this, Constants.sharedPref.s_BASE_URL)
                                .concat(Constants.webAPI.resendOTP);
                        String requestBody = resendOTPBody();
                        NetworkCommunicationHelper networkCommunicationHelper = new NetworkCommunicationHelper();

                        networkCommunicationHelper.sendPostRequest(getApplication(), webAPI, requestBody,
                                new NetworkCommunicationHelper.OnResponseReceived() {
                                    @Override
                                    public void onSuccess(final String res) {



                                    }

                                    @Override
                                    public void onFailure(final String err) {
                                        Helper.showToast(VerifyOTPActivity.this, err);
                                    }


                                });


                    }

                else
                    Helper.showToast(VerifyOTPActivity.this,getResources().getString(R.string.lbl_no_internet));
                break;
        }
    }


    public String generateVerifyOTPBody() {
        String mobileNumber = Helper.getSharedPrefValStr(VerifyOTPActivity.this,
                Constants.sharedPref.userName);
        VerifyOTPModel verifyOTPModel = new VerifyOTPModel();
        verifyOTPModel.setOtp(getOTP());
        verifyOTPModel.setPage(getIntent().getStringExtra(Intent.EXTRA_STREAM));
        verifyOTPModel.setMobile(mobileNumber);

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();
        Type type = new TypeToken<VerifyOTPModel>() {
        }.getType();
        return gson.toJson(verifyOTPModel, type);
    }

    public String resendOTPBody() {
        String mobileNumber = Helper.getSharedPrefValStr(VerifyOTPActivity.this,
                Constants.sharedPref.userName);
        VerifyOTPModel verifyOTPModel = new VerifyOTPModel();
        String username = Helper.getSharedPrefValStr(this, Constants.sharedPref.userName);
        verifyOTPModel.setMobile(mobileNumber);

        // verifyOTPModel.setMobile("7506938193");
        //getSavedNumberfromUserDATA
        //verifyOTPModel.setMobile();

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();
        Type type = new TypeToken<VerifyOTPModel>() {
        }.getType();
        return gson.toJson(verifyOTPModel, type);
    }


    public String getOTP() {

        String otp = null;
        otp = otp_a.getText().toString() + otp_b.getText().toString() +
                otp_c.getText().toString() + otp_d.getText().toString() +
                otp_e.getText().toString()+otp_f.getText().toString();

        return otp;
    }

    public boolean isvalidateOTPEmpty() {
        if (otp_a.getText().toString().matches("") ||
                otp_b.getText().toString().matches("") ||
                otp_c.getText().toString().matches("") ||
                otp_a.getText().toString().matches("") ||
                otp_b.getText().toString().matches("") ||
                otp_c.getText().toString().matches("")
        ) {
            return true;
        } else {
            return false;
        }
    }

    public void SubMitOTP(String Message){
Helper.showToast(VerifyOTPActivity.this,""+Message);
    }
}
