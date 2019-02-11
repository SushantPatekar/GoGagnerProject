package utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.regex.Pattern;

import dbModel.User;
import dbModel.UserModel;
import gogagner.goldenbrainsithub.com.R;

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

    public static void setFullScreen(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    public static void hideKeyboard(Activity activity) {
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


    public static String fetchResponseasObject(String res) throws JSONException {
        JSONObject main = new JSONObject(res);
        JSONObject j_response = main.getJSONObject("response");
        JSONObject data = j_response.getJSONObject("data");
        return data.toString();
    }

    public static String fetchMainResponseasObject(String res) throws JSONException {
        JSONObject main = new JSONObject(res);
        JSONObject j_response = main.getJSONObject("response");
        return j_response.toString();
    }
    public static int getServerErroCode(String res) {
        int statusCode = 0;
        try {
            JSONObject main = new JSONObject(res);
            statusCode = main.getInt("status");
        } catch (Exception e) {

        }
        return statusCode;
    }

    public static String getServerMessage(String res) {
        String messages = null;
        try {
            JSONObject main = new JSONObject(res);
            messages = main.getString("messages");
        } catch (Exception e) {

        }
        return messages;
    }

    public static String getServerSuccessMessage(String res) {
        String messages = null;
        try {
            JSONObject main = new JSONObject(res);
            JSONObject response = main.getJSONObject("response");
            messages = response.getString("messages");
        } catch (Exception e) {

        }
        return messages;
    }

    public static void logOut(Application contex) {
       /* new UserModel().deleteUser(contex,getSharedPrefValStr(contex,
                Constants.sharedPref.userName));*/
        updatedSharedPrefValBoolean(contex, Constants.login.isLoginSuccess, false);
    }
    static AlertDialog alertbox;
    public static void exitPopUp(final Activity context){
        alertbox = new AlertDialog.Builder(context)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        alertbox.dismiss();
                        context.finish();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        alertbox.dismiss();
                    }
                })
                .show();
        alertbox.setCancelable(false);
        }


        public static boolean isMobileOREmail(String input,Activity mAct){
        boolean isMobile=false;

        if(!isMobile(input, isMobile,mAct)){

            //showToast((Context) mAct,mAct.getResources().getString(R.string.enter_mob_no));
            isMobile =false;
        }
else {
    isMobile = true;
        }

       /* else if(!input.matches(Constants.UserDetails.EMAIL_REGEX)){

            showToast((Context) mAct,mAct.getResources().getString(R.string.enter_valid_email));
            isMobile= false;
        }
        else {
            isMobile = true;

        }*/
            return isMobile;
        //isMobile = isMobile(input, isMobile);

            //return isMobile;
        }

    private static  boolean isMobile(String input, boolean isMobile,Activity mAct) {
        try{
            int yourNumber = Integer.parseInt(input);

            if(input.trim().length()>10 ||input.trim().length()<10){
                showToast((Context) mAct,mAct.getResources().getString(R.string.enter_mob_no));
                isMobile = false;
                return isMobile ;

            }
            isMobile = true;
        }catch (NumberFormatException ex) {
            //handle exception here
            if(input.trim().length()>10){
                showToast((Context) mAct,mAct.getResources().getString(R.string.enter_mob_no));
                isMobile = false;
                return isMobile ;

            }
                  else  if(!input.matches(Constants.UserDetails.EMAIL_REGEX)){

                       showToast((Context) mAct,mAct.getResources().getString(R.string.enter_valid_email));
                       isMobile = false;
                   }
                   else {
                       isMobile = true;
                   }
           // isMobile = false;
        }
        return isMobile;
    }

    public static String getX_AccessToken(Application mContext){
        String XAccessToken = null;
        try {
            XAccessToken = new UserModel().getUserbyID(mContext,getSharedPrefValStr(mContext,Constants.sharedPref.userName)).getAccessToken();
        }
        catch (Exception e){

        }
        return  XAccessToken;
    }

    public String generateRefreshTokenObject(User mUser){
        JSONObject userJson = null;
        try
        {
            userJson = new JSONObject();
            userJson.put("refreshToken",mUser.getRefreshToken());
            userJson.put("userId",mUser.getId());
            userJson.put("firstName",mUser.getFirstName());
            userJson.put("lastName",mUser.getLastName());
            userJson.put("mobile",mUser.getMobile());
            userJson.put("userType",mUser.getUserType());

        }
        catch (Exception e){

        }
        return userJson.toString();
    }

    public void showDialog(ProgressDialog mProgressDialog, String message) {
        try {
            mProgressDialog.setMessage(message);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        } catch (Exception e) {

        }
    }

    public void hideDialog(ProgressDialog mProgressDialog) {
        try {
            mProgressDialog.hide();
        } catch (Exception e) {

        }
    }
}
   /* private boolean isValidMobile(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            //if(phone.length() < 6 || phone.length() > 10) {
            if (phone.length() != 10) {
                check = false;
            }
            return check;
        }

        return check;
    }*/


   /* public static boolean checkValidation(String data)
    {
       // String input = yourEditText.getText().toString();
        if(data.contains("@"))
        {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(data).matches();
        }
        else
        {
            return android.util.Patterns.PHONE.matcher(data).matches();
        }
    }*/



