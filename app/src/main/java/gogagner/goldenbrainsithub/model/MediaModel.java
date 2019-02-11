package gogagner.goldenbrainsithub.model;

import java.io.Serializable;
import java.util.List;

public class MediaModel implements Serializable {

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

    private mediaUrl mediaUrl;

}
