package com.example.w028006g.regnlogin.helper.MarkerClasses;

import android.graphics.Bitmap;
import android.location.Address;
import android.location.Location;
import android.os.Parcelable;
import android.widget.CheckBox;

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
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by a025178g on 21/06/2017.
 */

public class FilterManager
{
    private static ArrayList<POI> poiArrayList = new ArrayList<>();
    private static ArrayList<POI> FilteredPOI;
    private static ArrayList<String> tagsArrayList = new ArrayList<>();
    private static GoogleMap mMap;
    private static boolean[] filter;
    private static int maxRange = 50000;
    private static boolean rangeFilter = false;//Should be the same as MAX constant in FilterActivity
    private static Location location;
    private static LatLng userLatLng;

    //Type Filter: Attractions, Landmarks, Events, etc.
    private static HashMap<CheckBox, Boolean> TypeFilter;
    //Categorical Filter: Music, Business, Family, etc.
    private static boolean[] catFilter;

    public FilterManager(GoogleMap map, ArrayList<POI> arrayList)
    {
        poiArrayList = arrayList;
        fillTags();
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

        FilteredPOI = new ArrayList<>();

        if (!filter[0])
        {
            popMapPins();
        }

        if(!filter[1])
        {
            popMapAtt();
        }

        if(!filter[2])
        {
            popMapLnd();
        }

        if(!filter[3])
        {
            popMapEvents();
        }

        if(!filter[4])
        {
            popMapTotem();
        }
    }
    //Populates the maps with Attractions markers from the POI Array
    public static void popMapAtt()
    {
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
//                    temp = mMap.addMarker(new MarkerOptions()
//                            .position(dest)
//                            .title(add.getFeatureName())
//                            .snippet(item.getDescription())
//                            .icon(BitmapDescriptorFactory.fromResource(IconManager.nArrIconID[att.getIcon()])));

                    FilteredPOI.add(item);
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
                Landmark lnd = (Landmark)item;
                Address add = item.getAddressInfo();
                LatLng dest = new LatLng(add.getLatitude(), add.getLongitude());

                Location destLoc = new Location("");
                destLoc.setLatitude(dest.latitude);
                destLoc.setLongitude(dest.longitude);

                float distance = location.distanceTo(destLoc);

                if ((!rangeFilter || distance <= maxRange))
                {
//                    temp = mMap.addMarker(new MarkerOptions()
//                            .position(dest)
//                            .title(add.getFeatureName())
//                            .snippet(item.getDescription())
//                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.landmark)));

                    FilteredPOI.add(item);
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
                Event evnt = (Event)item;
                Address add = item.getAddressInfo();
                LatLng dest = new LatLng(add.getLatitude(), add.getLongitude());

                Location destLoc = new Location("");
                destLoc.setLatitude(dest.latitude);
                destLoc.setLongitude(dest.longitude);

                float distance = location.distanceTo(destLoc);

                if ((!rangeFilter || distance <= maxRange)&&(!filter[evnt.getIcon()]))
                {
//                    Bitmap bitmap = changeIcon(event.getIcon());
//                    temp = mMap.addMarker(new MarkerOptions()
//                            .position(dest)
//                            .title(add.getFeatureName())
//                            .snippet(item.getDescription())
//                            .icon(BitmapDescriptorFactory.fromResource(IconManager.nArrIconID[event.getIcon()])));

                    FilteredPOI.add(item);
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
//                    temp = mMap.addMarker(new MarkerOptions()
//                            .position(dest)
//                            .title(add.getFeatureName())
//                            .snippet(item.getDescription())
//                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.totem)));

                    FilteredPOI.add(item);
                }
            }
        }
    }

    public static void popMapPins()
    {
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
//                    temp = mMap.addMarker(new MarkerOptions()
//                            .position(dest)
//                            .title(add.getFeatureName())
//                            .snippet(item.getDescription())
//                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.userpin)));

                    FilteredPOI.add(item);
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

    public static ArrayList<POI> getFilteredPOI()
    {
        popFilter();
        return FilteredPOI;
    }

    public static Bitmap changeIcon(int bmp)
    {
        Bitmap icon = null;
        return icon;
    }

    public static void addPin(UserPin userPin)
    {
        poiArrayList.add(userPin);
    }

    private static void fillTags()
    {
        for(int i = 0; i < poiArrayList.size(); i++)
        {

        }
    }

    public static ArrayList<POI> getFilteredPoints()
    {
        return poiArrayList;
    }

}

