package com.example.ffbfapp;

public class SingleItem {
    private int imageResource;
    private String titleText, contentText;

    public SingleItem(int imageResource, String titleText, String contentText) {
        this.imageResource = imageResource;
        this.titleText = titleText;
        this.contentText = contentText;
    }

    public void changeTitleText(String text){
        titleText = text;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }
}
