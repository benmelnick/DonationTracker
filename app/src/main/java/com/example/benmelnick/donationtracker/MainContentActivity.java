package com.example.benmelnick.donationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainContentActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainContentActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
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

        Button search = (Button)findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainContentActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }
}