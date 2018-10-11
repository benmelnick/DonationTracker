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

public class MainContentActivity extends AppCompatActivity {

    private static final String TAG = "DonationTracker";

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

        Button mLogout = (Button) findViewById(R.id.logout_button);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        readFile();
    }

    /**
     * reads in data from CSV file and creates locations
     * adds each location to model
     */
    private void readFile() {
        Model model = Model.INSTANCE;
        try {
            //Open a stream on the raw file
            InputStream is = getResources().openRawResource(R.raw.locationdata);
            //From here we probably should call a model method and pass the InputStream
            //Wrap it in a BufferedReader so that we get the readLine() method
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            br.readLine(); //get rid of header line
            while ((line = br.readLine()) != null) {
                Log.d(MainContentActivity.TAG, line);
                String[] tokens = line.split(",");
                int id = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                double lat = Double.parseDouble(tokens[2]);
                double lon = Double.parseDouble(tokens[3]);
                String address = tokens[4];
                String city = tokens[5];
                String state = tokens[6];
                String zip = tokens[7];
                String type = tokens[8];
                String phone = tokens[9];
                String web = tokens[10];
                model.addItem(new Location(id, name, lat, lon, address, city, state, zip, type, phone, web));
            }
            br.close();
        } catch (IOException e) {
            Log.e(MainContentActivity.TAG, "error reading assets", e);
        }
    }
}