package com.example.benmelnick.donationtracker;

import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The activity shown post login
 */
@SuppressWarnings("CyclicClassDependency")
public class MainContentActivity extends AppCompatActivity {
    private static final String TAG = "MainContentActivity";

    private TextView mMessage;

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

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mMessage = findViewById(R.id.message);

        String userId = mAuth.getUid();
        if (userId != null) {
            DatabaseReference userRef = FirebaseHelper.INSTANCE
                    .getDatabaseReference("users", userId);
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    DataSnapshot nameSnap = dataSnapshot.child("name");
                    String userName = nameSnap.getValue(String.class);
                    String welcome = "Welcome, " + userName + "!";
                    mMessage.setText(welcome);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if (Model.INSTANCE.isEmpty()) {
            readFile();
        }

        Button mLogout = findViewById(R.id.logout_button);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(MainContentActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button goToList = findViewById(R.id.viewLocationList);
        goToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainContentActivity.this, LocationListActivity.class);
                startActivity(intent);
            }
        });

        Button goToMap = findViewById(R.id.viewLocationMap);
        goToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainContentActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        Button search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainContentActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * reads in data from CSV file and creates locations
     * adds each location to model
     */
    private void readFile() {
        Model model = Model.INSTANCE;
        try {
            //Open a stream on the raw file
            Resources resources = getResources();
            InputStream is = resources.openRawResource(R.raw.locationdata);
            //From here we probably should call a model method and pass the InputStream
            //Wrap it in a BufferedReader so that we get the readLine() method
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            br.readLine(); //get rid of header line
            line = br.readLine();
            while (line != null) {
                Log.d(TAG, line);
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
                model.addLocation(new Location(id, name, lat, lon, address,
                        city, state, zip, type, phone, web));
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            Log.e(TAG, "error reading assets", e);
        }
    }
}