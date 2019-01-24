package utility;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dbModel.User;
import dbModel.UserModel;

public class Helper {

    public static void updateSharedPrefValStr(Context context, String key, String val) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF_KEY, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, val).commit();
    }

    public static String getSharedPrefValStr(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static void updateSharedPrefValInt(Context context, String key, int val) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF_KEY, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, val).commit();
    }

    public static int getSharedPrefValInt(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    public static void updatedSharedPrefValBoolean(Context context, String key, boolean val) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF_KEY, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, val).commit();
    }

    public static boolean getSharedPrefValBoolean(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public static void updateSharedPrefValLong(Context context, String key, long val) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF_KEY, Context.MODE_PRIVATE);
        sharedPreferences.edit().putLong(key, val).commit();
    }

    public static long getSharedPrefValLong(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, 0);
    }

    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    public static void setFullScreen(Activity activity){
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    public static  void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && activity.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
    public static String getVersion(Context activityContext) {
        String versionName = "";
        int versionCode = -1;
        try {
            PackageInfo packageInfo = activityContext.getPackageManager().getPackageInfo(activityContext.getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public boolean isNetworkAvailable(Application mApplication) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mApplication.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String fetchResponse(String res) throws JSONException {
        JSONObject main = new JSONObject(res);
        JSONObject j_response = main.getJSONObject("response");
        JSONArray data = j_response.getJSONArray("data");
        return data.toString();
    }

    public static int getServerErroCode(String res){
        int statusCode=0;
        try {
           JSONObject main = new JSONObject(res);
           statusCode  = main.getInt("status");
        }
        catch (Exception e){

        }
        return statusCode;
    }

    public static String getServerErroMessage(String res){
        String messages=null;
        try {
            JSONObject main = new JSONObject(res);
            messages  = main.getString("messages");
        }
        catch (Exception e){

        }
        return messages;
    }
    public static void logOut(Application contex){
       /* new UserModel().deleteUser(contex,getSharedPrefValStr(contex,
                Constants.sharedPref.userName));*/
        updatedSharedPrefValBoolean(contex,Constants.login.isLoginSuccess,false);
    }
}