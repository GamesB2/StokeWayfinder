package com.example.w028006g.regnlogin.helper.MarkerClasses;

import java.util.ArrayList;

/**
 * Created by a025178g on 14/07/2017.
 */

public class Tag
{
    private String tagName;
    private static ArrayList<Tag> existingTags = new ArrayList<>();
    private ArrayList<POI> relevantPoints = new ArrayList<>();

    public Tag(String sTag, POI point)
    {
        tagName = sTag;
        relevantPoints.add(point);
        existingTags.add(this);
    }

    public static Tag getTag(String sTag)
    {
        for(int i = 0; i < existingTags.size(); i++)
        {
            if(existingTags.get(i).toString().equalsIgnoreCase(sTag))
            {
                return existingTags.get(i);
            }
        }
        return null;
    }

    public static boolean checkExisting(String sTag)
    {
        for(int i = 0; i < existingTags.size(); i++)
        {
            String temp = existingTags.get(i).tagName;

            if(temp.equalsIgnoreCase(sTag)) //An instance of the tag already exists
            {
                return true;//Return the existing tag to the POI
            }
        }
        return false;
    }

    public static ArrayList<Tag> getAllTags()
    {
        return existingTags;
    }

    //Returns all the tags associated with the passed list of points
    public static ArrayList<Tag> getRelevantTags(ArrayList<POI> points)
    {
        ArrayList<Tag> relevantTags = points.get(0).getTags();
        for(int i = 1; i < points.size(); i++)
        {
            ArrayList<Tag> tempTags =  points.get(i).getTags();
            for(int ii = 0; ii < tempTags.size(); ii++)
            {
                Tag tempTag = tempTags.get(ii);
                if(!relevantTags.contains(tempTag))
                {
                    relevantTags.add(tempTag);
                }
            }
        }
        return relevantTags;
    }
    public ArrayList<POI> getRelevant()
    {
        return relevantPoints;
    }

    @Override
    public String toString()
    {
        return tagName;
    }
}
