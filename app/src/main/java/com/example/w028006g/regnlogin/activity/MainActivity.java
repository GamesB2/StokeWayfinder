package com.example.w028006g.regnlogin.activity;
import com.example.w028006g.regnlogin.BottomNavigationViewHelper;

import com.example.w028006g.regnlogin.MainActivity1;
import com.example.w028006g.regnlogin.activity.LoginActivity;
import com.example.w028006g.regnlogin.app.AppController;
import com.example.w028006g.regnlogin.helper.DownloadImageTask;
import com.example.w028006g.regnlogin.helper.SQLiteHandler;
import com.example.w028006g.regnlogin.helper.SessionManager;
import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.listener.OnRequestSocialPersonCompleteListener;
import com.github.gorbin.asne.core.persons.SocialPerson;
import com.google.firebase.iid.FirebaseInstanceId;

import com.example.w028006g.regnlogin.ClickActionHelper;
import com.example.w028006g.regnlogin.GeolocationService;
import com.example.w028006g.regnlogin.Person;
import com.example.w028006g.regnlogin.R;
import com.example.w028006g.regnlogin.helper.DatabaseRetrievalNow;
import com.example.w028006g.regnlogin.helper.DownloadImageTask;
import com.example.w028006g.regnlogin.helper.SQLiteHandler;
import com.example.w028006g.regnlogin.helper.SessionManager;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;
    private Button btnMaps;
    private ImageView imgUser;
    private int networkId;
    private LinearLayout events;
    private LinearLayout land;
    private LinearLayout att;

    private SocialNetwork socialNetwork;
    private SQLiteHandler db;
    private SessionManager session;
    public static Person userDetails;

    String name;
    String email;
    String u_id;
    String tickets;

    private String personName;
    private String personGivenName;
    private String personFamilyName;
    private String personEmail;

    int numMessages = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend);


        Intent intent = new Intent(MainActivity.this, GeolocationService.class);
        startService(intent);
        Intent intent1 = new Intent(MainActivity.this, DatabaseRetrievalNow.class);
        startService(intent1);


        userDetails = new Person();

        txtName = (TextView) findViewById(R.id.uname);
        //txtEmail = (TextView) findViewById(R.id.uemail);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnMaps = (Button) findViewById(R.id.btnMaps);
        imgUser = (ImageView) findViewById(R.id.img_userprofile);
        events = (LinearLayout)findViewById(R.id.events);
        land = (LinearLayout)findViewById(R.id.landmarks);
        att = (LinearLayout)findViewById(R.id.attractions);
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());



        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        name = user.get("name");
        email = user.get("email");
        u_id = user.get("uid");
        tickets = user.get("tickets");
        userDetails.setName(AppController.personName);
        userDetails.setEmail(email);
        userDetails.setU_id(u_id);
        userDetails.setTickets(tickets);


        // Displaying the user details on the screen
//        txtName.setText(name);
//        txtEmail.setText(email);

        events.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("Test button 2");
                Intent intent = new Intent(getApplicationContext(),ListEvents.class);
                startActivity(intent);
            }
        });

        land.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("Test button 2");
                Intent intent1 = new Intent(getApplicationContext(),ListLandmarks.class);
                startActivity(intent1);
            }
        });
        att.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("Test button 2");
                Intent intent2 = new Intent(getApplicationContext(),ListAttractions.class);
                startActivity(intent2);
            }
        });



        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.w028006g.regnlogin",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


        // Logout button click event

//        btnLogout.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                logoutUser();
//                if (networkId == -1) {
//
//                } else {
//                    socialNetwork.logout();
//                }
//
//            }
//        });

//        btnMaps.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), MapsActivityNew.class);
//                startActivity(intent);
//            }
//        });

        checkIntent(getIntent());

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
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


        SharedPreferences prefs = AppController.getInstance().getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        networkId = prefs.getInt("SocialNet", -1);

        switch (networkId) {
            case 10:
                SharedPreferences emaillogin = AppController.getInstance().getSharedPreferences("EmailLogin", Context.MODE_PRIVATE);
                personEmail = emaillogin.getString("email", "WrongName");
                personName = emaillogin.getString("name", "WrongName");
                txtName.setText(personName);
                break;

            case 3:
                SharedPreferences login = AppController.getInstance().getSharedPreferences("GoogleLogin", Context.MODE_PRIVATE);
                personEmail = login.getString("email", "WrongName");
                personName = login.getString("name", "WrongName");
                txtName.setText(personName);
                break;

            case 4:
                socialNetwork = LoginActivity.mSocialNetworkManager.getSocialNetwork(networkId);
                socialNetwork.setOnRequestCurrentPersonCompleteListener(new OnRequestSocialPersonCompleteListener() {
                    @Override
                    public void onRequestSocialPersonSuccess(int socialNetworkId, SocialPerson socialPerson) {
                        txtName.setText(socialPerson.name);
                        MainActivity.userDetails.setName(socialPerson.name);
                        MainActivity.userDetails.setEmail(socialPerson.email);
                    }

                    @Override
                    public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
                        Toast.makeText(getApplicationContext(), "Unable To Load Profile", Toast.LENGTH_SHORT);
                    }
                });
                socialNetwork.requestCurrentPerson();
                break;

        }

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkIntent(intent);


        new DownloadImageTask((ImageView) findViewById(R.id.img_userprofile))
                .execute("https://concussive-shirt.000webhostapp.com/uploads/" + u_id + ".png");

    }

    public void checkIntent(Intent intent) {
        if (intent.hasExtra("click_action")) {
            ClickActionHelper.startActivity(intent.getStringExtra("click_action"), intent.getExtras(), this);
        }
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     */
    private void logoutUser() {
        if (networkId == -1) {
            socialNetwork.logout();
        } else {
            session.setLogin(false);
            db.deleteUsers();
        }


        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, MainActivity1.class);
        startActivity(intent);
        finish();
    }
}