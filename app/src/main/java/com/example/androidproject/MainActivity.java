package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
//        City matterhorn = new City();
//        matterhorn.setName("Matterhorn");
//        matterhorn.setCountry("Switzerland");
//        matterhorn.setIataCode("ZRH");
//        ArrayList<String> matterhorntags = new ArrayList<String>();
//        matterhorntags.add("nature");
//        matterhorn.setTags(matterhorntags);
//        matterhorn.setWeather("cold");
//        matterhorn.setDescription("The Matterhorn is one of the world’s most iconic peaks—the pyramid-shaped mountain, which is very difficult to climb, is said to be the most-photographed mountain in the world\n");
//        matterhorn.setImage(R.drawable.matterhorn);
//        String id = databaseReference.push().getKey();
//        databaseReference.child("City").child(id).setValue(matterhorn);
//
//        City lasVegas = new City();
//        lasVegas.setName("Las Vegas");
//        lasVegas.setCountry("United States");
//        lasVegas.setIataCode("LAS");
//        ArrayList<String> lasVegastags = new ArrayList<String>();
//        lasVegastags.add("partying");
//        lasVegas.setTags(lasVegastags);
//        lasVegas.setWeather("average");
//        lasVegas.setDescription("The lively city known for street music, festive vibes and a melting pot");
//        lasVegas.setImage(R.drawable.lasvegas);
//        String id1 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id1).setValue(lasVegas);
//
//
//        City buenos = new City();
//        buenos.setName("Buenos Aires");
//        buenos.setCountry("Argentina");
//        buenos.setIataCode("EZE");
//        ArrayList<String> buenostags = new ArrayList<String>();
//        buenostags.add("partying");
//        buenos.setTags(buenostags);
//        buenos.setWeather("average");
//        buenos.setDescription("Bookstores set in palatial theaters, tango dancing in the streets and brightly painted neighborhoods. These are just some of what makes Buenos Aires so beautiful");
//        buenos.setImage(R.drawable.buenosaires);
//        String id2 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id2).setValue(buenos);
//
//        City cinque = new City();
//        cinque.setName("Cinque Terre");
//        cinque.setCountry("Italy");
//        cinque.setIataCode("PSA");
//        ArrayList<String> cinquetags = new ArrayList<String>();
//        cinquetags.add("culture");
//        cinquetags.add("nature");
//        cinque.setTags(cinquetags);
//        cinque.setWeather("average");
//        cinque.setDescription("Is there anything prettier than this area of centuries-old seaside villages on the rugged Italian Riviera coastline? The five towns (Manarola, Riomaggiore, Corniglia, Vernazza and Monterosso al Mare) are made for bucket lists.\n");
//        cinque.setImage(R.drawable.cinque_terre);
//        String id3 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id3).setValue(cinque);
//
//        City budapest = new City();
//        budapest.setName("Budapest");
//        budapest.setCountry("Hungary");
//        budapest.setIataCode("BUD");
//        ArrayList<String> budapesttags = new ArrayList<String>();
//        budapesttags.add("culture");
//        budapesttags.add("relaxation");
//        budapest.setTags(budapesttags);
//        budapest.setWeather("cold");
//        budapest.setDescription("The capital city of Hungary, Budapest is a fairytale city in Eastern Europe.");
//        budapest.setImage(R.drawable.budapest);
//        String id4 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id4).setValue(budapest);
//
//        City beijing = new City();
//        beijing.setName("Beijing");
//        beijing.setCountry("China");
//        beijing.setIataCode("PEK");
//        ArrayList<String> beijingtags = new ArrayList<String>();
//        beijingtags.add("culture");
//        beijingtags.add("partying");
//        beijing.setTags(beijingtags);
//        beijing.setWeather("average");
//        beijing.setDescription("Beijing is China’s political, economic, and cultural center, with six Unesco World Heritage Sites in this city alone.\n");
//        beijing.setImage(R.drawable.beijing);
//        String id5 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id5).setValue(beijing);
//
//
//        City ibiza = new City();
//        ibiza.setName("Ibiza");
//        ibiza.setCountry("Spain");
//        ibiza.setIataCode("IBZ");
//        ArrayList<String> ibizatags = new ArrayList<String>();
//        ibizatags.add("culture");
//        ibizatags.add("nature");
//        ibizatags.add("relaxation");
//        ibiza.setTags(ibizatags);
//        ibiza.setWeather("average");
//        ibiza.setDescription("Ibiza is also one of the most beautiful Spanish islands, with a pretty Old Town and scenic beaches.");
//        ibiza.setImage(R.drawable.ibiza);
//        String id6 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id6).setValue(ibiza);
//
//
//        City hawaii = new City();
//        hawaii.setName("Hawaii");
//        hawaii.setCountry("United States");
//        hawaii.setIataCode("HNL");
//        ArrayList<String> hawaiitags = new ArrayList<String>();
//        hawaiitags.add("nature");
//        hawaiitags.add("relaxation");
//        hawaii.setTags(hawaiitags);
//        hawaii.setWeather("hot");
//        hawaii.setDescription("The Hawaiian Islands are pure paradise. Explore colorful canyons and waterfalls and eat your body weight in fresh poke");
//        hawaii.setImage(R.drawable.hawaii);
//        String id7 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id7).setValue(hawaii);
//
//
//        City hanoi = new City();
//        hanoi.setName("Hanoi");
//        hanoi.setCountry("Vietnam");
//        hanoi.setIataCode("HAN");
//        ArrayList<String> hanoitags = new ArrayList<String>();
//        hanoitags.add("culture");
//        hanoitags.add("partying");
//        hanoi.setTags(hanoitags);
//        hanoi.setWeather("hot");
//        hanoi.setDescription(" Hanoi—the capital of Vietnam—is known for its rich history, busy street life and centuries of French, Asian and Chinese influences all blended into one bustling city.\n");
//        hanoi.setImage(R.drawable.hanoi);
//        String id8 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id8).setValue(hanoi);
//
//
//        City lisbon = new City();
//        lisbon.setName("Lisbon");
//        lisbon.setCountry("Portugal");
//        lisbon.setIataCode("LIS");
//        ArrayList<String> lisbontags = new ArrayList<String>();
//        lisbontags.add("culture");
//        lisbontags.add("relaxation");
//        lisbon.setTags(lisbontags);
//        lisbon.setWeather("average");
//        lisbon.setDescription("Lisbon, the hilly capital of Portugal, is postcard-perfect with its cobbled streets, pristine waters and local Atlantic beaches.");
//        lisbon.setImage(R.drawable.lisbon);
//        String id9 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id9).setValue(lisbon);
//
//        City virunga = new City();
//        virunga.setName("Virunga National Park");
//        virunga.setCountry("Democratic Republic of Congo");
//        virunga.setIataCode("FIH");
//        ArrayList<String> virungatags = new ArrayList<String>();
//        virungatags.add("nature");
//        virunga.setTags(virungatags);
//        virunga.setWeather("hot");
//        virunga.setDescription("Virunga National Park is one of the most biologically diverse areas on the planet and home to the world’s critically endangered mountain gorillas");
//        virunga.setImage(R.drawable.virunga_park);
//        String id10 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id10).setValue(virunga);
//
//        City machuPicchu = new City();
//        machuPicchu.setName("Machu Picchu");
//        machuPicchu.setCountry("Peru");
//        machuPicchu.setIataCode("MFT");
//        ArrayList<String> machuPicchutags = new ArrayList<String>();
//        machuPicchutags.add("culture");
//        machuPicchutags.add("nature");
//        machuPicchu.setTags(machuPicchutags);
//        machuPicchu.setWeather("average");
//        machuPicchu.setDescription("The wide, panoramic windows are perfect for soaking up the view, plus they serve Pisco Sours on board.");
//        machuPicchu.setImage(R.drawable.machu_picchu);
//        String id11 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id11).setValue(machuPicchu);
//
//        City providencia = new City();
//        providencia.setName("Providencia");
//        providencia.setCountry("Colombia");
//        providencia.setIataCode("PVA");
//        ArrayList<String> providenciatags = new ArrayList<String>();
//        providenciatags.add("culture");
//        providenciatags.add("culture");
//        providencia.setTags(providenciatags);
//        providencia.setWeather("hot");
//        providencia.setDescription("The Colombian island of Providencia is the perfect combination of South America and the Caribbean.");
//        providencia.setImage(R.drawable.providencia);
//        String id12 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id12).setValue(providencia);
//
//        City laucala = new City();
//        laucala.setName("Laucala Island Resort");
//        laucala.setCountry("Fiji");
//        laucala.setIataCode("NAN");
//        ArrayList<String> laucalatags = new ArrayList<String>();
//        laucalatags.add("nature");
//        laucalatags.add("relaxation");
//        laucala.setTags(laucalatags);
//        laucala.setWeather("hot");
//        laucala.setDescription("Laucala Island Resort is a private island in Fiji, in absolute paradise. There are coconut trees, a sustainable farm and miles of beach, as well as coral reefs, postcard-perfect beaches and lush rainforest.");
//        laucala.setImage(R.drawable.laucala_island);
//        String id13 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id13).setValue(laucala);
//
//        City seoul = new City();
//        seoul.setName("Seoul");
//        seoul.setCountry("Korea");
//        seoul.setIataCode("ICN");
//        ArrayList<String> seoultags = new ArrayList<String>();
//        seoultags.add("partying");
//        seoul.setTags(seoultags);
//        seoul.setWeather("cold");
//        seoul.setDescription("Seoul is a vibrant metropolis where old-meets-new, with pop culture (K-Pop!) alongside Buddhist temples.");
//        seoul.setImage(R.drawable.seoul);
//        String id14 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id14).setValue(seoul);
//
//
//        City copenhagen = new City();
//        copenhagen.setName("Copenhagen");
//        copenhagen.setCountry("Denmark");
//        copenhagen.setIataCode("CPH");
//        ArrayList<String> copenhagentags = new ArrayList<String>();
//        copenhagentags.add("culture");
//        copenhagentags.add("partying");
//        copenhagen.setTags(copenhagentags);
//        copenhagen.setWeather("cold");
//        copenhagen.setDescription("Copenhagen’s rustic fishing ports, modern graffiti and winding red brick streets are just some of what makes it such a beautiful bucket list destination.");
//        copenhagen.setImage(R.drawable.copenhagen);
//        String id15 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id15).setValue(copenhagen);
//
//
//        City cairo = new City();
//        cairo.setName("Cairo");
//        cairo.setCountry("Egypt");
//        cairo.setIataCode("CAI");
//        ArrayList<String> cairotags = new ArrayList<String>();
//        cairotags.add("culture");
//        cairotags.add("nature");
//        cairo.setTags(cairotags);
//        cairo.setWeather("hot");
//        cairo.setDescription("Cairo is one of the most ancient cities in the world. Sitting on the Nile river with wonderful museums, vibrant culture and friendly locals, it makes for a great holiday.\n");
//        cairo.setImage(R.drawable.cairo);
//        String id16 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id16).setValue(cairo);
//
//
//        City santiago = new City();
//        santiago.setName("Santiago");
//        santiago.setCountry("Chile");
//        santiago.setIataCode("SCL");
//        ArrayList<String> santiagotags = new ArrayList<String>();
//        santiagotags.add("partying");
//        santiagotags.add("culture");
//        santiago.setTags(santiagotags);
//        santiago.setWeather("average");
//        santiago.setDescription("Santiago is a cosmopolitan city with the very best of Chilean culture; art galleries, design shops and handicraft markets, as well as lively Latino nightlife.");
//        santiago.setImage(R.drawable.santiago);
//        String id17 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id17).setValue(santiago);
//
//
//        City amsterdam = new City();
//        amsterdam.setName("Amsterdam");
//        amsterdam.setCountry("the Kingdom of the Netherlands");
//        amsterdam.setIataCode("AMS");
//        ArrayList<String> amsterdamtags = new ArrayList<String>();
//        amsterdamtags.add("partying");
//        amsterdamtags.add("culture");
//        amsterdam.setTags(amsterdamtags);
//        amsterdam.setWeather("average");
//        amsterdam.setDescription("Forget about cliched images of smoke shops and gaudy red lights. From floating flower markets to bohemian neighborhoods, this city has it all.");
//        amsterdam.setImage(R.drawable.amsterdam);
//        String id18 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id18).setValue(amsterdam);
//
//        City barbados = new City();
//        barbados.setName("Barbabos");
//        barbados.setCountry("Caribbean island");
//        barbados.setIataCode("BGI");
//        ArrayList<String> barbadostags = new ArrayList<String>();
//        barbadostags.add("culture");
//        barbadostags.add("relaxation");
//        barbados.setTags(barbadostags);
//        barbados.setWeather("hot");
//        barbados.setDescription("Barbados is one of those magical holiday destinations that everybody dreams about visiting.");
//        barbados.setImage(R.drawable.barbabos);
//        String id19 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id19).setValue(barbados);
//
//        City hongkong = new City();
//        hongkong.setName("Hong Kong");
//        hongkong.setCountry("China");
//        hongkong.setIataCode("HKG");
//        ArrayList<String> hongkongtags = new ArrayList<String>();
//        hongkongtags.add("partying");
//        hongkongtags.add("culture");
//        hongkong.setTags(hongkongtags);
//        hongkong.setWeather("average");
//        hongkong.setDescription("Famous for its skylines and vibrant food scene, what most people don’t know is that 70% of Hong Kong is mountains and lush parks.");
//        hongkong.setImage(R.drawable.hongkong);
//        String id20 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id20).setValue(hongkong);
//
//        City petra = new City();
//        petra.setName("Petra");
//        petra.setCountry("Jordan");
//        petra.setIataCode("KBR");
//        ArrayList<String> petratags = new ArrayList<String>();
//        petratags.add("nature");
//        petratags.add("culture");
//        petra.setTags(petratags);
//        petra.setWeather("average");
//        petra.setDescription("The ancient Nabatean city of Petra in southern Jordan is surrounded by beautiful red rocks and steep gorges. The world wonder is without a doubt Jordan’s most valuable treasure and greatest tourist attraction.");
//        petra.setImage(R.drawable.petra);
//        String id21 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id21).setValue(petra);
//
//        City riodeJaneiro = new City();
//        riodeJaneiro.setName("Rio de Janeiro");
//        riodeJaneiro.setCountry("Brazil");
//        riodeJaneiro.setIataCode("GIG");
//        ArrayList<String> riodeJaneirotags = new ArrayList<String>();
//        riodeJaneirotags.add("nature");
//        riodeJaneirotags.add("culture");
//        riodeJaneiro.setTags(riodeJaneirotags);
//        riodeJaneiro.setWeather("hot");
//        riodeJaneiro.setDescription("Rio de Janeiro has always been one of the most iconic cities in the world with instantly recognizable landscapes and landmarks.");
//        riodeJaneiro.setImage(R.drawable.riodejaneiro);
//        String id22 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id22).setValue(riodeJaneiro);
//
//        City london = new City();
//        london.setName("London");
//        london.setCountry("England");
//        london.setIataCode("YXU");
//        ArrayList<String> londontags = new ArrayList<String>();
//        londontags.add("culture");
//        london.setTags(londontags);
//        london.setWeather("average");
//        london.setDescription("Pretty pink restaurants, futuristic space-age toilets and jungle skyline views are just some of our favorite things about London.");
//        london.setImage(R.drawable.london);
//        String id23 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id23).setValue(london);
//
//        City singapore = new City();
//        singapore.setName("Singapore");
//        singapore.setCountry("Singapore");
//        singapore.setIataCode("SIN");
//        ArrayList<String> singaporetags = new ArrayList<String>();
//        singaporetags.add("partying");
//        singaporetags.add("culture");
//        singapore.setTags(singaporetags);
//        singapore.setWeather("hot");
//        singapore.setDescription("Singapore is a small island city-state off southern Malaysia which punches way above its weight on a global level. It’s a modern city with colorful buildings, futuristic bridges and a cloud forest.");
//        singapore.setImage(R.drawable.singapore);
//        String id24 = databaseReference.push().getKey();
//        databaseReference.child("City").child(id24).setValue(singapore);
    }

    public void start(){
        Intent intent = new Intent(this, ResultsPage.class);
        startActivity(intent);
    }
}