package com.example.benmelnick.donationtracker;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Button;

import org.w3c.dom.Text;

public class AddItemActivity extends AppCompatActivity {

    private TextInputEditText mShort;
    private TextInputEditText mLong;
    private TextInputEditText mValue;
    private Spinner mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mShort = (TextInputEditText)findViewById(R.id.short_description);
        mLong = (TextInputEditText)findViewById(R.id.full_description);
        mValue = (TextInputEditText)findViewById(R.id.value);
        mCategory = (Spinner)findViewById(R.id.category);

        Button submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateForm();
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }

    private void validateForm() {
        //validates the inputs in the form
    }
}
