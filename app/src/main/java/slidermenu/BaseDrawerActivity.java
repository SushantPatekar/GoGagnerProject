package slidermenu;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import dbModel.User;
import dbModel.UserModel;
import gogagner.goldenbrainsithub.com.LoginActivity;
import gogagner.goldenbrainsithub.com.R;
import gogagner.goldenbrainsithub.com.ResetPasswordActivity;
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

    private final int RESET_PWD = 0;
    private final int LOG_OUT = 1;
    private final int E_PURSE = 2;
    private final int HELP = 3;
    private final int SETTINGS = 4;
    private final int LOGOUT = 5;
    private static final int CHAT = 6;
    private static final int PARKING = 7;



//    static {
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base_drawer);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fl_drawer_activity_container);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        configureToolBar();

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
        View toolbarLayout = layoutInflater.inflate(R.layout.base_drawer_toolbar_layout, null);
        tvToolbarTitle = (TextView) toolbarLayout.findViewById(R.id.tv_toolbar_title);
        // tweaking action bar settings
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(toolbarLayout);
        }
        setTitleTextView(getString(R.string.app_name));
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
    public void onDrawerItemSelected(View view, int position) {
        switch (position) {

          case RESET_PWD:
             //   if (!(this instanceof ResetPasswordActivity)) {
                    //finish();
                 startActivity(new Intent(this,ResetPasswordActivity.class));
              //  }
                break;

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

   /* @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(profileUpdateReceiver,
                new IntentFilter(Constants.Actions.UPDATE_PROFILE_ACTION));
    }*/
}
