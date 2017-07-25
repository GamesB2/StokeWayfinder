package com.example.w028006g.regnlogin.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.w028006g.regnlogin.MyRecyclerViewAdapterList;
import com.example.w028006g.regnlogin.MyRecyclerViewAdapterListA;
import com.example.w028006g.regnlogin.MyRecyclerViewAdapterListL;
import com.example.w028006g.regnlogin.R;
import com.example.w028006g.regnlogin.helper.MarkerClasses.POI;

import java.util.ArrayList;

/**
 * Created by w028006g on 24/07/2017.
 */

public class ListLandmarks extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapterListL adapter;
    private ArrayList<POI> postist = new ArrayList<>();
    private String posts="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);

        postist.addAll(POI.getAllLndmk());


        mRecyclerView = (RecyclerView) findViewById(R.id.rvEL);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ListLandmarks.this));
        adapter = new MyRecyclerViewAdapterListL(ListLandmarks.this, postist);
        mRecyclerView.setAdapter(adapter);
    }
}
