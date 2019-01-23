package gogagner.goldenbrainsithub.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import gogagner.goldenbrainsithub.com.R;

import java.util.Timer;
import java.util.TimerTask;

import utility.Constants;
import utility.Helper;

//This is splash activity
public class SplashActivity extends Activity {
    public static String TAG = SplashActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.setFullScreen(SplashActivity.this);
        setContentView(R.layout.activity_main);
        startSplash();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void startSplash() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                if (Helper.getSharedPrefValBoolean(SplashActivity.this, Constants.login.isLoginSuccess)) {
                    //if (Helper.getSharedPrefValBoolean(SplashActivity.this, Constants.login.isSignupSuccess)) {
                        startActivity(new Intent(getApplicationContext(), BuyerSellerDashBoardActivity.class));
                        finish();
                   /* } else
                        startActivity(new Intent(getApplicationContext(), SignupActivity.class));
                    finish();*/
                } else
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();

            }
        }, 4000);
    }
}
