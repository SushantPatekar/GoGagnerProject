package gogagner.goldenbrainsithub.com;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import slidermenu.BaseDrawerActivity;
import utility.Constants;
import utility.Helper;
import networkcommunication.NetworkCommunicationHelper;
import webAPIModel.ChangePWDModel;

public class BuyerMyProfileActivity  extends BaseDrawerActivity implements View.OnClickListener{

    ImageView imvPannel_one,imvPannel_two,imvPannel_three;
    ImageView imline_one,imline_two,imline_three;
    EditText edOldPWD,edNewPWD,edCOnfirmPWD;
RelativeLayout editProfileChangePwd,editProfilChangeImg;
//
    Button btnResetPwd ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_myprofile);
        initView();
    }

    private void initView(){
        editProfileChangePwd =(RelativeLayout) findViewById(R.id.editProfileChangePwd);
        editProfilChangeImg =(RelativeLayout) findViewById(R.id.editProfilChangeImg);

        editProfilChangeImg.setVisibility(View.VISIBLE);
        editProfileChangePwd.setVisibility(View.GONE);
        edOldPWD =(EditText) findViewById(R.id.edOldPassword);
        edNewPWD = (EditText) findViewById(R.id.edNewPassword);
        edCOnfirmPWD = (EditText) findViewById(R.id.edConfirmPassword);

        edCOnfirmPWD.setHint(getResources().getString(R.string.lbl_cofirm_password).toUpperCase());
        edNewPWD.setHint(getResources().getString(R.string.lbl_new_password).toUpperCase());
        edOldPWD.setHint(getResources().getString(R.string.lbl_oldPwd).toUpperCase());

        imvPannel_one= (ImageView)findViewById(R.id.imvPannel_one);
        imvPannel_two= (ImageView)findViewById(R.id.imvPannel_two);
        imvPannel_three= (ImageView)findViewById(R.id.imvPannel_three);

        imline_one= (ImageView)findViewById(R.id.imline_one);
        imline_two= (ImageView)findViewById(R.id.imvline_two);
        imline_three= (ImageView)findViewById(R.id.imvline_three);

        imline_one.setVisibility(View.INVISIBLE);
        imline_two.setVisibility(View.VISIBLE);
        imline_three.setVisibility(View.INVISIBLE);

        imvPannel_one.setBackground(getDrawable(R.drawable.selector_row_buyer_menu));
        imvPannel_two.setBackground(getDrawable(R.drawable.bg_edittext_icon));
        imvPannel_three.setBackground(getDrawable(R.drawable.selector_row_buyer_menu));

        imvPannel_one.setImageResource(R.drawable.password);
        imvPannel_two.setImageResource(R.drawable.profile);
        imvPannel_three.setImageResource(R.drawable.edit_pencil);

        imvPannel_one.setOnClickListener(this);
        imvPannel_two.setOnClickListener(this);
        imvPannel_three.setOnClickListener(this);

        //Event related
        btnResetPwd = (Button) findViewById(R.id.btnResetPwd);
        btnResetPwd.setOnClickListener(this);
    }

    @Override
    public void onDrawerProfileClick(View view) {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.imvPannel_one:
                imvPannel_one.setBackground(getDrawable(R.drawable.bg_edittext_icon));
                imvPannel_two.setBackground(getDrawable(R.drawable.selector_row_buyer_menu));
                imvPannel_three.setBackground(getDrawable(R.drawable.selector_row_buyer_menu));
                imline_one.setVisibility(View.VISIBLE);
                imline_two.setVisibility(View.INVISIBLE);
                imline_three.setVisibility(View.INVISIBLE);

                editProfilChangeImg.setVisibility(View.GONE);
                editProfileChangePwd.setVisibility(View.VISIBLE);
                break;
            case R.id.imvPannel_two:
                imvPannel_one.setBackground(getDrawable(R.drawable.selector_row_buyer_menu));
                imvPannel_two.setBackground(getDrawable(R.drawable.bg_edittext_icon));
                imvPannel_three.setBackground(getDrawable(R.drawable.selector_row_buyer_menu));
                imline_one.setVisibility(View.INVISIBLE);
                imline_two.setVisibility(View.VISIBLE);
                imline_three.setVisibility(View.INVISIBLE);
                editProfilChangeImg.setVisibility(View.VISIBLE);
                editProfileChangePwd.setVisibility(View.GONE);
                break;
            case R.id.imvPannel_three:
                imvPannel_one.setBackground(getDrawable(R.drawable.selector_row_buyer_menu));
                imvPannel_two.setBackground(getDrawable
                        (R.drawable.selector_row_buyer_menu));
                imvPannel_three.setBackground(getDrawable(R.drawable.bg_edittext_icon));
                imline_one.setVisibility(View.INVISIBLE);
                imline_two.setVisibility(View.INVISIBLE);
                imline_three.setVisibility(View.VISIBLE);
                break;

            case R.id.btnResetPwd:
                fun_ChnagePWD();
                break;
        }
    }

    private void fun_ChnagePWD(){
        try {
            if(changePWDValidate())
            {
               fun_webAPIforChangePWD();
            }
        }
        catch (Exception e){

        }
    }

    private boolean changePWDValidate(){
        boolean isValid = false;
        String oldPWD = edOldPWD.getText().toString().trim();
        String newPWD = edNewPWD.getText().toString().trim();
        String confirmPWD = edCOnfirmPWD.getText().toString().trim();
          if (oldPWD.matches("")) {
            isValid = false;
            Helper.showToast(this, getString(R.string.lbl_empty_oldPwd));
        }
        else if (newPWD.matches("")) {
              isValid = false;
            Helper.showToast(this, getString(R.string.lbl_empty_newPwd));
        }
          else if (confirmPWD.matches("")) {
              isValid = false;
              Helper.showToast(this, getString(R.string.lbl_empty_newPwd));
          }
        else if(!newPWD.matches(confirmPWD)){
              isValid = false;
              Helper.showToast(this, getString(R.string.lbl_pwd_mismatch));

          }
        else if(newPWD.matches(oldPWD) || oldPWD.matches(confirmPWD)){
              Helper.showToast(this, getString(R.string.lbl_old_newPwd));
            return false;
          }
        else {
              isValid = true;
        }
        return  isValid;
    }
    
    private void fun_webAPIforChangePWD(){
        if(new Helper().isNetworkAvailable(getApplication()))   {

            String webAPI = Helper.getSharedPrefValStr(BuyerMyProfileActivity.this, Constants.sharedPref.s_BASE_URL)
                    .concat(Constants.webAPI.changePassword);
            String requestBody = resetPWDBody();
            NetworkCommunicationHelper networkCommunicationHelper = new NetworkCommunicationHelper();

            networkCommunicationHelper.sendUserPostRequest(getApplication(), webAPI, requestBody,
                    new NetworkCommunicationHelper.OnResponseReceived() {
                        @Override
                        public void onSuccess(final String res) {


                        }

                        @Override
                        public void onFailure(final String err) {
                            Helper.showToast(BuyerMyProfileActivity.this, ""+Helper.getServerErroMessage(err));
                        }


                    });
        }
        else {
            Helper.showToast(BuyerMyProfileActivity.this,getResources().getString(R.string.lbl_no_internet));
        }
    }

    public String resetPWDBody(){
        ChangePWDModel changePWDModel = new ChangePWDModel();
        changePWDModel.setOldPassword(edOldPWD.getText().toString());
        changePWDModel.setPassword(edNewPWD.getText().toString());
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();
        Type type = new TypeToken<ChangePWDModel>() {
        }.getType();
        return gson.toJson( changePWDModel, type);
    }
}
