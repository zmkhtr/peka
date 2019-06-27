package web.id.azammukhtar.peka.Model;

public class UserDevice {
    private String userEmail;
    private String userId;
    private String userPassword;
    private String handphoneBrand;
    private String handphoneModel;
    private String handphoneToken;

    public UserDevice(String userEmail, String userId, String userPassword, String handphoneBrand, String handphoneModel, String handphoneToken) {
        this.userEmail = userEmail;
        this.userId = userId;
        this.userPassword = userPassword;
        this.handphoneBrand = handphoneBrand;
        this.handphoneModel = handphoneModel;
        this.handphoneToken = handphoneToken;
    }

    public UserDevice() {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getHandphoneBrand() {
        return handphoneBrand;
    }

    public void setHandphoneBrand(String handphoneBrand) {
        this.handphoneBrand = handphoneBrand;
    }

    public String getHandphoneModel() {
        return handphoneModel;
    }

    public void setHandphoneModel(String handphoneModel) {
        this.handphoneModel = handphoneModel;
    }

    public String getHandphoneToken() {
        return handphoneToken;
    }

    public void setHandphoneToken(String handphoneToken) {
        this.handphoneToken = handphoneToken;
    }
}
