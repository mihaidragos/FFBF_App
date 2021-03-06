package com.example.ffbfapp.model;

public enum Rating {
    NONE("No Stars"),
    ONE("1 Star"),
    TWO("2 Stars"),
    THREE("3 Stars"),
    FOUR("4 Stars"),
    FIVE("5 Stars");


    // declaring private variable for getting values
    public String label;

    // getter method
    public String getLabel()
    {
        return this.label;
    }

    // enum constructor - cannot be public or protected
    private Rating(String label)
    {
        this.label = label;
    }
}
