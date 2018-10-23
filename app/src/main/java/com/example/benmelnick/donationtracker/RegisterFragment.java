package com.example.benmelnick.donationtracker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

import androidx.navigation.fragment.NavHostFragment;


public class RegisterFragment extends ActionBarFragment {

    //UI references
    private TextInputEditText mEmail;
    private EditText mPassword1;
    private EditText mPassword2;
    private EditText mName;
    private Spinner mType;

    //boolean to see if authentication has already been attempted
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RegisterFragment.
     */
    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        mEmail = (TextInputEditText) rootView.findViewById(R.id.register_email);
        mPassword1 = (EditText) rootView.findViewById(R.id.register_password1);
        mPassword2 = (EditText) rootView.findViewById(R.id.register_password2);
        mName = (EditText) rootView.findViewById(R.id.register_name);
        mType = (Spinner) rootView.findViewById(R.id.register_type);

        Button registerButton = (Button) rootView.findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        setActionBarTitle("DonationTracker");
        setActionBarSubtitle("Register");

        return rootView;
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
            ((TextView) mType.getSelectedView()).setError("Must select an account type");
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
            signUp();
        }
    }

    private void signUp() {
        String email = mEmail.getText().toString();
        String password = mPassword1.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser());
                } else {
                    Toast.makeText(getContext(), "Sign Up Failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onAuthSuccess(FirebaseUser user) {
        //create new user and add to database
        String id = user.getUid();
        String email = user.getEmail();
        String name = mName.getText().toString();
        String type = mType.getSelectedItem().toString();
        User newUser = new User(id, email, name, type);

        mDatabase.child("users").child(id).setValue(newUser);

        NavHostFragment.findNavController(this).navigate(R.id.action_registerFragment_to_postLoginFragment);
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
}
