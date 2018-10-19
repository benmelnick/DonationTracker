package com.example.benmelnick.donationtracker;

/**
 * Location class for storing information for centers read from CSV file
 */
public class Location {
    private int id;
    private String name;
    private String type;
    private double longitude;
    private double latitude;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;
    private String web;

    public Location(int _id, String n, double la, double lo, String a, String c, String s, String z, String t, String p, String w) {
        id = _id;
        name = n;
        type = t;
        latitude = la;
        longitude = lo;
        address = a;
        city = c;
        state = s;
        zip = z;
        phoneNumber = p;
        web = w;
    }

    public int getId() {
        return id;
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

    public String printFullAddress() {
        return address + ", " + city + ", " + state + " " + zip;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getWeb() {
        return web;
    }
}
