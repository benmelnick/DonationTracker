package com.example.benmelnick.donationtracker;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

final class FirebaseHelper {
    static final FirebaseHelper INSTANCE = new FirebaseHelper();

    private final DatabaseReference database;

    private FirebaseHelper() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        this.database = firebaseDatabase.getReference();
    }

    DatabaseReference getDatabaseReference(String... children) {
        DatabaseReference currentRef = this.database;

        for (String child: children) {
            currentRef = currentRef.child(child);
        }

        return currentRef;
    }

    void setDatabaseValue(Object value, String... path) {
        DatabaseReference currentRef = this.getDatabaseReference(path);
        currentRef.setValue(value);
    }
}