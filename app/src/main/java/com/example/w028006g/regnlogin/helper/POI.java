package com.example.w028006g.regnlogin.helper;

import android.location.Address;

/**
 * Created by a025178g on 15/06/2017.
 */

public abstract class POI
{
    Address aAdressInfo;
    String sDescription;
    double dPrice = 0;

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
            aAdressInfo.setUrl(sWeb);
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
            aAdressInfo.setAddressLine(nAdNum,sRemString);
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
            aAdressInfo.setPostalCode(sPC);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public void setLat (String sLat) throws Exception
    {
        try
        {
            aAdressInfo.setLatitude(Double.parseDouble(sLat));
        }
        catch (Exception e)
        {
            throw new Exception("Unable to set Latitude");
        }
    }

    public void setLong (String sLong) throws Exception
    {
        try
        {
            aAdressInfo.setLongitude(Double.parseDouble(sLong));
        }
        catch (Exception e)
        {
            throw new Exception("Unable to set Longitude");
        }
    }

    public void setName (String sName) throws Exception
    {
        try
        {
            aAdressInfo.setFeatureName(sName);
        }
        catch (Exception e)
        {
            throw new Exception("All points of interest Require a name that was unable to be set");
        }
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
