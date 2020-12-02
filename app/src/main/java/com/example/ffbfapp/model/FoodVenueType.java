package com.example.ffbfapp.model;

public enum FoodVenueType {
    R("Restaurant"),
    SF("Street Food");

    // declaring private variable for getting values
    public String label;

    // getter method
    public String getLabel()
    {
        return this.label;
    }

    // enum constructor - cannot be public or protected
    private FoodVenueType(String label)
    {
        this.label = label;
    }
}
