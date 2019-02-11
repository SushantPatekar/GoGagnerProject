package gogagner.goldenbrainsithub.com;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import gogagner.goldenbrainsithub.model.MainCategoryModel;
import networkcommunication.NetworkCommunicationHelper;
import slidermenu.BaseDrawerActivity;
import utility.Constants;
import utility.Helper;

public class BuyerSellerDashBoardActivity extends BaseDrawerActivity implements View.OnClickListener {
    public static String TAG = BuyerSellerDashBoardActivity.class.getSimpleName() ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_seller_dashboard_layout);
        initView();
        fetchCategoryData();
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


    private void fetchCategoryData(){
        try{

            String webAPI = Helper.getSharedPrefValStr(getApplicationContext(), Constants.sharedPref.s_BASE_URL)
                    .concat(Constants.webAPI.getCategory);

            NetworkCommunicationHelper networkCommunicationHelper = new NetworkCommunicationHelper();
            networkCommunicationHelper.sendSynchronousGetRequest(getApplication(), webAPI,
                    new NetworkCommunicationHelper.OnResponseReceived() {
                        @Override
                        public void onSuccess(final String res) {

                            try{

                                String responseString = Helper.fetchMainResponseasObject(res);
                                /*Gson gson = new GsonBuilder()
                                        .serializeNulls()
                                        .create();

                                Type type = new TypeToken<MainCategoryModel>() {
                                }.getType();
                                MainCategoryModel mainCategoryModel = gson.fromJson(responseString, type);

                                Log.i(TAG,"This is object "+mainCategoryModel.getStatus());*/


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

        catch (Exception e){

        }
    }
}
