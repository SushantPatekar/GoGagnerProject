package slidermenu;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import gogagner.goldenbrainsithub.com.R;


/**
 * Created by aejaz.ahmed on 20-08-2018.
 */

public abstract class BaseToolbarActivity extends AppCompatActivity implements View.OnClickListener{

    protected Toolbar mToolbar;

    public static final String TAG = BaseToolbarActivity.class.getSimpleName();
    private TextView tvToolbarTitle;
    private ImageView ivToolbarAlertOrNotif;
    private ImageView ivToolbarBack;
    private int optionClickedPosition = -1;



    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base_toolbar);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fl_drawer_activity_container);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        configureToolBar();
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setHomeButtonEnabled(true);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
    }


    protected void configureToolBar() {

        mToolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setContentInsetsRelative(0, 0);
        mToolbar.setContentInsetsAbsolute(0, 0);
        // setting up the listeners
        final LayoutInflater layoutInflater = LayoutInflater.from(this);
        View toolbarLayout = layoutInflater.inflate(R.layout.base_toolbar_layout, null);
        tvToolbarTitle = (TextView) toolbarLayout.findViewById(R.id.tv_toolbar_title);
        ivToolbarAlertOrNotif = (ImageView) toolbarLayout.findViewById(R.id.iv_toolbar_alert_or_notif);
        ivToolbarBack = (ImageView) toolbarLayout.findViewById(R.id.iv_toolbar_back);
        ivToolbarAlertOrNotif.setOnClickListener(this);
        ivToolbarBack.setOnClickListener(this);
        // tweaking action bar settings
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(toolbarLayout);
        }
//        setTitleTextView(getString(R.string.app_name));
    }

    public void setTitleTextView(String title) {
        tvToolbarTitle.setText(title);
    }



    public Toolbar getToolbar() {
        return mToolbar;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_toolbar_alert_or_notif:
                Log.d(TAG, "onClick: notif");
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
       // super.attachBaseContext(LanguageHelper.onAttach(base));
    }
}
