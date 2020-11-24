package com.example.ffbfapp;

public class SingleUserItem {
    private String uid;
    private String userName;
    private String userEmail;
    private boolean critic;


    public SingleUserItem() {
    }


    // temporar
    public SingleUserItem(String uid, String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.uid = uid;
    }


    public SingleUserItem(String userName, String userEmail, boolean critic) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.critic = critic;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean isCritic() {
        return critic;
    }

    public void setCritic(boolean critic) {
        this.critic = critic;
    }
}
