package com.example.benmelnick.donationtracker;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

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
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mShort = (TextInputEditText)findViewById(R.id.short_description);
        mFull = (TextInputEditText)findViewById(R.id.full_description);
        mValue = (TextInputEditText)findViewById(R.id.value);
        mCategory = (Spinner)findViewById(R.id.category);

        Button submit = (Button)findViewById(R.id.submit);

        List<String> legalCategories = Arrays.asList("Choose a category", "Clothing", "Shoes", "Hats", "Accessories");
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, legalCategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategory.setAdapter(adapter);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            int item_id = getArguments().getInt(ARG_ITEM_ID);
            mLocation = Model.INSTANCE.findItemById(item_id);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
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
        SimpleDateFormat dateFormat;

        //need time stamp
        try {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            dateFormat.format(new Date()); // Find todays date
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        //add new item to database
        Item item = new Item(dateFormat.toString(), mLocation, shortDescription, fullDescription, value, category);
        mDatabase.child("locations").child(mLocation.getName()).child(shortDescription).setValue(item);
    }
}
