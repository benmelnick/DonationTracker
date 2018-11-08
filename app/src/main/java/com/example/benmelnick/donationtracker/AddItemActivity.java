package com.example.benmelnick.donationtracker;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * The activity for adding a new item to a donation center's inventory
 */
public class AddItemActivity extends AppCompatActivity {

    private TextInputEditText mShort;
    private TextInputEditText mFull;
    private TextInputEditText mValue;
    private Spinner mCategory;
    private Location mLocation;

    //private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //mDatabase = firebaseDatabase.getReference();

        mShort = findViewById(R.id.short_description);
        mFull = findViewById(R.id.full_description);
        mValue = findViewById(R.id.value);
        mCategory = findViewById(R.id.category);

        List<String> legalCategories = Arrays.asList("Choose a category", "Clothing",
                "Shoes", "Hats", "Accessories");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, legalCategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategory.setAdapter(adapter);

        Intent intent = getIntent();
        if (intent != null) {
            int locationId = intent.getIntExtra("id", 0);
            mLocation = Model.INSTANCE.findLocationById(locationId);
        }

        Button submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateForm();
            }
        });
    }

    private void validateForm() {
        //validates the inputs in the form
        mShort.setError(null);
        mFull.setError(null);
        mValue.setError(null);

        Editable mShortText = mShort.getText();
        Editable mFullText = mFull.getText();
        Editable mValueText = mValue.getText();

        //check for valid inputs
        if ((mShortText == null) || (mShortText.length() == 0)) {
            mShort.setError("This field is required.");
            mShort.requestFocus();
        } else if ((mFullText == null) || (mFullText.length() == 0)) {
            mFull.setError("This field is required.");
            mFull.requestFocus();
        } else if ((mValueText == null) || (mValueText.length() == 0)) {
            mValue.setError("This field is required.");
            mValue.requestFocus();
        } else {
            String value = mValueText.toString();

            try {
                //noinspection ResultOfMethodCallIgnored
                Double.parseDouble(value);

                // this will only execute if value is a number
                addNewItem();
                Toast notification = Toast.makeText(AddItemActivity.this,
                        "New Inventory Added", Toast.LENGTH_SHORT);
                notification.show();
            } catch (NumberFormatException e) {
                mValue.setError("The value must be a number.");
                mValue.requestFocus();
            }
        }
    }

    /**
     * creates new item and adds to database
     */
    private void addNewItem() {
        Editable mShortText = mShort.getText();
        Editable mFullText = mFull.getText();
        Editable mValueText = mValue.getText();
        Object mCatSelected = mCategory.getSelectedItem();
        String location = mLocation.getName();

        if ((mShortText != null) && (mFullText != null) && (mValueText != null)
                && (mCatSelected != null) && (location != null)) {
            String shortDescription = mShortText.toString();
            String fullDescription = mFullText.toString();
            double value = Double.parseDouble(mValueText.toString());
            String category = mCatSelected.toString();
            SimpleDateFormat dateFormat;

            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String currentDate = dateFormat.format(new Date()); // Find todays date

            //add new item to database
            Item item = new Item(currentDate, shortDescription,
                    fullDescription, value, category, location);

            //updates info for location
            //adds new item to location's sub-database of items
            //mDatabase.child("locations").child(location).child("inventory")
            //        .child(shortDescription).setValue(item);
            //DatabaseReference newItemRef = FirebaseHelper.INSTANCE.getDatabaseReference(
            //        "locations", location, "inventory", shortDescription);
            //newItemRef.setValue(item);
            FirebaseHelper.INSTANCE.setDatabaseValue(item, "locations",
                    location, "inventory", shortDescription);
        }
    }
}
