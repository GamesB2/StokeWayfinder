package com.example.w028006g.regnlogin.helper;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by w028006g on 27/06/2017.
 */

public class Post extends POI
{
    private String video="";
    private String summary="";
    private String qr="";
    private boolean bScanned = false;
    private Date datFirst = null;
    private Date datLatest = null;
    private int nScanCount;
    private SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");

    public Post(String id, String lat, String lng)
    {
        super.setID(id);
        super.setLat(lat);
        super.setLong(lng);
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public void setScanned()
    {
        Calendar calendar = Calendar.getInstance();
        if(bScanned)
        {
            nScanCount++;
            datLatest = calendar.getTime();
        }
        else
        {
            nScanCount = 1;
            datFirst = calendar.getTime();
            datLatest= datFirst;
            bScanned = true;
        }
    }

    public String getFirstScanTime()
    {
        String n="";
        return n; //time.format(datFirst);
    }

    public String getLatestScanTime()
    {
        String n="";
        return n;//time.format(datLatest);
    }

    public int getScanCount()
    {
        return nScanCount;
    }
}
