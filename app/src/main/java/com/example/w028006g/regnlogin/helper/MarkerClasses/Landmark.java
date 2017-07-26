package com.example.w028006g.regnlogin.helper.MarkerClasses;

import com.example.w028006g.regnlogin.helper.MarkerClasses.POI;

/**
 * Created by a025178g on 15/06/2017.
 */

public class Landmark extends POI
{
    public Landmark(String sName,String sLat, String sLong)
    {
        super.setName(sName);
        super.setLat(sLat);
        super.setLong(sLong);
        POI.storePoint(this);
        super.setIcon("34");
    }
}
