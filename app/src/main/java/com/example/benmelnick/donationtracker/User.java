package com.example.benmelnick.donationtracker;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String userId;
    public String email;
    public String name;
    public String accountType;

    public User() {

    }

    public User(String id, String e, String n, String t) {
        userId = id;
        email = e;
        name = n;
        accountType = t;
    }


}
