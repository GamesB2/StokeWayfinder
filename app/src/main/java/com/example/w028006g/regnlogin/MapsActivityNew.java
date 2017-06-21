package com.example.w028006g.regnlogin;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebStorage;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.BottomNavigationView;


import com.example.w028006g.regnlogin.helper.Attraction;
import com.example.w028006g.regnlogin.helper.DatabaseRetrieval;

import com.example.w028006g.regnlogin.helper.Event;
import com.example.w028006g.regnlogin.helper.Landmark;
import com.example.w028006g.regnlogin.helper.POI;

import com.example.w028006g.regnlogin.helper.Event;
import com.example.w028006g.regnlogin.helper.Landmark;
import com.example.w028006g.regnlogin.helper.POI;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;

import com.example.w028006g.regnlogin.helper.SettingsActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;

import com.example.w028006g.regnlogin.helper.SettingsActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import com.google.android.gms.maps.model.Circle;

import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.gson.Gson;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;



import java.io.IOException;
import java.util.ArrayList;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Locale;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import com.google.android.gms.location.LocationListener;

public class MapsActivityNew extends AppCompatActivity implements OnMapReadyCallback {




    //Assigns the String "TAG" the name of the class for error reports
    private static final String TAG = MapsActivityNew.class.getSimpleName();


    private Marker currentMarker = null;
    private Marker destMarker = null;
    private LatLng currentLatLng = null;
    private Polyline line = null;


    private boolean firstRefresh = true;



    //Global flags


    public static Location userLocation;
    public static LatLng userLatLng;
    //private Button btnMenu;

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;


    private LatLngBounds Demo = new LatLngBounds(new LatLng(52.5027,-2.6794), new LatLng(53.5025,-1.6794));

    private Button btnMenu;

    LocationManager locationManager;

    //Constant used as a request code for the location permissions
    final int MY_PERMISSIONS_REQUEST_LOCATION = 14;
    public String lat;
    public String lon;
    public ArrayList<POI> poiArrayList = new ArrayList<>();
    public POI att;


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


    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;

    private TextView textLat, textLong;

    private MapFragment mapFragment;

    private static final String NOTIFICATION_MSG = "NOTIFICATION MSG";

    // Create a Intent send by the notification
    public static Intent makeNotificationIntent(Context context, String msg) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(NOTIFICATION_MSG, msg);
        return intent;
    }

    protected Marker myPositionMarker;
    static public boolean geofencesAlreadyRegistered = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_new);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        poiArrayList = DatabaseRetrieval.poiArrayList;

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            lat = extras.getString("Latitude");
            lon = extras.getString("Longitude");

        }


        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
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
            startService(new Intent(this, GeolocationService.class));
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                int resultCode = bundle.getInt("done");
                if (resultCode == 1) {
                    Double latitude = bundle.getDouble("latitude");
                    Double longitude = bundle.getDouble("longitude");

                    updateMarker(latitude, longitude);
                }
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();

        this.unregisterReceiver(receiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {

                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                    displayGeofences();
                }
            });
        }

        this.registerReceiver(receiver,
                new IntentFilter("me.hoen.geofence_21.geolocation.service"));
    }

    protected void displayGeofences() {
//		HashMap<String, SimpleGeofence> geofences = SimpleGeofenceStore.getInstance().getSimpleGeofences();
        HashMap<String, SimpleGeofence> geofences = SimpleGeofenceStore.getInstance().getSimpleGeofences();

//		for (Map.Entry<String, SimpleGeofence> item : geofences.entrySet()) {
//			SimpleGeofence sg = item.getValue();
//
//			CircleOptions circleOptions1 = new CircleOptions()
//					.center(new LatLng(sg.getLatitude(), sg.getLongitude()))
//					.radius(sg.getRadius()).strokeColor(Color.BLACK)
//					.strokeWidth(2).fillColor(0x500000ff);
//			map.addCircle(circleOptions1);
//		}

        for (Map.Entry<String, SimpleGeofence> item : geofences.entrySet()) {
            SimpleGeofence sg = item.getValue();

            CircleOptions circleOptions1 = new CircleOptions()
                    .center(new LatLng(sg.getLatitude(), sg.getLongitude()))
                    .radius(sg.getRadius()).strokeColor(Color.BLACK)
                    .strokeWidth(2).fillColor(0x500000ff);
            mMap.addCircle(circleOptions1);
        }
    }

    protected void createMarker(Double latitude, Double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);

        myPositionMarker = mMap.addMarker(new MarkerOptions().position(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    protected void updateMarker(Double latitude, Double longitude) {
        if (myPositionMarker == null) {
            createMarker(latitude, longitude);
        }
    }

    static public void startGeolocationService(Context context) {

        Intent geolocationService = new Intent(context,
                GeolocationService.class);
        PendingIntent piGeolocationService = PendingIntent.getService(context,
                0, geolocationService, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(piGeolocationService);
        alarmManager
                .setInexactRepeating(AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis(), 2 * 60 * 1000,
                        piGeolocationService);
    }


//    protected void displayGeofences() {
//        HashMap<String, SimpleGeofence> geofences = SimpleGeofenceStore.getInstance().getSimpleGeofences();
//
//        for(Map.Entry<String, SimpleGeofence>item : geofences.entrySet()){
//            SimpleGeofence sg = item.getValue();
//
//            CircleOptions circleOptions1 = new CircleOptions()
//                    .center(new LatLng(sg.getLatitude(), sg.getLongitude()))
//                    .radius(sg.getRadius()).strokeColor(Color.BLACK)
//                    .strokeWidth(2).fillColor(0x500000ff);
//            mMap.addCircle(circleOptions1);
//        }
//    }

    //Called to check if location is enabled on the device.
    //DOES NOT check to see if permission has been granted
    private boolean checkLocation() {
        if (!isLocationEnabled())
            showOffAlert();
        return isLocationEnabled();
    }

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


//    private void ListingNearbyDirection(final LatLng Origin, final LatLng Destination) {
//
//        String serverKey = "AIzaSyDkTMy7dLmxu3GLQttBfDBDsnwPLFseiCM";
//
//        GoogleDirection.withServerKey(serverKey)
//                .from(Origin)
//                .to(Destination)
//                .transportMode(TransportMode.WALKING)
//                .alternativeRoute(true)
//                .execute(new DirectionCallback() {
//
//                    @Override
//                    public void onDirectionSuccess(Direction direction, String rawBody) {
//                            Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_SHORT).show();
//                            mMap.addMarker(new MarkerOptions().position(Origin));
//                            mMap.addMarker(new MarkerOptions().position(Destination));
//
//                            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
//                            mMap.addPolyline(DirectionConverter.createPolyline(getApplicationContext(), directionPositionList, 5, Color.RED));
//
//
//                    }
//
//                    @Override
//                    public void onDirectionFailure(Throwable t) {
//
//                    }
//                }
//                );}

    private void ListingNearbyDirection(final LatLng Origin, final LatLng Destination) {

        String serverKey = "AIzaSyDkTMy7dLmxu3GLQttBfDBDsnwPLFseiCM";

        GoogleDirection.withServerKey(serverKey)
                .from(Origin)
                .to(Destination)
                .transportMode(TransportMode.WALKING)
                .alternativeRoute(true)
                .execute(new DirectionCallback() {

                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                            Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_SHORT).show();
                            mMap.addMarker(new MarkerOptions().position(Origin));
                            mMap.addMarker(new MarkerOptions().position(Destination));

                            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
                            mMap.addPolyline(DirectionConverter.createPolyline(getApplicationContext(), directionPositionList, 5, Color.RED));


                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {

                    }
                }
                );}

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

    public void centerOn(String sLat, String sLong) {
        LatLng focusPoint = new LatLng(Double.parseDouble(sLat), Double.parseDouble(sLong));

        mMap.addMarker(new MarkerOptions().position(focusPoint).title("Discount Day").icon(BitmapDescriptorFactory.fromResource(R.drawable.discountlogo)));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(25));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(focusPoint));
    }


    public void popMap()
    {
        popMapAtt();
        popMapLnd();
        popMapEvents();
    }
    public void popMapAtt()
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
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.film_and_media)));
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
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
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
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.health_and_fitness)));
                        break;
                    case HEALTH_AND_FITNESS:
                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.health)));
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
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                        break;
                    case CHARITY:
                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
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
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
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
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                        break;
                    case FASHION:
                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                        break;
                    case HOME_AND_LIFESTYLE:
                        mMap.addMarker(new MarkerOptions()
                                .position(dest)
                                .title(add.getFeatureName())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
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
                                .title(add.getFeatureName()));

                        break;
                }
            }
        }
    }

    public void popMapLnd()
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

    public void popMapEvents()
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



        displayGeofences();


        try {
            boolean success = mMap.setMapStyle
                    (MapStyleOptions.loadRawResourceStyle
                            (this, R.raw.style_json));
            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {

        try
        {


        displayGeofences();


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

        LatLng sydney = new LatLng(-34, 151);

        LatLng stoke = new LatLng(53.0027, -2.1794);
        LatLng center = new LatLng(0, 0);

        mMap.addMarker(new MarkerOptions().position(sydney).title("'Ere be prisoners").snippet("Why are you even reading this?").icon(BitmapDescriptorFactory.fromResource(R.drawable.lock)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(Demo, 0));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Demo.getCenter(), 10));

        if (checkLocation()) {

        mMap.addMarker(new MarkerOptions().position(stoke).title("Marker in Sydney").snippet("Test Snippet inserting text").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(stoke));
        mMap.addMarker(new MarkerOptions().position(sydney).title("'Ere be prisoners").snippet("Why are you even reading this?").icon(BitmapDescriptorFactory.fromResource(R.drawable.lock)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(Demo,0));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Demo.getCenter(),10));

        if (checkLocation())
        {
            checkLocationPermission();
        }

        if (lat != null && lon != null) {

            centerOn(lat, lon);

        }
        popMap();

    }

}



//            LatLng focusPoint = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
//            Toast.makeText(MapsActivityNew.this, "" + lat + " " + lon + "", Toast.LENGTH_SHORT).show();
//            mMap.addMarker(new MarkerOptions().position(focusPoint).title("Discount Day").icon(BitmapDescriptorFactory.fromResource(R.drawable.lock)));
//            mMap.moveCamera(CameraUpdateFactory.zoomTo(25));
//            mMap.animateCamera(CameraUpdateFactory.newLatLng(focusPoint));
        }
        //popMap();
//        userLocation = new Location(mMap.getMyLocation());
//        double uLat = userLocation.getLatitude();
//        double uLong = userLocation.getLongitude();
//        userLatLng = new LatLng(uLat,uLong);
//        Toast.makeText(MapsActivityNew.this, "User Location" + uLat + " " + uLong + "", Toast.LENGTH_SHORT).show();
//        LatLng origin = new LatLng(53.0027, -2.1794);
//        LatLng destination = new LatLng(53.010541, -2.228589);
//        ListingNearbyDirection(origin, destination);


        }
        //popMap();








