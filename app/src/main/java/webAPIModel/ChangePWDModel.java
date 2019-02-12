package webAPIModel;

import com.google.gson.annotations.SerializedName;

public class ChangePWDModel {
    @SerializedName("oldPassword")
    private String oldPassword;
    @SerializedName("password")
    private String password;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
