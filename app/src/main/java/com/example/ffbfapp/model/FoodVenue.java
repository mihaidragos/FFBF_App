package com.example.ffbfapp.model;

public class FoodVenue {
    private String uid, name, description, street, city, county, postcode, contactNo, foodVenueType;
    private int rating;

    // TODO: Add reviews list
    //  foodVenueType [Restaurant || Street Food]
    public FoodVenue() {
    }


    public FoodVenue(String uid, String name, String foodVenueType, String description, String street, String city, String county, String postcode, String contactNo, int rating) {
        this.uid = uid;
        this.name = name;
        this.description = description;
        this.street = street;
        this.city = city;
        this.county = county;
        this.postcode = postcode;
        this.contactNo = contactNo;
        this.rating = rating;
        this.foodVenueType = foodVenueType;
    }

    public String getUid() { return uid; }

    public void setUid(String uid) { this.uid = uid; }

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
}
