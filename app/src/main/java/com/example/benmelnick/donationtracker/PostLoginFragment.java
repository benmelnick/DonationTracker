package com.example.benmelnick.donationtracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import androidx.navigation.Navigation;


public class PostLoginFragment extends ActionBarFragment {

    private FirebaseAuth mAuth;

    public PostLoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PostLoginFragment.
     */
    public static PostLoginFragment newInstance() {
        PostLoginFragment fragment = new PostLoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_post_login, container, false);

        Button mLogout = (Button) rootView.findViewById(R.id.logout_button);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Navigation.findNavController(view).navigate(R.id.action_postLoginFragment_to_loginFragment);
            }
        });

        Button goToList = (Button) rootView.findViewById(R.id.viewLocations);
        goToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_postLoginFragment_to_locationListFragment);
            }
        });

        setActionBarTitle("Welcome to DonationTracker");
        setActionBarSubtitle("");

        return rootView;
    }
}
