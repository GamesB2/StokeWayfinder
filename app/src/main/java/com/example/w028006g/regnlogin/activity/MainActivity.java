package com.example.w028006g.regnlogin.activity;

import com.example.w028006g.regnlogin.ClickActionHelper;
import com.example.w028006g.regnlogin.GeolocationService;
import com.example.w028006g.regnlogin.Person;
import com.example.w028006g.regnlogin.R;
import com.example.w028006g.regnlogin.helper.DownloadImageTask;
import com.example.w028006g.regnlogin.helper.SQLiteHandler;
import com.example.w028006g.regnlogin.helper.SessionManager;


import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txtName;
    private TextView txtEmail;
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

    int numMessages = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, GeolocationService.class);

        startService(intent);

        userDetails = new Person();

        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnMaps = (Button) findViewById(R.id.btnMaps);
        imgUser = (ImageView) findViewById(R.id.img_userprofile);
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
        userDetails.setName(name);
        userDetails.setEmail(email);
        userDetails.setU_id(u_id);
        userDetails.setTickets(tickets);


        // Displaying the user details on the screen
        txtName.setText(name);
        txtEmail.setText(email);

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        btnMaps.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MapsActivityNew.class);
                startActivity(intent);
            }
        });

        checkIntent(getIntent());


    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkIntent(intent);


        new DownloadImageTask((ImageView) findViewById(R.id.img_userprofile))
                .execute("https://concussive-shirt.000webhostapp.com/uploads/" + u_id + ".png" );
    }

    public void checkIntent(Intent intent) {
        if (intent.hasExtra("click_action")) {
            ClickActionHelper.startActivity(intent.getStringExtra("click_action"), intent.getExtras(), this);
        }
    }
    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}