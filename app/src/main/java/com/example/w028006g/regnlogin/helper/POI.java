package com.example.w028006g.regnlogin.helper;

import android.location.Address;

/**
 * Created by a025178g on 15/06/2017.
 */

public abstract class POI
{
    Address aAddressInfo;
    String sDescription;
    double dPrice = 0;

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
        try
        {
            aAddressInfo.setUrl(sWeb);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public boolean setPrice(String sPrice)
    {
        try
        {
            Double.parseDouble(sPrice);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public boolean setDesc(String sDesc)
    {
        if (sDesc != null)
        {
            sDescription = sDesc;
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean setAddressLine(String sALine)
    {
        int nAdNum = 0;
        String sAdNum;
        String sRemString;

        if (Character.isDigit(sALine.charAt(0)))
        {
            sAdNum = (extractNumber(sALine));
            nAdNum = Integer.parseInt(sAdNum);
            sRemString = (sALine.substring(sAdNum.length()).trim());
            aAddressInfo.setAddressLine(nAdNum,sRemString);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean setPostCode(String sPC)
    {
        try
        {
            aAddressInfo.setPostalCode(sPC);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
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
