package com.example.ffbfapp.data;

public class Upload {
    private String name;
    private String mImage;

    public Upload() {
        //empty constructor needed
    }

    public Upload(String name, String mImage) {
        // if no name is passed for the image we will use "no name"
        if(name.trim().equals("")){
            this.name = "no name";
        }

        this.name = name;
        this.mImage = mImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String mImage) {
        this.mImage = mImage;
    }
}