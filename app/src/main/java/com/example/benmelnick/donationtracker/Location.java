package com.example.benmelnick.donationtracker;

import java.io.Serializable;

/**
 * Location class for storing information for centers read from CSV file
 */
@SuppressWarnings("unused")
public class Location implements Serializable {
    private final int id;
    private final String name;
    private final String type;
    private final double longitude;
    private final double latitude;
    private final String address;
    private final String city;
    private final String state;
    private final String zip;
    private final String phoneNumber;
    private final String web;

    /**
     * @param id The location id
     * @param n name
     * @param la latitude
     * @param lo longitude
     * @param a address
     * @param c City
     * @param s State
     * @param z Zip Code
     * @param t Type
     * @param p Phone Number
     * @param w Website
     */
    public Location(int id, String n, double la, double lo, String a,
                    String c, String s, String z, String t, String p, String w) {
        this.id = id;
        this.name = n;
        this.type = t;
        this.latitude = la;
        this.longitude = lo;
        this.address = a;
        this.city = c;
        this.state = s;
        this.zip = z;
        this.phoneNumber = p;
        this.web = w;
    }

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return full address
     */
    public String printFullAddress() {
        return address + ", " + city + ", " + state + " " + zip;
    }

    /**
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * @return zip code
     */
    public String getZip() {
        return zip;
    }

    /**
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @return website url
     */
    public String getWeb() {
        return web;
    }
}
