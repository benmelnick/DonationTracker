package com.example.benmelnick.donationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import com.google.firebase.auth.FirebaseAuth;

public class MainContentActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainContentActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

        mAuth = FirebaseAuth.getInstance();

        Button mLogout = (Button) findViewById(R.id.logout_button);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(MainContentActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button goToList = (Button) findViewById(R.id.viewLocations);
        goToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainContentActivity.this, LocationListActivity.class);
                startActivity(intent);
            }
        });
    }
}