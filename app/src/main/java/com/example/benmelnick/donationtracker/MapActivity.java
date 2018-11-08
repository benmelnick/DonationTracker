package com.example.benmelnick.donationtracker;

import android.support.v4.app.FragmentManager;
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

/**
 * The activity which shows a map of donation centers
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private List<Location> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_map);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment mapFragment =
                (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        locations = Model.INSTANCE.getLocations();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (!locations.isEmpty()) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            LatLng coordinates = null;
            for (Location donationCenter : locations) {
                coordinates = new LatLng(donationCenter.getLatitude(),
                        donationCenter.getLongitude());

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(coordinates);
                markerOptions.title(donationCenter.getName());
                markerOptions.snippet(donationCenter.getPhoneNumber());

                googleMap.addMarker(markerOptions);
                builder.include(coordinates);
            }

            try {
                // Center the map around all the donation centers
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 0));
                googleMap.moveCamera(CameraUpdateFactory.zoomTo(10));
            } catch (Exception e) {
                Log.e("MapActivity",
                        "Failed setting map bounds to all markers, using fall back.");
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
            }
        }
    }
}
