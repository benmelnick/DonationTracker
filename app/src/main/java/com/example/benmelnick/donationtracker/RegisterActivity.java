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
import android.widget.TextView;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {

    //UI references
    private TextInputEditText mUsername;
    private EditText mPassword1;
    private EditText mPassword2;


    //authentication for signing in - uses a class from LoginActivity
    private UserLoginTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsername = (TextInputEditText) findViewById(R.id.register_username);
        mPassword1 = (EditText) findViewById(R.id.register_password1);
        mPassword2 = (EditText) findViewById(R.id.register_password2);

        Button registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsername.setError(null);
        mPassword1.setError(null);
        mPassword2.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsername.getText().toString();
        String password1 = mPassword1.getText().toString();
        String password2 = mPassword2.getText().toString();

        boolean cancel = false;
        View focusView = null;

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

        // Check for a valid username
        if (TextUtils.isEmpty(username)) {
            mUsername.setError(getString(R.string.error_field_required));
            focusView = mUsername;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mAuthTask = new UserLoginTask(username, password1);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                finish();
                startActivity(new Intent(RegisterActivity.this, MainContentActivity.class));
            } else {
                mPassword1.setError(getString(R.string.error_incorrect_password));
                mPassword1.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}


