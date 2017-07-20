package com.example.w028006g.regnlogin.helper.MarkerClasses;

import android.location.Address;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.nearby.messages.Distance;
import com.google.maps.android.clustering.ClusterItem;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by a025178g on 15/06/2017.
 */

public abstract class POI implements ClusterItem
{
    protected int id;
    private static ArrayList<POI> allPOI = new ArrayList<>();
    protected Address aAddressInfo = new Address(Locale.getDefault());
    protected String sDescription;
    protected double dPrice = 0;
    private ArrayList<Tag> tags = new ArrayList<>();

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
    private String extractNumber(final String str)
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

    @Override
    public LatLng getPosition()
    {
        LatLng latLng = new LatLng(aAddressInfo.getLatitude(),aAddressInfo.getLongitude());
        return latLng;
    }

    @Override
    public String getTitle()
    {
        String title = aAddressInfo.getFeatureName();
        return title;
    }

    @Override
    public String getSnippet()
    {
        return sDescription;
    }

    public void setTags(String sTags)
    {
        {
            String def = sTags;
            if(!def.isEmpty())
            {
                if(def.contains("/"))
                {
                    String[] temp = def.split("/");
                    for(int i = 0; i < temp.length; i++)
                    {
                        addTag(temp[i]);
                    }
                }
                else
                {
                    addTag(def);
                }
            }
        }
    }

    private void addTag(String sTag)
    {
        Tag temp;
        if(Tag.checkExisting(sTag))
        {
            temp = Tag.getTag(sTag);
        }
        else
        {
            temp = new Tag(sTag,this);
        }
        tags.add(temp);
    }

    public ArrayList<Tag> getTags()
    {
        return tags;
    }

    public Location getLocation()
    {
        Location location = new Location("");
        location.setLatitude(aAddressInfo.getLatitude());
        location.setLongitude(aAddressInfo.getLongitude());
        return location;
    }

    public static void addPoint(POI point)
    {
        allPOI.add(point);
    }

    public static ArrayList<POI> getPoints()
    {
        return allPOI;
    }
    public static float getFurthest(Location anchor)
    {
        if(!allPOI.isEmpty())
        {
            POI furthest = allPOI.get(0);
            Location location = furthest.getLocation();
            float distance = location.distanceTo(anchor);
            for (int i = 0; i < allPOI.size(); i++) {
                POI compared = allPOI.get(i);
                Location compLoc = compared.getLocation();
                float compDist = compLoc.distanceTo(anchor);
                if (compDist >= distance) {
                    distance = compDist;
                }
            }
            return distance;
        }
        return 0;
    }
}
