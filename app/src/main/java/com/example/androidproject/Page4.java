package com.example.androidproject;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class Page4 extends AppCompatActivity {
    ViewPager2 viewPager2;
    ArrayList<ViewPagerItem> viewPagerItems;
    ArrayList<Integer> images = new ArrayList<>();
    ArrayList<String> cityNames = new ArrayList<>();
    ArrayList<String> countryNames = new ArrayList<>();
    ArrayList<String> descriptions = new ArrayList<>();
    ArrayList<City> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page4);
        Resources resources = getResources();

        ///////////////////////////////////////////////////////////////////////
        viewPager2 = findViewById(R.id.page4ViewPager);

        Bundle bundle = getIntent().getBundleExtra("page2");
        Bundle b1 = getIntent().getBundleExtra("bundle");
//        cities = bundle.getParcelableArrayList("cities");

//        Toast.makeText(this, " " + cities.size(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, " " + cities.get(0).getImage(), Toast.LENGTH_SHORT).show();

//        for (int i = 0; i < cities.size(); i++) {
//            String s  = cities.get(i).getImage();
//            int img = resources.getIdentifier(s, "drawable", getApplication().getPackageName());
//            images.add(img);
//            cityNames.add(cities.get(i).getName());
//            countryNames.add(cities.get(i).getCountry());
//            descriptions.add(cities.get(i).getDescription());
//        }
        viewPagerItems = new ArrayList<>();

//        for (int j = 0; j < cities.size(); j++) {
//            ViewPagerItem viewPagerItem = new ViewPagerItem(images.get(j), cityNames.get(j),
//                                                        countryNames.get(j), descriptions.get(j));
//            viewPagerItems.add(viewPagerItem);
//        }

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(viewPagerItems);
        viewPager2.setAdapter(myPagerAdapter);

        ImageButton close = findViewById(R.id.imageCancel);
        close.setOnClickListener(view->{
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1, true);
        });


        ImageButton check = findViewById(R.id.imageButtonCheck);
        check.setOnClickListener(view -> next(viewPager2.getCurrentItem(), b1));


//        ViewPagerItem viewPagerItem = new ViewPagerItem(images.get(0), cityNames.get(0),
//                                                        countryNames.get(0), descriptions.get(0));
//        viewPagerItems.add(viewPagerItem);
//        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(viewPagerItems);
//        viewPager2.setAdapter(myPagerAdapter);
//        Toast.makeText(this, " " + R.drawable.matterhorn, Toast.LENGTH_LONG).show();

//        Toast.makeText(this, " " + viewPagerItems.get(0).getImage(), Toast.LENGTH_LONG).show();
//        Toast.makeText(this, " " + viewPagerItems.get(0).getCity(), Toast.LENGTH_LONG).show();
//        Toast.makeText(this, " " + viewPagerItems.get(0).getCountry(), Toast.LENGTH_LONG).show();
//        Toast.makeText(this, " " + viewPagerItems.get(0).getDescription(), Toast.LENGTH_LONG).show();

//        ViewPagerItem viewPagerItem = new ViewPagerItem(images.get(50), cityNames.get(50),
//                                                        countryNames.get(50), descriptions.get(50));
//        viewPagerItems.add(viewPagerItem);
//        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(viewPagerItems);
//        viewPager2.setAdapter(myPagerAdapter);





        ///////////////////////////////////////////////////////////////////////





//        Button button = findViewById(R.id.Page4_button);
//        button.setOnClickListener(view -> next());
    }

    public void next(int cityIndex, Bundle c){
//        Toast.makeText(getApplicationContext(), "Current Page:" + viewPager2.getCurrentItem(), Toast.LENGTH_SHORT).show();
        Bundle b = new Bundle();
        b.putParcelable("destination", cities.get(cityIndex));
        Intent intent = new Intent(this, ResultsPage.class);
        intent.putExtra("Page4",b);
        intent.putExtra("bundle", c);
        startActivity(intent);
    }
}