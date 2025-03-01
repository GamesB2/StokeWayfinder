
package com.example.w028006g.regnlogin;

import android.text.format.DateUtils;

import com.google.android.gms.location.Geofence;

import java.util.HashMap;

public class SimpleGeofenceStore {
	private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
	public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = GEOFENCE_EXPIRATION_IN_HOURS
			* DateUtils.HOUR_IN_MILLIS;
	protected HashMap<String, SimpleGeofence> geofences = new HashMap<String, SimpleGeofence>();
	private static SimpleGeofenceStore instance = new SimpleGeofenceStore();

	public static SimpleGeofenceStore getInstance() {
		return instance;
	}

	private SimpleGeofenceStore() {
		geofences.put("The Shire", new SimpleGeofence("The Shire", 53.010054, -2.180195,
				100, GEOFENCE_EXPIRATION_IN_MILLISECONDS,
				Geofence.GEOFENCE_TRANSITION_ENTER
						| Geofence.GEOFENCE_TRANSITION_EXIT));
	}

	public HashMap<String, SimpleGeofence> getSimpleGeofences() {
		return this.geofences;
	}
}
