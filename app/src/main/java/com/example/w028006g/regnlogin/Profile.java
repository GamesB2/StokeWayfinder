package com.example.w028006g.regnlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

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
                        startActivity(intent0);
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
    }
}
