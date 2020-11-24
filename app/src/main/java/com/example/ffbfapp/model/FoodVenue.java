package com.example.ffbfapp.model;

public class FoodVenue {
    private String uid;
    private String name;
    private String description;
    private String street;
    private String city;
    private String county;
    private String postcode;
    private String contactNo;
    private String foodVenueType;
    private Integer imageResourceReference;
    private int rating;

    // TODO: Add reviews list
    //  foodVenueType [Restaurant || Street Food]
    public FoodVenue() {
    }

    public FoodVenue(String uid, int rating, String name, String description, String street, String city, String county, String postcode, String contactNo, String foodVenueType, Integer imageResourceReference) {
        this.uid = uid;
        this.rating = rating;
        this.name = name;
        this.description = description;
        this.street = street;
        this.city = city;
        this.county = county;
        this.postcode = postcode;
        this.contactNo = contactNo;
        this.foodVenueType = foodVenueType;
        this.imageResourceReference = imageResourceReference;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

//    public int getUid() {
//        return uid;
//    }
//
//    public void setUid(int uid) {
//        this.uid = uid;
//    }

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFoodVenueType() {
        return foodVenueType;
    }

    public void setFoodVenueType(String foodVenueType) {
        this.foodVenueType = foodVenueType;
    }

    public Integer getImageResourceReference() {
        return imageResourceReference;
    }

    public void setImageResourceReference(Integer imageResourceReference) {
        this.imageResourceReference = imageResourceReference;
    }

}
