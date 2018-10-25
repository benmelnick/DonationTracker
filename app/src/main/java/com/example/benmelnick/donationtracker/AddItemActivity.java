package com.example.benmelnick.donationtracker;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AddItemActivity extends AppCompatActivity {

    public static final String ARG_ITEM_ID = "item_id";

    private TextInputEditText mShort;
    private TextInputEditText mFull;
    private TextInputEditText mValue;
    private Spinner mCategory;
    private Location mLocation;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mShort = (TextInputEditText)findViewById(R.id.short_description);
        mFull = (TextInputEditText)findViewById(R.id.full_description);
        mValue = (TextInputEditText)findViewById(R.id.value);
        mCategory = (Spinner) findViewById(R.id.category);

        Button submit = (Button)findViewById(R.id.submit);

        List<String> legalCategories = Arrays.asList("Choose a category", "Clothing", "Shoes", "Hats", "Accessories");
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, legalCategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategory.setAdapter(adapter);

        int locationId = getIntent().getIntExtra("id", 0);
        mLocation = Model.INSTANCE.findLocationById(locationId);
    }

    public void validateForm(View v) {
        //validates the inputs in the form
        mShort.setError(null);
        mFull.setError(null);
        mValue.setError(null);

        boolean cancel = false;
        View focusView = null;

        String shortDescription = mShort.getText().toString();
        String fullDescription = mFull.getText().toString();
        double value = 0;

        //check for valid inputs
        if (TextUtils.isEmpty(shortDescription)) {
            mShort.setError("This field is required.");
            cancel = true;
            focusView = mShort;
        }

        if (TextUtils.isEmpty(fullDescription)) {
            mFull.setError("This field is required.");
            cancel = true;
            focusView = mFull;
        }

        if (TextUtils.isEmpty(mValue.getText().toString())) {
            mValue.setError("This field is required.");
            cancel = true;
            focusView = mValue;
        }

        try {
            value = Double.parseDouble(mValue.getText().toString());
        } catch (NumberFormatException e) {
            mValue.setError("The value must be a number.");
            cancel = true;
            focusView = mValue;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            addNewItem();
            Toast.makeText(AddItemActivity.this, "New Inventory Added",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * creates new item and adds to database
     */
    private void addNewItem() {
        String shortDescription = mShort.getText().toString();
        String fullDescription = mFull.getText().toString();
        double value = Double.parseDouble(mValue.getText().toString());
        String category = mCategory.getSelectedItem().toString();
        String location = mLocation.getName();
        SimpleDateFormat dateFormat;

        //need time stamp
        String currentDate;
        try {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            currentDate = dateFormat.format(new Date()); // Find todays date
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        //add new item to database
        Item item = new Item(currentDate, shortDescription, fullDescription, value, category, location);

        //updates info for location
        //mDatabase.child("locations").child(mLocation.getName()).setValue(mLocation);
        //adds new item to location's sub-database of items
        mDatabase.child("locations").child(mLocation.getName()).child("inventory").child(shortDescription).setValue(item);
    }
}
