package com.google.firebase.arczone.firebase;

public class ArcZoneItem {
    private String name;
    private String description;
    private int rating;

    public ArcZoneItem() {}

    public ArcZoneItem(String name, String description, int rating) {
        this.name = name;
        this.description = description;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }
}

