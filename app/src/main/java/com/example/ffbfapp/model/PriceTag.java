package com.example.ffbfapp.model;

public enum PriceTag {
    £("Cheap Prices"),
    ££("Moderate Prices"),
    £££("Expensive Prices");


    // declaring private variable for getting values
    public String label;

    // getter method
    public String getLabel()
    {
        return this.label;
    }

    // enum constructor - cannot be public or protected
    private PriceTag(String label)
    {
        this.label = label;
    }
}
