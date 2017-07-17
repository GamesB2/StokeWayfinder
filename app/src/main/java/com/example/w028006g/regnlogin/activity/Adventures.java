package com.example.w028006g.regnlogin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.w028006g.regnlogin.BottomNavigationViewHelper;
import com.example.w028006g.regnlogin.R;

/**
 * Created by User on 4/15/2017.
 */

public class Adventures extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adventures);

        TextView title = (TextView) findViewById(R.id.activityTitle2);
        title.setText("Adventures");

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_map:
                        Intent intent0 = new Intent(Adventures.this, MapsActivityNew.class);
                        startActivity(intent0);
                        break;

                    case R.id.ic_Profile:
                        Intent intent1 = new Intent(Adventures.this, Profile.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_Rec:

                        break;

                    case R.id.ic_qr:
                        Intent intent3 = new Intent(Adventures.this, Tickets.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_shop:
                        Intent intent4 = new Intent(Adventures.this, Tickets.class);
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });
    }

}
