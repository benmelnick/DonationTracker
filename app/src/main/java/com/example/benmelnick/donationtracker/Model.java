package com.example.benmelnick.donationtracker;

import android.util.Log;

import com.example.benmelnick.donationtracker.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robertwaters on 9/18/17.
 */

public class Model {
    public static final Model INSTANCE = new Model();

    private List<Location> items;

    private Model() {
        items = new ArrayList<>();
    }

    public void addItem(Location item) {
        items.add(item);
    }

    public List<Location> getItems() {
        return items;
    }

    public Location findItemById(int id) {
        for (Location d : items) {
            if (d.getId() == id) return d;
        }
        Log.d("MYAPP", "Warning - Failed to find id: " + id);
        return null;
    }
}
