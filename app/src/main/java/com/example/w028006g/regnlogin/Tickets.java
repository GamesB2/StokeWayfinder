package com.example.w028006g.regnlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by User on 4/15/2017.
 */

public class Tickets extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tickets);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
        });
    }
}
