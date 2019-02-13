package slidermenu;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import dbModel.Locality;
import dbModel.LocalityModel;
import dbModel.State;
import dbModel.StateModel;
import dbModel.User;
import dbModel.UserModel;
import gogagner.goldenbrainsithub.com.BuyerMyProfileActivity;
import gogagner.goldenbrainsithub.com.BuyerPostAddActitivity;
import gogagner.goldenbrainsithub.com.BuyerSellerDashBoardActivity;
import gogagner.goldenbrainsithub.com.LoginActivity;
import gogagner.goldenbrainsithub.com.R;
import gogagner.goldenbrainsithub.com.ResetPasswordActivity;
import gogagner.goldenbrainsithub.com.SignupActivity;
import gogagner.goldenbrainsithub.model.ChildCategoryModel;
import gogagner.goldenbrainsithub.model.MainCategoryModel;
import networkcommunication.NetworkCommunicationHelper;
import utility.Constants;
import utility.Helper;


public abstract class BaseDrawerActivity extends AppCompatActivity implements DrawerFragment.DrawerEventsCallback, DrawerFragment.FragmentDrawerListener {

    protected Toolbar mToolbar;
    private AppBarLayout appBar;

    public static final String TAG = BaseDrawerActivity.class.getSimpleName();
    private DrawerLayout drawerLayout;
    private DrawerFragment drawerFragment;
    private TextView tvToolbarTitle;
    private String strMobileNumber;
    private ImageView ivToolbarAlertOrNotif;
    private int optionClickedPosition = -1;

    private final int BUYER_HOME = 0;
    private final int BUYER_MY_PROFILE = 5;
    private final int BUYER_POST_ADD = 7;
    private final int LOG_OUT = 16;
    private final int E_PURSE = 2;
    private final int HELP = 3;
    private final int SETTINGS = 4;
    private static final int CHAT = 6;
    private static final int PARKING = 7;



private Spinner SpinnerLocation;
private User user;
private EditText medsearch;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base_drawer);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fl_drawer_activity_container);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        user =fetchUser();
        //clear
        configureToolBar();
        //clear

        //fetch Category & Add
        fetchCategoryData();
        setNavigationDrawer();
    }

    protected void configureToolBar() {
        appBar = findViewById(R.id.appBar);
        mToolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setContentInsetsRelative(32, 0);
        mToolbar.setContentInsetsAbsolute(32, 0);
        // setting up the listeners
        final LayoutInflater layoutInflater = LayoutInflater.from(this);


        //View toolbarLayout = layoutInflater.inflate(R.layout.base_drawer_toolbar_layout, null);
        View toolbarLayout = layoutInflater.inflate(R.layout.custom_base_drawer_toolbar_layout, null);

       // tvToolbarTitle = (TextView) toolbarLayout.findViewById(R.id.tv_toolbar_title);
        medsearch = (EditText) toolbarLayout.findViewById(R.id.edsearch);

        SpinnerLocation = (Spinner) findViewById(R.id.spLocation);
        updateSpinnerLocation(SpinnerLocation,user);
        // tweaking action bar settings
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(toolbarLayout);
        }
      //  setTitleTextView(getString(R.string.app_name));
    }

    public void setTitleTextView(String title) {
        tvToolbarTitle.setText(title);
    }

    public void setToolbarBg(Drawable resId) {
        mToolbar.setBackground(resId);
        appBar.setStateListAnimator(null);
    }

    private void setNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerFragment = (DrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        drawerFragment.setUp(R.id.fragment_navigation_drawer, drawerLayout, mToolbar);
        drawerFragment.setDrawerListener(this);

       // drawerFragment.setUserName(getUserName());
        //drawerFragment.setProfilePic(getUserProfilePic());
    }

    private String getUserProfilePic() {
        return new UserModel().getUserbyID(getApplication(), Helper.getSharedPrefValStr(this,
                Constants.sharedPref.userName)).getProfileURL();
    }


    private String getUserName() {
        String username = Helper.getSharedPrefValStr(this, Constants.sharedPref.userName);
        String fullName=null;

        if (!username.equals("")) {
            User mUser =new UserModel().getUserbyID(getApplication(),username);
            fullName = mUser.getFirstName().concat(" "+mUser.getLastName());

        }
        return fullName;
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public void onGroupItemSelected(View view, int groupPosition) {

        switch (groupPosition){
            case LOG_OUT:
                Helper.logOut(getApplication());
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;

            case BUYER_MY_PROFILE :
                startActivity(new Intent(this, BuyerMyProfileActivity.class));
                finish();
                break;

            case BUYER_HOME :
                startActivity(new Intent(this, BuyerSellerDashBoardActivity.class));
                finish();
                break;

            case BUYER_POST_ADD:
                startActivity(new Intent(this, BuyerPostAddActitivity.class));
                finish();
                break;

                default:
                    break;
        }
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        switch (position) {



/*
          case RESET_PWD:
             //   if (!(this instanceof ResetPasswordActivity)) {
                    //finish();
                 startActivity(new Intent(this,ResetPasswordActivity.class));
              //  }
                break;*/

            case LOG_OUT:

                Helper.logOut(getApplication());
                startActivity(new Intent(this,LoginActivity.class));
                finish();

               /* if (!(this instanceof MyTicketActivity)) {
                   // finish();
                    gotoMyTicket();
                }*/
                break;
           /*   case E_PURSE:
                if (!(this instanceof EpurseActivity)) {
                   // finish();
                    Intent intent = new Intent(this, EpurseActivity.class);
                    // bundle = new Bundle();
                    //bundle.putString("Language_Chosen", "EpurseActivity");
                    //intent.putExtras(bundle);
                    startActivity(intent);
                    //startActivity(new Intent(this, EpurseActivity.class));
                }
                break;

            case HELP:
                if (!(this instanceof HelpActivity)) {
                    //finish();
                    Intent intent = new Intent(this, HelpActivity.class);
                   // Bundle bundle = new Bundle();
                   // bundle.putString("Language_Chosen", "HelpActivity");
                    //intent.putExtras(bundle);
                    startActivity(intent);
                    //startActivity(new Intent(this, HelpActivity.class));
                }
                break;
            case SETTINGS:
                if (!(this instanceof SettingsActivity)) {
                    //finish();
                    startActivity(new Intent(this, SettingsActivity.class));
                }
                break;
            case LOGOUT:
                finishAffinity();
                new UserDataModel().deleteUser(getApplication(), strMobileNumber); //To delete userdata from DB
                Helper.logout(this);
                startActivity(new Intent(this, LoginActivity.class));
                break;

            case CHAT:
//                if (!(this instanceof ChatActivity)) {
//                    finish();

                    startActivity(new Intent(this, ChatActivity.class));
//                }
                break;

            case PARKING:
//                if (!(this instanceof ChatActivity)) {
//                    finish();
                startActivity(new Intent(this, ParkingTicketActivity.class));
//                }
                break;*/
        }

//        startActivity(intent);
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//        finish();
//        overridePendingTransition(0, 0);
    }

  /*  private void gotoHome() {
        Intent intent = new Intent(this, BuyTicketActivity.class);
        //Bundle bundle = new Bundle();
        //bundle.putString("Language_Chosen", "BuyTicketActivity");
        //intent.putExtras(bundle);
        startActivity(intent);
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

    private void gotoMyTicket() {
        Intent intent = new Intent(this, MyTicketActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Language_Chosen", "MyTicketActivity");
        intent.putExtras(bundle);
        startActivity(intent);
    }*/


    @Override
    public void onOpen() {
//        Log.d(TAG, "onOpen: ");
        Helper.hideKeyboard(BaseDrawerActivity.this);
    }

    @Override
    public void onClose() {
//        Log.d(TAG, "onClose: ");
    }

   /* @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.onAttach(base));
    }

    @Override
    public void onDrawerProfileClick(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    BroadcastReceiver profileUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Log.d(TAG, "onReceive: ");
            if (drawerFragment != null) {
//                Log.d(TAG, "onReceive: not null");
                drawerFragment.setUserName(getUserName());
                drawerFragment.setProfilePic(getUserProfilePic());
            }

        }
    };*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(profileUpdateReceiver);
    }



   private void updateSpinnerLocation(Spinner mSpinner,User mUser){
       //Fetch Locality  & update user
       List<Locality> mcityList=new LocalityModel().getAllLocality(getApplication());
       mSpinner.setAdapter(new LocalityAdapter(getApplicationContext(),
               R.layout.simple_spinner_dropdown_item,mcityList));
       if(mUser!=null){
           mSpinner.setSelection(mUser.getLocalityId());
       }
       //

   }

   private User fetchUser(){
       User mUser=null;
       String mUsrname =Helper.getSharedPrefValStr(getApplication(),Constants.sharedPref.userName);
       mUser = new UserModel().getUserbyID(getApplication(),mUsrname);
       return mUser;
   }

    public class LocalityAdapter extends ArrayAdapter<Locality> {
        List<dbModel.Locality> mList ;
        public LocalityAdapter(@NonNull Context context, int resource, @NonNull List<dbModel.Locality> objects) {
            super(context, resource, objects);
            mList = objects;
        }



        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            // TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            //return super.getView(position, convertView, parent);

            LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.simple_location_spinner_dropdown, parent, false);
            TextView label=(TextView)row.findViewById(R.id.idText);
            label.setText(""+mList.get(position).getName());



            return row;
        }
    }

    private void fetchCategoryData(){
        try{

            String webAPI = Helper.getSharedPrefValStr(BaseDrawerActivity.this, Constants.sharedPref.s_BASE_URL)
                    .concat(Constants.webAPI.getCategory);

            NetworkCommunicationHelper networkCommunicationHelper = new NetworkCommunicationHelper();
            networkCommunicationHelper.sendSynchronousGetRequest(getApplication(), webAPI,
                    new NetworkCommunicationHelper.OnResponseReceived() {
                        @Override
                        public void onSuccess(final String res) {

                            try{

                               String responseString = Helper.fetchMainResponseasObject(res);
                                Gson gson = new GsonBuilder()
                                        .serializeNulls()
                                        .create();

                                String categoryResponse = Helper.fetchResponse(res);

                                Type type = new TypeToken<MainCategoryModel>() {
                                }.getType();
                                MainCategoryModel mainCategoryModel = gson.fromJson(responseString, type);



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
