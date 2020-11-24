package com.example.ffbfapp.model;

public class User {
    public  String name, lastName, email;
    boolean admin, critic;

    public User(){

    }



    public User(String name, String lastName, String email, boolean admin, boolean critic) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.admin = admin;
        this.critic = critic;
    }

    public String getFullName() {
        return name + " " + lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isCritic() {
        return critic;
    }

    public void setCritic(boolean critic) {
        this.critic = critic;
    }
}
