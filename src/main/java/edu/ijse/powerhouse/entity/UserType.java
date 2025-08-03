package edu.ijse.powerhouse.entity;

public class UserType {

    private String user_Type_Id;
    private String userTypeName;

    public UserType() {
    }

    public UserType(String user_Type_Id, String userTypeName) {
        this.user_Type_Id = user_Type_Id;
        this.userTypeName = userTypeName;
    }
    public String getUser_Type_Id() {
        return user_Type_Id;
    }

    public void setUser_Type_Id(String user_Type_Id) {
        this.user_Type_Id = user_Type_Id;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }
}
