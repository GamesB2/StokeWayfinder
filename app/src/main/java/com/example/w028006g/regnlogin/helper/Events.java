package com.example.w028006g.regnlogin.helper;

/**
 * Created by w028006g on 21/06/2017.
 */

public class Events {
    String id="";
    String name="";
    String dateS="";
    String desc="";
    String cName="";
    String cNumber="";
    String web="";
    String price="";
    String time="";
    String dateE="";
    String cat="";
    String add="";
    String pcode="";
    String lat="";
    String lng="";

    public Events(String id, String name, String dateS, String desc, String cName, String cNumber, String web, String price, String time, String dateE, String cat, String add, String pcode, String lat, String lng) {
        this.id = id;
        this.name = name;
        this.dateS = dateS;
        this.desc = desc;
        this.cName = cName;
        this.cNumber = cNumber;
        this.web = web;
        this.price = price;
        this.time = time;
        this.dateE = dateE;
        this.cat = cat;
        this.add=add;
        this.pcode=pcode;
        this.lat=lat;
        this.lng=lng;
    }


    public Events(){

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

    public String getDateS() {
        return dateS;
    }

    public void setDateS(String dateS) {
        this.dateS = dateS;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcNumber() {
        return cNumber;
    }

    public void setcNumber(String cNumber) {
        this.cNumber = cNumber;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDateE() {
        return dateE;
    }

    public void setDateE(String dateE) {
        this.dateE = dateE;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }


}
