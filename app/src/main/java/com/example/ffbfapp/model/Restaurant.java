package com.example.ffbfapp.model;

public class Restaurant {
    private String name, description, street, city, county, postcode, contactNo;
    private float rating;
    private String review;

    public Restaurant() {
    }

    public Restaurant(String name, String description, String street, String city, String county, String postcode, String contactNo, float rating, String review) {
        this.name = name;
        this.description = description;
        this.street = street;
        this.city = city;
        this.county = county;
        this.postcode = postcode;
        this.contactNo = contactNo;
        this.rating = rating;
        this.review = review;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
