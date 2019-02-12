package webAPIModel;

import com.google.gson.annotations.SerializedName;

public class VerifyOTPModel {

    @SerializedName("mobile")
    public String mobile;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
    @SerializedName("action")
    public String page ;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
    @SerializedName("otp")
    public String otp;


}
