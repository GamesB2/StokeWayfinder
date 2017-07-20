package com.example.w028006g.regnlogin.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.w028006g.regnlogin.R;
import com.example.w028006g.regnlogin.helper.MarkerClasses.FilterManager;
import com.example.w028006g.regnlogin.helper.MarkerClasses.Tag;

import java.util.ArrayList;

public class NewFilterActivity extends AppCompatActivity
{
    private ListView tagLV;
    private ListView selTagLV;
    private ArrayAdapter<Tag> tagArrayAdapter;
    private ArrayAdapter<Tag> selTagArrayAdapter;
    private CheckBox[] typeCheckBoxes = new CheckBox[3];
    private boolean[] checkStatus = FilterManager.getTypeFilter();
    private int[] typeCBID =
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

        restoreSettings();

        printTags();
    }

    private void restoreSettings()
    {
        restoreTypeFilter();
        restoreRangeFilter();
        restoreTagFilter();
    }

    private void restoreTagFilter()
    {
        final ArrayList<Tag> availableTags = FilterManager.getAvailableTags(null);
        ArrayList<Tag> selectedTags = FilterManager.getSelectedTags();

        if(FilterManager.isTagFilterFlag())
        {
            for(int i = 0; i < selectedTags.size(); i++)
            {
                availableTags.add(i,selectedTags.get(i));
            }
        }
        tagArrayAdapter = new ArrayAdapter<>(this,R.layout.listview_layout,availableTags);
        tagLV = (ListView) findViewById(R.id.TagFilterList);
        tagLV.setAdapter(tagArrayAdapter);

        if(FilterManager.isTagFilterFlag())
        {
            for(int i = 0; i < selectedTags.size(); i++)
            {
                CheckBox checkBox = (CheckBox)tagArrayAdapter.getView(i,null,tagLV);
                checkBox.setChecked(true);
            }
        }
        tagLV.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Tag selected = availableTags.get(position);
                FilterManager.setSelectedTag(selected);
                updateActivity();
            }
        });
    }

    private void restoreRangeFilter()
    {
//        int MAX = Math.round(FilterManager.getRangeFilterCap());
//        int nRadius = Math.round(FilterManager.getRangeFilter());
//
//        final Switch swRadius = (Switch)findViewById(R.id.switch3);
//        final EditText radius = (EditText)findViewById(R.id.radiusID);
//        final SeekBar slider = (SeekBar)findViewById(R.id.seekBar);
//        final TextView tv1 = (TextView)findViewById(R.id.textView);
//        final TextView tv2 = (TextView)findViewById(R.id.textView2);
//
//        if(!FilterManager.isRangeFilterFlag())
//        {
//            swRadius.setChecked(true);
//
//            slider.setProgress(MAX);
//            slider.setEnabled(false);
//
//            radius.setText(String.valueOf(MAX));
//            radius.setEnabled(false);
//
//            tv1.setAlpha((float)0.5);
//            tv2.setAlpha((float)0.5);
//        }
//        else
//        {
//            swRadius.setChecked(false);
//
//            slider.setProgress(nRadius);
//            slider.setEnabled(true);
//
//            radius.setText(String.valueOf(nRadius));
//            radius.setEnabled(true);
//
//            tv1.setAlpha(1);
//            tv2.setAlpha(1);
//        }
//
//        swRadius.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                if(swRadius.isChecked())
//                {
//                    slider.setProgress(MAX);
//                    slider.setEnabled(false);
//                    FilterManager.setRangeFilter(false);
//
//                    radius.setText(String.valueOf(MAX));
//                    radius.setEnabled(false);
//
//                    tv1.setAlpha((float)0.5);
//                    tv2.setAlpha((float)0.5);
//                }
//                else if(!swRadius.isChecked())
//                {
//                    FilterManager.setRangeFilter(true);
//                    slider.setEnabled(true);
//
//                    radius.setText(String.valueOf(MAX));
//                    radius.setEnabled(true);
//
//                    tv1.setAlpha(1);
//                    tv2.setAlpha(1);
//                }
//            }
//        });
//
//        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
//        {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
//            {
//                radius.setText(String.valueOf(progress));
//                nRadius = progress;
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar)
//            {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar)
//            {
//
//            }
//        });
//
//        radius.addTextChangedListener(new TextWatcher()
//        {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after)
//            {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count)
//            {
//                String input = s.toString();
//                int nInput;
//                char[] chars = input.toCharArray();
//                boolean bIntFlag = false;
//                if (chars.length == 0|| chars.length > 4)
//                {
//                    bIntFlag = true;
//                }
//                for(int i = 0; i<chars.length; i++)
//                {
//                    if(!Character.isDigit(chars[i]))
//                    {
//                        bIntFlag = true;
//                    }
//                }
//                if(!bIntFlag)
//                {
//                    nInput = Integer.parseInt(input);
//                    if(nInput >= 0 && nInput <= MAX)
//                    {
//                        slider.setProgress(nInput);
//
//                    }
//                }
//                radius.setSelection(chars.length);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s)
//            {
//
//            }
//        });
    }

    private void restoreTypeFilter()
    {
        checkStatus = FilterManager.getTypeFilter();
        for(int i = 0; i < typeCheckBoxes.length; i++)
        {
            typeCheckBoxes[i] = (CheckBox)findViewById(typeCBID[i]);
            typeCheckBoxes[i].setChecked(checkStatus[i]);
            typeCheckBoxes[i].setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int checkLoc = -1;
                    CheckBox checkBox = (CheckBox)v;
                    for (int ii = 0; ii < typeCheckBoxes.length; ii++)
                    {
                        if(checkBox.equals(typeCheckBoxes[ii]))
                        {
                            checkLoc = ii;
                        }

                    }
                    updateActivity();
                }
            });
        }
    }

    private void updateActivity()
    {
        FilterManager.applyFilter();
        restoreSettings();
    }

    private void printTags()
    {
        ArrayList<Tag> tags = FilterManager.getAvailableTags(null);
        for(int i = 0; i < tags.size(); i++)
        {
            System.out.println("Tag" + i + " " + tags.get(i).toString());
        }
    }
}
