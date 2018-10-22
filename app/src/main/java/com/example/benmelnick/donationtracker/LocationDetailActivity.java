package com.example.benmelnick.donationtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LocationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(LocationDetailFragment.ARG_ITEM_ID,
                    getIntent().getIntExtra(LocationDetailFragment.ARG_ITEM_ID, 1000));
            // pass the entire location object rather than its ID number
            arguments.putSerializable(LocationDetailFragment.ARG_LOCATION,
                    getIntent().getSerializableExtra(LocationDetailFragment.ARG_LOCATION));
            LocationDetailFragment fragment = new LocationDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.linear, fragment).commit();
        }

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Location Information");
    }
}
