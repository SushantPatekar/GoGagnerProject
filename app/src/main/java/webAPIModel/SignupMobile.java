package webAPIModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SignupMobile implements Serializable {
    @SerializedName("medium")
    private String medium;
    @SerializedName("small")
    private String small;

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }
}
