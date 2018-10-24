package com.example.benmelnick.donationtracker;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private Spinner mChooseLocation;
    private TextInputEditText mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mChooseLocation = (Spinner)findViewById(R.id.location_spinner);
        mItem = (TextInputEditText)findViewById(R.id.itemText);

        //populate spinner with location names
        List<String> legalCategories = populateSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, legalCategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mChooseLocation.setAdapter(adapter);

        Button submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //link to an activity of the search results
                //send location name and item name to the intent
                String location = mChooseLocation.getSelectedItem().toString();
                String item = mItem.getText().toString();
                Intent intent = new Intent(SearchActivity.this, SearchResultsActivity.class);
                intent.putExtra("location", location);
                intent.putExtra("item", item);
                startActivity(intent);
            }
        });
    }

    /**
     * iterates through every child in "locations" database and adds it to an arraylist
     * @return array list with "All locations" and individual location names
     */
    private ArrayList<String> populateSpinner() {
        final ArrayList<String> locationNames = new ArrayList<>();
        locationNames.add("All locations");
        mDatabase.child("locations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String locationName = ds.child("name").getValue().toString();
                    locationNames.add(locationName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return locationNames;
    }
}
