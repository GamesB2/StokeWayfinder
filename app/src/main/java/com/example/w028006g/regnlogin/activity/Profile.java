package com.example.w028006g.regnlogin.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.w028006g.regnlogin.BottomNavigationViewHelper;
import com.example.w028006g.regnlogin.History;
import com.example.w028006g.regnlogin.My_Points;
import com.example.w028006g.regnlogin.R;
import com.example.w028006g.regnlogin.Tickets_My;
import com.example.w028006g.regnlogin.helper.DownloadImageTask;
import android.view.View;

public class Profile extends AppCompatActivity {

    private TextView txtName;
    private TextView txtEmail;
    private Button btnView;
    private Button btnHistory;
    private Button btnPoints;


    private Scene scene1, scene2;
    //transition to move between scenes
    private Transition transition;
    //flag to swap between scenes
    private boolean start;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(android.view.Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtUserEmail);
        btnView = (Button) findViewById(R.id.btnTickets);
        btnHistory = (Button) findViewById(R.id.btnHistory);
        btnPoints = (Button) findViewById(R.id.btnPoints);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_map:
                        Intent intent0 = new Intent(Profile.this, MapsActivityNew.class);
                        startActivity(intent0, ActivityOptions.makeSceneTransitionAnimation(Profile.this).toBundle());;

                        break;

                    case R.id.ic_Profile:

                        break;

                    case R.id.ic_Adventures:
                        Intent intent2 = new Intent(Profile.this, Adventures.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_Tickets:
                        Intent intent3 = new Intent(Profile.this, Tickets.class);
                        startActivity(intent3);
                        break;
                }
                return false;
            }
        });

        //Download user image
        new DownloadImageTask((ImageView) findViewById(R.id.profilePic))
                .execute("https://concussive-shirt.000webhostapp.com/uploads/" + StartScreen.userDetails.getU_id() + ".png" );
        // Displaying the user details on the screen
        txtName.setText(StartScreen.userDetails.getName());
        txtEmail.setText(StartScreen.userDetails.getEmail());

        btnView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(Profile.this, Tickets_My.class);
                startActivity(intent4);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(Profile.this, History.class);
                startActivity(intent4);
            }
        });

        btnPoints.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(Profile.this, My_Points.class);
                startActivity(intent5);
            }
        });

    }


    public void changeScene(View v){

        //check flag
        if(start) {
            TransitionManager.go(scene2, transition);
            start=false;
        }
        else {
            TransitionManager.go(scene1, transition);
            start=true;
        }
    }
}
