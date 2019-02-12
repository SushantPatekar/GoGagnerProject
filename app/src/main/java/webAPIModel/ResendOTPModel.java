package webAPIModel;

import com.google.gson.annotations.SerializedName;

public class ResendOTPModel {
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String mobile;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @SerializedName("username")
    public String username;



}
