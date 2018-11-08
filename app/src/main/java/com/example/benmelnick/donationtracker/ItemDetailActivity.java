package com.example.benmelnick.donationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * The activity which shows information about a donated item
 */
public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            Intent intent = getIntent();
            //send item to fragment
            arguments.putSerializable(ItemDetailFragment.ARG_ITEM,
                    intent.getSerializableExtra(ItemDetailFragment.ARG_ITEM));

            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.linear, fragment);
            transaction.commit();
        }

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Item Information");
        }
    }
}
