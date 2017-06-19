package com.example.w028006g.regnlogin.helper;

/**
 * Created by a025178g on 15/06/2017.
 */

public class Landmark extends POI
{
    public Landmark(String sName,String sLat, String sLong) throws Exception
    {
        super.setName(sName);
        super.setLat(sLat);
        super.setLong(sLong);
    }
}
