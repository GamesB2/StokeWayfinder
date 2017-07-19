package com.example.w028006g.regnlogin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.w028006g.regnlogin.BottomNavigationViewHelper;
import com.example.w028006g.regnlogin.ClickActionHelper;
import com.example.w028006g.regnlogin.GeolocationService;
import com.example.w028006g.regnlogin.Person;
import com.example.w028006g.regnlogin.R;
import com.example.w028006g.regnlogin.helper.DatabaseRetrievalNow;
import com.example.w028006g.regnlogin.helper.DownloadImageTask;
import com.example.w028006g.regnlogin.helper.SQLiteHandler;
import com.example.w028006g.regnlogin.helper.SessionManager;

import java.util.HashMap;

/**
 * Created by w028006g on 04/07/2017.
 */

public class StartScreen extends AppCompatActivity {

    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;
    private ImageView btnMap;
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
        setContentView(R.layout.recommend);

        Intent intent = new Intent(StartScreen.this, GeolocationService.class);
        startService(intent);
        Intent intent1 = new Intent(StartScreen.this, DatabaseRetrievalNow.class);
        startService(intent1);

        userDetails = new Person();

        txtName = (TextView) findViewById(R.id.uname);
        //txtEmail = (TextView) findViewById(R.id.uemail);
        //btnLogout = (Button) findViewById(R.id.btnLogout);
        btnMap = (ImageView) findViewById(R.id.btnMap);
        //imgUser = (ImageView) findViewById(R.id.img_userprofile);
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
/*        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
       });*/

        //Menu bar at the bottom
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

        btnMap.setOnClickListener(new View.OnClickListener() {

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
        //Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        //startActivity(intent);
        //finish();
    }
}
