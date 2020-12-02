package com.example.ffbfapp.model;


import java.util.HashMap;

public class FoodVenue {
    private String uid;
    private String imageResourceReference;
    private double rating;
    private String name;
    private String description;
    private String reviewsListReference;
    private String foodMenuReference;
    private String reservationsReference;
    private Address address;
    private HashMap<String,String> addressHashmap;
    private FoodVenueType foodVenueType;
    private Cuisine cuisine;
    private PriceTag priceTagValue;

    public FoodVenue() {
    }

    public FoodVenue(String uid, String imageResourceReference, double rating, String name, String description, String reviewsListReference, String foodMenuReference, String reservationsReference, Address address, FoodVenueType foodVenueType, Cuisine cuisine, PriceTag priceTagValue) {
        this.uid = uid;
        this.imageResourceReference = imageResourceReference;
        this.rating = rating;
        this.name = name;
        this.description = description;
        this.reviewsListReference = reviewsListReference;
        this.foodMenuReference = foodMenuReference;
        this.reservationsReference = reservationsReference;
        this.address = address;
        this.foodVenueType = foodVenueType;
        this.cuisine = cuisine;
        this.priceTagValue = priceTagValue;
    }

    public FoodVenue(String uid, String imageResourceReference, double rating, String name, String description, String reviewsListReference, String foodMenuReference, String reservationsReference, HashMap<String,String> addressHashmap, FoodVenueType foodVenueType, Cuisine cuisine, PriceTag priceTagValue) {
        this.uid = uid;
        this.imageResourceReference = imageResourceReference;
        this.rating = rating;
        this.name = name;
        this.description = description;
        this.reviewsListReference = reviewsListReference;
        this.foodMenuReference = foodMenuReference;
        this.reservationsReference = reservationsReference;
        this.addressHashmap = addressHashmap;
        this.foodVenueType = foodVenueType;
        this.cuisine = cuisine;
        this.priceTagValue = priceTagValue;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImageResourceReference() {
        return imageResourceReference;
    }

    public void setImageResourceReference(String imageResourceReference) {
        this.imageResourceReference = imageResourceReference;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
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

    public String getReviewsListReference() {
        return reviewsListReference;
    }

    public void setReviewsListReference(String reviewsListReference) {
        this.reviewsListReference = reviewsListReference;
    }

    public String getFoodMenuReference() {
        return foodMenuReference;
    }

    public void setFoodMenuReference(String foodMenuReference) {
        this.foodMenuReference = foodMenuReference;
    }

    public String getReservationsReference() {
        return reservationsReference;
    }

    public void setReservationsReference(String reservationsReference) {
        this.reservationsReference = reservationsReference;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public FoodVenueType getFoodVenueType() {
        return foodVenueType;
    }

    public void setFoodVenueType(FoodVenueType foodVenueType) {
        this.foodVenueType = foodVenueType;
    }

    public Cuisine getCuisine() {
        return cuisine;
    }

    public void setCuisine(Cuisine cuisine) {
        this.cuisine = cuisine;
    }

    public PriceTag getPriceTagValue() {
        return priceTagValue;
    }

    public void setPriceTagValue(PriceTag priceTagValue) {
        this.priceTagValue = priceTagValue;
    }

    public HashMap<String, String> getAddressHashmap() {
        return addressHashmap;
    }

    public void setAddressHashmap(HashMap<String, String> addressHashmap) {
        this.addressHashmap = addressHashmap;
    }
}



