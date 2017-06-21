package com.example.w028006g.regnlogin;

import android.location.Address;

import com.example.w028006g.regnlogin.helper.Attraction;
import com.example.w028006g.regnlogin.helper.Event;
import com.example.w028006g.regnlogin.helper.Landmark;
import com.example.w028006g.regnlogin.helper.POI;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by a025178g on 21/06/2017.
 */

public class MarkerManager
{

    public static ArrayList<POI> poiArrayList = new ArrayList<>();
    public static GoogleMap mMap;

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

    public MarkerManager(GoogleMap map, ArrayList<POI> arrayList)
    {
        poiArrayList = arrayList;
        mMap = map;
    }
    //Populates the map with ALL markers and icons from the POI Array
    public static void popMap()
    {
        popMapAtt();
        popMapLnd();
        popMapEvents();
    }
    //Populates the map with Attractions markers from the POI Array
    public static void popMapAtt()
    {
        for (int i = 0; i < poiArrayList.size(); i++)
        {
            POI item = poiArrayList.get(i);
            if(item instanceof Attraction)
            {
                Address add = item.getAddressInfo();
                LatLng dest = new LatLng(add.getLatitude(), add.getLongitude());
                switch (((Attraction) item).getIcon())
                {
                    case MUSIC:
                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.music)));
                        break;
                    case BUSINESS:
                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.business)));
                        break;
                    case FOOD_AND_DRINK:
                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.health)));
                        break;
                    case COMMUNITY:

                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        break;

                    case ARTS:

                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.art)));
                        break;

                    case FILM_AND_MEDIA:
                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.film_and_media)));
                        break;
                    case SPORTS:
                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.sport)));
                        break;
                    case HEALTH_AND_FITNESS:
                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.health_and_fitness)));
                        break;
                    case SCIENCE_AND_TECH:
                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.science)));
                        break;
                    case TRAVEL_AND_OUTDOOR:
                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.travel_and_outdoor)));
                        break;
                    case CHARITY:
                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.charity)));
                        break;
                    case SPIRITUALITY:
                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                        break;
                    case FAMILY_AND_EDUCATION:
                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.health)));
                        break;
                    case HOLIDAY:
                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                        break;
                    case GOVERNMENT:
                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.government)));
                        break;
                    case FASHION:
                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.fashion)));
                        break;
                    case HOME_AND_LIFESTYLE:
                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.home_and_life)));
                        break;
                    case AUTO_BOAT_AND_AIR:
                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                        break;
                    case HOBBIES:
                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                        break;
                    default:
                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                        break;
                }
            }
        }
    }
    //Populates the map with Landmarks markers from the POI Array
    public static void popMapLnd()
    {
        for (int i = 0; i < poiArrayList.size(); i++)
        {
            POI item = poiArrayList.get(i);
            if(item instanceof Landmark)
            {
                Address add = item.getAddressInfo();
                LatLng dest = new LatLng(add.getLatitude(), add.getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .position(dest)
                        .title(add.getFeatureName())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
            }
        }
    }
    //Populates the map with Events markers from the POI Array
    public static void popMapEvents()
    {
        for (int i = 0; i < poiArrayList.size(); i++)
        {
            POI item = poiArrayList.get(i);
            if(item instanceof Event)
            {
                Address add = item.getAddressInfo();
                LatLng dest = new LatLng(add.getLatitude(), add.getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .position(dest)
                        .title(add.getFeatureName())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
        }
    }

}
