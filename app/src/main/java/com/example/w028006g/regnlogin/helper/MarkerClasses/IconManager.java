package com.example.w028006g.regnlogin.helper.MarkerClasses;

import com.example.w028006g.regnlogin.R;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/**
 * Created by a025178g on 11/07/2017.
 */

public class IconManager extends POI
{
    protected static int[] nArrIconID =
            {
            R.drawable.music,
            R.drawable.business,
            R.drawable.food_and_drink,
            R.drawable.community,
            R.drawable.art,
            R.drawable.film_and_media,
            R.drawable.sport,
            R.drawable.health_and_fitness,
            R.drawable.science,
            R.drawable.travel_and_outdoor,
            R.drawable.charity,
            R.drawable.spirituality,
            R.drawable.family_and_education,
            R.drawable.holiday,
            R.drawable.government,
            R.drawable.fashion,
            R.drawable.home_and_life,
            R.drawable.auto_boat_and_air,
            R.drawable.hobbies
            };

    private int nIcon = -1;

    public void setIcon(String code)
    {
        nIcon = Integer.parseInt(code);

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(nArrIconID[nIcon]);

        super.setIconBMP(icon);
    }

    public int getIcon()
    {
        return nIcon;
    }


}
