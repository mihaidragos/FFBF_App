package com.example.ffbfapp;

import android.widget.RatingBar;
import android.widget.TextView;


public class SingleReview {
    private String username, title, content;
    private int rating;

    public SingleReview() {
    }

    public SingleReview(String username, String title, String content, int rating) {
        this.username = username;
        this.title = title;
        this.content = content;
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
