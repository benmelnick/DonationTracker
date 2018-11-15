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
     * @param name name
     * @param latitude latitude
     * @param longitude longitude
     * @param address address
     * @param city City
     * @param state State
     * @param zip Zip Code
     * @param type Type
     * @param phone Phone Number
     * @param website Website
     */
    @SuppressWarnings("ConstructorWithTooManyParameters")
    public Location(int id, String name, double latitude, double longitude,
                    String address, String city, String state, String zip,
                    String type, String phone, String website) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phone;
        this.web = website;
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

    @Override
    public String toString() {
        return name;
    }
}
