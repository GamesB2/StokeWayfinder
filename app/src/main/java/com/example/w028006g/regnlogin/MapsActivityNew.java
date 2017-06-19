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
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.design.widget.BottomNavigationView;

import com.example.w028006g.regnlogin.helper.Attraction;
import com.example.w028006g.regnlogin.helper.DatabaseRetrieval;
import com.example.w028006g.regnlogin.helper.SettingsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.lang.reflect.Method;
import java.util.List;

public class MapsActivityNew extends FragmentActivity implements OnMapReadyCallback
{

    //Assigns the String "TAG" the name of the class for error reports
    private static final String TAG = MapsActivityNew.class.getSimpleName();

    private GoogleMap mMap;
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
    private Attraction at;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_new);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        Gson gS = new Gson();
//        String target = getIntent().getStringExtra("MyObjectAsString");
//        Attraction src = gS.fromJson(target, Attraction.class); // Converts the JSON String to an Object
        at = DatabaseRetrieval.att;
       Toast.makeText(getApplicationContext(),
                "TEST DATA: " + at.getName() + " Lat: " + at.getLat() + " Long: " + at.getLng(),
                Toast.LENGTH_LONG).show();






        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

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

    public void centerOn(String sLat, String sLong)
    {
        LatLng focusPoint = new LatLng(Double.parseDouble(sLat),Double.parseDouble(sLong));

        mMap.addMarker(new MarkerOptions().position(focusPoint).title("Discount Day").icon(BitmapDescriptorFactory.fromResource(R.drawable.discountlogo)));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(25));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(focusPoint));
    }

    public void popMap()
    {
        LatLng attLat = new LatLng(Double.parseDouble(at.getLat()),Double.parseDouble(at.getLng()));
        mMap.addMarker(new MarkerOptions().position(attLat).title(at.getName()).snippet("Why are you even reading this?").icon(BitmapDescriptorFactory.fromResource(R.drawable.lock)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(attLat));

    }

    //Search implementation, pins a marker on the location of the user
    public void onMapSearch(View view)
    {
        EditText locationSearch = (EditText) findViewById(R.id.gSearch);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

       //

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
        mMap.addMarker(new MarkerOptions().position(sydney).title("'Ere be prisoners").snippet("Why are you even reading this?").icon(BitmapDescriptorFactory.fromResource(R.drawable.lock)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(Demo,0));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Demo.getCenter(),10));

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
        popMap();
    }

}

