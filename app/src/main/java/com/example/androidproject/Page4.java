package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

public class Page4 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page4);
        Button button = findViewById(R.id.Page4_button);
        button.setOnClickListener(view -> next());
    }

    public void next(){
        Intent intent = new Intent(this, Page6.class);
        startActivity(intent);
    }
}