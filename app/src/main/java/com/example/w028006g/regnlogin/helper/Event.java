package com.example.w028006g.regnlogin.helper;


import java.util.Calendar;

/**
 * Created by a025178g on 15/06/2017.
 */

public class Event extends POI
{
    protected Calendar startCalendar;
    protected Calendar endCalendar;
    private String sConName;
    private String sConNum;
    private String sIcon;

    public Event(String sName,String sLat, String sLong)
    {
        super.setName(sName);
        super.setLat(sLat);
        super.setLong(sLong);
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
    }

    public void setStartDate(String date, String time)
    {
        String sYear = date.substring(0,4);
        String sMonth= date.substring(5,7);
        String sDay= date.substring(8);

        String sHour    = time.substring(0,2);
        String sMin     = time.substring(3,5);
        String sSec     = time.substring(6);

        int nYear = Integer.parseInt(sYear);
        int nMonth = Integer.parseInt(sMonth);
        int nDay = Integer.parseInt(sDay);

        int nHour = Integer.parseInt(sHour);
        int nMin = Integer.parseInt(sMin);
        int nSec = Integer.parseInt(sSec);

        startCalendar.set(nYear,nMonth,nDay,nHour,nMin,nSec);
    }

    public void setEndDate(String date)
    {
        String sYear = date.substring(0,4);
        String sMonth= date.substring(5,7);
        String sDay= date.substring(8);

        int nYear = Integer.parseInt(sYear);
        int nMonth = Integer.parseInt(sMonth);
        int nDay = Integer.parseInt(sDay);

        endCalendar.set(nYear,nMonth,nDay);
    }

    public void setIcon (String Icon)
    {
        sIcon = Icon;
    }

    public String getIcon()
    {
        return sIcon;
    }

    public void setConName(String name)
    {
        sConName = name;
    }

    public String getConName()
    {
        return sConName;
    }

    public void setConNum(String num)
    {
        sConNum = num;
    }

    public String getConNum()
    {
        return sConNum;
    }


}
