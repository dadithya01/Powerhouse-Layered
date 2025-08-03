package edu.ijse.powerhouse.view.tdm;

public class UserTM  {
    private String userId;
    private String name;
    private String phone;
    private String email;
    private String userName;
    private String password;
    private String userTypeId;
    private String registrationDate;
    private String status;

    public UserTM() {
    }

    public UserTM(String userId, String name, String phone, String email, String userName, String password, String userTypeId, String registrationDate, String status) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.userTypeId = userTypeId;
        this.registrationDate = registrationDate;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserTypeId() {
        return userTypeId;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserTypeId(String userTypeId) {
        this.userTypeId = userTypeId;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}