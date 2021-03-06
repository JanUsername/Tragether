package com.example.tragether;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import com.example.tragether.model.Travel;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends MenuHandler {

    ImageView imageView;

    Button editProfile;
    Button addTravel;
    Button addEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_profile);

        imageView = findViewById(R.id.imageView1);
        int imageRes = getResources().getIdentifier("@drawable/cat", null, this.getPackageName());
        imageView.setImageResource(imageRes);

        editProfile = findViewById(R.id.btnGoToProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
            }
        });

        addTravel = findViewById(R.id.btnAddTravelP);
        addTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, TravelActivity.class));
            }
        });
        addEvent = findViewById(R.id.btnAddEventP);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, EventActivity.class));
            }
        });


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = new Fragment();
        Fragment fragment2 = new Fragment();
        fragmentTransaction.add(R.id.yourEventsFragment, fragment, "fragment Events");
        fragmentTransaction.add(R.id.yourTripsFragment, fragment2, "fragment Trip");
        fragmentTransaction.commit();


    }
/*
    @Override
    public void onStart() {
        super.onStart();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new Fragment();
        Fragment fragment2 = new Fragment();
        fragmentTransaction.add(R.id.yourEventsFragment, fragment, "fragment Events");
        fragmentTransaction.add(R.id.yourTripsFragment, fragment2, "fragment Trip");

        fragmentTransaction.commit();
    }
*/
    @Override
    protected void onResume() {
        super.onResume();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new Fragment();
        Fragment fragment2 = new Fragment();
        fragmentTransaction.add(R.id.yourEventsFragment, fragment, "fragment Events");
        fragmentTransaction.add(R.id.yourTripsFragment, fragment2, "fragment Trip");

        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProfileActivity.this, logged_activity.class));
    }

}
