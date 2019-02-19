package webAPIModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import gogagner.goldenbrainsithub.model.mobileModel;

public class PostAddMedia implements Serializable {
    @SerializedName("mobile")
    private mobileModel mobileModel;

    public gogagner.goldenbrainsithub.model.mobileModel getMobileModel() {
        return mobileModel;
    }

    public void setMobileModel(gogagner.goldenbrainsithub.model.mobileModel mobileModel) {
        this.mobileModel = mobileModel;
    }
}
