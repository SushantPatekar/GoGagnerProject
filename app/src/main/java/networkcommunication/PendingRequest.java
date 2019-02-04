package networkcommunication;

/**
 * Created by sneha.chandiyekar on 6/16/2018.
 */
public class PendingRequest {

    private String url;
    private int type;
    private Object body;
    private NetworkCommunicationHelper.OnResponseReceived responseReceived;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public NetworkCommunicationHelper.OnResponseReceived getResponseReceived() {
        return responseReceived;
    }

    public void setResponseReceived(NetworkCommunicationHelper.OnResponseReceived responseReceived) {
        this.responseReceived = responseReceived;
    }
}
