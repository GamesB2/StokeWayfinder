package com.example.w028006g.regnlogin.helper;

/**
 * Created by w028006g on 27/06/2017.
 */

public class Post {

    String id="";
    String name="";
    String website="";
    String lat="";
    String lng="";
    String txt="";
    String video="";
    String summary="";
    String qr="";

    public Post(String id, String name, String website, String lat, String lng, String txt, String video, String summary, String qr) {
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


    public void Post() {

    }


}
