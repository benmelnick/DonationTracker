package com.example.benmelnick.donationtracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.graphics.Color;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    //UI references
    private TextInputEditText mEmail;
    private EditText mPassword1;
    private EditText mPassword2;
    private EditText mName;
    private Spinner mType;

    //boolean to see if authentication has already been attempted
    private boolean mAuthTask = false;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = (TextInputEditText) findViewById(R.id.register_email);
        mPassword1 = (EditText) findViewById(R.id.register_password1);
        mPassword2 = (EditText) findViewById(R.id.register_password2);
        mName = (EditText) findViewById(R.id.register_name);
        mType = (Spinner) findViewById(R.id.register_type);

        Button registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        List<String> legalAccounts = Arrays.asList("Choose an account", "Admin", "Manager", "Location Employee", "User");
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, legalAccounts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mType.setAdapter(adapter);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask) {
            return;
        }

        // Reset errors.
        mEmail.setError(null);
        mPassword1.setError(null);
        mPassword2.setError(null);
        mName.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmail.getText().toString();
        String password1 = mPassword1.getText().toString();
        String password2 = mPassword2.getText().toString();
        String name = mName.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //check for a name
        if (TextUtils.isEmpty(name)) {
            mName.setError("This field is required.");
            focusView = mName;
            cancel = true;
        }

        //check for valid account type
        if(mType.getSelectedItem().toString().equals("Choose an account type")) {
            ((TextView)mType.getSelectedView()).setError("Must select an account type");
            focusView = mType;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password1)) {
            mPassword1.setError(getString(R.string.error_field_required));
            focusView = mPassword1;
            cancel = true;
        }
        if (TextUtils.isEmpty(password2)) {
            mPassword2.setError(getString(R.string.error_field_required));
            focusView = mPassword2;
            cancel = true;
        }
        if (!TextUtils.isEmpty(password1) && !isPasswordValid(password1)) {
            mPassword1.setError(getString(R.string.error_invalid_password));
            focusView = mPassword1;
            cancel = true;
        }
        if (!TextUtils.isEmpty(password2) && !isPasswordValid(password2)) {
            mPassword2.setError(getString(R.string.error_invalid_password));
            focusView = mPassword2;
            cancel = true;
        }
        if (!password1.equals(password2)) {
            mPassword1.setError("Passwords must match.");
            focusView = mPassword1;
            cancel = true;
        }

        // Check for a valid email
        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            cancel = true;
        }
        if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            //add the user to login credentials
            LoginActivity.DUMMY_CREDENTIALS.add(email + ":" + password1);
            mAuthTask = true;
            Intent intent = new Intent(RegisterActivity.this, MainContentActivity.class);
            startActivity(intent);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

}


