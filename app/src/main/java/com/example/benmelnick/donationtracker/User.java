package com.example.benmelnick.donationtracker;

import com.google.firebase.database.IgnoreExtraProperties;

@SuppressWarnings("unused")
@IgnoreExtraProperties
class User {
    private String userId;
    private String email;
    private String name;
    private String accountType;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


    User(String id, String e, String n, String t) {
        userId = id;
        email = e;
        name = n;
        accountType = t;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getAccountType() {
        return accountType;
    }
}
