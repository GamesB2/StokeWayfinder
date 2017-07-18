package com.example.w028006g.regnlogin;

import android.location.Address;
import android.location.Location;
import android.widget.Toast;

import com.example.w028006g.regnlogin.activity.FilterActivity;
import com.example.w028006g.regnlogin.helper.Attraction;
import com.example.w028006g.regnlogin.helper.Event;
import com.example.w028006g.regnlogin.helper.Landmark;
import com.example.w028006g.regnlogin.helper.POI;
import com.example.w028006g.regnlogin.helper.Post;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by a025178g on 21/06/2017.
 */

public class MarkerManager
{
    public static ArrayList<POI> poiArrayList = new ArrayList<>();
    public static ArrayList<Marker> markerArrayList = new ArrayList<>();
    public static GoogleMap mMap;
    private static boolean[] filter;
    private static int maxRange = 50000;
    private static boolean rangeFilter = false;//Should be the same as MAX constant in FilterActivity
    private static Location location;
    private static LatLng userLatLng;

    final static int MUSIC= 0;
    final static int BUSINESS= 1;
    final static int FOOD_AND_DRINK=2;
    final static int COMMUNITY=3;
    final static int ARTS=4;
    final static int FILM_AND_MEDIA=5;
    final static int SPORTS=6;
    final static int HEALTH_AND_FITNESS=7;
    final static int SCIENCE_AND_TECH=8;
    final static int TRAVEL_AND_OUTDOOR=9;
    final static int CHARITY=10;
    final static int SPIRITUALITY=11;
    final static int FAMILY_AND_EDUCATION=12;
    final static int HOLIDAY=13;
    final static int GOVERNMENT=14;
    final static int FASHION=15;
    final static int HOME_AND_LIFESTYLE=16;
    final static int AUTO_BOAT_AND_AIR=17;
    final static int HOBBIES=18;
    final static int ATTRACTIONS=19;
    final static int LANDMARKS=20;
    final static int EVENTS=21;
    final static int TOTEM=22;

    public MarkerManager(GoogleMap map, ArrayList<POI> arrayList)
    {
        poiArrayList = arrayList;
        mMap = map;
        filter = new boolean[25];

        userLatLng = GeolocationService.getLatLng();
    }

    public static boolean[] getFilter()
    {
        return filter;
    }

    public static void filterOut(int CONSTANT)
    {
        filter[CONSTANT] = true;
    }

    public static void filterIn(int CONSTANT)
    {
        filter[CONSTANT] = false;
    }

    //Populates the maps with ALL markers and icons from the POI Array
    public static void popMap()
    {
        location = new Location("");
        location.setLatitude(userLatLng.latitude);
        location.setLongitude(userLatLng.longitude);

        if(!filter[ATTRACTIONS])
        {
            popMapAtt();
        }

        if(!filter[LANDMARKS])
        {
            popMapLnd();
        }

        if(!filter[EVENTS])
        {
            popMapEvents();
        }

        if(!filter[TOTEM])
        {
            popMapTotem();
        }
    }
    //Populates the maps with Attractions markers from the POI Array
    public static void popMapAtt()
    {
        Marker temp = null;
        for (int i = 0; i < poiArrayList.size(); i++)
        {
            POI item = poiArrayList.get(i);
            if(item instanceof Attraction)
            {
                Address add = item.getAddressInfo();
                LatLng dest = new LatLng(add.getLatitude(), add.getLongitude());


                Location destLoc = new Location("");
                destLoc.setLatitude(dest.latitude);
                destLoc.setLongitude(dest.longitude);

                float distance = location.distanceTo(destLoc);

                if (distance <= maxRange && rangeFilter)
                {
                    switch (((Attraction) item).getIcon())
                    {
                        case MUSIC:
                            if (!filter[MUSIC])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.music)));
                            }
                            break;
                        case BUSINESS:
                            if (!filter[BUSINESS])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.business)));
                            }
                            break;
                        case FOOD_AND_DRINK:
                            if (!filter[FOOD_AND_DRINK])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.health)));
                            }
                            break;
                        case COMMUNITY:
                            if (!filter[COMMUNITY])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                            }
                            break;
                        case ARTS:
                            if (!filter[ARTS])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.art)));
                            }
                            break;
                        case FILM_AND_MEDIA:
                            if (!filter[FILM_AND_MEDIA])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.film_and_media)));
                            }
                            break;
                        case SPORTS:
                            if (!filter[SPORTS])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.sport)));
                            }
                            break;
                        case HEALTH_AND_FITNESS:
                            if (!filter[HEALTH_AND_FITNESS])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.health_and_fitness)));
                            }
                            break;
                        case SCIENCE_AND_TECH:
                            if (!filter[SCIENCE_AND_TECH])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.science)));
                            }
                            break;
                        case TRAVEL_AND_OUTDOOR:
                            if (!filter[TRAVEL_AND_OUTDOOR])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.travel_and_outdoor)));
                            }
                            break;
                        case CHARITY:
                            if (!filter[CHARITY])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.charity)));
                            }
                            break;
                        case SPIRITUALITY:
                            if (!filter[SPIRITUALITY])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                            }
                            break;
                        case FAMILY_AND_EDUCATION:
                            if (!filter[FAMILY_AND_EDUCATION])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.health)));
                            }
                            break;
                        case HOLIDAY:
                            if (!filter[HOLIDAY])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                            }
                            break;
                        case GOVERNMENT:
                            if (!filter[GOVERNMENT])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.government)));
                            }
                            break;
                        case FASHION:
                            if (!filter[FASHION])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.fashion)));
                            }
                            break;
                        case HOME_AND_LIFESTYLE:
                            if (!filter[HOME_AND_LIFESTYLE])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.home_and_life)));
                            }
                            break;
                        case AUTO_BOAT_AND_AIR:
                            if (!filter[AUTO_BOAT_AND_AIR])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                            }
                            break;
                        case HOBBIES:
                            if (!filter[HOBBIES])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                            }
                            break;
                        default:
                            temp = mMap.addMarker(new MarkerOptions()
                                    .position(dest)
                                    .title(add.getFeatureName())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));


                            break;
                    }
                }else if (!rangeFilter)
                {
                    switch (((Attraction) item).getIcon())
                    {
                        case MUSIC:
                            if (!filter[MUSIC])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.music)));
                            }
                            break;
                        case BUSINESS:
                            if (!filter[BUSINESS])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.business)));
                            }
                            break;
                        case FOOD_AND_DRINK:
                            if (!filter[FOOD_AND_DRINK])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.health)));
                            }
                            break;
                        case COMMUNITY:
                            if (!filter[COMMUNITY])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                            }
                            break;
                        case ARTS:
                            if (!filter[ARTS])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.art)));
                            }
                            break;
                        case FILM_AND_MEDIA:
                            if (!filter[FILM_AND_MEDIA])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.film_and_media)));
                            }
                            break;
                        case SPORTS:
                            if (!filter[SPORTS])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.sport)));
                            }
                            break;
                        case HEALTH_AND_FITNESS:
                            if (!filter[HEALTH_AND_FITNESS])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.health_and_fitness)));
                            }
                            break;
                        case SCIENCE_AND_TECH:
                            if (!filter[SCIENCE_AND_TECH])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.science)));
                            }
                            break;
                        case TRAVEL_AND_OUTDOOR:
                            if (!filter[TRAVEL_AND_OUTDOOR])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.travel_and_outdoor)));
                            }
                            break;
                        case CHARITY:
                            if (!filter[CHARITY])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.charity)));
                            }
                            break;
                        case SPIRITUALITY:
                            if (!filter[SPIRITUALITY])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                            }
                            break;
                        case FAMILY_AND_EDUCATION:
                            if (!filter[FAMILY_AND_EDUCATION])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.health)));
                            }
                            break;
                        case HOLIDAY:
                            if (!filter[HOLIDAY])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                            }
                            break;
                        case GOVERNMENT:
                            if (!filter[GOVERNMENT])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.government)));
                            }
                            break;
                        case FASHION:
                            if (!filter[FASHION])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.fashion)));
                            }
                            break;
                        case HOME_AND_LIFESTYLE:
                            if (!filter[HOME_AND_LIFESTYLE])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.home_and_life)));
                            }
                            break;
                        case AUTO_BOAT_AND_AIR:
                            if (!filter[AUTO_BOAT_AND_AIR])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                            }
                            break;
                        case HOBBIES:
                            if (!filter[HOBBIES])
                            {
                                temp = mMap.addMarker(new MarkerOptions()
                                        .position(dest)
                                        .title(add.getFeatureName())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                            }
                            break;
                        default:
                            temp = mMap.addMarker(new MarkerOptions()
                                    .position(dest)
                                    .title(add.getFeatureName())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            break;
                    }
                }
                markerArrayList.add(temp);
            }
        }
    }

    //Populates the maps with Landmarks markers from the POI Array
    public static void popMapLnd()
    {
        Marker temp = null;
        for (int i = 0; i < poiArrayList.size(); i++)
        {
            POI item = poiArrayList.get(i);
            if(item instanceof Landmark)
            {
                Address add = item.getAddressInfo();
                LatLng dest = new LatLng(add.getLatitude(), add.getLongitude());


                Location destLoc = new Location("");
                destLoc.setLatitude(dest.latitude);
                destLoc.setLongitude(dest.longitude);

                float distance = location.distanceTo(destLoc);

                if (distance <= maxRange && rangeFilter)
                {
                    temp = mMap.addMarker(new MarkerOptions()
                            .position(dest)
                            .title(add.getFeatureName())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                    markerArrayList.add(temp);
                }
                else if (!rangeFilter)
                {
                    temp = mMap.addMarker(new MarkerOptions()
                            .position(dest)
                            .title(add.getFeatureName())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                    markerArrayList.add(temp);
                }
            }
        }
    }

    //Populates the maps with Events markers from the POI Array
    public static void popMapEvents()
    {
        Marker temp = null;
        for (int i = 0; i < poiArrayList.size(); i++)
        {
            POI item = poiArrayList.get(i);
            if(item instanceof Event)
            {
                Address add = item.getAddressInfo();
                LatLng dest = new LatLng(add.getLatitude(), add.getLongitude());

                Location destLoc = new Location("");
                destLoc.setLatitude(dest.latitude);
                destLoc.setLongitude(dest.longitude);

                float distance = location.distanceTo(destLoc);

                if (distance <= maxRange && rangeFilter)
                {
                    temp = mMap.addMarker(new MarkerOptions()
                            .position(dest)
                            .title(add.getFeatureName())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    markerArrayList.add(temp);
                }
                else if (!rangeFilter)
                {
                    temp = mMap.addMarker(new MarkerOptions()
                            .position(dest)
                            .title(add.getFeatureName())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    markerArrayList.add(temp);
                }
            }
        }
    }

    public static void popMapTotem()
    {
        Marker temp = null;
        for (int i = 0; i < poiArrayList.size(); i++)
        {
            POI item = poiArrayList.get(i);
            if(item instanceof Post)
            {
                Address add = item.getAddressInfo();
                LatLng dest = new LatLng(add.getLatitude(), add.getLongitude());

                Location destLoc = new Location("");
                destLoc.setLatitude(dest.latitude);
                destLoc.setLongitude(dest.longitude);

                float distance = location.distanceTo(destLoc);

                if (distance <= maxRange && rangeFilter)
                {
                    temp = mMap.addMarker(new MarkerOptions()
                            .position(dest)
                            .title(add.getFeatureName())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                    markerArrayList.add(temp);
                }
                else if (!rangeFilter)
                {
                    temp = mMap.addMarker(new MarkerOptions()
                            .position(dest)
                            .title(add.getFeatureName())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                    markerArrayList.add(temp);
                }
            }
        }
    }

    public static void setMaxRange(int nRange)
    {
        maxRange = nRange;
    }

    public static int getMaxRange()
    {
        return maxRange;
    }

    public static void setRangeFilter(boolean filter)
    {
        rangeFilter = filter;
    }

    public static boolean getRangeFilter()
    {
        return rangeFilter;
    }
}

