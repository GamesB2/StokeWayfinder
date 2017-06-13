//package com.example.w028006g.regnlogin.activity;
//
//import android.content.res.Resources;
//import android.location.Address;
//import android.location.Geocoder;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.EditText;
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//
//import android.support.v7.app.AppCompatActivity;
//
//import com.example.w028006g.regnlogin.R;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.maps.model.MapStyleOptions;
//
//import java.io.IOException;
//import java.util.List;
//
//public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback
//{
//    //Assigns the String "TAG" the name of the class for error reports
//    private static final String TAG = MapsActivity.class.getSimpleName();
//
//    private GoogleMap mMap;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps_new);
//        // Obtain the SupportMapFragment and register for the callback to get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//    }
//
//    //Search implementation
//    public void onMapSearch(View view) {
//        EditText locationSearch = (EditText) findViewById(R.id.gSearch);
//        String location = locationSearch.getText().toString();
//        List<Address> addressList = null;
//
//        if (location != null || !location.equals("")) {
//            Geocoder geocoder = new Geocoder(this);
//            try {
//                addressList = geocoder.getFromLocationName(location, 1);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Address address = addressList.get(0);
//            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        }
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap)
//    {
//        mMap = googleMap;
//
//        try
//        {
//            boolean success = mMap.setMapStyle
//                    (MapStyleOptions.loadRawResourceStyle
//                            (this, R.raw.style_json));
//            if (!success)
//            {
//                Log.e(TAG, "Style parsing failed.");
//            }
//        }catch (Resources.NotFoundException e)
//        {
//            Log.e(TAG, "Can't find style. Error: ", e);
//        }
//
//        LatLng sydney = new LatLng(-34, 151);
//        LatLng stoke = new LatLng(53.0027,2.1794);
//        LatLng center = new LatLng(0,0);
//
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").snippet("Test Snippet inserting text").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(stoke));
//        try
//        {
//            mMap.setMyLocationEnabled(true);
//        }catch (SecurityException SE)
//        {
//            Log.e(TAG, "Requires Location Permissions to be enabled manually");
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(center));
//        }
//
//    }
//
////    private boolean isLocationEnabled() {
////        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
////                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//}
