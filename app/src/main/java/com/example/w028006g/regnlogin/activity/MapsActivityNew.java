package com.example.w028006g.regnlogin.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.support.design.widget.BottomNavigationView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w028006g.regnlogin.BottomNavigationViewHelper;
import com.example.w028006g.regnlogin.GeolocationService;
import com.example.w028006g.regnlogin.app.AppController;
import com.example.w028006g.regnlogin.helper.GMapV2Direction;
import com.example.w028006g.regnlogin.helper.GMapV2DirectionAsyncTask;
import com.example.w028006g.regnlogin.helper.MarkerClasses.FilterManager;
import com.example.w028006g.regnlogin.R;
import com.example.w028006g.regnlogin.SimpleGeofence;
import com.example.w028006g.regnlogin.SimpleGeofenceStore;
import com.example.w028006g.regnlogin.helper.DatabaseRetrieval;
import com.example.w028006g.regnlogin.helper.MarkerClasses.MarkerRenderer;
import com.example.w028006g.regnlogin.helper.MarkerClasses.POI;
import com.example.w028006g.regnlogin.helper.MarkerClasses.UserPin;
import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.listener.OnPostingCompleteListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import org.w3c.dom.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RunnableFuture;


public class MapsActivityNew extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener

{
    //Assigns the String "TAG" the name of the class for error reports
    private static final String TAG = MapsActivityNew.class.getSimpleName();
    private LatLngBounds Demo = new LatLngBounds(new LatLng(49.495091,-10.722460), new LatLng(59.497134,1.843598));
    private LatLngBounds StokeBounds = new LatLngBounds(new LatLng(52.722390, -2.654259), new LatLng(53.252159, -1.687220));
    private static ArrayList<POI> poiArrayList = new ArrayList<>();
    LocationManager locationManager;
    private ClusterManager<POI> clusterManager;
    private boolean pauseState = false;
    private FilterManager filterManager;
    public MarkerRenderer markerRenderer;
    private int networkId;

    //Constant used as a request code for the location permissions
    final int MY_PERMISSIONS_REQUEST_LOCATION = 14;

    public String lat;
    public String lon;
    private GoogleMap mMap;
    private Button mButton1;
    private BottomSheetBehavior mBottomSheetBehavior1;

    private static final String NOTIFICATION_MSG = "NOTIFICATION MSG";
    public Button btnQR;

    public ImageButton btnFilter;

    private TextView Title;
    private ImageView Picture;
    private TextView Descritption;

    private BottomSheetBehavior mBottomSheetBehavior;


    static public boolean geofencesAlreadyRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_new);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Bottom Sheet stuff
        //-------------------------END Sheets


        MapsInitializer.initialize(getApplicationContext());

//
//         Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);

        //Landmarks, Attractions, and Events Stored in POI Array
        poiArrayList = POI.getAllPoints();
        //Lat and Long from FireMSGService brought in here
        Bundle FireNotification = getIntent().getExtras();
        if (FireNotification != null) {
            lat = FireNotification.getString("Latitude");
            lon = FireNotification.getString("Longitude");
        }

        //Menu bar at the bottom
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId()) {
                    case R.id.ic_map:
                        Intent intent = new Intent(getApplicationContext(), MapsActivityNew.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        break;

                    case R.id.ic_Profile:
                        Intent intent1 = new Intent(getApplicationContext(), Profile.class);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        finish();
                        break;

                    case R.id.ic_qr:
                        Intent intent2 = new Intent(getApplicationContext(), qrActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        finish();
                        break;

                    case R.id.ic_shop:
                        Intent intent3 = new Intent(getApplicationContext(), Tickets.class);
                        startActivity(intent3);
                        overridePendingTransition(0, 0);
                        finish();
                        break;

                    case R.id.ic_Rec:

                        Intent intent4 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent4);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                }
                return false;
            }
        });

        //Starts Geolocation Service
        startService(new Intent(this, GeolocationService.class));

        btnFilter = (ImageButton) findViewById(R.id.FilterButton);
        btnFilter.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View view)
            {
                Intent i = new Intent(MapsActivityNew.this,
                        FilterActivity.class);
                startActivity(i);
            }
        });

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setBoundsBias(StokeBounds);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                LatLng latlng = place.getLatLng();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
                UserPin userPin = new UserPin(place);
                Log.i(TAG, "Place: " + place.getName());
            }



            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });



    }


//    public void onClick()
//    {
//        Intent i = new Intent(MapsActivityNew.this,
//                FilterActivity.class);
//        startActivity(i);
//    }


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
    private boolean checkLocation()
    {
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

    private void pullBottomSheet(final LatLng location) {

        View bottomSheet = findViewById( R.id.bottom_sheet );
        TextView Directions = (TextView) findViewById(R.id.direction);
        final LatLng stoke = new LatLng(53.0027, -2.1794);
        Directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    route(GeolocationService.getLatLng(), location);
                }
                catch (Exception e)
                {
                    Log.d(TAG, e.getMessage());
                    route(stoke, location);

                }

                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                mBottomSheetBehavior.setPeekHeight(300);
            }
        });
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mBottomSheetBehavior.setPeekHeight(0);
            }
        });
        runOnUiThread(new Runnable() {
            public void run() {

                mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(View bottomSheet, int newState) {
                        if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                            mBottomSheetBehavior.setPeekHeight(300);
                        }
                    }

                    @Override
                    public void onSlide(View bottomSheet, float slideOffset) {
                        mBottomSheetBehavior.setPeekHeight(300);
                    }
                });
            }
        });

        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            mBottomSheetBehavior.setPeekHeight(300);
        }
    }

    private OnPostingCompleteListener postingComplete = new OnPostingCompleteListener() {
        @Override
        public void onPostSuccessfully(int socialNetworkID) {
            Toast.makeText(MapsActivityNew.this, "Sent", Toast.LENGTH_LONG).show();
        }


        @Override
        public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
            Toast.makeText(MapsActivityNew.this, "Post Has Not shared", Toast.LENGTH_SHORT).show();
        }
    };
    private android.app.AlertDialog.Builder alertDialogInit(String title, String message) {
        android.app.AlertDialog.Builder ad = new android.app.AlertDialog.Builder(MapsActivityNew.this);
        ad.setTitle(title);
        ad.setMessage(message);
        ad.setCancelable(true);
        return ad;
    }

    protected void route(LatLng sourcePosition, LatLng destPosition) {
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                try {
                    Document doc = (Document) msg.obj;
                    GMapV2Direction md = new GMapV2Direction();
                    ArrayList<LatLng> directionPoint = md.getDirection(doc);
                    PolylineOptions rectLine = new PolylineOptions().width(15).color(R.color.bg_login) ;
                    for (int i = 0; i < directionPoint.size(); i++) {
                        rectLine.add(directionPoint.get(i));
                    }
                    mMap.addPolyline(rectLine);md.getDurationText(doc);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ;
        };

        new GMapV2DirectionAsyncTask(handler, sourcePosition, destPosition, GMapV2Direction.MODE_WALKING).execute();
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




//   // Search implementation, pins a marker on the location of the user
//    public void onMapSearch(View view) {
//        EditText locationSearch = (EditText) findViewById(R.id.place_autocomplete_fragment);
//        String location = locationSearch.getText().toString();
//        List<Address> addressList = null;
//
//        if (!location.equals("")) {
//            Geocoder geocoder = new Geocoder(this);
//            try {
//                addressList = geocoder.getFromLocationName(location, 1);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Address address = addressList.get(0);
//
//            //Marker mSearch = new Marker();
//            //mSearch.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.searchbutton));
//
//            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//            Marker mSearch = (mMap.addMarker(new MarkerOptions().position(latLng).title("Search query").icon(BitmapDescriptorFactory.fromResource(R.drawable.searchbutton))));
//            mSearch.setDraggable(true);
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        }
//}

    @Override
    protected void onResume()
    {

        super.onResume();
        if (clusterManager != null) 
        {
            clusterManager.clearItems();
            MarkerRenderer markerRenderer = new MarkerRenderer(getApplicationContext(), mMap, clusterManager);
            filterManager.popFilter();
            mMap.clear();
            fillCM();
        }

        //startService(new Intent(this, DatabaseRetrieval.class));

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        mMap.setLatLngBoundsForCameraTarget(Demo);
        mMap.setMinZoomPreference(5);
        clusterManager = new ClusterManager<>(this,mMap);
        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);
        LatLng stoke = new LatLng(53.0027, -2.1794);
        filterManager = new FilterManager(mMap,poiArrayList);
         
        markerRenderer = new MarkerRenderer(getApplicationContext(), mMap, clusterManager);
        filterManager = new FilterManager(mMap,poiArrayList);


        fillCM();
        pullBottomSheet(stoke);
        clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<POI>()
        {
            @Override
            public boolean onClusterItemClick(POI poi)
            {
                final POI PointOfInt = poi;

                runOnUiThread(new Runnable() {
                    public void run() {

                        Runnable test = new Runnable() {
                            @Override
                            public void run() {
                                LatLng poiLocation = PointOfInt.getPosition();

                                Title = (TextView) findViewById(R.id.title);
                                Picture = (ImageView) findViewById(R.id.pic);
                                Descritption = (TextView) findViewById(R.id.desc);
                                Title.setText(PointOfInt.getAddressInfo().getFeatureName());
                                Descritption.setText(PointOfInt.getDescription());


                                pullBottomSheet(poiLocation);
                            }
                        };

                        runOnUiThread(test);
                    }
                });
                return false;
            }
        });

        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<POI>() {
            @Override
            public boolean onClusterClick(Cluster<POI> cluster) {
                mBottomSheetBehavior.setPeekHeight(0);
                return false;
            }
        });

        //Calls function to display geofence circle
        displayGeofences();

        //To set the maps style and json file
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

        //LatLng center = new LatLng(0, 0);

        //Permanent Markers added and camera zoom on initial startup
        //mMap.addMarker(new MarkerOptions().position(stoke).title("Marker in Stoke").snippet("Test Snippet inserting text").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(stoke));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));

        checkLocation();
        checkLocationPermission();

        //Checks that something has been passed to lat and lon before trying to execute
        if (lat != null && lon != null)
        {
            centerOn(lat, lon);
        }

        //Executes popMap to populate the markers on the maps
//        filterManager = new FilterManager(mMap,poiArrayList);
//        filterManager.popMap();

        fillCM();
    }

    @Override
    public boolean onMarkerClick(Marker marker)
    {

        return false;
    }

    public void fillCM()
    {
        ArrayList<POI> points = new ArrayList<>();
        points = FilterManager.getFilteredPoints();
        for(int i = 0; i < points.size(); i++)
        {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(points.get(i).getPosition())
                    .icon(points.get(i).getIconBMP());

            clusterManager.addItem(points.get(i));
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void finish() {
        clusterManager.clearItems();
        super.finish();
    }
}









