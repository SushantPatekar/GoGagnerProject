package gogagner.goldenbrainsithub.model;

import java.io.Serializable;
import java.util.List;

public class MainCategoryModel implements Serializable {

    public String status ;
    public String messages ;

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
