package com.example.w028006g.regnlogin.helper;

import java.text.DecimalFormat;

/**
 * Created by chriswood on 22/06/2017.
 */

public class Ticket {
    private  int id =0;
    private  String name ="";
    private  String sDate="";
    private  String eDate ="";
    private  String loc = "";
    private  String desc="";
    private  String organ="";
    private  String type="";
    private  double price=0.00;
    private  String thumbnail = "";

    public Ticket(){

    }
    
    public Ticket(int id, String name, String sDate, String loc, String desc, String organ, String type, double price, String img){
        this.id = id;
        this.name = name;
        this.sDate = sDate;
        this.loc = loc;
        this.desc = desc;
        this.organ = organ;
        this.type = type;
        this.price = price;
        thumbnail = img;
    }

    public  int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public  String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    public  String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public  String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public  String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public  String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public  double getPrice() {
        return price;
    }

    public  String getPriceS() {
        String p ="";
        if (price < 1) {
            p = "Free!";
        }else
        {

            DecimalFormat df = new DecimalFormat("#.00");
            p = "£" + df.format(price);

        }
        return p;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public  String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getIDs(){
        return String.valueOf(id);
    }



}
