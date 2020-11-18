package com.example.ffbfapp;

public class SingleItem {
    private int imageResource;
    private String locationNameText, descriptionText, uid;


    public SingleItem(int imageResource, String locationNameText, String descriptionText, String uid) {
        this.uid = uid;
        this.imageResource = imageResource;
        this.locationNameText = locationNameText;
        this.descriptionText = descriptionText;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void changeTitleText(String text) {
        locationNameText = text;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getLocationNameText() {
        return locationNameText;
    }

    public void setLocationNameText(String locationNameText) {
        this.locationNameText = locationNameText;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }
}
