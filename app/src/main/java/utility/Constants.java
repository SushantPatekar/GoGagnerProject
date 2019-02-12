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
        String x_access_token = "x_access_token";
    }

    interface webAPI{
        String BASE_URL= "http://13.234.17.31:3000";
        String IMAGE_UPLOAD_BASE_URL= "http://13.234.17.31:7504";
        String header_content_type= "Content-Type";
        String value_content_type = "application/json";

        String apitoken = "apitoken";
        String apitoken_val = "dde4f4c184c845fab2dbc6e65820814c";

        String header_x_access_token= "x-access-token";
        String value_x_access_token = "application/json";

        String apiRegister = "/api/users/register";
        String apiCity = "/api/city?";
        String resendOTP = "/api/users/resendOtp";
        String verifyOTP = "/api/users/verifyOtp";
        String apiLogin = "/api/users/login";
        String apiState = "/api/state?";
        String apiLocality = "/api/locality?";
        String apiResetPWD = "/api/users/resetPassword";
        String forgotPassword = "/api/users/forgotPassword";
        String signFlag = "signup";
        String forgotPWDFlag= "forgotpassword";
        String SMSTAG = "HNSHIL";
        int firstIndex = 1;
        String changePassword ="/api/users/changePassword";
        String refreshToken = "/api/users/token";
        String saveChanges= "/api/users/editProfile";
       // String uploadImage= "/services/upload/profile";
        String uploadImage = "/services/upload/profile";
        String getCategory = "/api/category";
        //api names

    }
    interface Session {
        String GrantTypePassword = "password";
        String GrantTypeRefresh = "refresh_token";
        String TOKEN_EXPIRY_CODE = "401";
        String ACCESS_TOKEN_URL = "token";
        String REFRESH_TOKEN_URL = "token";
        String IS_REFRESHING = "IS_REFRESHING";
        String EXPIRES_IN = "expires_in";
        String INVALID_GRANT_CODE = "400";
        String INVALID_GRANT_MSG = "invalid_grant";
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
