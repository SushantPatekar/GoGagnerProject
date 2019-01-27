package gogagner.goldenbrainsithub.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

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
int size = 1;
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

        autoScrollonOTP();
    }

    private void autoScrollonOTP() {
        otp_a.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(otp_a.getText().toString().length()==size)     //size as per your requirement
                {
                    otp_b.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        otp_b.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(otp_b.getText().toString().length()==size)     //size as per your requirement
                {
                    otp_c.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        otp_c.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(otp_c.getText().toString().length()==size)     //size as per your requirement
                {
                    otp_d.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        otp_d.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(otp_d.getText().toString().length()==size)     //size as per your requirement
                {
                    otp_e.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        otp_e.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(otp_e.getText().toString().length()==size)     //size as per your requirement
                {
                    otp_f.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        otp_f.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(otp_b.getText().toString().length()==size)     //size as per your requirement
                {
                    Helper.hideKeyboard(VerifyOTPActivity.this);
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
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
                                      //userDetails
                                      try {
                                          JSONObject main = new JSONObject(res);
                                          JSONObject j_response = main.getJSONObject("response");
                                          String messages = j_response.getString("messages");
                                          if(j_response.has("userDetails")){
                                         // if(messages.matches("OTP verified sucessfully.")){
                                              startActivity(new Intent(
                                                      VerifyOTPActivity.this,
                                                      LoginActivity.class
                                              ));
                                          }
                                          else {
                                              startActivity(new Intent(
                                                      VerifyOTPActivity.this,
                                                      ResetPasswordActivity.class
                                              ));
                                          }

                                      }
                                      catch (Exception e){

                                      }


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
                                        Helper.showToast(VerifyOTPActivity.this, ""+Helper.getServerErroMessage(err));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), RequestOTP.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {

    }

}
