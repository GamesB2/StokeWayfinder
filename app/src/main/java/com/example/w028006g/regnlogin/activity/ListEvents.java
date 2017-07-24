package com.example.w028006g.regnlogin.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.w028006g.regnlogin.MyRecyclerViewAdapterList;
import com.example.w028006g.regnlogin.R;
import com.example.w028006g.regnlogin.helper.DatabaseRetrieval;
import com.example.w028006g.regnlogin.helper.MarkerClasses.Event;
import com.example.w028006g.regnlogin.helper.MarkerClasses.POI;
import com.example.w028006g.regnlogin.helper.MarkerClasses.Post;
import com.example.w028006g.regnlogin.helper.MyRecyclerViewAdapterPosts;

import java.util.ArrayList;

/**
 * Created by w028006g on 24/07/2017.
 */

public class ListEvents  extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapterList adapter;
    private ArrayList<POI> postist = new ArrayList<>();
    private String posts="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);

        postist.addAll(POI.getAllEvent());


        mRecyclerView = (RecyclerView) findViewById(R.id.rvEL);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ListEvents.this));
        adapter = new MyRecyclerViewAdapterList(ListEvents.this, postist);
        mRecyclerView.setAdapter(adapter);
    }
}
