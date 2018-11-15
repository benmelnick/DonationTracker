package com.example.benmelnick.donationtracker;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by robertwaters on 9/18/17.
 */

public final class Model {
    public static final Model INSTANCE = new Model();

    private final List<Location> locations;

    private Model() {
        locations = new ArrayList<>();
    }

    /**
     * @param item The new location to add to the list
     */
    public void addLocation(Location item) {
        locations.add(item);
    }

    /**
     * @return The list of locations
     */
    public final List<Location> getLocations() {
        return Collections.unmodifiableList(locations);
    }

    /**
     * @param id The location id to search for
     * @return The location with the given id
     */
    public Location findLocationById(int id) {
        for (Location d : locations) {
            if (d.getId() == id) {
                return d;
            }
        }

        Log.d("MYAPP", "Warning - Failed to find id: " + id);
        return null;
    }

    /**
     * @return true if the location list is empty
     */
    public boolean isEmpty() {
        return locations.isEmpty();
    }
}
