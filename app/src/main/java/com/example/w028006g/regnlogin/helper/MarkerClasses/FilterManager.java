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
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;

/**
 * Created by a025178g on 21/06/2017.
 */

public class FilterManager
{
    public static ArrayList<POI> poiArrayList = new ArrayList<>();
    private static ArrayList<POI> FilteredPoints;
    public static GoogleMap mMap;
    private static boolean[] filter;
    private static int maxRange = 50000;
    private static boolean rangeFilter = false;//Should be the same as MAX constant in FilterActivity
    private static Location location;
    private static LatLng userLatLng;

    //Group Categories
    final static int ATTRACTIONS= IconManager.nArrIconID.length;
    final static int LANDMARKS= ATTRACTIONS+1;
    final static int EVENTS= ATTRACTIONS+2;
    final static int TOTEM= ATTRACTIONS+3;
    final static int USERPINS = ATTRACTIONS+4;

    public FilterManager(GoogleMap map, ArrayList<POI> arrayList)
    {

        poiArrayList = arrayList;
        mMap = map;
        filter = new boolean[50];

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
    public static void popFilter()
    {
        location = new Location("");
        location.setLatitude(userLatLng.latitude);
        location.setLongitude(userLatLng.longitude);

        FilteredPoints = new ArrayList<>();

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
        ArrayList<Attraction> attractions = POI.getAllAtt();
        for (int i = 0; i < attractions.size(); i++)
        {
            Attraction att = attractions.get(i);
            Address add = att.getAddressInfo();
            LatLng dest = new LatLng(add.getLatitude(), add.getLongitude());

            Location destLoc = new Location("");
            destLoc.setLatitude(dest.latitude);
            destLoc.setLongitude(dest.longitude);

            float distance = location.distanceTo(destLoc);

            if (!rangeFilter || distance <= maxRange)
            {
                FilteredPoints.add(att);
            }
        }
    }


    //Populates the maps with Landmarks markers from the POI Array
    public static void popMapLnd()
    {
        ArrayList<Landmark> landmarks = POI.getAllLndmk();
        for (int i = 0; i < landmarks.size(); i++)
        {
            Landmark landmark = landmarks.get(i);
            Address add = landmark.getAddressInfo();
            LatLng dest = new LatLng(add.getLatitude(), add.getLongitude());

            Location destLoc = new Location("");
            destLoc.setLatitude(dest.latitude);
            destLoc.setLongitude(dest.longitude);

            float distance = location.distanceTo(destLoc);

            if (!rangeFilter || distance <= maxRange)
            {
                FilteredPoints.add(landmark);
            }
        }
    }

    //Populates the maps with Events markers from the POI Array
    public static void popMapEvents()
    {
        ArrayList<Event> events = POI.getAllEvent();
        for (int i = 0; i < events.size(); i++)
        {
            Event event = events.get(i);
            Address add = event.getAddressInfo();
            LatLng dest = new LatLng(add.getLatitude(), add.getLongitude());

            Location destLoc = new Location("");
            destLoc.setLatitude(dest.latitude);
            destLoc.setLongitude(dest.longitude);

            float distance = location.distanceTo(destLoc);

            if (!rangeFilter || distance <= maxRange)
            {
                FilteredPoints.add(event);
            }
        }
    }

    public static void popMapTotem()
    {
        ArrayList<Post> posts = POI.getAllPost();
        for (int i = 0; i < posts.size(); i++)
        {
            Post post = posts.get(i);
            Address add = post.getAddressInfo();
            LatLng dest = new LatLng(add.getLatitude(), add.getLongitude());

            Location destLoc = new Location("");
            destLoc.setLatitude(dest.latitude);
            destLoc.setLongitude(dest.longitude);

            float distance = location.distanceTo(destLoc);

            if (!rangeFilter || distance <= maxRange)
            {
                FilteredPoints.add(post);
            }
        }
    }

    public static void popMapPins()
    {
        ArrayList<UserPin> userPins = POI.getAllUserPins();
        for (int i = 0; i < userPins.size(); i++)
        {
            UserPin userPin = userPins.get(i);
            Address add = userPin.getAddressInfo();
            LatLng dest = new LatLng(add.getLatitude(), add.getLongitude());

            Location destLoc = new Location("");
            destLoc.setLatitude(dest.latitude);
            destLoc.setLongitude(dest.longitude);

            float distance = location.distanceTo(destLoc);

            if (!rangeFilter || distance <= maxRange)
            {
                FilteredPoints.add(userPin);
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

    public static ArrayList<POI> getFilteredPoints()
    {
        popFilter();
        return FilteredPoints;
    }

    public static Bitmap changeIcon(int bmp)
    {
        Bitmap icon = null;
        return icon;
    }

}

