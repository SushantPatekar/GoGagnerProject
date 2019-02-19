package webAPIModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import gogagner.goldenbrainsithub.model.MediaModel;

public class PostAddModel implements Serializable {

   @SerializedName("locationId")
   private String locationId;
    @SerializedName("businessId")
    private String businessId;
    @SerializedName("sourceId")
    private String sourceId;
    @SerializedName("publishDate")
    private String publishDate;


    @SerializedName("media")
    private PostAddMedia postAddMedia;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }



    public PostAddMedia getPostAddMedia() {
        return postAddMedia;
    }

    public void setPostAddMedia(PostAddMedia postAddMedia) {
        this.postAddMedia = postAddMedia;
    }
}
