package com.example.w028006g.regnlogin.helper;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.w028006g.regnlogin.activity.MapsActivityNew;

import com.example.w028006g.regnlogin.activity.MyDialog;
import com.example.w028006g.regnlogin.helper.MarkerClasses.Attraction;
import com.example.w028006g.regnlogin.helper.MarkerClasses.Event;
import com.example.w028006g.regnlogin.helper.MarkerClasses.MIcon;
import com.example.w028006g.regnlogin.helper.MarkerClasses.IconManager;
import com.example.w028006g.regnlogin.helper.MarkerClasses.Landmark;
import com.example.w028006g.regnlogin.helper.MarkerClasses.Post;

import com.google.android.gms.maps.MapsInitializer;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class DatabaseRetrieval  extends Service {

    private String TAG = DatabaseRetrieval.class.getSimpleName();
    ArrayList<HashMap<String, String>> dataList;


    public static IconManager iconManager;

    public static ArrayList<Ticket> ticketsAl = new ArrayList<>();
    //public static ArrayList<Events> eventsAl = new ArrayList(); //Main events list.
    public static ArrayList<Post> prevPost = new ArrayList<>();


    public static Attraction att;
    public static Landmark lndmk;
    public static Event event;
    public static Ticket ticket;
    public static Post post;

    public static int ncount = 0;







    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
//        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        MapsInitializer.initialize(getApplicationContext());
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onCreate()  {



        Intent intent = new Intent(getApplicationContext(),MyDialog.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        MapsInitializer.initialize(getApplicationContext());
        new GetAttractions().execute();
        dataList = new ArrayList<>();
    }

    private class GetAttractions extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            Toast.makeText(DatabaseRetrieval.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            MIcon.createIcons();

            HttpHandler shA = new HttpHandler();
            HttpHandler shL = new HttpHandler();
            HttpHandler shE = new HttpHandler();
            HttpHandler shT = new HttpHandler();
            HttpHandler shP = new HttpHandler();

            // Making a request to url and getting response

            String urlA = "https://concussive-shirt.000webhostapp.com/get_details_attractions.php";
            String urlL = "https://concussive-shirt.000webhostapp.com/get_details_landmarks.php";
            String urlE = "https://concussive-shirt.000webhostapp.com/get_details_events.php";
            String urlT = "https://concussive-shirt.000webhostapp.com/get_details_tickets.php";
            String urlP = "https://concussive-shirt.000webhostapp.com/get_details_posts.php";

            String jsonStrA = shA.makeServiceCall(urlA);
            String jsonStrL = shL.makeServiceCall(urlL);
            String jsonStrE = shE.makeServiceCall(urlE);
            String jsonStrT = shT.makeServiceCall(urlT);
            String jsonStrP = shP.makeServiceCall(urlP);

            Log.e(TAG, "Response from url: " + jsonStrA);
            Log.e(TAG, "Response from url: " + jsonStrL);
            Log.e(TAG, "Response from url: " + jsonStrE);
            Log.e(TAG, "Response from url: " + jsonStrT);
            Log.e(TAG, "Response from url: " + jsonStrP);
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

                    att = new Attraction(name, lat, lng);

                    att.setID(id);
                    att.setDesc(desc);
                    att.setPrice(price);
                    att.setAddressLine(address);
                    att.setPostCode(postcode);
                    att.setWeb(website);
                    att.setIcon(icon);

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

            if (jsonStrP != null)
            {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStrP);

                    // Getting JSON Array node
                    JSONArray dataRp = jsonObj.getJSONArray("posts");

                    // looping through All Attractions
                    for (int i = 0; i < dataRp.length(); i++) {
                        JSONObject c = dataRp.getJSONObject(i);
                        String id = c.getString("id");
                        String name = c.getString("name");
                        String website = c.getString("website");
                        String lat = c.getString("lat");
                        String lng = c.getString("lng");
                        String txt = c.getString("txt");
                        String video = c.getString("video");
                        String summary = c.getString("summary");
                        String qr = c.getString("qr");

                        post = new Post(id,lat,lng);
                        post.setName(name);
                        post.setWeb(website);
                        post.setDesc(txt);
                        post.setVideo(video);
                        post.setSummary(summary);
                        post.setQr(qr);



                        Log.e(TAG, "Posts Added OK!: ");

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

            if (jsonStrE != null)
            {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStrE);

                    // Getting JSON Array node
                    JSONArray dataRe = jsonObj.getJSONArray("events");

                    // looping through All Attractions
                    for (int i = 0; i < dataRe.length(); i++) {
                        JSONObject c = dataRe.getJSONObject(i);
                        String id = c.getString("autoNumber");
                        String name = c.getString("name");
                        String dateStart = c.getString("dateStart");
                        String desc = c.getString("description");
                        String cName = c.getString("contactName");
                        String cNumber = c.getString("contactNumber");
                        String website = c.getString("website");
                        String price = c.getString("price");
                        String time = c.getString("time");
                        String dateEnd = c.getString("dateEnd");
                        String cat = c.getString("cat");
                        String add = c.getString("address");
                        String pcode = c.getString("postcode");
                        String lat = c.getString("lat");
                        String lng = c.getString("lng");

                        event = new Event(name,lat,lng);
                        event.setID(id);
                        event.setDesc(desc);
                        event.setWeb(website);
                        event.setPrice(price);
                        event.setPostCode(pcode);
                        event.setAddressLine(add);
                        event.setStartDate(dateStart,time);
                        event.setEndDate(dateEnd);
                        event.setIcon(cat);
                        event.setConName(cName);
                        event.setConNum(cNumber);


                        Log.e(TAG, "Events Added OK!: ");

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
                        String id = c.getString("id");
                        String name = c.getString("name");
                        String desc = c.getString("des");
                        String price = c.getString("price");
                        String address = c.getString("address");
                        String postcode = c.getString("postcode");
                        String website = c.getString("website");
                        String lat = c.getString("lat");
                        String lng = c.getString("lng");


                        lndmk = new Landmark(name, lat, lng);
                        lndmk.setID(id);
                        lndmk.setDesc(desc);
                        lndmk.setPrice(price);
                        lndmk.setAddressLine(address);
                        lndmk.setPostCode(postcode);
                        lndmk.setWeb(website);



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

                if (jsonStrT != null)
                {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStrT);

                        // Getting JSON Array node
                        JSONArray dataRt = jsonObj.getJSONArray("tickets");

                        // looping through All Attractions
                        for (int i = 0; i < dataRt.length(); i++)
                        {
                            JSONObject c = dataRt.getJSONObject(i);
                            String id = c.getString("id");
                            String name = c.getString("title");
                            String sDate = c.getString("date_time");
                            String loc = c.getString("loc");
                            String desc = c.getString("summary");
                            String organ = c.getString("organ");
                            String type = c.getString("type");
                            String price = c.getString("price");
                            String img = c.getString("img");

                            ticket = new Ticket(Integer.parseInt(id),name,sDate,loc,desc,organ,type,Double.parseDouble(price),img);
                            ticketsAl.add(ticket);
                            Log.e(TAG, "Tickets Added OK!: ");
                        }
                    } catch (final JSONException eT) {
                        Log.i(TAG, "Json parsing error: " + eT.getMessage());
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

            MyDialog.getInstance().finish();
            //MyDialog.hide();
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