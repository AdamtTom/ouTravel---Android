package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        // addData();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.startBtn);
        btn.setOnClickListener(v -> start());
    }

    private void addData(){
        City vancouver = new City();
        vancouver.setName("Vancouver");
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("nature");
        tags.add("partying");
        vancouver.setTags(tags);
        vancouver.setWeather("rainy");
        vancouver.setDescription("A place where a rainy day is just a day.");
        vancouver.setImage(R.drawable.vancouver);

        String id = databaseReference.push().getKey();
        databaseReference.child("City").child(id).setValue(vancouver);
    }

    public void start(){
        Intent intent = new Intent(this, Page2.class);
        startActivity(intent);
    }
}