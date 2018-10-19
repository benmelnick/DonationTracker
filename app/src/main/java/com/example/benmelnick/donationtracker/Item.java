package com.example.benmelnick.donationtracker;

/**
 * information holder for donation items
 */
public class Item {
    private String timeStamp;
    private String shortDescription;
    private String fullDescription;
    private double value;
    private String category;

    public Item() {

    }

    public Item(String t, String s, String f, double v, String c) {
        timeStamp = t;
        shortDescription = s;
        fullDescription = f;
        value = v;
        category = c;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String t) {
        timeStamp = t;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String s) {
        shortDescription = s;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String f) {
        fullDescription = f;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double v) {
        value = v;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String c) {
        category = c;
    }
}
