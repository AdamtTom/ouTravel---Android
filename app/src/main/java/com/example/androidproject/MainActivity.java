package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class MainActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "fa211ad253385ab5e5f303af6dfebb44";
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        System.out.println("FIREBASE " + firebaseDatabase);
        System.out.println("REF " + databaseReference);
//        addData();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.startBtn);
        btn.setOnClickListener(v -> start());

        tvResult = findViewById(R.id.textView2);
        String cityName = "Toronto";
//        String tempUrl = url + "?q=" + cityName + "&appid=" + appid;
        String tempUrl = "https://skyscanner50.p.rapidapi.com/api/v1/searchFlights?origin=LOND&destination=YVR&date=2022-11-16&returnDate=2022-11-22&adults=1&currency=CAD";
//        AsyncTaskRunner2 runner = new AsyncTaskRunner2();
//        runner.execute(tempUrl);

    }

    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, strings[0], null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject jsonObjectMain = response.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
                        double feelslike = jsonObjectMain.getDouble("feels_like") - 273.15;
                        int pressure = jsonObjectMain.getInt("pressure");
                        int humidity = jsonObjectMain.getInt("humidity");

                        JSONArray weather = response.getJSONArray("weather");
                        JSONObject jsonObjectWeather = weather.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");

                        JSONObject jsonObjectWind = response.getJSONObject("wind");
                        double speed = jsonObjectWind.getDouble("speed");
                        int degree = jsonObjectWind.getInt("deg");

                        JSONObject jsonObjectClouds = response.getJSONObject("clouds");
                        int cloud = jsonObjectClouds.getInt("all");

                        JSONObject jsonObjectSys = response.getJSONObject("sys");
                        String currentCountry = jsonObjectSys.getString("country");
                        String currentCity = response.getString("name");

                        tvResult.setText("Current weather of " + currentCity + " (" + currentCountry + ")\n"
                                + "Temperature: " + temp + " \u2103\n"
                                + "Feels like: " + feelslike + " \u2103\n"
                                + "Humidity: " + humidity + "%\n"
                                + "Description: " + description + "\n"
                                + "Wind speed: " + speed + "\n"
                                + "Wind degree: " + degree + "\n"
                                + "Cloudiness: " + cloud + "%\n"
                                + "Pressure: " + pressure + " hPa");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(request);
            return null;
        }
    }


    private class AsyncTaskRunner2 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, strings[0], null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray data = response.getJSONArray("data");
                        JSONObject priceObj = data.getJSONObject(0).getJSONObject("price");
                        double price = priceObj.getDouble("amount");

                        tvResult.setText("Trip Price (YVR-GIG): " + price);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.i("tag", "big error Lol" + e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    //headers.put("Content-Type", "application/json");
                    headers.put("X-RapidAPI-Key", "2997260c62mshe6779e627ae5e7fp1ced22jsnf8590df105f3");
                    headers.put("X-RapidAPI-Host", "skyscanner50.p.rapidapi.com");
                    return headers;
                }
            };

            queue.add(request);
            return null;
        }
    }

    private void addData(){
        Resources resource = getResources();
        String name = resource.getResourceName(R.drawable.matterhorn);
        City matterhorn = new City();
        matterhorn.setName("Matterhorn");
        matterhorn.setCountry("Switzerland");
        matterhorn.setIataCode("ZRH");
        ArrayList<String> matterhorntags = new ArrayList<String>();
        matterhorntags.add("nature");
        matterhorn.setTags(matterhorntags);
        matterhorn.setWeather("cold");
        matterhorn.setDescription("The Matterhorn is one of the world’s most iconic peaks—the pyramid-shaped mountain, which is very difficult to climb, is said to be the most-photographed mountain in the world\n");
        matterhorn.setImage(name);
        String id = databaseReference.push().getKey();
        databaseReference.child("City").child(id).setValue(matterhorn);

        City lasVegas = new City();
        String name1 = resource.getResourceName(R.drawable.lasvegas);
        lasVegas.setName("Las Vegas");
        lasVegas.setCountry("United States");
        lasVegas.setIataCode("LAS");
        ArrayList<String> lasVegastags = new ArrayList<String>();
        lasVegastags.add("partying");
        lasVegas.setTags(lasVegastags);
        lasVegas.setWeather("average");
        lasVegas.setDescription("The lively city known for street music, festive vibes and a melting pot");
        lasVegas.setImage(name1);
        String id1 = databaseReference.push().getKey();
        databaseReference.child("City").child(id1).setValue(lasVegas);


        City buenos = new City();
        String name2 = resource.getResourceName(R.drawable.buenosaires);
        buenos.setName("Buenos Aires");
        buenos.setCountry("Argentina");
        buenos.setIataCode("EZE");
        ArrayList<String> buenostags = new ArrayList<String>();
        buenostags.add("partying");
        buenos.setTags(buenostags);
        buenos.setWeather("average");
        buenos.setDescription("Bookstores set in palatial theaters, tango dancing in the streets and brightly painted neighborhoods. These are just some of what makes Buenos Aires so beautiful");
        buenos.setImage(name2);
        String id2 = databaseReference.push().getKey();
        databaseReference.child("City").child(id2).setValue(buenos);

        City cinque = new City();
        String name3 = resource.getResourceName(R.drawable.cinque_terre);
        cinque.setName("Cinque Terre");
        cinque.setCountry("Italy");
        cinque.setIataCode("PSA");
        ArrayList<String> cinquetags = new ArrayList<String>();
        cinquetags.add("culture");
        cinquetags.add("nature");
        cinque.setTags(cinquetags);
        cinque.setWeather("average");
        cinque.setDescription("Is there anything prettier than this area of centuries-old seaside villages on the rugged Italian Riviera coastline? The five towns (Manarola, Riomaggiore, Corniglia, Vernazza and Monterosso al Mare) are made for bucket lists.\n");
        cinque.setImage(name3);
        String id3 = databaseReference.push().getKey();
        databaseReference.child("City").child(id3).setValue(cinque);

        City budapest = new City();
        String name4 = resource.getResourceName(R.drawable.budapest);
        budapest.setName("Budapest");
        budapest.setCountry("Hungary");
        budapest.setIataCode("BUD");
        ArrayList<String> budapesttags = new ArrayList<String>();
        budapesttags.add("culture");
        budapesttags.add("relaxation");
        budapest.setTags(budapesttags);
        budapest.setWeather("cold");
        budapest.setDescription("The capital city of Hungary, Budapest is a fairytale city in Eastern Europe.");
        budapest.setImage(name4);
        String id4 = databaseReference.push().getKey();
        databaseReference.child("City").child(id4).setValue(budapest);

        City beijing = new City();
        String name5 = resource.getResourceName(R.drawable.beijing);
        beijing.setName("Beijing");
        beijing.setCountry("China");
        beijing.setIataCode("PEK");
        ArrayList<String> beijingtags = new ArrayList<String>();
        beijingtags.add("culture");
        beijingtags.add("partying");
        beijing.setTags(beijingtags);
        beijing.setWeather("average");
        beijing.setDescription("Beijing is China’s political, economic, and cultural center, with six Unesco World Heritage Sites in this city alone.\n");
        beijing.setImage(name5);
        String id5 = databaseReference.push().getKey();
        databaseReference.child("City").child(id5).setValue(beijing);


        City ibiza = new City();
        String name6 = resource.getResourceName(R.drawable.ibiza);
        ibiza.setName("Ibiza");
        ibiza.setCountry("Spain");
        ibiza.setIataCode("IBZ");
        ArrayList<String> ibizatags = new ArrayList<String>();
        ibizatags.add("culture");
        ibizatags.add("nature");
        ibizatags.add("relaxation");
        ibiza.setTags(ibizatags);
        ibiza.setWeather("average");
        ibiza.setDescription("Ibiza is also one of the most beautiful Spanish islands, with a pretty Old Town and scenic beaches.");
        ibiza.setImage(name6);
        String id6 = databaseReference.push().getKey();
        databaseReference.child("City").child(id6).setValue(ibiza);


        City hawaii = new City();
        String name7 = resource.getResourceName(R.drawable.hawaii);
        hawaii.setName("Hawaii");
        hawaii.setCountry("United States");
        hawaii.setIataCode("HNL");
        ArrayList<String> hawaiitags = new ArrayList<String>();
        hawaiitags.add("nature");
        hawaiitags.add("relaxation");
        hawaii.setTags(hawaiitags);
        hawaii.setWeather("hot");
        hawaii.setDescription("The Hawaiian Islands are pure paradise. Explore colorful canyons and waterfalls and eat your body weight in fresh poke");
        hawaii.setImage(name7);
        String id7 = databaseReference.push().getKey();
        databaseReference.child("City").child(id7).setValue(hawaii);


        City hanoi = new City();
        String name8 = resource.getResourceName(R.drawable.hanoi);
        hanoi.setName("Hanoi");
        hanoi.setCountry("Vietnam");
        hanoi.setIataCode("HAN");
        ArrayList<String> hanoitags = new ArrayList<String>();
        hanoitags.add("culture");
        hanoitags.add("partying");
        hanoi.setTags(hanoitags);
        hanoi.setWeather("hot");
        hanoi.setDescription(" Hanoi—the capital of Vietnam—is known for its rich history, busy street life and centuries of French, Asian and Chinese influences all blended into one bustling city.\n");
        hanoi.setImage(name8);
        String id8 = databaseReference.push().getKey();
        databaseReference.child("City").child(id8).setValue(hanoi);


        City lisbon = new City();
        String name9 = resource.getResourceName(R.drawable.lisbon);
        lisbon.setName("Lisbon");
        lisbon.setCountry("Portugal");
        lisbon.setIataCode("LIS");
        ArrayList<String> lisbontags = new ArrayList<String>();
        lisbontags.add("culture");
        lisbontags.add("relaxation");
        lisbon.setTags(lisbontags);
        lisbon.setWeather("average");
        lisbon.setDescription("Lisbon, the hilly capital of Portugal, is postcard-perfect with its cobbled streets, pristine waters and local Atlantic beaches.");
        lisbon.setImage(name9);
        String id9 = databaseReference.push().getKey();
        databaseReference.child("City").child(id9).setValue(lisbon);

        City virunga = new City();
        String name10 = resource.getResourceName(R.drawable.virunga_park);
        virunga.setName("Virunga National Park");
        virunga.setCountry("Democratic Republic of Congo");
        virunga.setIataCode("FIH");
        ArrayList<String> virungatags = new ArrayList<String>();
        virungatags.add("nature");
        virunga.setTags(virungatags);
        virunga.setWeather("hot");
        virunga.setDescription("Virunga National Park is one of the most biologically diverse areas on the planet and home to the world’s critically endangered mountain gorillas");
        virunga.setImage(name10);
        String id10 = databaseReference.push().getKey();
        databaseReference.child("City").child(id10).setValue(virunga);

        City machuPicchu = new City();
        String name11 = resource.getResourceName(R.drawable.machu_picchu);
        machuPicchu.setName("Machu Picchu");
        machuPicchu.setCountry("Peru");
        machuPicchu.setIataCode("MFT");
        ArrayList<String> machuPicchutags = new ArrayList<String>();
        machuPicchutags.add("culture");
        machuPicchutags.add("nature");
        machuPicchu.setTags(machuPicchutags);
        machuPicchu.setWeather("average");
        machuPicchu.setDescription("The wide, panoramic windows are perfect for soaking up the view, plus they serve Pisco Sours on board.");
        machuPicchu.setImage(name11);
        String id11 = databaseReference.push().getKey();
        databaseReference.child("City").child(id11).setValue(machuPicchu);

        City providencia = new City();
        String name12 = resource.getResourceName(R.drawable.providencia);
        providencia.setName("Providencia");
        providencia.setCountry("Colombia");
        providencia.setIataCode("PVA");
        ArrayList<String> providenciatags = new ArrayList<String>();
        providenciatags.add("culture");
        providenciatags.add("culture");
        providencia.setTags(providenciatags);
        providencia.setWeather("hot");
        providencia.setDescription("The Colombian island of Providencia is the perfect combination of South America and the Caribbean.");
        providencia.setImage(name12);
        String id12 = databaseReference.push().getKey();
        databaseReference.child("City").child(id12).setValue(providencia);

        City laucala = new City();
        String name13 = resource.getResourceName(R.drawable.laucala_island);
        laucala.setName("Laucala Island Resort");
        laucala.setCountry("Fiji");
        laucala.setIataCode("NAN");
        ArrayList<String> laucalatags = new ArrayList<String>();
        laucalatags.add("nature");
        laucalatags.add("relaxation");
        laucala.setTags(laucalatags);
        laucala.setWeather("hot");
        laucala.setDescription("Laucala Island Resort is a private island in Fiji, in absolute paradise. There are coconut trees, a sustainable farm and miles of beach, as well as coral reefs, postcard-perfect beaches and lush rainforest.");
        laucala.setImage(name13);
        String id13 = databaseReference.push().getKey();
        databaseReference.child("City").child(id13).setValue(laucala);

        City seoul = new City();
        String name14 = resource.getResourceName(R.drawable.seoul);
        seoul.setName("Seoul");
        seoul.setCountry("Korea");
        seoul.setIataCode("ICN");
        ArrayList<String> seoultags = new ArrayList<String>();
        seoultags.add("partying");
        seoul.setTags(seoultags);
        seoul.setWeather("cold");
        seoul.setDescription("Seoul is a vibrant metropolis where old-meets-new, with pop culture (K-Pop!) alongside Buddhist temples.");
        seoul.setImage(name14);
        String id14 = databaseReference.push().getKey();
        databaseReference.child("City").child(id14).setValue(seoul);


        City copenhagen = new City();
        String name15 = resource.getResourceName(R.drawable.copenhagen);
        copenhagen.setName("Copenhagen");
        copenhagen.setCountry("Denmark");
        copenhagen.setIataCode("CPH");
        ArrayList<String> copenhagentags = new ArrayList<String>();
        copenhagentags.add("culture");
        copenhagentags.add("partying");
        copenhagen.setTags(copenhagentags);
        copenhagen.setWeather("cold");
        copenhagen.setDescription("Copenhagen’s rustic fishing ports, modern graffiti and winding red brick streets are just some of what makes it such a beautiful bucket list destination.");
        copenhagen.setImage(name15);
        String id15 = databaseReference.push().getKey();
        databaseReference.child("City").child(id15).setValue(copenhagen);


        City cairo = new City();
        String name16 = resource.getResourceName(R.drawable.cairo);
        cairo.setName("Cairo");
        cairo.setCountry("Egypt");
        cairo.setIataCode("CAI");
        ArrayList<String> cairotags = new ArrayList<String>();
        cairotags.add("culture");
        cairotags.add("nature");
        cairo.setTags(cairotags);
        cairo.setWeather("hot");
        cairo.setDescription("Cairo is one of the most ancient cities in the world. Sitting on the Nile river with wonderful museums, vibrant culture and friendly locals, it makes for a great holiday.\n");
        cairo.setImage(name16);
        String id16 = databaseReference.push().getKey();
        databaseReference.child("City").child(id16).setValue(cairo);


        City santiago = new City();
        String name17 = resource.getResourceName(R.drawable.santiago);
        santiago.setName("Santiago");
        santiago.setCountry("Chile");
        santiago.setIataCode("SCL");
        ArrayList<String> santiagotags = new ArrayList<String>();
        santiagotags.add("partying");
        santiagotags.add("culture");
        santiago.setTags(santiagotags);
        santiago.setWeather("average");
        santiago.setDescription("Santiago is a cosmopolitan city with the very best of Chilean culture; art galleries, design shops and handicraft markets, as well as lively Latino nightlife.");
        santiago.setImage(name17);
        String id17 = databaseReference.push().getKey();
        databaseReference.child("City").child(id17).setValue(santiago);


        City amsterdam = new City();
        String name18 = resource.getResourceName(R.drawable.amsterdam);
        amsterdam.setName("Amsterdam");
        amsterdam.setCountry("the Kingdom of the Netherlands");
        amsterdam.setIataCode("AMS");
        ArrayList<String> amsterdamtags = new ArrayList<String>();
        amsterdamtags.add("partying");
        amsterdamtags.add("culture");
        amsterdam.setTags(amsterdamtags);
        amsterdam.setWeather("average");
        amsterdam.setDescription("Forget about cliched images of smoke shops and gaudy red lights. From floating flower markets to bohemian neighborhoods, this city has it all.");
        amsterdam.setImage(name18);
        String id18 = databaseReference.push().getKey();
        databaseReference.child("City").child(id18).setValue(amsterdam);

        City barbados = new City();
        String name19 = resource.getResourceName(R.drawable.barbabos);
        barbados.setName("Barbabos");
        barbados.setCountry("Caribbean island");
        barbados.setIataCode("BGI");
        ArrayList<String> barbadostags = new ArrayList<String>();
        barbadostags.add("culture");
        barbadostags.add("relaxation");
        barbados.setTags(barbadostags);
        barbados.setWeather("hot");
        barbados.setDescription("Barbados is one of those magical holiday destinations that everybody dreams about visiting.");
        barbados.setImage(name19);
        String id19 = databaseReference.push().getKey();
        databaseReference.child("City").child(id19).setValue(barbados);

        City hongkong = new City();
        String name20 = resource.getResourceName(R.drawable.hongkong);
        hongkong.setName("Hong Kong");
        hongkong.setCountry("China");
        hongkong.setIataCode("HKG");
        ArrayList<String> hongkongtags = new ArrayList<String>();
        hongkongtags.add("partying");
        hongkongtags.add("culture");
        hongkong.setTags(hongkongtags);
        hongkong.setWeather("average");
        hongkong.setDescription("Famous for its skylines and vibrant food scene, what most people don’t know is that 70% of Hong Kong is mountains and lush parks.");
        hongkong.setImage(name20);
        String id20 = databaseReference.push().getKey();
        databaseReference.child("City").child(id20).setValue(hongkong);

        City petra = new City();
        String name21 = resource.getResourceName(R.drawable.petra);
        petra.setName("Petra");
        petra.setCountry("Jordan");
        petra.setIataCode("KBR");
        ArrayList<String> petratags = new ArrayList<String>();
        petratags.add("nature");
        petratags.add("culture");
        petra.setTags(petratags);
        petra.setWeather("average");
        petra.setDescription("The ancient Nabatean city of Petra in southern Jordan is surrounded by beautiful red rocks and steep gorges. The world wonder is without a doubt Jordan’s most valuable treasure and greatest tourist attraction.");
        petra.setImage(name21);
        String id21 = databaseReference.push().getKey();
        databaseReference.child("City").child(id21).setValue(petra);

        City riodeJaneiro = new City();
        String name22 = resource.getResourceName(R.drawable.riodejaneiro);
        riodeJaneiro.setName("Rio de Janeiro");
        riodeJaneiro.setCountry("Brazil");
        riodeJaneiro.setIataCode("GIG");
        ArrayList<String> riodeJaneirotags = new ArrayList<String>();
        riodeJaneirotags.add("nature");
        riodeJaneirotags.add("culture");
        riodeJaneiro.setTags(riodeJaneirotags);
        riodeJaneiro.setWeather("hot");
        riodeJaneiro.setDescription("Rio de Janeiro has always been one of the most iconic cities in the world with instantly recognizable landscapes and landmarks.");
        riodeJaneiro.setImage(name22);
        String id22 = databaseReference.push().getKey();
        databaseReference.child("City").child(id22).setValue(riodeJaneiro);

        City london = new City();
        String name23 = resource.getResourceName(R.drawable.london);
        london.setName("London");
        london.setCountry("England");
        london.setIataCode("YXU");
        ArrayList<String> londontags = new ArrayList<String>();
        londontags.add("culture");
        london.setTags(londontags);
        london.setWeather("average");
        london.setDescription("Pretty pink restaurants, futuristic space-age toilets and jungle skyline views are just some of our favorite things about London.");
        london.setImage(name23);
        String id23 = databaseReference.push().getKey();
        databaseReference.child("City").child(id23).setValue(london);

        City singapore = new City();
        String name24 = resource.getResourceName(R.drawable.singapore);
        singapore.setName("Singapore");
        singapore.setCountry("Singapore");
        singapore.setIataCode("SIN");
        ArrayList<String> singaporetags = new ArrayList<String>();
        singaporetags.add("partying");
        singaporetags.add("culture");
        singapore.setTags(singaporetags);
        singapore.setWeather("hot");
        singapore.setDescription("Singapore is a small island city-state off southern Malaysia which punches way above its weight on a global level. It’s a modern city with colorful buildings, futuristic bridges and a cloud forest.");
        singapore.setImage(name24);
        String id24 = databaseReference.push().getKey();
        databaseReference.child("City").child(id24).setValue(singapore);

        City bali = new City();
        String name25 = resource.getResourceName(R.drawable.bali);
        bali.setName("Bali");
        bali.setCountry("Indonesia");
        bali.setIataCode("DPS");
        ArrayList<String> balitags = new ArrayList<String>();
        balitags.add("nature");
        balitags.add("relaxation");
        bali.setTags(balitags);
        bali.setWeather("hot");
        bali.setDescription("A place where you can relax you can find beaches, volcanoes, and jungles. \n");
        bali.setImage(name25);
        String id25 = databaseReference.push().getKey();
        databaseReference.child("City").child(id25).setValue(bali);

        City newOrleans = new City();
        String name26 = resource.getResourceName(R.drawable.neworleans);
        newOrleans.setName("New Orleans");
        newOrleans.setCountry("United States");
        newOrleans.setIataCode("MSY");
        ArrayList<String> newOrleanstags = new ArrayList<String>();
        newOrleanstags.add("partying");
        newOrleanstags.add("culture");
        newOrleans.setTags(newOrleanstags);
        newOrleans.setWeather("hot");
        newOrleans.setDescription("The lively city known for street music, festive vibes and a melting pot");
        newOrleans.setImage(name26);
        String id26 = databaseReference.push().getKey();
        databaseReference.child("City").child(id26).setValue(newOrleans);


        City kerry = new City();
        String name27 = resource.getResourceName(R.drawable.kerry);
        kerry.setName("Kerry");
        kerry.setCountry("Ireland");
        kerry.setIataCode("KIR");
        ArrayList<String> kerrytags = new ArrayList<String>();
        kerrytags.add("nature");
        kerrytags.add("relaxation");
        kerry.setTags(kerrytags);
        kerry.setWeather("average");
        kerry.setDescription("The city is famous for having unique small towns such as dingle and Killarney National Park, mountains, lakes and coasts.");
        kerry.setImage(name27);
        String id27 = databaseReference.push().getKey();
        databaseReference.child("City").child(id27).setValue(kerry);

        City marrakesh = new City();
        String name28 = resource.getResourceName(R.drawable.marrakech);
        marrakesh.setName("Marrakesh");
        marrakesh.setCountry("Morocco");
        marrakesh.setIataCode("RAK");
        ArrayList<String> marrakeshtags = new ArrayList<String>();
        marrakeshtags.add("culture");
        marrakesh.setTags(marrakeshtags);
        marrakesh.setWeather("hot");
        marrakesh.setDescription("This ancient walled city is home to mosques, places and lush gardens. It is known as red city");
        marrakesh.setImage(name28);
        String id28 = databaseReference.push().getKey();
        databaseReference.child("City").child(id28).setValue(marrakesh);

        City sydney = new City();
        String name29 = resource.getResourceName(R.drawable.sydney);
        sydney.setName("Sydney");
        sydney.setCountry("Australia");
        sydney.setIataCode("SYD");
        ArrayList<String> sydneytags = new ArrayList<String>();
        sydneytags.add("partying");
        sydneytags.add("relaxation");
        sydney.setTags(sydneytags);
        sydney.setWeather("average");
        sydney.setDescription("This city has gorgeous beaches, great-cafes and world class entertainment on offer.");
        sydney.setImage(name29);
        String id29 = databaseReference.push().getKey();
        databaseReference.child("City").child(id29).setValue(sydney);

        City maldives = new City();
        String name30 = resource.getResourceName(R.drawable.maldives);
        maldives.setName("Maldives");
        maldives.setIataCode("MLE");
        ArrayList<String> maldivestags = new ArrayList<String>();
        maldivestags.add("partying");
        maldivestags.add("relaxation");
        maldivestags.add("nature");
        maldives.setTags(maldivestags);
        maldives.setWeather("average");
        maldives.setDescription("This city is home to some of the world’s most luxurious hotel resorts with white sandy beaches, underwater villas and restaurants and bright blue waters.");
        maldives.setImage(name30);
        String id30 = databaseReference.push().getKey();
        databaseReference.child("City").child(id30).setValue(maldives);

        City paris = new City();
        String name31 = resource.getResourceName(R.drawable.paris);
        paris.setName("Paris");
        paris.setCountry("France");
        paris.setIataCode("CDG");
        ArrayList<String> paristags = new ArrayList<String>();
        paristags.add("relaxation");
        paristags.add("culture");
        paris.setTags(paristags);
        paris.setWeather("average");
        paris.setDescription("It is one of the most iconic cities in the world, famous for Eiffel Tower, Arc de Triomphe, Notre Dame cathedral.");
        paris.setImage(name31);
        String id31 = databaseReference.push().getKey();
        databaseReference.child("City").child(id31).setValue(paris);

        City capeTown= new City();
        String name32 = resource.getResourceName(R.drawable.capetown);
        capeTown.setName("Cape Town");
        capeTown.setCountry("South Africa");
        capeTown.setIataCode("CPT");
        ArrayList<String> capetags = new ArrayList<String>();
        capetags.add("relaxation");
        capeTown.setTags(capetags);
        capeTown.setWeather("hot");
        capeTown.setDescription(" Cape town is a dream location to visit with endless natural beauty and cliff top views, pastel pink neighborhoods and turquoise waters");
        capeTown.setImage(name32);
        String id32 = databaseReference.push().getKey();
        databaseReference.child("City").child(id32).setValue(capeTown);

        City dubai= new City();
        String name33 = resource.getResourceName(R.drawable.dubai);
        dubai.setName("Dubai");
        dubai.setCountry("UAE");
        dubai.setIataCode("DXB");
        ArrayList<String> dubaitags = new ArrayList<String>();
        dubaitags.add("relaxation");
        dubaitags.add("partying");
        dubai.setTags(dubaitags);
        dubai.setWeather("hot");
        dubai.setDescription("The high-flying city of the U.A. E, Dubai is one the most glamorous destinations you’ll ever visit, and is particularly popular with big 7 travel readers.");
        dubai.setImage(name33);
        String id33 = databaseReference.push().getKey();
        databaseReference.child("City").child(id33).setValue(dubai);

        City bora= new City();
        String name34 = resource.getResourceName(R.drawable.borabora);
        bora.setName("Bora Bora");
        bora.setCountry("French Polynesia");
        bora.setIataCode("BOB");
        ArrayList<String> boratags = new ArrayList<String>();
        boratags.add("relaxation");
        boratags.add("nature");
        bora.setTags(boratags);
        bora.setWeather("average");
        bora.setDescription("Bora bora is Tahiti’s most famous island. It has some overwater bungalows and underwater adventures..");
        bora.setImage(name34);
        String id34 = databaseReference.push().getKey();
        databaseReference.child("City").child(id34).setValue(bora);

        City newYork= new City();
        String name35 = resource.getResourceName(R.drawable.new_york1);
        newYork.setName("New York");
        newYork.setCountry("America");
        newYork.setIataCode("LGA");
        ArrayList<String> newYorktags = new ArrayList<String>();
        newYorktags.add("culture");
        newYorktags.add("partying");
        newYork.setTags(newYorktags);
        newYork.setWeather("cold");
        newYork.setDescription("This city has charming upstate scenery, world-class cuisine and culture. The city’s five boroughs all have special features.");
        newYork.setImage(name35);
        String id35 = databaseReference.push().getKey();
        databaseReference.child("City").child(id35).setValue(newYork);

        City Dubrovnik= new City();
        String name36 = resource.getResourceName(R.drawable.dubrovnik);
        Dubrovnik.setName("Dubrovnik");
        Dubrovnik.setCountry("Croatia");
        Dubrovnik.setIataCode("DBV");
        ArrayList<String> Dubrovniktags = new ArrayList<String>();
        Dubrovniktags.add("relaxation");
        Dubrovniktags.add("partying");
        Dubrovnik.setTags(Dubrovniktags);
        Dubrovnik.setWeather("cold");
        Dubrovnik.setDescription("This city is famous for winding streets, cliff side beach bars and UNESCO World Heritage Site of the old Town.");
        Dubrovnik.setImage(name36);
        String id36 = databaseReference.push().getKey();
        databaseReference.child("City").child(id36).setValue(Dubrovnik);

        City Edinburgh= new City();
        String name37 = resource.getResourceName(R.drawable.edinburgh);
        Edinburgh.setName("Edinburgh");
        Edinburgh.setCountry("Scotland");
        Edinburgh.setIataCode("EDI");
        ArrayList<String> Edinburghtags = new ArrayList<String>();
        Edinburghtags.add("culture");
        Edinburghtags.add("partying");
        Edinburgh.setTags(Edinburghtags);
        Edinburgh.setWeather("hot");
        Edinburgh.setDescription("with the historic edinburgh castle looming over the city, culture in spades and wonderfully friendly locals, this is one of the world’s greatest city breaks.");
        Edinburgh.setImage(name37);
        String id37 = databaseReference.push().getKey();
        databaseReference.child("City").child(id37).setValue(Edinburgh);

        City Rome= new City();
        String name38 = resource.getResourceName(R.drawable.rome);
        Rome.setName("Rome");
        Rome.setCountry("Italy");
        Rome.setIataCode("FCO");
        ArrayList<String> Rometags = new ArrayList<String>();
        Rometags.add("culture");
        Rometags.add("partying");
        Rometags.add("relaxation");
        Rome.setTags(Rometags);
        Rome.setWeather("average");
        Rome.setDescription("whether it’s your first time in Rome or your 50th, you’ll always discover something new each time you stroll the scenic streets.");
        Rome.setImage(name38);
        String id38 = databaseReference.push().getKey();
        databaseReference.child("City").child(id38).setValue(Rome);

        City ParoValley= new City();
        String name39 = resource.getResourceName(R.drawable.poravalley);
        ParoValley.setName("Paro Valley");
        ParoValley.setCountry("Bhutan");
        ParoValley.setIataCode("PBH");
        ArrayList<String> ParoValleytags = new ArrayList<String>();
        ParoValleytags.add("culture");
        ParoValleytags.add("nature");
        ParoValley.setTags(ParoValleytags);
        ParoValley.setWeather("average");
        ParoValley.setDescription("Paro Valley is known for its monasteries, fortresses (or dzongs) and dramatic landscapes. Tucked between China and India, Bhutan is a mysterious country that prides itself on sustainable tourism.");
        ParoValley.setImage(name39);
        String id39 = databaseReference.push().getKey();
        databaseReference.child("City").child(id39).setValue(ParoValley);

        City Jaipur = new City();
        String name40 = resource.getResourceName(R.drawable.jaipur);
        Jaipur.setName("Jaipur");
        Jaipur.setCountry("India");
        Jaipur.setIataCode("JAI");
        ArrayList<String> Jaipurtags = new ArrayList<String>();
        Jaipurtags.add("culture");
        Jaipurtags.add("nature");
        Jaipur.setTags(Jaipurtags);
        Jaipur.setWeather("hot");
        Jaipur.setDescription("Jaipur is known as the ‘Pink City’ for its pale terracotta buildings. This was originally done to impress the visiting Prince Albert during his 1876 tour of India by order of the Maharaja (Sawai Ram Singh).");
        Jaipur.setImage(name40);
        String id40 = databaseReference.push().getKey();
        databaseReference.child("City").child(id40).setValue(Jaipur);

        City Waikato = new City();
        String name41 = resource.getResourceName(R.drawable.waikato);
        Waikato.setName("Waikato");
        Waikato.setCountry("New Zealand");
        Waikato.setIataCode("HLZ");
        ArrayList<String> Waikatotags = new ArrayList<String>();
        Waikatotags.add("culture");
        Waikatotags.add("nature");
        Waikatotags.add("relaxation");
        Waikato.setTags(Waikatotags);
        Waikato.setWeather("hot");
        Waikato.setDescription("Waikato, a region in New Zealand’s North Island, is home to massive underground caves, lush rainforest and the buzzy city of Hamilton.");
        Waikato.setImage(name41);
        String id41 = databaseReference.push().getKey();
        databaseReference.child("City").child(id41).setValue(Waikato);

        City Havana= new City();
        String name42 = resource.getResourceName(R.drawable.havana);
        Havana.setName("Havana");
        Havana.setCountry("Cuba");
        Havana.setIataCode("HAV");
        ArrayList<String> Havanatags = new ArrayList<String>();
        Havanatags.add("culture");
        Havanatags.add("partying");
        Havana.setTags(Havanatags);
        Havana.setWeather("hot");
        Havana.setDescription("Cuba’s capital is almost 500 years old and a riot of color. Brightly painted buildings and vintage cars make Havana a photogenic dream.");
        Havana.setImage(name42);
        String id42 = databaseReference.push().getKey();
        databaseReference.child("City").child(id42).setValue(Havana);

        City Tokyo = new City();
        String name43 = resource.getResourceName(R.drawable.tokyo);
        Tokyo.setName("Tokyo");
        Tokyo.setCountry("Japan");
        Tokyo.setIataCode("HND");
        ArrayList<String> Tokyotags = new ArrayList<String>();
        Tokyotags.add("culture");
        Tokyotags.add("partying");
        Tokyo.setTags(Tokyotags);
        Tokyo.setWeather("average");
        Tokyo.setDescription("Visiting Tokyo is like visiting the future—flashing neon lights, incredible technology—yet there’s still a rich sense of culture and history.");
        Tokyo.setImage(name43);
        String id43 = databaseReference.push().getKey();
        databaseReference.child("City").child(id43).setValue(Tokyo);

        City Antarctica = new City();
        String name44 = resource.getResourceName(R.drawable.antartica);
        Antarctica.setName("Antarctica");
        Antarctica.setIataCode("SAYE");
        ArrayList<String> Antarcticatags = new ArrayList<String>();
        Antarcticatags.add("relaxation");
        Antarctica.setTags(Antarcticatags);
        Antarctica.setWeather("cold");
        Antarctica.setDescription("Earth’s southernmost continent, Antarctica is a once-in-a-lifetime destination. Nowhere else can compare with the extreme remoteness of this snowy place.");
        Antarctica.setImage(name44);
        String id44 = databaseReference.push().getKey();
        databaseReference.child("City").child(id44).setValue(Antarctica);

        City Vancouver= new City();
        String name45 = resource.getResourceName(R.drawable.vancouver);
        Vancouver.setName("Vancouver");
        Vancouver.setCountry("Canada");
        Vancouver.setIataCode("YVR");
        ArrayList<String> Vancouvertags = new ArrayList<String>();
        Vancouvertags.add("nature");
        Vancouvertags.add("partying");
        Vancouver.setTags(Vancouvertags);
        Vancouver.setWeather("cold");
        Vancouver.setDescription("Vancouver is surrounded by water yet close to the mountains and has world-class art, restaurants and heaps of other attractions to keep you entertained.");
        Vancouver.setImage(name45);
        String id45 = databaseReference.push().getKey();
        databaseReference.child("City").child(id45).setValue(Vancouver);

        City LosAngeles = new City();
        String name46 = resource.getResourceName(R.drawable.losangles);
        LosAngeles.setName("Los Angeles");
        LosAngeles.setCountry("United States");
        LosAngeles.setIataCode("LAX");
        ArrayList<String> LosAngelestags = new ArrayList<String>();
        LosAngelestags.add("nature");
        LosAngelestags.add("partying");
        LosAngeles.setTags(LosAngelestags);
        LosAngeles.setWeather("average");
        LosAngeles.setDescription(" In a city with year-round sunshine, glam bars, beaches and hikes, there are endless incredible experiences to enjoy in Los Angeles.");
        LosAngeles.setImage(name46);
        String id46 = databaseReference.push().getKey();
        databaseReference.child("City").child(id46).setValue(LosAngeles);

        City Kruger = new City();
        String name47 = resource.getResourceName(R.drawable.krugernationalpark);
        Kruger.setName("Kruger National Park");
        Kruger.setCountry("South Africa");
        Kruger.setIataCode("MQP");
        ArrayList<String> Krugertags = new ArrayList<String>();
        Krugertags.add("nature");
        Kruger.setTags(Krugertags);
        Kruger.setWeather("average");
        Kruger.setDescription("The Kruger National Park is a vast space in northeastern South Africa that is home to a huge array of wildlife.");
        Kruger.setImage(name46);
        String id47 = databaseReference.push().getKey();
        databaseReference.child("City").child(id47).setValue(Kruger);

        City Santorini= new City();
        String name48 = resource.getResourceName(R.drawable.santorini);
        Santorini.setName("Santorini");
        Santorini.setCountry("Greece");
        Santorini.setIataCode("JTR");
        ArrayList<String> Santorinitags = new ArrayList<String>();
        Santorinitags.add("nature");
        Santorinitags.add("culture");
        Santorini.setTags(Santorinitags);
        Santorini.setWeather("average");
        Santorini.setDescription("Beaches with volcanic black or red sand and clear blue waters make this an ideal holiday spot. With its famous Santorini sunsets, it’s no wonder that it’s one of the most popular bucket list destinations.");
        Santorini.setImage(name48);
        String id48 = databaseReference.push().getKey();
        databaseReference.child("City").child(id48).setValue(Santorini);

        City Moscow = new City();
        String name49 = resource.getResourceName(R.drawable.moscow);
        Moscow.setName("Moscow");
        Moscow.setCountry("Russia");
        Moscow.setIataCode("DME");
        ArrayList<String> Moscowtags = new ArrayList<String>();
        Moscowtags.add("culture");
        Moscow.setTags(Moscowtags);
        Moscow.setWeather("cold");
        Moscow.setDescription("Russia’s cosmopolitan capital, Moscow is a beautiful destination to visit in any season. Culture lovers will be impressed with the museums and ballet.");
        Moscow.setImage(name49);
        String id49 = databaseReference.push().getKey();
        databaseReference.child("City").child(id49).setValue(Moscow);
         }


    public void start(){
        Intent intent = new Intent(this, Page2.class);
        startActivity(intent);
    }
}