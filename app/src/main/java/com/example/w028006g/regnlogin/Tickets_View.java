package com.example.w028006g.regnlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by User on 4/15/2017.
 */

public class Tickets_View extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(android.view.Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_view);

        Transition ts = new Explode();  //Slide(); //Explode();
        ts.setDuration(300);
        getWindow().setEnterTransition(ts);
        getWindow().setExitTransition(ts);

        TextView title;
        TextView event_time;
        TextView event_location;
        TextView event_organiser;
        TextView summary;

        title  = (TextView)findViewById(R.id.title);
        event_time  = (TextView)findViewById(R.id.title);
        event_location  = (TextView)findViewById(R.id.title);
        event_organiser  = (TextView)findViewById(R.id.title);
        summary  = (TextView)findViewById(R.id.title);

//        title.setText(titleinfo);
//        title.setText(ev_time);
//        title.setText(ev_location);
//        title.setText(ev_organiser);
//        title.setText(summaryText);


    }
}
