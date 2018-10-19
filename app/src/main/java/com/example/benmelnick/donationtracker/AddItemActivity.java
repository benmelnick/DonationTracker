package com.example.benmelnick.donationtracker;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {

    private TextInputEditText mShort;
    private TextInputEditText mFull;
    private TextInputEditText mValue;
    private Spinner mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mShort = (TextInputEditText)findViewById(R.id.short_description);
        mFull = (TextInputEditText)findViewById(R.id.full_description);
        mValue = (TextInputEditText)findViewById(R.id.value);
        mCategory = (Spinner) findViewById(R.id.category);

        Button submit = (Button)findViewById(R.id.submit);

        List<String> legalCategories = Arrays.asList("Choose a category", "Clothing", "Shoes", "Hats", "Accessories");
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, legalCategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategory.setAdapter(adapter);
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
    }
}
