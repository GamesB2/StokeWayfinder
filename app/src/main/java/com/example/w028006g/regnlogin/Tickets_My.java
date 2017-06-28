package com.example.w028006g.regnlogin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.w028006g.regnlogin.activity.MainActivity;
import com.example.w028006g.regnlogin.helper.DatabaseRetrieval;
import com.example.w028006g.regnlogin.helper.MyRecyclerViewAdapter;
import com.example.w028006g.regnlogin.helper.Ticket;

import java.util.ArrayList;

/**
 * Created by User on 4/15/2017.
 */

public class Tickets_My extends AppCompatActivity {

    private CardView btnCard1;
    public ArrayList<Ticket> t = DatabaseRetrieval.ticketsAl;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private Person p;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tickets);

//        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
//        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
//        Menu menu = bottomNavigationView.getMenu();
//        MenuItem menuItem = menu.getItem(3);
//        menuItem.setChecked(true);
        p = MainActivity.userDetails;


        String tickets = p.getTickets();
        String[] ticketParts = tickets.split(",");
        ArrayList<Ticket> ticketList = new ArrayList<>();
        for(int i=0;i<ticketParts.length;i++)
        {
            for (int j=0;j<DatabaseRetrieval.ticketsAl.size();j++)
            {
                Integer check = Integer.parseInt(ticketParts[i]);

                if(check == DatabaseRetrieval.ticketsAl.get(j).getId())
                {
                    ticketList.add(DatabaseRetrieval.ticketsAl.get(j));
                }
            }
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(Tickets_My.this, ticketList);
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
