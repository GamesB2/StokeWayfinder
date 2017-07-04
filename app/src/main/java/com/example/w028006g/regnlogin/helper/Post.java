package com.example.w028006g.regnlogin.helper;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by w028006g on 27/06/2017.
 */

public class Post
{
    private String id="";
    private String name="";
    private String website="";
    private String lat="";
    private String lng="";
    private String txt="";
    private String video="";
    private String summary="";
    private String qr="";
    private boolean bScanned = false;
    private Date datFirst = null;
    private Date datLatest = null;
    private int nScanCount;
    private SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");

    public Post(String id, String name, String lat, String lng)
    {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public Post(String id, String name, String website, String lat, String lng, String txt, String video, String summary, String qr)
    {
        this.id = id;
        this.name = name;
        this.website = website;
        this.lat = lat;
        this.lng = lng;
        this.txt = txt;
        this.video = video;
        this.summary = summary;
        this.qr = qr;
    }

    public String getId() {
        return id;
    }

    public Integer getIdI() {
        return Integer.parseInt(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
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
            bScanned = true;
        }
    }

    public String getFirstScanTime()
    {
        return time.format(datFirst);
    }

    public String getLatestScanTime()
    {
        return time.format(datLatest);
    }

    public int getScanCount()
    {
        return nScanCount;
    }
}
