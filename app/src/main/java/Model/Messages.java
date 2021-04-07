package Model;

public class Messages {
    String uId, message;
    long timeStamp;
    String encType;


    public Messages(String uId, String message, long timeStamp) {
        this.uId = uId;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public Messages(String uId, String message, String encType) {
        this.uId = uId;
        this.message = message;
        this.encType=encType;
    }

    public Messages(){}

    public String getuId() {
        return uId;
    }

    public String getMessage() {
        return message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getEncType() {
        return encType;
    }

    public void setEncType(String encType) {
        this.encType = encType;
    }
}
