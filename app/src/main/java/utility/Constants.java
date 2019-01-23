package utility;

public interface Constants {
    String SHARED_PREF_KEY = "GO_GANGER_PREF";


    interface UserDetails {
        String UserData = "UserData";
        String UserId = "UserId";
        String UserName = "UserName";
        String MOBILE_NO = "MOBILE_NO";
        String PW_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    }

    interface  login{
        String isLoginSuccess = "isLoginSuccess";
        String isSignupSuccess = "isSignupSuccess";
    }

    interface webAPI{
        String header_content_type= "Content-Type";
        String value_content_type = "application/json";
        String BASE_URL= "http://13.234.17.31:3000";
        String apitoken = "apitoken";
        String apitoken_val = "dde4f4c184c845fab2dbc6e65820814c";
        String apiRegister = "/api/users/register";
        String apiCity = "/api/city";
        String resendOTP = "/api/users/resendOtp";
        String verifyOTP = "/api/users/verifyOtp";
        String apiLogin = "/api/users/login";
        String apiState = "/api/state";
        String apiLocality = "/api/locality";
        String apiResetPWD = "/api/users/resetPassword";
        String forgotPassword = "/api/users/forgotPassword";
        String signFlag = "signup";
        String forgotPWDFlag= "forgotpassword";
        String SMSTAG = "HNSHIL";
        //api names

    }

    interface DB{
        String NAME = "Gogagner.db";

        String USER_TABLE = "USER";
        String userName="Username";
        String userMoboNumber= "userMobileNumber";


    }
    interface sharedPref{
        String s_BASE_URL = "s_BASE_URL";
        String userName = "userName";
    }
    interface serverErroCode{
        int code_406=406;
        int code_409=409;
    }

}
