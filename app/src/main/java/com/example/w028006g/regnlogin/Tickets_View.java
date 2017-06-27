package com.example.w028006g.regnlogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Transition;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w028006g.regnlogin.helper.DatabaseRetrieval;
import com.example.w028006g.regnlogin.helper.Ticket;

import java.util.ArrayList;

/**
 * Created by User on 4/15/2017.
 */

public class Tickets_View extends AppCompatActivity {

    private ArrayList<Ticket> feedItemList = DatabaseRetrieval.ticketsAl;
    private Context mContext;
    private Button btnBuy;
    private Ticket t;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(android.view.Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_view);

        Intent mIntent = getIntent();
        final int intValue = mIntent.getIntExtra("id", 0);

        t = feedItemList.get(intValue);

        Transition ts = new Explode();  //Slide(); //Explode();
        ts.setDuration(300);
        getWindow().setEnterTransition(ts);
        getWindow().setExitTransition(ts);

        TextView title;
        TextView event_time;
        TextView event_location;
        TextView event_organiser;
        TextView summary;
        ImageView img;
        final String method="register";

        title  = (TextView)findViewById(R.id.title);
        event_time  = (TextView)findViewById(R.id.title);
        event_location  = (TextView)findViewById(R.id.title);
        event_organiser  = (TextView)findViewById(R.id.title);
        summary  = (TextView)findViewById(R.id.title);
        img  = (ImageView)findViewById(R.id.ticketView_id);
        btnBuy  = (Button)findViewById(R.id.btnBuy);

        title.setText(t.getName());
        event_time.setText(t.geteDate());
        summary.setText(t.getDesc());
        event_organiser.setText(t.getOrgan());
        event_location.setText(t.getPriceS());

        btnBuy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                BackgroundTask backgroundTask=new BackgroundTask(Tickets_View.this);
                backgroundTask.execute(method,MainActivity.userDetails.getEmail(), String.valueOf(t.getId()));
                Toast.makeText(getApplicationContext(),
                        "You Sucessfully Purchased a Ticket for: " + t.getName(),
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });







    }





}
