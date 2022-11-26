package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Page3 extends AppCompatActivity {
    ///////////////////////////////////////////////////
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
        Intent intent = getIntent();
        Bundle b = intent.getBundleExtra("bundle");

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


        //Currency Spinner
        Spinner spinner = findViewById(R.id.spinner);
        String[] currencies = getResources().getStringArray(R.array.currencies);
        Arrays.sort(currencies);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, currencies);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                b.putString("currency", spinner.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
        spinner.setSelection(2);

        ArrayList<String> selectedCheckBoxesInterests = new ArrayList<>();
        for (CheckBox checkBox : checkBoxArrayListInterests) {
            // Override the onCheckedChanged method defined in CompoundButton.OnCheckedChangeListener
            checkBox.setOnCheckedChangeListener((compoundButton, bool) -> {
                if (bool){
                    System.out.println(checkBox.getId() + " Checked");
                    String selectedCheckBox = checkBox.getText().toString();
                    selectedCheckBoxesInterests.add(selectedCheckBox.toLowerCase(Locale.ROOT));
                } else {
                    String unselectedCheckBox = checkBox.getText().toString();
                    System.out.println(checkBox.getId() + " Unhecked");
                    selectedCheckBoxesInterests.remove(unselectedCheckBox);
                }
            });
        }


        Button nextButton = findViewById(R.id.Page3_button1);
        nextButton.setOnClickListener(view -> {

            if (selectedCheckBoxesInterests.size() == 0) {
                Toast.makeText(getApplicationContext(),  "Must select at least 1 option for interest.", Toast.LENGTH_SHORT).show();
            } else {
                b.putStringArrayList("interests", selectedCheckBoxesInterests);
                RadioGroup r = findViewById(R.id.radio_group);
                int  selected_id = r.getCheckedRadioButtonId();
                RadioButton button = findViewById(selected_id);
                if (selected_id != -1) {
                    String weather = button.getText().toString();
                    b.putString("weather", weather);
                    b.putInt("checkboxInterestBundleSize", selectedCheckBoxesInterests.size());
                    next(b);
                }else{
                    Toast.makeText(getApplicationContext(),  "Must select 1 option for weather.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void next(Bundle b){
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
                intent.putExtra("page2",bundle);
                intent.putExtra("bundle", b);
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
