package com.example.w028006g.regnlogin.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.w028006g.regnlogin.BottomNavigationViewHelper;
import com.example.w028006g.regnlogin.Person;
import com.example.w028006g.regnlogin.R;
import com.example.w028006g.regnlogin.Tickets_My;
import com.example.w028006g.regnlogin.helper.DatabaseRetrieval;
import com.example.w028006g.regnlogin.helper.DownloadImageTask;
import com.example.w028006g.regnlogin.helper.MyRecyclerViewAdapterPosts;
import com.example.w028006g.regnlogin.helper.Post;
import com.example.w028006g.regnlogin.helper.SQLiteHandler;
import com.example.w028006g.regnlogin.helper.SessionManager;

import android.view.View;

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

public class Profile extends AppCompatActivity {

    private TextView txtName;
    private TextView txtEmail;
    private Button btnView;
    private Button btnHistory;
    private Button btnPoints;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapterPosts adapter;
    private ArrayList<Post> postist = new ArrayList<>();
    private String posts="";

    private Button btnLogout;
    private Button btnMaps;
    private ImageView imgUser;



    private SQLiteHandler db;
    private SessionManager session;
    public static Person userDetails;

    String name;
    String email;
    String u_id;
    String tickets;


    private Scene scene1, scene2;
    //transition to move between scenes
    private Transition transition;
    //flag to swap between scenes
    private boolean start;

    private ArrayList<Integer> post = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(android.view.Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtUserEmail);
        btnView = (Button) findViewById(R.id.btnTickets);
        //btnHistory = (Button) findViewById(R.id.btnHistory);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId()) {
                    case R.id.ic_map:
                        Intent intent = new Intent(getApplicationContext(), MapsActivityNew.class);
                        startActivity(intent);
                        break;

                    case R.id.ic_Profile:
                        Intent intent1 = new Intent(getApplicationContext(), Profile.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_qr:
                        Intent intent2 = new Intent(getApplicationContext(), qrActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_shop:
                        Intent intent3 = new Intent(getApplicationContext(), Tickets.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_Rec:
                        Intent intent4 = new Intent(getApplicationContext(), StartScreen.class);
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });

        //Download user image
        new DownloadImageTask((ImageView) findViewById(R.id.profilePic))
                .execute("https://concussive-shirt.000webhostapp.com/uploads/" + MainActivity.userDetails.getU_id() + ".png" );
        // Displaying the user details on the screen
        txtName.setText(MainActivity.userDetails.getName());
        txtEmail.setText(MainActivity.userDetails.getEmail());

        btnView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(Profile.this, Tickets_My.class);
                startActivity(intent4);
            }
        });

/*        btnHistory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(Profile.this, History.class);
                startActivity(intent4);
            }
        });*/

/*        btnPoints.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(Profile.this, My_Points.class);
                startActivity(intent5);
            }
        });*/

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        new Profile.AsyncLogin().execute(MainActivity.userDetails.getEmail());
    }


    public void changeScene(View v){

        //check flag
        if(start) {
            TransitionManager.go(scene2, transition);
            start=false;
        }
        else {
            TransitionManager.go(scene1, transition);
            start=true;
        }
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

                    if (!posts.equalsIgnoreCase("")) {
                        posts = posts.substring(1);
                        String[] postsParts = posts.split(",");


                        //Now Sort

                        Set<String> mySet = new HashSet<String>(Arrays.asList(postsParts));
                        for (String s : mySet) {
                            post.add(Integer.parseInt(s));
                        }

                        for (int i = 0; i < post.size(); i++) {
                            for (int j = 0; j < DatabaseRetrieval.postsAl.size(); j++) {
                                Integer check = post.get(i);

                                if (check == DatabaseRetrieval.postsAl.get(j).getId()) {
                                    //if(!post.contains(postsParts[i]))
                                    //{
                                    postist.add(DatabaseRetrieval.postsAl.get(j));
                                    //}
                                }
                            }
                        }

                        mRecyclerView = (RecyclerView) findViewById(R.id.rvH);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(Profile.this));
                        adapter = new MyRecyclerViewAdapterPosts(Profile.this, postist);
                        mRecyclerView.setAdapter(adapter);

                        System.out.println(posts);

                        // Launch main activity
                    }
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

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(Profile.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
