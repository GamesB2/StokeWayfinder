package com.example.w028006g.regnlogin.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.w028006g.regnlogin.R;
import com.example.w028006g.regnlogin.helper.MarkerClasses.FilterManager;
import com.example.w028006g.regnlogin.helper.MarkerClasses.Tag;

public class NewFilterActivity extends AppCompatActivity
{
    private ListView tagLV;
    private ArrayAdapter<Tag> tagArrayAdapter;
    private CheckBox[] checkBoxes = new CheckBox[3];
    private boolean[] checkStatus = FilterManager.getTypeFilter();
    private int[] cbID =
            {
                    R.id.attractions,
                    R.id.events,
                    R.id.landmarks
            };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_filter);

        for(int i = 0; i < checkBoxes.length; i++)
        {
            checkBoxes[i] = (CheckBox)findViewById(cbID[i]);
            checkBoxes[i].setChecked(checkStatus[i]);
            checkBoxes[i].setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    CheckBox checkBox = (CheckBox)v;
                    for(int ii = 0; ii < checkBoxes.length; ii++)
                    {
                        if(checkBox.equals(checkBoxes[ii]))
                        {
                            if(checkBox.isChecked())
                            {
                                checkBoxes[ii].setChecked(false);
                                FilterManager.setFilterByType(-1);
                            }
                            else
                            {
                                checkBoxes[ii].setChecked(true);
                                FilterManager.setFilterByType(ii);
                            }
                        }
                        else
                        {
                            checkBoxes[ii].setChecked(false);
                        }
                    }
                }
            });
        }

        tagArrayAdapter = new ArrayAdapter<>(this,R.layout.listview_layout, FilterManager.getFilteredTags());
        tagLV = (ListView) findViewById(R.id.TagFilterList);
        tagLV.setAdapter(tagArrayAdapter);
        tagLV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });
    }

    private void setTypeFilter()
}
