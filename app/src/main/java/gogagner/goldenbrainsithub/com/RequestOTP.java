package gogagner.goldenbrainsithub.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
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
import utility.NetworkCommunicationHelper;
import webAPIModel.LoginAccountModel;
import webAPIModel.ResendOTPModel;

public class RequestOTP extends Activity implements View.OnClickListener{
    public static String TAG = RequestOTP.class.getSimpleName();
    Button btnRegisterOTP;
    EditText edRegisterOTP;
    TextView tvRegisterOTP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.setFullScreen(RequestOTP.this);
        setContentView(R.layout.activity_request_otp);
        initView();
    }

    public void initView(){
        try
        {
            edRegisterOTP = (EditText) findViewById(R.id.edRegisterOTP);
            tvRegisterOTP = (TextView) findViewById(R.id.tvRegisterOTP);
            btnRegisterOTP = (Button) findViewById(R.id.btnRegisterOTP) ;

            edRegisterOTP.setHint(getResources().getString(R.string.lbl_register_otp).toUpperCase());

            btnRegisterOTP.setOnClickListener(this);

            ;
        }
        catch (Exception e){

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegisterOTP:


                if(new Helper().isNetworkAvailable(getApplication())){
                    if((!TextUtils.isEmpty(edRegisterOTP.getText().toString().trim()))){
                        String webAPI = Helper.getSharedPrefValStr(RequestOTP.this, Constants.sharedPref.s_BASE_URL)
                                .concat(Constants.webAPI.forgotPassword);
                        String requestBody =  generateForgotPwdBody();
                        NetworkCommunicationHelper networkCommunicationHelper = new NetworkCommunicationHelper();

                        networkCommunicationHelper.sendPostRequest(getApplication(),webAPI,requestBody,
                                new NetworkCommunicationHelper.OnResponseReceived() {
                                    @Override
                                    public void onSuccess(final String res) {
                                        startActivity(new Intent(
                                                RequestOTP.this,
                                                VerifyOTPActivity.class
                                        ).putExtra(Intent.EXTRA_STREAM, Constants.webAPI.forgotPWDFlag));
                                        finish();

                                    }

                                    @Override
                                    public void onFailure(final String err) {
                                        Helper.showToast(RequestOTP.this, ""+Helper.getServerErroMessage(err));

                                    }
                                });
                    }
                    else {
                        Helper.showToast(getApplication(),getResources().getString(R.string.enter_valid_email_mobilenumber));
                    }
                }else
                    Helper.showToast(getApplication(),getResources().getString(R.string.lbl_no_internet));



                break;


        }
    }



    public String generateForgotPwdBody(){
            ResendOTPModel resendOTPModel = new ResendOTPModel();
            resendOTPModel.setUsername(edRegisterOTP.getText().toString());
            Gson gson = new GsonBuilder()
                    .serializeNulls()
                    .create();
            Type type = new TypeToken<ResendOTPModel>() {
            }.getType();
            return gson.toJson(resendOTPModel, type);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}
