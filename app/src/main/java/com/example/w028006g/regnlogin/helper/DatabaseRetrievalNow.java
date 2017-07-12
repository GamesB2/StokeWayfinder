package com.example.w028006g.regnlogin.helper;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.example.w028006g.regnlogin.activity.MainActivity;
import com.example.w028006g.regnlogin.activity.MapsActivityNew;

import com.example.w028006g.regnlogin.activity.LoginActivity;
import com.example.w028006g.regnlogin.app.AppConfig;
import com.example.w028006g.regnlogin.app.AppController;
import com.google.gson.Gson;

import org.json.JSONArray;


import com.example.w028006g.regnlogin.app.AppConfig;
import com.example.w028006g.regnlogin.app.AppController;
import com.example.w028006g.regnlogin.helper.MarkerClasses.Post;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseRetrievalNow {

    private String TAG = DatabaseRetrievalNow.class.getSimpleName();

    private ProgressDialog pDialog;

    ArrayList<HashMap<String, String>> dataList;

    //public static ArrayList<List> postsAl = new ArrayList();

    public static Post post;
    private String posts="";
    public static String[] postsParts;
    public boolean bol=false;

    public DatabaseRetrievalNow()
    {
        //new GetAttractions().execute();
        //dataList = new ArrayList<>();
        registerUser(MainActivity.userDetails.getEmail());
    }


    public void onCreate()  {


    }



    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String email) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        //pDialog.setMessage("Get Posts....");
        //showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_POSTS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
               // hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    error=false;
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String posts = jObj.getString("posts");


                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());

               // hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }



   /* public class GetAttractions extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0)
        {

            HttpHandler shA = new HttpHandler();


            // Making a request to url and getting response

            String urlA = "https://concussive-shirt.000webhostapp.com/get_details_user_posts.php";


            String jsonStrA = shA.makeServiceCall(urlA);


            Log.e(TAG, "Response from url: " + jsonStrA);

            if (jsonStrA != null)
            {
            try {
                JSONObject jsonObj = new JSONObject(jsonStrA);

                // Getting JSON Array node
                JSONArray dataRe = jsonObj.getJSONArray("posts");

                // looping through All Attractions
                for (int i = 0; i < dataRe.length(); i++) {
                    JSONObject c = dataRe.getJSONObject(i);
                    posts = c.getString("posts");

                    Log.e(TAG, "Got OK!: ");
                    bol =true;
                }

                postsParts = posts.split(",");
            } catch (final JSONException eA) {
                Log.i(TAG, "Json parsing error: " + eA.getMessage());
                    *//*runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });*//*

            }
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

    }*/


}