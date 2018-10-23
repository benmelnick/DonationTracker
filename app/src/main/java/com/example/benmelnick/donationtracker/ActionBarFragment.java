package com.example.benmelnick.donationtracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

public abstract class ActionBarFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("DonationTracker");
        setActionBarSubtitle("");
    }

    public ActionBar getActionBar() {
        return ((MainActivity) getActivity()).getSupportActionBar();
    }

    public void setActionBarTitle(String title) {
        getActionBar().setTitle(title);
    }

    public void setActionBarSubtitle(String subtitle) {
        getActionBar().setSubtitle(subtitle);
    }

    public void setActionBarVisible(boolean visible) {
        ActionBar actionBar = getActionBar();
        if (visible) {
            actionBar.show();
        } else {
            actionBar.hide();
        }
    }
}