package com.example.ffbfapp;

public class SingleItem {
    private int imageResource;
    private String name, description, uid;


    public SingleItem(int imageResource, String name, String description, String uid) {
        this.uid = uid;
        this.imageResource = imageResource;
        this.name = name;
        this.description = description;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void changeTitleText(String text) {
        name = text;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
