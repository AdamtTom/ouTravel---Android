package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
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
//        Button button = findViewById(R.id.Page3_button1);
        Intent intent = getIntent();
        Bundle b = intent.getBundleExtra("bundle");
//        button.setOnClickListener(view -> next(b));

        //////////////////////////////////////////////////////
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        //////////////////////////////////////////////////////


        CheckBox check_box_nature = findViewById(R.id.Page3_checkBox);
        CheckBox check_box_culture = findViewById(R.id.Page3_checkBox1);
        CheckBox check_box_partying = findViewById(R.id.Page3_checkBox2);
        CheckBox check_box_relaxation = findViewById(R.id.Page3_checkBox3);


        ArrayList<CheckBox> checkBoxArrayListInterests = new ArrayList<>();
        checkBoxArrayListInterests.add(check_box_nature);
        checkBoxArrayListInterests.add(check_box_culture);
        checkBoxArrayListInterests.add(check_box_partying);
        checkBoxArrayListInterests.add(check_box_relaxation);

        CheckBox check_box_hot = findViewById(R.id.Page3_checkBox4);
        CheckBox check_box_cold = findViewById(R.id.Page3_checkBox5);
        CheckBox check_box_average = findViewById(R.id.Page3_checkBox6);


        ArrayList<CheckBox> checkBoxArrayListWeather = new ArrayList<>();
        checkBoxArrayListWeather.add(check_box_hot);
        checkBoxArrayListWeather.add(check_box_cold);
        checkBoxArrayListWeather.add(check_box_average);


        ArrayList<String> selectedCheckBoxesInterests = new ArrayList<>();
        for (CheckBox checkBox : checkBoxArrayListInterests) {
            // Override the onCheckedChanged method defined in CompoundButton.OnCheckedChangeListener
            checkBox.setOnCheckedChangeListener((compoundButton, bool) -> {
                if (bool){
                    System.out.println(checkBox.getId() + " Checked");
                    Toast.makeText(getApplicationContext(), checkBox + "is checked", Toast.LENGTH_SHORT).show();
                    String selectedCheckBox = checkBox.getText().toString();
                    selectedCheckBoxesInterests.add(selectedCheckBox);
                } else {
                    String unselectedCheckBox = checkBox.getText().toString();
                    Toast.makeText(getApplicationContext(), checkBox + "is unchecked", Toast.LENGTH_SHORT).show();
                    System.out.println(checkBox.getId() + " Unhecked");
                    selectedCheckBoxesInterests.remove(unselectedCheckBox);
                }
            });
        }

        ArrayList<String> selectedCheckBoxesWeather = new ArrayList<>();
        for (CheckBox checkBox : checkBoxArrayListWeather) {
            // Override the onCheckedChanged method defined in CompoundButton.OnCheckedChangeListener
            checkBox.setOnCheckedChangeListener((compoundButton, bool) -> {
                if (bool){
                    System.out.println(checkBox.getId() + " Checked");
                    Toast.makeText(getApplicationContext(), checkBox + "is checked", Toast.LENGTH_SHORT).show();
                    String selectedCheckBox = checkBox.getText().toString();
                    selectedCheckBoxesWeather.add(selectedCheckBox);
                } else {
                    String unselectedCheckBox = checkBox.getText().toString();
                    Toast.makeText(getApplicationContext(), checkBox + "is unchecked", Toast.LENGTH_SHORT).show();
                    System.out.println(checkBox.getId() + " Unhecked");
                    selectedCheckBoxesWeather.remove(unselectedCheckBox);
                }
            });
        }

        Button nextButton = findViewById(R.id.Page3_button1);
        nextButton.setOnClickListener(view -> {
            if (selectedCheckBoxesInterests.size() == 0 || selectedCheckBoxesWeather.size() == 0) {
                Toast.makeText(getApplicationContext(),  "Must select at least 1 option for interest and weather.", Toast.LENGTH_SHORT).show();
            } else {
                Intent page4Intent = new Intent(view.getContext(), Page4.class);
                for (int i = 0; i <  selectedCheckBoxesInterests.size(); i++){
                    System.out.println(selectedCheckBoxesInterests.get(i));
                    b.putString("checkboxBundleInterests" + i, selectedCheckBoxesInterests.get(i));
                }
                for (int i = 0; i <  selectedCheckBoxesWeather.size(); i++){
                    System.out.println(selectedCheckBoxesWeather.get(i));
                    b.putString("checkboxBundleWeather" + i, selectedCheckBoxesWeather.get(i));
                }

                b.putInt("checkboxInterestBundleSize", selectedCheckBoxesInterests.size());
                b.putInt("checkboxWeatherBundleSize", selectedCheckBoxesWeather.size());

                page4Intent.putExtra("bundle", b);
                startActivity(page4Intent);
            }
        });

    }

    public void next(Bundle b){
        /////////////////////////////////////////////////////////////
        ArrayList<City> cities = new ArrayList<>();
//        Bundle bundle = new Bundle();
//        Intent intent = new Intent(this, Page4.class);
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
//                bundle.putParcelableArrayList("cities", cities);
//                intent.putExtra("page2",bundle);
//                intent.putExtra("bundle", b);
//                startActivity(intent);
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
