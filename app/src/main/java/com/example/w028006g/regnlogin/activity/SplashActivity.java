package com.example.w028006g.regnlogin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.w028006g.regnlogin.MainActivity1;
import com.example.w028006g.regnlogin.MapsActivityNew;
import com.example.w028006g.regnlogin.Person;
import com.example.w028006g.regnlogin.R;
import com.example.w028006g.regnlogin.activity.LoginActivity;
import com.example.w028006g.regnlogin.helper.SQLiteHandler;
import com.example.w028006g.regnlogin.helper.SessionManager;

import java.util.HashMap;

import static com.example.w028006g.regnlogin.MainActivity.userDetails;
import static com.example.w028006g.regnlogin.R.id.btnLogout;
import static com.example.w028006g.regnlogin.R.id.btnMaps;
import static com.example.w028006g.regnlogin.R.id.email;
import static com.example.w028006g.regnlogin.R.id.name;
import static com.example.w028006g.regnlogin.R.id.txtName;

/**
 * Created by a025178g on 08/06/2017.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                    Intent intent = new Intent(getApplicationContext(),MainActivity1.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
//---------------------------------------------------

    }
}
