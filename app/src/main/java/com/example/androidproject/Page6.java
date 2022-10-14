package com.example.androidproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Page6 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page6);


        Page6Fragment fragment1 = new Page6Fragment();
        // obtain the manager for Fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        // FragmentTransaction is an API for performing a set of Fragment operations
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // replace an existing Fragment that was added to a container
        fragmentTransaction.replace(R.id.linear_layout, fragment1).commit();
        // an optional String tag name for the fragment can be added as the third parameter
        // to later retrieve the fragment with FragmentManager.findFragmentByTag(String)
//        fragmentTransaction.replace(R.id.linear_layout, fragment1, "myFragment1").commit();


//        Page6Fragment fragment1 = new Page6Fragment();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.linear_layout, fragment1).commit();
//
//
//        Button button = findViewById(R.id.page6_button);
//        Button.setOnClickListener(view -> {
//            Intent intent = new Intent(this, Page7.class);
//            startActivity(intent);
//        });
    }
}