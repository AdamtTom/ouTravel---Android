package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ResultsPage extends AppCompatActivity {

    TextView airline1;
    TextView airline2;
    TextView time1leg1;
    TextView time1leg2;
    TextView time2leg1;
    TextView time2leg2;
    TextView airport1leg1;
    TextView airport1leg2;
    TextView airport2leg1;
    TextView airport2leg2;
    TextView stops_leg1;
    TextView stops_leg2;
    TextView tripPrice;
    ImageView airplane1img;
    ImageView airplane2img;
    TextView dash1;
    TextView dash2;

    String originIATA;
    String destIATA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);

        airline1 = findViewById(R.id.airline1);
        airline2 = findViewById(R.id.airline2);
        time1leg1 = findViewById(R.id.time1leg1);
        time1leg2 = findViewById(R.id.time1leg2);
        time2leg1 = findViewById(R.id.time2leg1);
        time2leg2 = findViewById(R.id.time2leg2);
        airport1leg1 = findViewById(R.id.airport1leg1);
        airport1leg2 = findViewById(R.id.airport1leg2);
        airport2leg1 = findViewById(R.id.airport2leg1);
        airport2leg2 = findViewById(R.id.airport2leg2);
        stops_leg1 = findViewById(R.id.stops_leg1);
        stops_leg2 = findViewById(R.id.stops_leg2);
        tripPrice = findViewById(R.id.TripPrice);
        airplane1img = findViewById(R.id.airplane1img);
        airplane2img = findViewById(R.id.airplane2img);
        dash1 = findViewById(R.id.dash1);
        dash2 = findViewById(R.id.dash2);

        Bundle bundle = getIntent().getBundleExtra("Page4");
        Bundle b1 = getIntent().getBundleExtra("bundle");
        City dest = bundle.getParcelable("destination");
//        Toast.makeText(getApplicationContext(), "Iata: " + dest.getIataCode(), Toast.LENGTH_LONG).show();

        originIATA = "YVR";
        destIATA = dest.getIataCode();
        String departDate = b1.getString("start");
        String returnDate =b1.getString("end");
        String adults = "1";
        String currency = "CAD";
        String children = "0";
        String infants = "0";
        String cabinClass = "economy";
        String filter = "price"; // filter=price& before currency
        String tempUrl = "https://skyscanner50.p.rapidapi.com/api/v1/searchFlights?origin=" +
                originIATA + "&destination=" + destIATA + "&date=" + departDate + "&returnDate=" +
                returnDate + "&adults=" + adults + "&currency=" + currency;

//        Toast.makeText(getApplicationContext(), "URL: " + tempUrl, Toast.LENGTH_LONG).show();
        Log.i("request url: ", tempUrl);
        String imgStr = dest.getImage();
        int imgId = getResources().getIdentifier(imgStr, "drawable", getApplication().getPackageName());
        CircleImageView img = findViewById(R.id.roundedCityImg);
        img.setImageResource(imgId);

        TextView cityNameTV = findViewById(R.id.roundedCityName);
        cityNameTV.setText(dest.getName());

        TextView countryNameTV = findViewById(R.id.countryNameResult);
        String sourceString = "<b>Country:</b> " + dest.getCountry();
        countryNameTV.setText(Html.fromHtml(sourceString, 0));

        TextView departDateTV = findViewById(R.id.departDateResult);
        sourceString = "<b>Departure Date:</b> " + departDate;
        departDateTV.setText(Html.fromHtml(sourceString, 0));

        TextView returnDateTV = findViewById(R.id.returnDateResult);
        sourceString = "<b>Return Date:</b> " + returnDate;
        returnDateTV.setText(Html.fromHtml(sourceString, 0));

        TextView passengers = findViewById(R.id.passengerCountResult);
        sourceString = "<b>Passengers:</b> 1";// + returnDate;
        passengers.setText(Html.fromHtml(sourceString, 0));

//        ResultsPage.AsyncTaskRunner runner = new ResultsPage.AsyncTaskRunner();
//        runner.execute(tempUrl);
    }

    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            RequestQueue queue = Volley.newRequestQueue(ResultsPage.this);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, strings[0], null, response -> {
                try {
                    JSONArray data = response.getJSONArray("data");
                    Toast.makeText(getApplicationContext(), "data: " + data.toString(5), Toast.LENGTH_LONG).show();
                    JSONObject option1 = data.getJSONObject(0);
                    JSONObject priceObj = option1.getJSONObject("price");
                    double price = priceObj.getDouble("amount");

                    JSONObject leg1 = option1.getJSONArray("legs").getJSONObject(0);
                    JSONObject leg2 = option1.getJSONArray("legs").getJSONObject(1);
                    //Leg1
                    int leg1duration = leg1.getInt("duration");
                    String leg1carrier = leg1.getJSONArray("carriers").getJSONObject(0).getString("name");
                    int leg1stops = leg1.getInt("stop_count");
                    String leg1departTime = leg1.getString("departure").substring(11, 16);
                    String leg1arrivalTime = leg1.getString("arrival").substring(11, 16);

                    //Leg2
                    int leg2duration = leg2.getInt("duration");
                    String leg2carrier = leg2.getJSONArray("carriers").getJSONObject(0).getString("name");
                    int leg2stops = leg2.getInt("stop_count");
                    String leg2departTime = leg2.getString("departure").substring(11, 16);
                    String leg2arrivalTime = leg2.getString("arrival").substring(11, 16);
//                    Log.i("tag", "response:" + leg1carrier + leg2carrier + leg1departTime + leg1arrivalTime + leg2departTime + leg2arrivalTime);
                    airline1.setText(leg1carrier.replace(' ', '\n'));
                    airline2.setText(leg2carrier.replace(' ', '\n'));
                    time1leg1.setText(leg1departTime);
                    time1leg2.setText(leg1arrivalTime);
                    time2leg1.setText(leg2departTime);
                    time2leg2.setText(leg2arrivalTime);
                    airport1leg1.setText(originIATA);
                    airport1leg2.setText(destIATA);
                    airport2leg1.setText(destIATA);
                    airport2leg2.setText(originIATA);
                    stops_leg1.setText(leg1stops + " stop(s)");
                    stops_leg2.setText(leg2stops + " stop(s)");
                    airplane1img.setAlpha((float)1);
                    airplane2img.setAlpha((float)1);
                    dash1.setText("---------");
                    dash2.setText("---------");
                    String sourceString = "<b>Total Trip Price:</b> " + price + " CAD";
                    tripPrice.setText(Html.fromHtml(sourceString, 0));

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("tag", "JSON Error while handling request: " + e);
                    tripPrice.setText("No flights were found. Try again Later.");
                    tripPrice.setTextColor(Color.RED);
                }
            }, error -> Toast.makeText(ResultsPage.this, error.toString(), Toast.LENGTH_SHORT).show()) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("X-RapidAPI-Key", "2997260c62mshe6779e627ae5e7fp1ced22jsnf8590df105f3");
                    headers.put("X-RapidAPI-Host", "skyscanner50.p.rapidapi.com");
                    return headers;
                }
            };

            queue.add(request);
            return null;
        }
    }
}