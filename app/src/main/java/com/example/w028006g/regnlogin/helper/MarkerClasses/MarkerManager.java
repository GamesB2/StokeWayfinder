package com.example.w028006g.regnlogin.helper.MarkerClasses;

import android.graphics.Bitmap;
import android.location.Address;
import android.location.Location;
import android.os.Parcelable;

import com.example.w028006g.regnlogin.GeolocationService;
import com.example.w028006g.regnlogin.R;
import com.example.w028006g.regnlogin.helper.MarkerClasses.Attraction;
import com.example.w028006g.regnlogin.helper.MarkerClasses.Event;
import com.example.w028006g.regnlogin.helper.MarkerClasses.Landmark;
import com.example.w028006g.regnlogin.helper.MarkerClasses.POI;
import com.example.w028006g.regnlogin.helper.MarkerClasses.Post;
import com.example.w028006g.regnlogin.helper.MarkerClasses.UserPin;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
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

    //Group Categories
    final static int ATTRACTIONS=IconManager.nArrIconID.length+1;
    final static int LANDMARKS=ATTRACTIONS+1;
    final static int EVENTS=ATTRACTIONS+2;
    final static int TOTEM=ATTRACTIONS+3;
    final static int USERPINS = ATTRACTIONS+4;

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

        if (!filter[USERPINS])
        {
            popMapPins();
        }

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
            if (item instanceof Attraction)
            {
                Attraction att = (Attraction)item;
                Address add = item.getAddressInfo();
                LatLng dest = new LatLng(add.getLatitude(), add.getLongitude());

                Location destLoc = new Location("");
                destLoc.setLatitude(dest.latitude);
                destLoc.setLongitude(dest.longitude);

                float distance = location.distanceTo(destLoc);

                if ((!rangeFilter || distance <= maxRange)&&(!filter[att.getIcon()]))
                {
                    temp = mMap.addMarker(new MarkerOptions()
                            .position(dest)
                            .title(add.getFeatureName())
                            .snippet(item.getDescription())
                            .icon(BitmapDescriptorFactory.fromResource(IconManager.nArrIconID[att.getIcon()])));

                    markerArrayList.add(temp);
                }
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
            if (item instanceof Landmark)
            {
                Landmark lnd = (Landmark) item;
                Address add = item.getAddressInfo();
                LatLng dest = new LatLng(add.getLatitude(), add.getLongitude());

                Location destLoc = new Location("");
                destLoc.setLatitude(dest.latitude);
                destLoc.setLongitude(dest.longitude);

                float distance = location.distanceTo(destLoc);

                if ((!rangeFilter || distance <= maxRange))
                {
                    temp = mMap.addMarker(new MarkerOptions()
                            .position(dest)
                            .title(add.getFeatureName())
                            .snippet(item.getDescription())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.landmark)));

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
            if (item instanceof Event)
            {
                Event event = (Event)item;
                Address add = item.getAddressInfo();
                LatLng dest = new LatLng(add.getLatitude(), add.getLongitude());

                Location destLoc = new Location("");
                destLoc.setLatitude(dest.latitude);
                destLoc.setLongitude(dest.longitude);

                float distance = location.distanceTo(destLoc);

                if ((!rangeFilter || distance <= maxRange)&&(!filter[event.getIcon()]))
                {
                    Bitmap bitmap = changeIcon(event.getIcon());
                    temp = mMap.addMarker(new MarkerOptions()
                            .position(dest)
                            .title(add.getFeatureName())
                            .snippet(item.getDescription())
                            .icon(BitmapDescriptorFactory.fromResource(IconManager.nArrIconID[event.getIcon()])));

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
            if (item instanceof Post)
            {
                Post post = (Post) item;
                Address add = item.getAddressInfo();
                LatLng dest = new LatLng(add.getLatitude(), add.getLongitude());

                Location destLoc = new Location("");
                destLoc.setLatitude(dest.latitude);
                destLoc.setLongitude(dest.longitude);

                float distance = location.distanceTo(destLoc);

                if ((!rangeFilter || distance <= maxRange))
                {
                    temp = mMap.addMarker(new MarkerOptions()
                            .position(dest)
                            .title(add.getFeatureName())
                            .snippet(item.getDescription())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.totem)));

                    markerArrayList.add(temp);
                }
            }
        }
    }

    public static void popMapPins()
    {
        Marker temp = null;
        for (int i = 0; i < poiArrayList.size(); i++)
        {
            POI item = poiArrayList.get(i);
            if (item instanceof UserPin)
            {
                UserPin userPin = (UserPin) item;
                Address add = item.getAddressInfo();
                LatLng dest = new LatLng(add.getLatitude(), add.getLongitude());

                Location destLoc = new Location("");
                destLoc.setLatitude(dest.latitude);
                destLoc.setLongitude(dest.longitude);

                //TODO: implement if we enable range filter on user pins
                //float distance = location.distanceTo(destLoc);
                //if((!rangeFilter || distance <= maxRange))
                {
                    temp = mMap.addMarker(new MarkerOptions()
                            .position(dest)
                            .title(add.getFeatureName())
                            .snippet(item.getDescription())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.userpin)));

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

    public static Bitmap changeIcon(int bmp)
    {
        Bitmap icon = null;
        return icon;
    }
}

