package com.example.w028006g.regnlogin.helper.MarkerClasses;

import android.location.Address;

import java.util.Locale;

/**
 * Created by a025178g on 15/06/2017.
 */

public abstract class POI
{
    protected int id;
    protected Address aAddressInfo = new Address(Locale.getDefault());
    protected String sDescription;
    protected double dPrice = 0;

    public boolean setID(String sID)
    {
        id = Integer.parseInt(sID);
        return true;
    }

    public int getId()
    {
        return id;
    }

    public Address getAddressInfo()
    {
        return aAddressInfo;
    }

    public String getDescription()
    {
        return sDescription;
    }

    public double getPrice()
    {
        return dPrice;
    }

    public boolean setWeb(String sWeb)
    {

            aAddressInfo.setUrl(sWeb);
            return true;

    }

    public boolean setPrice(String sPrice)
    {
            Double.parseDouble(sPrice);
            return true;

    }

    public boolean setDesc(String sDesc)
    {

            sDescription = sDesc;
            return true;
    }

    public boolean setAddressLine(String sALine)
    {
            aAddressInfo.setAddressLine(0,sALine);

            return true;
    }

    public boolean setPostCode(String sPC)
    {

            aAddressInfo.setPostalCode(sPC);
            return true;

    }

    public void setLat (String sLat)
    {

            aAddressInfo.setLatitude(Double.parseDouble(sLat));


    }

    public void setLong (String sLong)
    {

            aAddressInfo.setLongitude(Double.parseDouble(sLong));


    }

    public void setName (String sName)
    {

            aAddressInfo.setFeatureName(sName);


    }

    //Method used to extract the number from the address
    private static String extractNumber(final String str)
    {

        if(str == null || str.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        boolean found = false;
        for(char c : str.toCharArray())
        {
            if(Character.isDigit(c))
            {
                sb.append(c);
                found = true;
            } else if(found)
            {
                // If we already found a digit before and this char is not a digit, stop looping
                break;
            }
        }

        return sb.toString();
    }
}
