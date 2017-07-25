package com.example.w028006g.regnlogin.helper.MarkerClasses;

/**
 * Created by a025178g on 15/06/2017.
 */

public class Attraction extends IconManager
{
    public Attraction(String sName,String sLat, String sLong)
    {
        super.setName(sName);
        super.setLat(sLat);
        super.setLong(sLong);
        POI.storePoint(this);
    }

//    public void setIcon(String string)
//    {
//        super.setIcon("0");
//    }
}
