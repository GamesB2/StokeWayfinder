package com.example.w028006g.regnlogin.helper;

/**
 * Created by w028006g on 21/06/2017.
 */

public class Events {
    String name="";
    String date="";
    String desc="";
    String add="";

    public Events(){

    }

    public Events(String name, String date){
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

}
