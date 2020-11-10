package com.example.ffbfapp.model;

public class Rating {
    private float rating;

    public Rating() {
    }

    public Rating(float rating) {
        if(rating >= 0 && rating <= 5){
            this.rating = rating;
        }
        this.rating = 0;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        if(rating >= 0 && rating <= 5){
            this.rating = rating;
        }
    }
}
