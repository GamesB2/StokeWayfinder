package com.example.w028006g.regnlogin.helper.MarkerClasses;

import com.example.w028006g.regnlogin.helper.MarkerClasses.POI;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by a025178g on 10/07/2017.
 */

public class UserPin extends POI
{
    public UserPin (Place place)
    {
        super.setName(place.getName().toString());
        LatLng latLng = place.getLatLng();
        super.setLat(String.valueOf(latLng.latitude));
        super.setLong(String.valueOf(latLng.longitude));
        super.addPoint(this);
    }
}
