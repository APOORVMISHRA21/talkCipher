package Model;

public class Users {
    String userName, phoneNumber, userId, lastMessage, profilePic;

    public Users(String userName, String phoneNumber, String userId, String lastMessage, String profilePic) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.lastMessage = lastMessage;
        this.profilePic = profilePic;
    }

    public Users(){}

    public Users(String userName, String phoneNumber, String userId) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
    }

    //Constructor for SignUp page.
    public Users(String userName, String phoneNumber) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
