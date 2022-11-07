package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

         addData();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.startBtn);
        btn.setOnClickListener(v -> start());

        tvResult = findViewById(R.id.textView2);
        String cityName = "Toronto";
        String tempUrl = url + "?q=" + cityName + "&appid=" + appid;

//        AsyncTaskRunner runner = new AsyncTaskRunner();
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

    private void addData(){
        City bali = new City();
        bali.setName("Bali");
        bali.setCounrty("Indonesia");
        ArrayList<String> balitags = new ArrayList<String>();
        balitags.add("nature");
        balitags.add("relaxation");
        bali.setTags(balitags);
        bali.setWeather("hot");
        bali.setDescription("A place where you can relax you can find beaches, volcanoes, and jungles. \n");
        bali.setImage(R.drawable.bali);
        String id = databaseReference.push().getKey();
        databaseReference.child("City").child(id).setValue(bali);

        City newOrleans = new City();
        newOrleans.setName("New Orleans");
        newOrleans.setCounrty("United States");
        ArrayList<String> newOrleanstags = new ArrayList<String>();
        newOrleanstags.add("partying");
        newOrleanstags.add("culture");
        newOrleans.setTags(newOrleanstags);
        newOrleans.setWeather("hot");
        newOrleans.setDescription("The lively city known for street music, festive vibes and a melting pot");
        newOrleans.setImage(R.drawable.neworleans);
        String id1 = databaseReference.push().getKey();
        databaseReference.child("City").child(id1).setValue(newOrleans);


        City kerry = new City();
        kerry.setName("Kerry");
        kerry.setCounrty("Ireland");
        ArrayList<String> kerrytags = new ArrayList<String>();
        kerrytags.add("nature");
        kerrytags.add("relaxation");
        kerry.setTags(kerrytags);
        kerry.setWeather("average");
        kerry.setDescription("The city is famous for having unique small towns such as dingle and Killarney National Park, mountains, lakes and coasts.");
        kerry.setImage(R.drawable.kerry);
        String id2 = databaseReference.push().getKey();
        databaseReference.child("City").child(id2).setValue(kerry);

        City marrakesh = new City();
        marrakesh.setName("Marrakesh");
        marrakesh.setCounrty("Morocco");
        ArrayList<String> marrakeshtags = new ArrayList<String>();
        marrakeshtags.add("culture");
        marrakesh.setTags(marrakeshtags);
        marrakesh.setWeather("hot");
        marrakesh.setDescription("This ancient walled city is home to mosques, places and lush gardens. It is known as red city");
        marrakesh.setImage(R.drawable.marrakech);
        String id3 = databaseReference.push().getKey();
        databaseReference.child("City").child(id3).setValue(marrakesh);
    }


    public void start(){
        Intent intent = new Intent(this, Page2.class);
        startActivity(intent);
    }
}