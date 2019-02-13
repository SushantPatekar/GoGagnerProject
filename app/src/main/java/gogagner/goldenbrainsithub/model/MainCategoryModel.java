package gogagner.goldenbrainsithub.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MainCategoryModel implements Serializable {

    @SerializedName("status")
    public String status ;
    @SerializedName("messages")
    public String messages ;

    @SerializedName("data")
    List <ChildCategoryModel> childCategoryModel;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public List<ChildCategoryModel> getChildCategoryModel() {
        return childCategoryModel;
    }

    public void setChildCategoryModel(List<ChildCategoryModel> childCategoryModel) {
        this.childCategoryModel = childCategoryModel;
    }
}
