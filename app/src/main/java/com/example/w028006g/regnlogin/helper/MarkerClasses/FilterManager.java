package com.example.w028006g.regnlogin.helper.MarkerClasses;

import android.location.Location;
import android.widget.CheckBox;

import com.example.w028006g.regnlogin.GeolocationService;
import com.example.w028006g.regnlogin.helper.DatabaseRetrieval;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class FilterManager
{
    private static Location userLocation = LatLngToLocation(GeolocationService.getLatLng());

    private static final ArrayList<POI> POINTSARRAY = POI.getPoints();
    private static final ArrayList<POI> FILTERABLE = getFilterablePoints();
    private static final ArrayList<Tag> ALLTAGS = Tag.getAllTags(null);
    private static final float RANGEFILTERCAP = POI.getFurthest(userLocation);

    private static final int ATTRACTION = 0;
    private static final int LANDMARK = 1;
    private static final int EVENT = 2;

    private static boolean[] typeFilter = new boolean[3];
    private static float rangeFilter = RANGEFILTERCAP;
    private static ArrayList<Tag> tagFilter = new ArrayList<>();

    private static ArrayList<POI> filteredPoints;
    private static ArrayList<Tag> availableTags = ALLTAGS;
    private static ArrayList<Tag> selectedTags = null;

    private static boolean typeFilterFlag = false;
    private static boolean tagFilterFlag = false;
    private static boolean rangeFilterFlag = false;

    public static ArrayList<POI> applyFilter()
    {
        filteredPoints = FILTERABLE;

        if(typeFilterFlag)
        {
            filteredPoints = filterByCat(filteredPoints);
        }

        if(rangeFilterFlag)
        {
            filteredPoints = filterByRange(filteredPoints);
        }

        if(tagFilterFlag)
        {
            filteredPoints = filterByTag(filteredPoints);
        }

        return filteredPoints;
    }

    private static ArrayList<POI> filterByCat(ArrayList<POI> unfiltered)
    {
        ArrayList<POI> filtered = new ArrayList<>();
        if(typeFilter[ATTRACTION])
        {
            for(int i = 0; i < unfiltered.size(); i++)
            {
                POI temp = unfiltered.get(i);
                if(temp instanceof Attraction)
                {
                    filtered.add(temp);
                }
            }
        } else if (typeFilter[EVENT])
        {
            for(int i = 0; i < unfiltered.size(); i++)
            {
                POI temp = unfiltered.get(i);
                if(temp instanceof Event)
                {
                    filtered.add(temp);
                }
            }
        } else if (typeFilter[LANDMARK])
        {
            for(int i = 0; i < unfiltered.size(); i++)
            {
                POI temp = unfiltered.get(i);
                if(temp instanceof Landmark)
                {
                    filtered.add(temp);
                }
            }
        } else
        {
            filtered = unfiltered;
        }
        return filtered;
    }

    private static ArrayList<POI> filterByRange(ArrayList<POI> unfiltered)
    {
        ArrayList<POI> filtered = new ArrayList<>();
        for(int i = 0; i < unfiltered.size(); i++)
        {
            float fDistance = unfiltered.get(i).getLocation().distanceTo(userLocation);
            if(fDistance <= rangeFilter)
            {
                filtered.add(unfiltered.get(i));
            }
        }
        return filtered;
    }

    private static ArrayList<POI> filterByTag(ArrayList<POI> unfiltered)
    {
        ArrayList<POI> filtered = new ArrayList<>();
        for(int i = 0; i < unfiltered.size(); i++)
        {
            POI temp = unfiltered.get(i);
            ArrayList<Tag> pointTags = temp.getTags();
            for(int ii = 0; ii < pointTags.size(); ii++)
            {
                if(pointTags.containsAll(tagFilter))
                {
                    filtered.add(temp);
                }
            }
        }
        return filtered;
    }

    private static ArrayList<POI> getFilterablePoints()
    {
        ArrayList<POI> templist = new ArrayList<>();
        for(int i = 0; i < POINTSARRAY.size(); i++)
        {
            POI temp = POINTSARRAY.get(i);
            if(temp instanceof Attraction|| temp instanceof Event || temp instanceof Landmark)
            {
                templist.add(temp);
            }
        }
        return templist;
    }

    public static Location LatLngToLocation(LatLng latLng)
    {
        Location location = new Location("");
        location.setLatitude(latLng.latitude);
        location.setLongitude(latLng.longitude);
        return location;
    }

    public static ArrayList<Tag> getAvailableTags(ArrayList<POI> points)
    {
        if (points == null)
        {
            points = filteredPoints;
        }

        return Tag.getAllTags(points);
    }
    public static boolean isTypeFilterFlag()
    {
        return typeFilterFlag;
    }

    public static boolean isRangeFilterFlag()
    {
        return rangeFilterFlag;
    }

    public static boolean isTagFilterFlag()
    {
        return tagFilterFlag;
    }

    public static boolean[] getTypeFilter()
    {
        return typeFilter;
    }

    public static void clearTypeFilter()
    {
        typeFilter = new boolean[3];
    }

    public static float getRangeFilterCap()
    {
        return RANGEFILTERCAP;
    }

    public static float getRangeFilter()
    {
        return rangeFilter;
    }

    public static void setRangeFilter(int num)
    {
        rangeFilter = num;
        setRangeFilterFlag(true);
    }

    public static void setRangeFilterFlag(boolean b)
    {
        rangeFilterFlag = b;
        if(!b)
        {
            rangeFilter = RANGEFILTERCAP;
        }
    }

    public static void adjustTypeFilter(int ii, boolean b)
    {
        typeFilter[ii] = b;
        for (boolean aTypeFilter : typeFilter)
        {
            if(aTypeFilter)
            {
                typeFilterFlag = true;
            }
        }
    }

    public static ArrayList<Tag> getSelectedTags()
    {
        return selectedTags;
    }

    public static void setSelectedTag(Tag tag)
    {
        selectedTags.add(tag);
        tagFilterFlag = true;
    }

    public static void removeTag(int index)
    {
        selectedTags.remove(index);
        if(selectedTags.isEmpty())
        {
            tagFilterFlag = false;
        }
    }
}

