package gogagner.goldenbrainsithub.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class mediaUrl implements Serializable {
    @SerializedName("mobile")
    private mobileModel mobileModel;

    public gogagner.goldenbrainsithub.model.mobileModel getMobileModel() {
        return mobileModel;
    }

    public void setMobileModel(gogagner.goldenbrainsithub.model.mobileModel mobileModel) {
        this.mobileModel = mobileModel;
    }
}
