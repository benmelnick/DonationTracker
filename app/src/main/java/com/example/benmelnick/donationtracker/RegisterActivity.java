package com.example.benmelnick.donationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;

import java.util.Arrays;
import java.util.List;

/**
 * The activity for registering a new account
 */
@SuppressWarnings("CyclicClassDependency")
public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    //UI references
    private TextInputEditText mEmail;
    private EditText mPassword1;
    private EditText mPassword2;
    private EditText mName;
    private Spinner mType;

    private FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();

        mEmail = findViewById(R.id.register_email);
        mPassword1 = findViewById(R.id.register_password1);
        mPassword2 = findViewById(R.id.register_password2);
        mName = findViewById(R.id.register_name);
        mType = findViewById(R.id.register_type);

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        List<String> legalAccounts = Arrays.asList("Choose an account", "Admin",
                "Manager", "Location Employee", "User");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, legalAccounts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mType.setAdapter(adapter);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Register");
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmail.setError(null);
        mPassword1.setError(null);
        mPassword2.setError(null);
        mName.setError(null);

        Editable emailText = mEmail.getText();
        Editable passwordText = mPassword1.getText();
        Editable passwordConfText = mPassword2.getText();
        Editable nameText = mName.getText();
        Object mTypeSelected = mType.getSelectedItem();

        if ((emailText != null) && (passwordText != null)
                && (passwordConfText != null) && (nameText != null) && (mTypeSelected != null)) {
            if (nameText.length() == 0) {
                mName.setError("This field is required.");
                mName.requestFocus();
            } else if ("Choose an account type".equals(mTypeSelected.toString())) {
                ((TextView) mType.getSelectedView()).setError("Must select an account type");
                mType.requestFocus();
            } else if (passwordText.length() == 0) {
                mPassword1.setError("This field is required");
                mPassword1.requestFocus();
            } else if (passwordConfText.length() == 0) {
                mPassword2.setError("This field is required");
                mPassword2.requestFocus();
            } else if (isPasswordInvalid(passwordText)) {
                mPassword1.setError("This password is too short");
                mPassword1.requestFocus();
            } else if (!passwordText.equals(passwordConfText)) {
                mPassword2.setError("Passwords must match.");
                mPassword2.requestFocus();
            } else if (isEmailInvalid(emailText)) {
                mEmail.setError("This email address is invalid");
                mEmail.requestFocus();
            } else {
                signUp();
            }
        }
    }

    private void signUp() {
        Editable emailText = mEmail.getText();
        Editable passwordText = mPassword1.getText();

        if ((emailText != null) && (passwordText != null)) {
            String email = emailText.toString();
            String password = passwordText.toString();

            Task<AuthResult> registerTask = mAuth.createUserWithEmailAndPassword(email, password);

            registerTask.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());

                    if (task.isSuccessful()) {
                        AuthResult result = task.getResult();

                        if (result != null) {
                            onAuthSuccess(result.getUser());
                        }
                    } else {
                        Toast toast = Toast.makeText(RegisterActivity.this,
                                "Sign Up Failed", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });
        }
    }

    private void onAuthSuccess(UserInfo user) {
        Editable mNameText = mName.getText();
        Object mTypeSelected = mType.getSelectedItem();

        if ((mNameText != null) && (mTypeSelected != null)) {
            //create new user and add to database
            String id = user.getUid();
            String email = user.getEmail();
            String name = mNameText.toString();
            String type = mTypeSelected.toString();
            User newUser = new User(id, email, name, type);

            FirebaseHelper.INSTANCE.setDatabaseValue(newUser, "users", id);

            // Go to MainActivity
            startActivity(new Intent(RegisterActivity.this, MainContentActivity.class));
            finish();
        }
    }

    private boolean isPasswordInvalid(CharSequence password) {
        return (password == null) || (password.length() <= 4);
    }

    private boolean isEmailInvalid(CharSequence email) {
        if ((email == null) || (email.length() <= 5)) {
            return true;
        } else {
            String emailString = email.toString();
            return !emailString.contains("@");
        }
    }

}


