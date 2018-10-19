package com.example.benmelnick.donationtracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.Button;

public class LocationDetailActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    void makeButtonVisible() {
        final Button addBtn = (Button) findViewById(R.id.add_item);

        //make the button visible
        addBtn.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("users").child(id).child("accountType").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = (String)dataSnapshot.getValue();
                if (value.equals("Location Employee")) {
                    System.out.println("Equals employee");
                    makeButtonVisible();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(LocationDetailFragment.ARG_ITEM_ID,
                    getIntent().getIntExtra(LocationDetailFragment.ARG_ITEM_ID, 1000));
            LocationDetailFragment fragment = new LocationDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.linear, fragment).commit();
        }
    }
}
