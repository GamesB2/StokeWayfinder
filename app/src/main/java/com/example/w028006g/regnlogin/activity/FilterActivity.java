package com.example.w028006g.regnlogin.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;


import com.example.w028006g.regnlogin.MarkerManager;

import com.example.w028006g.regnlogin.helper.MarkerClasses.MarkerManager;

import com.example.w028006g.regnlogin.R;

import java.util.ArrayList;

import static com.example.w028006g.regnlogin.R.id.seekBar;
import static com.example.w028006g.regnlogin.R.id.textView;
import static com.example.w028006g.regnlogin.R.id.textView2;

public class FilterActivity extends AppCompatActivity
{
    public ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    public Button btnClearFilter;
    public Button btnClearMap;
    public TextView tv1;
    public TextView tv2;
    public Switch swRadius;
    public SeekBar slider;
    public EditText radius;
    private int MAX = 50000;
    public int nRadius;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_filter);

        final int[] boxID = {
                R.id.music,
                R.id.business,
                R.id.food_and_drink,
                R.id.community,
                R.id.arts,
                R.id.film_and_media,
                R.id.sports,
                R.id.health_and_fitness,
                R.id.science_and_tech,
                R.id.travel_and_outdoor,
                R.id.charity,
                R.id.spirituality,
                R.id.family_and_education,
                R.id.holiday,
                R.id.government,
                R.id.fashion,
                R.id.home_and_lifestyle,
                R.id.auto_boat_and_air,
                R.id.hobbies,
                R.id.attractions,
                R.id.landmarks,
                R.id.events,
                R.id.totems,
                R.id.userpins};


        boolean[] filter = MarkerManager.getFilter();
        btnClearFilter = (Button)findViewById(R.id.clearFilter);
        btnClearMap = (Button)findViewById(R.id.clearMap);
        swRadius = (Switch)findViewById(R.id.switch3);
        radius = (EditText)findViewById(R.id.radiusID);
        slider = (SeekBar)findViewById(seekBar);
        tv1 = (TextView)findViewById(textView);
        tv2 = (TextView)findViewById(textView2);
        nRadius = MarkerManager.getMaxRange();
        if(!MarkerManager.getRangeFilter())
        {
            swRadius.setChecked(true);

            slider.setProgress(MAX);
            slider.setEnabled(false);

            radius.setText(String.valueOf(MAX));
            radius.setEnabled(false);

            tv1.setAlpha((float)0.5);
            tv2.setAlpha((float)0.5);
        }
        else
        {
            swRadius.setChecked(false);

            slider.setProgress(nRadius);
            slider.setEnabled(true);

            radius.setText(String.valueOf(nRadius));
            radius.setEnabled(true);

            tv1.setAlpha(1);
            tv2.setAlpha(1);
        }

        for (int i = 0; i < boxID.length; i++)
        {
            CheckBox temp = (CheckBox)findViewById(boxID[i]);
            if(filter[i])
            {
                temp.setChecked(false);
            }
            checkBoxes.add(i,temp);
        }

        btnClearFilter.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                for (int i = 0; i < boxID.length; i++)
                {
                    checkBoxes.get(i).setChecked(true);
                }
            }
        });

        btnClearMap.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                for (int i = 0; i < checkBoxes.size(); i++)
                {
                    checkBoxes.get(i).setChecked(false);
                }
            }
        });

        slider.setMax(MAX);
        slider.setProgress(nRadius);

        swRadius.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(swRadius.isChecked())
                {
                    slider.setProgress(MAX);
                    slider.setEnabled(false);
                    MarkerManager.setRangeFilter(false);

                    radius.setText(String.valueOf(MAX));
                    radius.setEnabled(false);

                    tv1.setAlpha((float)0.5);
                    tv2.setAlpha((float)0.5);
                }
                else if(!swRadius.isChecked())
                {
                    MarkerManager.setRangeFilter(true);
                    slider.setEnabled(true);

                    radius.setText(String.valueOf(MAX));
                    radius.setEnabled(true);

                    tv1.setAlpha(1);
                    tv2.setAlpha(1);
                }
            }
        });

        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                radius.setText(String.valueOf(progress));
                nRadius = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        radius.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String input = s.toString();
                int nInput;
                char[] chars = input.toCharArray();
                boolean bIntFlag = false;
                if (chars.length == 0|| chars.length > 4)
                {
                    bIntFlag = true;
                }
                for(int i = 0; i<chars.length; i++)
                {
                    if(!Character.isDigit(chars[i]))
                    {
                        bIntFlag = true;
                    }
                }
                if(!bIntFlag)
                {
                    nInput = Integer.parseInt(input);
                    if(nInput >= 0 && nInput <= MAX)
                    {
                        slider.setProgress(nInput);

                    }
                }
                radius.setSelection(chars.length);
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    @Override
    public void onBackPressed()
    {
        for (int i = 0; i < checkBoxes.size(); i++)
        {
            if(checkBoxes.get(i).isChecked())
            {
                MarkerManager.filterIn(i);
            }
            else
            {
                MarkerManager.filterOut(i);
            }
        }
        MarkerManager.setMaxRange(nRadius);
        this.finish();
    }
}
