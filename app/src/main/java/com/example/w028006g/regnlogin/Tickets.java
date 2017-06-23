package com.example.w028006g.regnlogin;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Contacts;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w028006g.regnlogin.helper.DatabaseRetrieval;
import com.example.w028006g.regnlogin.helper.MyRecyclerViewAdapter;
import com.example.w028006g.regnlogin.helper.Ticket;

import java.util.ArrayList;

import static android.transition.Fade.IN;

/**
 * Created by User on 4/15/2017.
 */

public class Tickets extends AppCompatActivity {

    private CardView btnCard1;
    public ArrayList<Ticket> t = DatabaseRetrieval.ticketsAl;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tickets);

//        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
//        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
//        Menu menu = bottomNavigationView.getMenu();
//        MenuItem menuItem = menu.getItem(3);
//        menuItem.setChecked(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(Tickets.this, t);
        mRecyclerView.setAdapter(adapter);



/*        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_map:
                        Intent intent0 = new Intent(Tickets.this, MapsActivityNew.class);
                        startActivity(intent0);

                        break;

                    case R.id.ic_Profile:
                        Intent intent1 = new Intent(Tickets.this, Profile.class);
                        startActivity(intent1);
                        startActivity(intent1,
                                ActivityOptions.makeSceneTransitionAnimation(Tickets.this).toBundle());
                        break;

                    case R.id.ic_Adventures:
                        Intent intent2 = new Intent(Tickets.this, Adventures.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_Tickets:

                        break;
                }
                return false;

            }
        });*/



    }
}
