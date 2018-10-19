package com.example.benmelnick.donationtracker;

public class User {
    private String userId;
    private String email;
    private String name;
    private String accountType;

    public User(String id, String e, String n, String t) {
        userId = id;
        email = e;
        name = n;
        accountType = t;
    }
}
