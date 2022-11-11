package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Page3 extends AppCompatActivity {
    ///////////////////////////////////////////////////
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
        Button button = findViewById(R.id.Page3_button1);
        button.setOnClickListener(view -> next());

        //////////////////////////////////////////////////////
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        //////////////////////////////////////////////////////
    }

    public void next(){
        /////////////////////////////////////////////////////////////
        ArrayList<City> cities = new ArrayList<>();
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, Page4.class);
        databaseReference.child("City").addListenerForSingleValueEvent(new ValueEventListener() {
//            int counter = 0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    City city = snapshot.getValue(City.class);
                    cities.add(city);
//                    Toast.makeText(Page3.this, " " + cities.size(), Toast.LENGTH_SHORT).show();
//                    counter++;
//                    Toast.makeText(Page3.this, city.getCountry(), Toast.LENGTH_LONG).show();
//                    if (counter == 2) {
//                        break;
//                    }
                }
                bundle.putParcelableArrayList("cities", cities);
                intent.putExtras(bundle);
                startActivity(intent);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        Toast.makeText(Page3.this, cities.get(0).getCountry(), Toast.LENGTH_LONG).show();

//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList("cities", cities);
//        /////////////////////////////////////////////////////////////
//
//
//        Intent intent = new Intent(this, Page4.class);
//
//        /////////////////////////////////////////////////////////////
//        intent.putExtras(bundle);
//        /////////////////////////////////////////////////////////////
//
//
//        startActivity(intent);
    }
}
