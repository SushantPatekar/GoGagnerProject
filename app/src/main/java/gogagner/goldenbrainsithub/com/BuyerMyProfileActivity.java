package gogagner.goldenbrainsithub.com;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import slidermenu.BaseDrawerActivity;

public class BuyerMyProfileActivity  extends BaseDrawerActivity implements View.OnClickListener{

    ImageView imvPannel_one,imvPannel_two,imvPannel_three;
    ImageView imline_one,imline_two,imline_three;
    EditText edOldPWD,edNewPWD,edCOnfirmPWD;
RelativeLayout editProfileChangePwd,editProfilChangeImg;
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
        }
    }
}
