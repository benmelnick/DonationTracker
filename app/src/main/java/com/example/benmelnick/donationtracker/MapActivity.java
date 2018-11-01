package com.example.benmelnick.donationtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private List<Location> locations;

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

        locations = Model.INSTANCE.getLocations();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (locations.size() > 0) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            LatLng coordinates = null;
            for (Location donationCenter : locations) {
                coordinates = new LatLng(donationCenter.getLatitude(), donationCenter.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(coordinates).title(donationCenter.getName()).snippet(donationCenter.getPhoneNumber()));
                builder.include(coordinates);
            }

            try {
                // Center the map around all the donation centers
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 0));
                googleMap.moveCamera(CameraUpdateFactory.zoomTo(10));
            } catch (Exception e) {
                Log.e("MapActivity", "Failed setting map bounds to all markers, falling back to last marker.");
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
            }
        }
    }
}
