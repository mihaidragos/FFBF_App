package com.example.ffbfapp.model;

public class User {
    public  String name, lastName, email, userType;

    public User(){

    }

    public User(String name, String lastName, String email){
        this.name   = name;
        this.lastName   = lastName;
        this.email  = email;
        this.userType  = "USER"; // "ADMIN" / "CRITIC"
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public User(String name, String lastName, String email, String userType){
        this.name   = name;
        this.lastName   = lastName;
        this.email  = email;
        this.userType  = userType;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
