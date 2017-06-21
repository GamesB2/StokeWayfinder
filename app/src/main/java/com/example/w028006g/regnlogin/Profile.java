package com.example.w028006g.regnlogin;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.transition.AutoTransition;
import android.transition.Explode;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.w028006g.regnlogin.helper.DownloadImageTask;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.w028006g.regnlogin.helper.DownloadImageTask;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

public class Profile extends AppCompatActivity {

    private TextView txtName;
    private TextView txtEmail;


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
                .execute("https://concussive-shirt.000webhostapp.com/uploads/" + MainActivity.userDetails.getU_id() + ".png" );
        // Displaying the user details on the screen
        txtName.setText(MainActivity.userDetails.getName());
        txtEmail.setText(MainActivity.userDetails.getEmail());
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
