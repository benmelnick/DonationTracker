package com.example.benmelnick.donationtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            //send item to fragment
            arguments.putSerializable(ItemDetailFragment.ARG_ITEM,
                    getIntent().getSerializableExtra(ItemDetailFragment.ARG_ITEM));
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.linear, fragment).commit();
        }
    }
}
