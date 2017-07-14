package com.example.w028006g.regnlogin.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.w028006g.regnlogin.R;
import com.example.w028006g.regnlogin.helper.MarkerClasses.Tag;

import java.util.ArrayList;

public class NewFilterActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_filter);

        ArrayAdapter<Tag> tagArrayAdapter = new ArrayAdapter<>(this,R.layout.listview_layout,Tag.getAllTags());
        ListView tagLV = (ListView) findViewById(R.id.TagFilterList);
        tagLV.setAdapter(tagArrayAdapter);
    }
}
