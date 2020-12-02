package com.example.ffbfapp.model;

public enum Cuisine {
    THAI("Thai"),
    SPANISH("Spanish"),
    MOROCCAN("Moroccan"),
    JAPANESE("Japanese"),
    INDIAN("Indian"),
    ITALIAN("Italian"),
    FRENCH("French"),
    MEDITERRANEAN("Mediterranean"),
    ROMANIAN("Romanian"),
    CHINESE("Chinese");


    // declaring private variable for getting values
    public String label;

    // getter method
    public String getLabel()
    {
        return this.label;
    }

    // enum constructor - cannot be public or protected
    private Cuisine(String label)
    {
        this.label = label;
    }
}
