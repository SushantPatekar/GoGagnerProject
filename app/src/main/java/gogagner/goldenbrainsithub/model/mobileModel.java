package gogagner.goldenbrainsithub.model;

import java.io.Serializable;

public class mobileModel implements Serializable {
private String medium;
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
