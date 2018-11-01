package com.example.benmelnick.donationtracker;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robertwaters on 9/18/17.
 */

public class Model {
    public static final Model INSTANCE = new Model();

    private List<Location> locations;

    private Model() {
        locations = new ArrayList<>();
    }

    public void addLocation(Location item) {
        locations.add(item);
    }

    public List<Location> getLocations() {
        return locations;
    }

    public Location findLocationById(int id) {
        for (Location d : locations) {
            if (d.getId() == id) return d;
        }
        Log.d("MYAPP", "Warning - Failed to find id: " + id);
        return null;
    }

    public boolean isEmpty() {
        return locations.isEmpty();
    }
}
