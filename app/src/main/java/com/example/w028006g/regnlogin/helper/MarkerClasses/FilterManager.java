package com.example.w028006g.regnlogin.helper.MarkerClasses;

import com.example.w028006g.regnlogin.helper.DatabaseRetrieval;

import java.util.ArrayList;

public class FilterManager
{
    private static final ArrayList<POI> POINTSARRAY = DatabaseRetrieval.getPoints();
    private static ArrayList<POI> FILTERABLE = getFilterablePoints();
    private static ArrayList<POI> filteredPoints = POINTSARRAY;
    private static ArrayList<Tag> filteredTags = null;
    private static ArrayList<Tag> ALLTAGS = Tag.getAllTags();
    private static ArrayList<Tag> availableTags = ALLTAGS;
    private static boolean[] typeFilter = new boolean[3];
    private static boolean typeFilterFlag = false;
    private static final int ATTRACTION = 0;
    private static final int LANDMARK = 1;
    private static final int EVENT = 2;

    public static void setFilteredTags(Tag tag)
    {
        if (filteredTags == null)
        {
            filteredTags.add(tag);
        }

    }

    public static ArrayList<Tag> getFilteredTags()
    {
        return filteredTags;
    }

    public static void setFilterByType(int index)
    {
        typeFilter = new boolean[3];
        typeFilterFlag = false;
        if(index >=0 && index <= 2)
        {
            typeFilter[index] = true;
            typeFilterFlag = true;
        }
    }

    public static boolean[] getTypeFilter()
    {
        return typeFilter;
    }

    private static void setFilteredPoints()
    {
        filteredPoints.clear();
        if(typeFilterFlag)
        {
            filterOutTypes();
        }
        else
        {
            filteredPoints = FILTERABLE;
        }

        availableTags = Tag.getRelevantTags(filteredPoints);

        if(!filteredTags.isEmpty()) //No filtered tags have been selected so show all results
        {
            refineByTags();
        }
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

    private static void filterOutTypes()
    {
        if(typeFilter[ATTRACTION])
        {
            for(int i = 0; i < FILTERABLE.size(); i++)
            {
                POI temp = filteredPoints.get(i);
                if(temp instanceof Attraction)
                {
                    filteredPoints.add(temp);
                }
            }
        } else if (typeFilter[EVENT])
        {
            for(int i = 0; i < FILTERABLE.size(); i++)
            {
                POI temp = filteredPoints.get(i);
                if (temp instanceof Event)
                {
                    filteredPoints.add(temp);
                }
            }
        } else if (typeFilter[LANDMARK])
        {
            for(int i = 0; i < FILTERABLE.size(); i++)
            {
                POI temp = filteredPoints.get(i);
                if (temp instanceof Landmark)
                {
                    filteredPoints.add(temp);
                }
            }
        }
    }

    private static void refineByTags()
    {

    }

}

