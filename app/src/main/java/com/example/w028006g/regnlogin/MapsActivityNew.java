package com.example.w028006g.regnlogin;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.w028006g.regnlogin.helper.SettingsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class MapsActivityNew extends FragmentActivity implements OnMapReadyCallback
{

    //Assigns the String "TAG" the name of the class for error reports
    private static final String TAG = MapsActivityNew.class.getSimpleName();

    private GoogleMap mMap;
    private Button btnMenu;

    LocationManager locationManager;

    //Constant used as a request code for the location permissions
    final int MY_PERMISSIONS_REQUEST_LOCATION = 14;
    public String lat;
    public String lon;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            lat = extras.getString("Latitude");
            lon = extras.getString("Longitude");

        }





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem item)
            {
                // Handle navigation view item clicks here.
                int id = item.getItemId();

                if (id == R.id.nav_profile) {
                    // Handle the camera action
                } else if (id == R.id.nav_events) {

                } else if (id == R.id.nav_logout) {

                } else if (id == R.id.nav_settings) {
                    Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_exit) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }

        });

        btnMenu = (Button) findViewById(R.id.button3);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);

            }
        });

    }
    public void centerOn(String sLat, String sLong)
    {

        LatLng focusPoint = new LatLng(Double.parseDouble(sLat),Double.parseDouble(sLong));

        mMap.addMarker(new MarkerOptions().position(focusPoint).title("Discount Day").icon(BitmapDescriptorFactory.fromResource(R.drawable.lock)));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(20));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(focusPoint));
    }
    //Called to check if location is enabled on the device.
    //DOES NOT check to see if permission has been granted
    private boolean checkLocation()
    {
        if(!isLocationEnabled())
            showOffAlert();
        return isLocationEnabled();
    }

    private boolean isLocationEnabled()
    {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    //Checks to see if the user has granted location permissions to the app.
    private boolean checkLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(this,
                                          android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission. ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            return false;
        } else
        {
            mMap.setMyLocationEnabled(true);
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)
    {
        LatLng stoke = new LatLng(53.0027,-2.1794);

        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    try
                    {
                        mMap.setMyLocationEnabled(true);
                    }catch (SecurityException SE)
                    {
                        Log.e(TAG, "Permissions error: Requires Location Permissions to be enabled manually");
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(stoke));
                    }

                } else
                {
                    Log.e(TAG, "Location permissions denied");
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(stoke));
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
        }
    }

    //Creates an alert window to prompt the user to turn their location settings on
    private void showOffAlert()
    {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings are turned off.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt)
                    {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt)
                    {
                    }
                });
        dialog.show();
    }

    //Search implementation, pins a marker on the location of the user
    public void onMapSearch(View view)
    {
        EditText locationSearch = (EditText) findViewById(R.id.gSearch);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

        if (!location.equals(""))
        {
            Geocoder geocoder = new Geocoder(this);
            try
            {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        try
        {
            boolean success = mMap.setMapStyle
                    (MapStyleOptions.loadRawResourceStyle
                            (this, R.raw.style_json));
            if (!success)
            {
                Log.e(TAG, "Style parsing failed.");
            }
        }catch (Resources.NotFoundException e)
        {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        LatLng sydney = new LatLng(-34, 151);
        LatLng stoke = new LatLng(53.0027,-2.1794);
        LatLng center = new LatLng(0,0);

        mMap.addMarker(new MarkerOptions().position(stoke).title("Marker in Sydney").snippet("Test Snippet inserting text").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(stoke));

        if (checkLocation())
        {
            checkLocationPermission();
        }

        if (lat != null && lon != null) {

            centerOn(lat, lon);

//            LatLng focusPoint = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
//            Toast.makeText(MapsActivityNew.this, "" + lat + " " + lon + "", Toast.LENGTH_SHORT).show();
//            mMap.addMarker(new MarkerOptions().position(focusPoint).title("Discount Day").icon(BitmapDescriptorFactory.fromResource(R.drawable.lock)));
//            mMap.moveCamera(CameraUpdateFactory.zoomTo(25));
//            mMap.animateCamera(CameraUpdateFactory.newLatLng(focusPoint));
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

}
