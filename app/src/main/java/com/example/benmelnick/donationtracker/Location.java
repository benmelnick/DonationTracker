package com.example.benmelnick.donationtracker;

/**
 * Location class for storing information for centers read from CSV file
 */
public class Location {
    private String name;
    private String type;
    private double longitude;
    private double latitude;
    private String address;
    private String phoneNumber;

    public Location(String n, String t, double lo, double la, String a, String p) {
        name = n;
        type = t;
        longitude = lo;
        latitude = la;
        address = a;
        phoneNumber = p;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
