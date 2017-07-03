package com.example.w028006g.regnlogin.helper;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by a025178g on 30/06/2017.
 */

public class ScannedPost extends Post
{

    private Calendar calendar = Calendar.getInstance();
    private Date datFirstScan;

    public ScannedPost(Post p)
    {
        super(p.getId(),p.getName(),p.getLat(),p.getLng());
        setFirstScan();
    }

    public ScannedPost(String id, String name, String lat, String lng)
    {
        super(id, name, lat, lng);
    }

    public void setFirstScan()
    {
        datFirstScan = calendar.getTime();
    }

}
