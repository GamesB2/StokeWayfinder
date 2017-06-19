package com.example.w028006g.regnlogin.helper;

/**
 * Created by a025178g on 15/06/2017.
 */

public class Attraction
{
    String name ="";
    String lat = "";
    String lng ="";

    public Attraction(String sName,String sLat, String sLong)
    {
        name = sName;
        lat = sLat;
        lng = sLong;
    }

    public String getName(){
        return name;
    }

    public String getLat() {
        return lat;
    }
    public String getLng(){
        return lng;
    }
}
