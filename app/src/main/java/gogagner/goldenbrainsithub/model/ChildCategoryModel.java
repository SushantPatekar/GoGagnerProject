package gogagner.goldenbrainsithub.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ChildCategoryModel implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("title")
    private String title;
    @SerializedName("subTitle")
    private String subTitle;
    @SerializedName("metaTitle")
    private String metaTitle;
    @SerializedName("metaDescription")
    private String metaDescription;
    @SerializedName("metaKeywords")
    private String metaKeywords;
    @SerializedName("offerText")
    private String offerText;
    @SerializedName("description")
    private String description;
    @SerializedName("shareUrl")
    private String shareUrl;

    @SerializedName("Media")
    private List<MediaModel> mediaModels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getOfferText() {
        return offerText;
    }

    public void setOfferText(String offerText) {
        this.offerText = offerText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public List<MediaModel> getMediaModels() {
        return mediaModels;
    }

    public void setMediaModels(List<MediaModel> mediaModels) {
        this.mediaModels = mediaModels;
    }
}
