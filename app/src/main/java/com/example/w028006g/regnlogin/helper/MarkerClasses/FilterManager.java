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

                    FilteredPoints.add(item);
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

                    FilteredPoints.add(item);
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

                    FilteredPoints.add(item);
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

                    FilteredPoints.add(item);
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

                    FilteredPoints.add(item);
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

