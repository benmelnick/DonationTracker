package com.example.benmelnick.donationtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ArrayList<Location> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_map);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null && extras.containsKey("Locations")) {
            locations = (ArrayList<Location>) extras.getSerializable("Locations");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        for (Location donationCenter: locations) {
            LatLng coordinates = new LatLng(donationCenter.getLatitude(), donationCenter.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(coordinates).title(donationCenter.getName()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
        }
    }
}
