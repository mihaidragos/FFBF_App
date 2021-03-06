package com.example.ffbfapp.model;

public class Review {
    private String owner, title, content;
    private int rating;

    public Review(String owner, String title, String content, int rating) {
        this.owner = owner;
        this.title = title;
        this.content = content;
        this.rating = rating;
    }

    public Review() {
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
