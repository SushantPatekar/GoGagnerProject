package gogagner.goldenbrainsithub.com;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import utility.Constants;
import utility.Helper;

public class GoGagnerApplication extends Application {
    public static GoGagnerApplication instance;
    RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        storeBaseURL();
    }

    public void storeBaseURL() {
        String baseUrl = Helper.getSharedPrefValStr(this, Constants.sharedPref.s_BASE_URL);
        if (baseUrl.isEmpty()) {
            Helper.updateSharedPrefValStr(this, Constants.sharedPref.s_BASE_URL,
                    Constants.webAPI.BASE_URL);
        }
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this);
        }
        return requestQueue;
    }

    public static GoGagnerApplication getConfig() {
        return instance;
    }
}
