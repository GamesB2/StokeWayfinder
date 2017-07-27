package com.example.w028006g.regnlogin.helper.MarkerClasses;

import com.example.w028006g.regnlogin.R;

import java.util.ArrayList;

/**
 * Created by a025178g on 26/07/2017.
 */

public class MIcon
{
    private static ArrayList<Integer> iconId = initialiseIconId();
    private static ArrayList<String> iconNames = initialiseStrings();
    private ArrayList<POI> points = new ArrayList<>();
    private static ArrayList<MIcon> allIcons = new ArrayList<>();
    private int nImageId;
    private String sIconName;
    private int universalIndex;
    private static int nextAvailableIndex = 0;

    private MIcon(String name, int id)
    {
        nImageId = id;
        sIconName = name;
        universalIndex = assignIndex();
        allIcons.add(this);
    }

    public static void createIcons()
    {
        for(int i = 0; i < iconId.size(); i++)
        {
            MIcon temp = new MIcon(iconNames.get(i),iconId.get(i));
        }
    }

    private int assignIndex()
    {
        int temp = nextAvailableIndex;
        nextAvailableIndex++;
        return temp;
    }

    private boolean checkExisting(String name)
    {
        for (int i = 0; i < allIcons.size(); i++)
        {
            MIcon temp = allIcons.get(i);
            if(temp.toString().equalsIgnoreCase(name))
            {
                return true;
            }
        }
        return false;
    }

    public int getUniversalIndex()
    {
        return universalIndex;
    }

    public int getIconId()
    {
        return nImageId;
    }

    @Override
    public String toString()
    {
        return sIconName;
    }

    public static MIcon getByIndex(int i)
    {
        return allIcons.get(i);
    }

    private static ArrayList<String> initialiseStrings()
    {
        ArrayList<String> names = new ArrayList<>();

        names.add("Activity Attractions");
        names.add("Antique Shops");
        names.add("Arts and Crafts");
        names.add("Business");
        names.add("Tea Shops and Cafes");
        names.add("Boats, Planes, Trains and Automobiles");
        names.add("Countryside and Villages");
        names.add("Education");
        names.add("Exhibition Centres");
        names.add("Fast Food");
        names.add("Sports, Leisure and Fitness Centres");
        names.add("Food and Drink Shops");
        names.add("Games");
        names.add("Parks and Gardens");
        names.add("Health and Beauty");
        names.add("History and Heritage");
        names.add("Indoor Activities");
        names.add("Libraries");
        names.add("Fairs and Markets");
        names.add("Animal and Nature Attractions");
        names.add("Museums");
        names.add("Music");
        names.add("Nightlife");
        names.add("Outdoor Activities");
        names.add("Pubs, Winebars and Bistros");
        names.add("Religious Sites");
        names.add("General Shopping");
        names.add("Shopping Centres");
        names.add("Theatre");
        names.add("Theme Parks");
        names.add("Guides and Tours");
        names.add("Water Activities");
        names.add("Weddings");
        names.add("Totem");
        names.add("Landmark");
        names.add("User Pin");


//        names.add("Gift Shops");
//        names.add("Group Travel");
//        names.add("Groups and Associations");
//        names.add("Hobby and Craft Shops");
//        names.add("Information");
//        names.add("Information Centres");
//        names.add("Parties");
//        names.add("Post Offices and Banks");
//        names.add("Public Offices");
//        names.add("Restaurants");
//        names.add("Rooms for Hire and Conference Locations");
//        names.add("Services");
//        names.add("Towns and Cities");



        return names;
    }

    private static ArrayList<Integer> initialiseIconId()
    {
        ArrayList<Integer> temp = new ArrayList<>();
        int[] nArrIconID =
                {
                        R.drawable.activity,
                        R.drawable.antique,
                        R.drawable.arts,
                        R.drawable.business,
                        R.drawable.cafe,
                        R.drawable.car,
                        R.drawable.countryside,
                        R.drawable.education,
                        R.drawable.exibition,
                        R.drawable.fastfood,
                        R.drawable.fitness,
                        R.drawable.food,
                        R.drawable.games,
                        R.drawable.garden,
                        R.drawable.health,
                        R.drawable.history,
                        R.drawable.indoor,
                        R.drawable.library,
                        R.drawable.market,
                        R.drawable.nature,
                        R.drawable.museum,
                        R.drawable.music,
                        R.drawable.nightlife,
                        R.drawable.outdoor,
                        R.drawable.pub,
                        R.drawable.religous,
                        R.drawable.shop,
                        R.drawable.shoppingc,
                        R.drawable.theatre,
                        R.drawable.theme_park,
                        R.drawable.tour,
                        R.drawable.water,
                        R.drawable.wedding,
                        R.drawable.totem,
                        R.drawable.landmark,
                        R.drawable.pin

                };
        for (int id:nArrIconID)
        {
           temp.add(id);
        }
        return temp;
    }
}
