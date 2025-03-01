package com.example.w028006g.regnlogin;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.support.annotation.IntDef;
import android.widget.Toast;

import com.example.w028006g.regnlogin.activity.MapsActivityNew;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

public class GeolocationService extends Service implements ConnectionCallbacks,
        OnConnectionFailedListener, LocationListener, ResultCallback<Status> {
	public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
	public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 5;
	protected GoogleApiClient mGoogleApiClient;
	protected LocationRequest mLocationRequest;
    private static Location userLocation;
	private static LatLng userLatLng;
	private PendingIntent mPendingIntent;

	LocationManager locationManager;

	@Override
	public void onStart(Intent intent, int startId) {
		buildGoogleApiClient();
		mGoogleApiClient.connect();

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

	}

	@Override
	public int onStartCommand(Intent intent,  int flags, int startId)
	{
		return super.onStartCommand(intent, flags, START_STICKY);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}

	}


	protected void registerGeofences() {
		if (MapsActivityNew.geofencesAlreadyRegistered) {
			return;
		}

		//Log.d(MapsActivityNew.TAG, "Registering Geofences");

		HashMap<String, SimpleGeofence> geofences = SimpleGeofenceStore.getInstance().getSimpleGeofences();

		GeofencingRequest.Builder geofencingRequestBuilder = new GeofencingRequest.Builder();
		for (Map.Entry<String, SimpleGeofence> item : geofences.entrySet()) {
			SimpleGeofence sg = item.getValue();

			geofencingRequestBuilder.addGeofence(sg.toGeofence());
		}

		GeofencingRequest geofencingRequest = geofencingRequestBuilder.build();

		mPendingIntent = requestPendingIntent();
		if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == PackageManager.PERMISSION_GRANTED) {
			LocationServices.GeofencingApi.addGeofences(mGoogleApiClient, geofencingRequest, mPendingIntent).setResultCallback(this);
		} else {
			isLocationEnabled();
		}


		MapsActivityNew.geofencesAlreadyRegistered = true;
	}

	private PendingIntent requestPendingIntent() {

		if (null != mPendingIntent) {

			return mPendingIntent;
		} else {

			Intent intent = new Intent(this, GeofenceReceiver.class);
			return PendingIntent.getService(this, 0, intent,
					PendingIntent.FLAG_UPDATE_CURRENT);

		}
	}

	public void broadcastLocationFound(Location location) {
		Intent intent = new Intent("me.hoen.geofence_21.geolocation.service");
		intent.putExtra("latitude", location.getLatitude());
		intent.putExtra("longitude", location.getLongitude());
		intent.putExtra("done", 1);

		sendBroadcast(intent);
	}

	protected void startLocationUpdates() {
		if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == PackageManager.PERMISSION_GRANTED) {
			LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
		} else {
			isLocationEnabled();
		}
	}

	protected void stopLocationUpdates() {
		LocationServices.FusedLocationApi.removeLocationUpdates(
				mGoogleApiClient, this);
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		//Log.i(MainActivity.TAG, "Connected to GoogleApiClient");
		if (mGoogleApiClient.isConnected()) {
			startLocationUpdates();
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		//Log.d(MainActivity.TAG,"new location : " + location.getLatitude() + ", " + location.getLongitude() + ". " + location.getAccuracy());
        userLocation = location;
		userLatLng = new LatLng(userLocation.getLatitude(),userLocation.getLongitude());
		broadcastLocationFound(location);

		if (!MapsActivityNew.geofencesAlreadyRegistered) {
			registerGeofences();
		}
	}

	@Override
	public void onConnectionSuspended(int cause) {
		//Log.i(MainActivity.TAG, "Connection suspended");
		mGoogleApiClient.connect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		//Log.i(MainActivity.TAG,"Connection failed: ConnectionResult.getErrorCode() = "+ result.getErrorCode());
	}

	protected synchronized void buildGoogleApiClient() {
		//Log.i(MainActivity.TAG, "Building GoogleApiClient");
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API).build();
		createLocationRequest();
	}

	protected void createLocationRequest() {
		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
		mLocationRequest
				.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public void onResult(Status status) {
		if (status.isSuccess()) {
//			Toast.makeText(getApplicationContext(),
//					"", Toast.LENGTH_SHORT)
//					.show();
		} else {
			MapsActivityNew.geofencesAlreadyRegistered = false;
			String errorMessage = getErrorString(this, status.getStatusCode());
		}
	}

	public static String getErrorString(Context context, int errorCode) {
		Resources mResources = context.getResources();
		switch (errorCode) {
		case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
			return "Not Available";
		case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
			return "Too Many Geofences";
		case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
			return "Too Many Pending Geofences";
		default:
			return "Unknown Error";
		}
	}


	private boolean isLocationEnabled() {
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
				locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}

	public static LatLng getLatLng()
    {
        //LatLng userLatLng = new LatLng(userLocation.getLatitude(),userLocation.getLongitude());
        //return userLatLng;
		LatLng userLatLng = new LatLng(53.02,-2.32);
        return userLatLng;
    }

}
