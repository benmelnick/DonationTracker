package com.example.benmelnick.donationtracker;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The activity which allows users to search for an item
 */
public class SearchActivity extends AppCompatActivity {

    private Spinner mChooseLocation;
    private Spinner mCategory;
    private TextInputEditText mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mChooseLocation = findViewById(R.id.location_spinner);
        mCategory = findViewById(R.id.category_spinner);
        mItem = findViewById(R.id.itemText);

        //populate spinner with location names
        List<String> legalLocations = populateSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, legalLocations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mChooseLocation.setAdapter(adapter);

        List<String> legalCategories = Arrays.asList("Choose a category", "Clothing",
                "Shoes", "Hats", "Accessories");
        ArrayAdapter<String> adapterCategories = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, legalCategories);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategory.setAdapter(adapterCategories);

        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    Object selectedLocation = mChooseLocation.getSelectedItem();
                    Editable itemText = mItem.getText();
                    Object selectedCategory = mCategory.getSelectedItem();

                    if ((selectedLocation != null) && (itemText != null)
                            && (selectedCategory != null)) {
                        //link to an activity of the search results
                        //send location name, category, and item name to the intent
                        String location = selectedLocation.toString();
                        String item = itemText.toString();
                        String category = selectedCategory.toString();

                        Intent intent = new Intent(
                                SearchActivity.this, SearchResultsActivity.class);
                        intent.putExtra("location", location);
                        intent.putExtra("item", item);
                        intent.putExtra("category", category);

                        startActivity(intent);
                    }
                }
            }
        });
    }

    /**
     * iterates through every child in "locations" database and adds it to an arraylist
     * @return array list with "All locations" and individual location names
     */
    private List<String> populateSpinner() {
        final List<String> locationNames = new ArrayList<>();
        locationNames.add("All locations");

        DatabaseReference locs = FirebaseHelper.INSTANCE.getDatabaseReference("locations");
        locs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    DataSnapshot nameSnap = ds.child("name");
                    String locationName = nameSnap.getValue(String.class);
                    locationNames.add(locationName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return locationNames;
    }

    /**
     * checks to see if at least one of the fields has been entered
     * @return true if form can be submitted
     */
    private boolean validateForm() {
        Object selectedCategory = mCategory.getSelectedItem();
        String catString = selectedCategory.toString();

        Editable itemText = mItem.getText();

        if ("Choose a category".equals(catString) && (itemText != null)
                && (itemText.length() == 0)) {
            Toast toast = Toast.makeText(SearchActivity.this,
                    "You must apply one of the two possible filters.",
                    Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
        return true;
    }
}
