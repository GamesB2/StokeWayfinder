package com.example.w028006g.regnlogin.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.w028006g.regnlogin.R;

public class testActivity extends AppCompatActivity
{

    private TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent instigator = getIntent();
        Bundle extras = instigator.getExtras();
        int n = extras.getInt("locCode");

        test = (TextView)findViewById(R.id.testResult);

        test.setText(String.valueOf(n));
    }
}
