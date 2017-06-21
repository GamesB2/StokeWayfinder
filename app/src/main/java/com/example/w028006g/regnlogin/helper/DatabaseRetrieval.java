package com.example.w028006g.regnlogin.helper;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
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

import com.example.w028006g.regnlogin.MapsActivityNew;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseRetrieval  extends Service {

    private String TAG = DatabaseRetrieval.class.getSimpleName();

    ArrayList<HashMap<String, String>> dataList;

    public static ArrayList<POI> poiArrayList = new ArrayList<>();
    public static Attraction att;
    public static Landmark lndmk;
    public static Event event;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }



    @Override
    public void onCreate()  {

        new GetAttractions().execute();
        dataList = new ArrayList<>();
    }

    private class GetAttractions extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(DatabaseRetrieval.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            HttpHandler shA = new HttpHandler();
            HttpHandler shL = new HttpHandler();
            HttpHandler shE = new HttpHandler();

            // Making a request to url and getting response

            String urlA = "https://concussive-shirt.000webhostapp.com/get_details_attractions.php";
            String urlL = "https://concussive-shirt.000webhostapp.com/get_details_landmarks.php";
            String urlE = "https://concussive-shirt.000webhostapp.com/get_details_events.php";

            String jsonStrA = shA.makeServiceCall(urlA);
            String jsonStrL = shL.makeServiceCall(urlL);
            String jsonStrE = shE.makeServiceCall(urlE);

            Log.e(TAG, "Response from url: " + jsonStrA);
            Log.e(TAG, "Response from url: " + jsonStrL);
            Log.e(TAG, "Response from url: " + jsonStrE);
            if (jsonStrA != null)
            {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStrA);

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
                        String icon = c.getString("cat");


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
                        data.put("icon", icon);

                        att = new Attraction(name, lat, lng);

                        att.setDesc(desc);
                        att.setPrice(price);
                        att.setAddressLine(address);
                        att.setPostCode(postcode);
                        att.setWeb(website);
                        att.setIcon(icon);
                        poiArrayList.add(att);


                        // adding contact to contact list
                        dataList.add(data);


                        Log.e(TAG, "Attractions Added OK!: ");

                    }
                } catch (final JSONException eA) {
                    Log.i(TAG, "Json parsing error: " + eA.getMessage());
                    /*runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });*/

                }
            }
            if (jsonStrL != null)
            {
                try
                {
                    JSONObject jsonObj = new JSONObject(jsonStrL);

                    // Getting JSON Array node
                    JSONArray dataRe = jsonObj.getJSONArray("landmark");

                    // looping through All Attractions
                    for (int i = 0; i < dataRe.length(); i++)
                    {
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

                        lndmk = new Landmark(name, lat, lng);

                        lndmk.setDesc(desc);
                        lndmk.setPrice(price);
                        lndmk.setAddressLine(address);
                        lndmk.setPostCode(postcode);
                        lndmk.setWeb(website);
                        poiArrayList.add(lndmk);


                        // adding contact to contact list
                        dataList.add(data);


                        Log.e(TAG, "Landmarks Added OK!: ");

                    }
                } catch (final JSONException eL)
                {
                    Log.i(TAG, "Json parsing error: " + eL.getMessage());
                    /*runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });*/

                }
//            if (jsonStrE != null)
//            {
//                try {
//                    JSONObject jsonObj = new JSONObject(jsonStrE);
//
//                    // Getting JSON Array node
//                    JSONArray dataRe = jsonObj.getJSONArray("attractions");
//
//                    // looping through All Attractions
//                    for (int i = 0; i < dataRe.length(); i++) {
//                        JSONObject c = dataRe.getJSONObject(i);
//                        String id = c.getString("autoNum");
//                        String name = c.getString("name");
//                        String desc = c.getString("des");
//                        String price = c.getString("price");
//                        String address = c.getString("address");
//                        String postcode = c.getString("postcode");
//                        String website = c.getString("website");
//                        String lat = c.getString("lat");
//                        String lng = c.getString("lng");
//
//
//                        // tmp hash map for single contact
//                        HashMap<String, String> data = new HashMap<>();
//
//                        // adding each child node to HashMap key => value
//                        data.put("id", id);
//                        data.put("name", name);
//                        data.put("des", desc);
//                        data.put("price", price);
//                        data.put("address", address);
//                        data.put("postcode", postcode);
//                        data.put("website", website);
//                        data.put("lat", lat);
//                        data.put("lng", lng);
//
//                        event = new Event(name, lat, lng);
//
//                        event.setDesc(desc);
//                        event.setPrice(price);
//                        event.setAddressLine(address);
//                        event.setPostCode(postcode);
//                        event.setWeb(website);
//                        poiArrayList.add(event);
//
//
//                        // adding contact to contact list
//                        dataList.add(data);
//
//
//                        Log.e(TAG, "Attractions Added OK!: ");
//
//                    }
//                } catch (final JSONException eA) {
//                    Log.i(TAG, "Json parsing error: " + eA.getMessage());
//                /*runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                "Json parsing error: " + e.getMessage(),
//                                Toast.LENGTH_LONG).show();
//                    }
//                });*/
//
//                }
//            }

            } else
                {
                Log.e(TAG, "Couldn't get json from attractions server.");
                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });*/
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            //super.onPostExecute(result);

            //Intent intent = new Intent(DatabaseRetrieval.this);
                //intent.putExtra("DatabaseRetrieval" , att);
            //test();


        }

        public void test () {
            Attraction src = att;
            Gson gS = new Gson();
            String target = gS.toJson(src); // Converts the object to a JSON String
            Intent i = new Intent(DatabaseRetrieval.this, MapsActivityNew.class);
            i.putExtra("MyObjectAsString", target);
            startActivity(i);
        }
    }
}