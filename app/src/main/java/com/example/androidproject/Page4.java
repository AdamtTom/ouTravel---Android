package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Page4 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page4);
        ImageButton button = (ImageButton) findViewById(R.id.Page4_imageButton);
        button.setOnClickListener(view -> {
            Thread t = new Thread(() -> {
                try {
                    next();
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
            t.start();
        });
    }

    public  void next(){
        Intent intent = new Intent(this, Page6.class);
        startActivity(intent);
    }
}