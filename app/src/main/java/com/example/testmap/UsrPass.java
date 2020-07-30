package com.example.testmap;

public class UsrPass {
    String userName,phone_number;

    public UsrPass(String userName, String phone_number) {
        this.userName=userName;
        this.phone_number=phone_number;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
