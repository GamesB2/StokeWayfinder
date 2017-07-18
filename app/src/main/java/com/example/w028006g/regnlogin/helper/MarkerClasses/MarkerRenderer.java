package com.example.w028006g.regnlogin.helper.MarkerClasses;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/**
 * Created by b016104b on 17/07/2017.
 */

public class MarkerRenderer extends DefaultClusterRenderer<POI> {

    public MarkerRenderer(Context context, GoogleMap map, ClusterManager<POI> clusterManager) {
        super(context, map, clusterManager);
        clusterManager.setRenderer(this);
    }


    @Override
    protected void onBeforeClusterItemRendered(POI item , MarkerOptions markerOptions) {
        if (item.getIconBMP() != null) {
            markerOptions.icon(item.getIconBMP()); //Here you retrieve BitmapDescriptor from ClusterItem and set it as marker icon
        }
        markerOptions.visible(true);

    }
}