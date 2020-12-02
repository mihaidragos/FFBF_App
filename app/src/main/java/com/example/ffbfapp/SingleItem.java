package com.example.ffbfapp;

public class SingleItem {
    private String imageResourceUri;
    private String name, description, uid;


    public SingleItem(String imageResourceUri, String name, String description, String uid) {
        this.uid = uid;
        this.imageResourceUri = imageResourceUri;
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

    public String getImageResourceUri() {
        return imageResourceUri;
    }

    public void setImageResourceUri(String imageResourceUri) {
        this.imageResourceUri = imageResourceUri;
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
