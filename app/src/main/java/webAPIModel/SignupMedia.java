package webAPIModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SignupMedia implements Serializable {
    @SerializedName("mobile")
    private SignupMobile signupMobile;

    public SignupMobile getSignupMobile() {
        return signupMobile;
    }

    public void setSignupMobile(SignupMobile signupMobile) {
        this.signupMobile = signupMobile;
    }
}
