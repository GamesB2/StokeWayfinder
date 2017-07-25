package com.example.w028006g.regnlogin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.w028006g.regnlogin.MainActivity1;


/**
 * Created by a025178g on 08/06/2017.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity1.class);
        startActivity(intent);
        finish();
    }

}
