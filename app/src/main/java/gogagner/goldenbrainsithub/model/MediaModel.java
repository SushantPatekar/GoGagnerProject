package gogagner.goldenbrainsithub.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MediaModel implements Serializable {
    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public mediaUrl getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(mediaUrl mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    @SerializedName("mediaUrl")
    private mediaUrl mediaUrl;

    //This is teat

}
