package com.example.benmelnick.donationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * The activity which shows information about a location
 */
public class LocationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            Intent intent = getIntent();

            arguments.putInt(LocationDetailFragment.ARG_ITEM_ID,
                    intent.getIntExtra(LocationDetailFragment.ARG_ITEM_ID, 1000));
            // pass the entire location object rather than its ID number
            arguments.putSerializable(LocationDetailFragment.ARG_LOCATION,
                    intent.getSerializableExtra(LocationDetailFragment.ARG_LOCATION));

            LocationDetailFragment fragment = new LocationDetailFragment();
            fragment.setArguments(arguments);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.linear, fragment);
            transaction.commit();
        }

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Location Information");
        }
    }
}
