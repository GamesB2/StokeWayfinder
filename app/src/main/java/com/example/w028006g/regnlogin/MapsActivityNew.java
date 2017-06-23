package com.example.w028006g.regnlogin;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.design.widget.BottomNavigationView;
import android.widget.Toast;

import com.example.w028006g.regnlogin.helper.DatabaseRetrieval;
import com.example.w028006g.regnlogin.helper.Events;
import com.example.w028006g.regnlogin.helper.POI;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivityNew extends AppCompatActivity implements OnMapReadyCallback
{

    //Assigns the String "TAG" the name of the class for error reports
    private static final String TAG = MapsActivityNew.class.getSimpleName();
    private LatLngBounds Demo = new LatLngBounds(new LatLng(49.495091,-10.722460), new LatLng(59.497134,1.843598));
    private static ArrayList<POI> poiArrayList = new ArrayList<>();
    LocationManager locationManager;

    //Constant used as a request code for the location permissions
    final int MY_PERMISSIONS_REQUEST_LOCATION = 14;

    public String lat;
    public String lon;
    private GoogleMap mMap;

    private ArrayList<Events> ev = DatabaseRetrieval.eventsAl;

    //List of constants declared to sort the markers
    final int MUSIC= 0;
    final int BUSINESS= 1;
    final int FOOD_AND_DRINK=2;
    final int COMMUNITY=3;
    final int ARTS=4;
    final int FILM_AND_MEDIA=5;
    final int SPORTS=6;
    final int HEALTH_AND_FITNESS=7;
    final int SCIENCE_AND_TECH=8;
    final int TRAVEL_AND_OUTDOOR=9;
    final int CHARITY=10;
    final int SPIRITUALITY=11;
    final int FAMILY_AND_EDUCATION=12;
    final int HOLIDAY=13;
    final int GOVERNMENT=14;
    final int FASHION=15;
    final int HOME_AND_LIFESTYLE=16;
    final int AUTO_BOAT_AND_AIR=17;
    final int HOBBIES=18;

    private static final String NOTIFICATION_MSG = "NOTIFICATION MSG";

    static public boolean geofencesAlreadyRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_new);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Landmarks, Attractions, and Events Stored in POI Array
        poiArrayList = DatabaseRetrieval.poiArrayList;
        //Lat and Long from FireMSGService brought in here
        Bundle FireNotification = getIntent().getExtras();
        if (FireNotification != null)
        {
            lat = FireNotification.getString("Latitude");
            lon = FireNotification.getString("Longitude");

        }


        for(int i=0;i<ev.size();i++)
        {
            Toast.makeText(this, "Event:\n" + ev.get(i).getName(), Toast.LENGTH_LONG).show();
        }


        //Menu bar at the bottom
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.ic_map:

                        break;

                    case R.id.ic_Profile:
                        Intent intent1 = new Intent(MapsActivityNew.this, Profile.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_Adventures:
                        Intent intent2 = new Intent(MapsActivityNew.this, Adventures.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_Tickets:
                        Intent intent3 = new Intent(MapsActivityNew.this, Tickets.class);
                        startActivity(intent3);
                        break;
                }
                return false;
            }
        });
            //Starts Geolocation Service
            startService(new Intent(this, GeolocationService.class));
    }

    //Displays the circle around the geofence - wont need this for final just so we can see where they are
    protected void displayGeofences() {
        HashMap<String, SimpleGeofence> geofences = SimpleGeofenceStore.getInstance().getSimpleGeofences();

        for (Map.Entry<String, SimpleGeofence> item : geofences.entrySet()) {
            SimpleGeofence sg = item.getValue();

            CircleOptions circleOptions1 = new CircleOptions()
                    .center(new LatLng(sg.getLatitude(), sg.getLongitude()))
                    .radius(sg.getRadius()).strokeColor(Color.BLACK)
                    .strokeWidth(2).fillColor(0x500000ff);
            mMap.addCircle(circleOptions1);
        }
    }

    //Called to check if location is enabled on the device.
    //DOES NOT check to see if permission has been granted
    private boolean checkLocation() {
        if (!isLocationEnabled())
            showOffAlert();
        return isLocationEnabled();
    }

    //Popup to ask to turn GPS on (Not permissions allow or deny box)
    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    //Checks to see if the user has granted location permissions to the app.
    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
            return false;
        } else {
            mMap.setMyLocationEnabled(true);
            return true;
        }
    }


    //Asks for permission to access GPS and handles the outcome of allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        LatLng stoke = new LatLng(53.0027, -2.1794);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        mMap.setMyLocationEnabled(true);
                    } catch (SecurityException SE) {
                        Log.e(TAG, "Permissions error: Requires Location Permissions to be enabled manually");
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(stoke));
                    }

                } else {
                    Log.e(TAG, "Location permissions denied");
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(stoke));
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
        }
    }

    //Creates an alert window to prompt the user to turn their location settings on
    private void showOffAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings are turned off.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    //Focuses the camera on the coordinates passed to it
    public void centerOn(String sLat, String sLong) {
        LatLng focusPoint = new LatLng(Double.parseDouble(sLat), Double.parseDouble(sLong));

        mMap.addMarker(new MarkerOptions().position(focusPoint).title("Discount Day").icon(BitmapDescriptorFactory.fromResource(R.drawable.discountlogo)));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(25));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(focusPoint));
    }




    //Search implementation, pins a marker on the location of the user
    public void onMapSearch(View view) {
        EditText locationSearch = (EditText) findViewById(R.id.gSearch);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

        //

        if (!location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);

            //Marker mSearch = new Marker();
            //mSearch.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.searchbutton));

            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            Marker mSearch = (mMap.addMarker(new MarkerOptions().position(latLng).title("Search query").icon(BitmapDescriptorFactory.fromResource(R.drawable.searchbutton))));
            mSearch.setDraggable(true);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap)
    {

        mMap = googleMap;
        mMap.setLatLngBoundsForCameraTarget(Demo);
        mMap.setMinZoomPreference(5);

        //Calls function to display geofence circle
        displayGeofences();

        //To set the map style and json file
        try
        {
            boolean success = mMap.setMapStyle
                    (MapStyleOptions.loadRawResourceStyle
                            (this, R.raw.style_json));
            if (!success)
            {
                Log.e(TAG, "Style parsing failed.");
            }
        }catch (Resources.NotFoundException ex)
        {

            Log.e(TAG, "Can't find style. Error: ", ex);
        }

        //Location LatLng defined here
        LatLng sydney = new LatLng(-34, 151);
        LatLng stoke = new LatLng(53.0027, -2.1794);
        LatLng center = new LatLng(0, 0);

        //Permanent Markers added and camera zoom on initial startup
        mMap.addMarker(new MarkerOptions().position(stoke).title("Marker in Sydney").snippet("Test Snippet inserting text").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(stoke));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));


        checkLocationPermission();

        //Checks that something has been passed to lat and lon before trying to execute
        if (lat != null && lon != null)
        {

            centerOn(lat, lon);

        }

        //Executes popMap to populate the markers on the map
        MarkerManager mm = new MarkerManager(mMap,poiArrayList);
        mm.popMap();
    }


}









