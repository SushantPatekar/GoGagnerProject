package gogagner.goldenbrainsithub.com;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;


import slidermenu.BaseDrawerActivity;
import utility.Helper;

public class BuyerSellerDashBoardActivity extends BaseDrawerActivity implements View.OnClickListener {
    public static String TAG = BuyerSellerDashBoardActivity.class.getSimpleName() ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_seller_dashboard_layout);
        initView();
    }


    public void initView(){
        //setTitleTextView(getString(R.string.app_name));
      //  setToolbarBg(getDrawable(R.drawable.purple_gradient));
    }
    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDrawerProfileClick(View view) {

    }



    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Helper.exitPopUp(BuyerSellerDashBoardActivity.this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
