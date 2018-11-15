package com.example.benmelnick.donationtracker;

import java.io.Serializable;

/**
 * information holder for donation items
 */
@SuppressWarnings("unused")
class Item implements Serializable  {
    private String timeStamp;
    private String shortDescription;
    private String fullDescription;
    private double value;
    private String category;
    private String location;

    public Item() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    /**
     * @param t The timestamp for when the item was added
     * @param s The item's short description or name
     * @param f The item's full description
     * @param v The item's value
     * @param c The item's category
     * @param l The item's location
     */
    @SuppressWarnings("ConstructorWithTooManyParameters")
    Item(String t, String s, String f, double v, String c, String l) {
        this.timeStamp = t;
        this.shortDescription = s;
        this.fullDescription = f;
        this.value = v;
        this.category = c;
        this.location = l;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public double getValue() {
        return value;
    }

    public String getCategory() {
        return category;
    }

    public String getLocation() {
        return location;
    }
}
