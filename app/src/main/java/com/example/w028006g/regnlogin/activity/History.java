package com.example.w028006g.regnlogin.activity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.example.w028006g.regnlogin.R;
import com.example.w028006g.regnlogin.helper.DatabaseRetrieval;

import com.example.w028006g.regnlogin.helper.MyRecyclerViewAdapterPosts;
import com.example.w028006g.regnlogin.helper.MarkerClasses.Post;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.example.w028006g.regnlogin.app.AppConfig.CONNECTION_TIMEOUT;
import static com.example.w028006g.regnlogin.app.AppConfig.READ_TIMEOUT;

/**
 * Created by w028006g on 28/06/2017.
 */

public class History extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapterPosts adapter;
    private ArrayList<Post> postist = new ArrayList<>();
    private String posts="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        new AsyncLogin().execute(MainActivity.userDetails.getEmail());

    }

    private class AsyncLogin extends AsyncTask<String, String, String>
    {
        //ProgressDialog pdLoading = new ProgressDialog(DatabaseQ.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            //pdLoading.setMessage("\tLoading...");
            //pdLoading.setCancelable(false);
            //pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("https://concussive-shirt.000webhostapp.com/get_details_user_posts.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("email", params[0]);
                //.appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }

        }

        @Override
        protected void onPostExecute(String response) {
            //this method will be running on UI thread
            // pdLoading.dismiss();
            System.out.println(response);

            try {
                JSONObject jObj = new JSONObject(response);
                boolean error = jObj.getInt("success")==1 ? false : true;

                // Check for error node in json
                if (!error) {
                    JSONArray contacts = jObj.getJSONArray("posts");
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        posts = c.getString("posts");
                    }

                    posts = posts.substring(1);
                    String[] postsParts = posts.split(",");
                    ArrayList<Integer> post = new ArrayList<>();

                    //Now Sort

                    Set<String> mySet = new HashSet<String>(Arrays.asList(postsParts));
                    for(String s : mySet)
                    {
                        post.add(Integer.parseInt(s));
                    }

                    for(int i = 0; i< post.size(); i++)
                    {
                    for (int j = 0; j< DatabaseRetrieval.postsAl.size(); j++)
                    {
                        Integer check = post.get(i);

                        if(check == DatabaseRetrieval.postsAl.get(j).getId())
                        {
                            //if(!post.contains(postsParts[i]))
                            //{
                                postist.add(DatabaseRetrieval.postsAl.get(j));
                            //}
                        }
                    }
                }

                    mRecyclerView = (RecyclerView) findViewById(R.id.rvH);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(History.this));
                    adapter = new MyRecyclerViewAdapterPosts(History.this, postist);
                    mRecyclerView.setAdapter(adapter);

                    System.out.println(posts);

                    // Launch main activity

                } else {
                    // Error in login. Get the error message
                    String errorMsg = jObj.getString("error_msg");
                    //Toast.makeText(getApplicationContext(),
                    //errorMsg, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                // JSON error
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }



        }

    }
}
