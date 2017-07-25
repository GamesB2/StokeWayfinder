package com.example.w028006g.regnlogin.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

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
import com.example.w028006g.regnlogin.MainActivity1;
import com.example.w028006g.regnlogin.Person;
import com.example.w028006g.regnlogin.R;
import com.example.w028006g.regnlogin.app.AppController;
import com.example.w028006g.regnlogin.helper.DatabaseRetrieval;
import com.example.w028006g.regnlogin.helper.DownloadImageTask;
import com.example.w028006g.regnlogin.helper.MarkerClasses.POI;
import com.example.w028006g.regnlogin.helper.MyRecyclerViewAdapterPosts;
import com.example.w028006g.regnlogin.helper.MarkerClasses.Post;
import com.example.w028006g.regnlogin.helper.SQLiteHandler;
import com.example.w028006g.regnlogin.helper.SessionManager;
import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.listener.OnRequestSocialPersonCompleteListener;
import com.github.gorbin.asne.core.persons.SocialPerson;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import android.view.View;
import android.widget.Toast;


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

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.w028006g.regnlogin.app.AppConfig.CONNECTION_TIMEOUT;
import static com.example.w028006g.regnlogin.app.AppConfig.READ_TIMEOUT;

public class Profile extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {


    private TextView txtName;
    private TextView txtEmail;
    private Button btnView;
    private Button btnHistory;
    private Button btnPoints;
    private Button btnSelPhoto;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapterPosts adapter;
    private ArrayList<Post> postist = new ArrayList<>();
    private String posts="";

    private Button btnLogout;
    private Button btnMaps;
    private CircleImageView imgUser;

    private GoogleApiClient mGoogleApiClient;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private SQLiteHandler db;
    private SessionManager session;
    public static Person userDetails;

    private SocialNetwork socialNetwork;
    private int networkId;

    private ArrayList<Post> alPrevScan = new ArrayList<>();

    private TextView emptyText;

    String name;
    String email;
    String u_id;
    String tickets;
    private Thread thread;

    private Scene scene1, scene2;
    //transition to move between scenes
    private Transition transition;
    //flag to swap between scenes
    private boolean start;


    private String personName;
    private String personGivenName;
    private String personFamilyName;
    private String personEmail;
    private String personId;
    private Uri personPhoto;

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
        imgUser = (CircleImageView) findViewById(R.id.profilePic);
        imgUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setType("image*//*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);


        for(int i = 0; i < DatabaseRetrieval.prevPost.size(); i++)
        {
            alPrevScan.add(DatabaseRetrieval.prevPost.get(i));
        }

        if(alPrevScan.size()==0)
        {
            emptyText = (TextView) findViewById(R.id.emptyText);
            emptyText.setVisibility(View.VISIBLE);
        }
        else
        {
            mRecyclerView = (RecyclerView) findViewById(R.id.rvH);
            mLayoutManager = new LinearLayoutManager(Profile.this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new MyRecyclerViewAdapterPosts(Profile.this, alPrevScan);
            mRecyclerView.setAdapter(mAdapter);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.ic_map:
                        Intent intent = new Intent(getApplicationContext(), MapsActivityNew.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        break;

                    case R.id.ic_Profile:
                        Intent intent1 = new Intent(getApplicationContext(), Profile.class);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        finish();
                        break;

                    case R.id.ic_qr:
                        Intent intent2 = new Intent(getApplicationContext(), qrActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        finish();
                        break;

                    case R.id.ic_shop:
                        Intent intent3 = new Intent(getApplicationContext(), Tickets.class);
                        startActivity(intent3);
                        overridePendingTransition(0, 0);
                        finish();
                        break;

                    case R.id.ic_Rec:

                        Intent intent4 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent4);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                }
                return false;
            }
        });

//        //Download user image
//        new DownloadImageTask((ImageView) findViewById(R.id.profilePic))
//
//                .execute("https://concussive-shirt.000webhostapp.com/uploads/" + MainActivity.userDetails.getU_id() + ".png");
//
//        // Displaying the user details on the screen
//        txtName.setText(MainActivity.userDetails.getName());
//        txtEmail.setText(MainActivity.userDetails.getEmail());

        btnView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(Profile.this, Tickets_My.class);
                startActivity(intent4);
                overridePendingTransition(0,0);
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

        SharedPreferences prefs = AppController.getInstance().getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        networkId = prefs.getInt("SocialNet", -1);

        switch (networkId) {
            case 10:
                new DownloadImageTask((ImageView) findViewById(R.id.profilePic))
                        .execute("https://concussive-shirt.000webhostapp.com/uploads/" + MainActivity.userDetails.getU_id() + ".png");
                // Displaying the user details on the screen
                SharedPreferences emaillogin = AppController.getInstance().getSharedPreferences("EmailLogin", Context.MODE_PRIVATE);
                personEmail = emaillogin.getString("email", "WrongName");
                personName = emaillogin.getString("name", "WrongName");
                txtName.setText(personName);
                txtEmail.setText(personEmail);
                break;

            case 3:

//                MainActivity.userDetails.setName(AppController.getInstance().getGName());
//                MainActivity.userDetails.setEmail(AppController.getInstance().getGEmail());


                // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
                SharedPreferences login = AppController.getInstance().getSharedPreferences("GoogleLogin", Context.MODE_PRIVATE);
                personEmail = login.getString("email", "WrongName");
                personName = login.getString("name", "WrongName");
                txtName.setText(personName);
                txtEmail.setText(personEmail);
                break;

            case 4:
                socialNetwork = LoginActivity.mSocialNetworkManager.getSocialNetwork(networkId);
                socialNetwork.setOnRequestCurrentPersonCompleteListener(new OnRequestSocialPersonCompleteListener() {
                    @Override
                    public void onRequestSocialPersonSuccess(int socialNetworkId, SocialPerson socialPerson) {
                        txtName.setText(socialPerson.name);
                        txtEmail.setText(socialPerson.email);
                        new DownloadImageTask((ImageView) findViewById(R.id.profilePic))
                                .execute(socialPerson.avatarURL);
                        MainActivity.userDetails.setName(socialPerson.name);
                        MainActivity.userDetails.setEmail(socialPerson.email);
                    }

                    @Override
                    public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
//                        Toast.makeText(getApplicationContext(), "something went oopsy", Toast.LENGTH_SHORT);
                    }
                });
                socialNetwork.requestCurrentPerson();
                break;

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        buildGoogleApiClient();
        mGoogleApiClient.connect();
    }

    protected synchronized void buildGoogleApiClient() {

        if (mGoogleApiClient == null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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

                    if(!posts.equalsIgnoreCase("")) {
                        posts = posts.substring(1);
                        String[] postsParts = posts.split(",");
                        ArrayList<Integer> post = new ArrayList<>();

                        //Now Sort

                        Set<String> mySet = new HashSet<String>(Arrays.asList(postsParts));
                        for (String s : mySet) {
                            post.add(Integer.parseInt(s));
                        }

                        for (int i = 0; i < post.size(); i++) {
                            for (int j = 0; j < POI.getAllPost().size(); j++) {
                                Integer check = post.get(i);

                                if (check == POI.getAllPost().get(j).getId()) {
                                    //if(!post.contains(postsParts[i]))
                                    //{
                                    postist.add(POI.getAllPost().get(j));
                                    //}
                                }
                            }
                        }

                        mRecyclerView = (RecyclerView) findViewById(R.id.rvH);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        adapter = new MyRecyclerViewAdapterPosts(Profile.this, postist);
                        mRecyclerView.setAdapter(adapter);

                        System.out.println(posts);
                    }
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

    private void logoutUser() {

        switch (networkId) {
            case 10:
                session.setLogin(false);
                db.deleteUsers();
                break;

            case 3:
                session.setLogin(false);
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // [START_EXCLUDE]
                                //updateUI(false);
                                // [END_EXCLUDE]
                            }
                        });
                db.deleteUsers();
                break;
            case 4:
                session.setLogin(false);
                socialNetwork.logout();
                db.deleteUsers();
                break;

        }
        // Launching the login activity
        Intent intent = new Intent(Profile.this, MainActivity1.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (thread != null) {
            thread.interrupt();
        }

    }
}
