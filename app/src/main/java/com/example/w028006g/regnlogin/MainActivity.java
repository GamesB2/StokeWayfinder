package com.example.w028006g.regnlogin;

import com.example.w028006g.regnlogin.activity.LoginActivity;
import com.example.w028006g.regnlogin.helper.DownloadImageTask;
import com.example.w028006g.regnlogin.helper.SQLiteHandler;
import com.example.w028006g.regnlogin.helper.SessionManager;
import com.google.firebase.iid.FirebaseInstanceId;


import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Exchanger;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.w028006g.regnlogin.R.id.imageView;
import static com.example.w028006g.regnlogin.R.id.img_userprofile;

public class MainActivity extends AppCompatActivity {

    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;
    private Button btnMaps;
    private ImageView imgUser;

    private SQLiteHandler db;
    private SessionManager session;

    String name;
    String email;
    String u_id;

    int numMessages = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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