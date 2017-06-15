package com.example.w028006g.regnlogin.helper;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseRetrieval extends AppCompatActivity {

    private String TAG = DatabaseRetrieval.class.getSimpleName();

    ArrayList<HashMap<String, String>> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataList = new ArrayList<>();

        new GetAttractions().execute();
    }

    private class GetAttractions extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(DatabaseRetrieval.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://concussive-shirt.000webhostapp.com/get_details_attractions.php";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray dataRe = jsonObj.getJSONArray("attractions");

                    // looping through All Attractions
                    for (int i = 0; i < dataRe.length(); i++) {
                        JSONObject c = dataRe.getJSONObject(i);
                        String id = c.getString("autoNum");
                        String name = c.getString("name");
                        String desc = c.getString("des");
                        String price = c.getString("price");
                        String address = c.getString("address");
                        String postcode = c.getString("postcode");
                        String website = c.getString("website");
                        String lat = c.getString("lat");
                        String lng = c.getString("lng");

                        if(price.equalsIgnoreCase("0")) {
                            price = "FREE!";
                        }

                        // tmp hash map for single contact
                        HashMap<String, String> data = new HashMap<>();

                        // adding each child node to HashMap key => value
                        data.put("id", id);
                        data.put("name", name);
                        data.put("des", desc);
                        data.put("price", price);
                        data.put("address", address);
                        data.put("postcode", postcode);
                        data.put("website", website);
                        data.put("lat", lat);
                        data.put("lng", lng);

                        // adding contact to contact list
                        dataList.add(data);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }
    }
}