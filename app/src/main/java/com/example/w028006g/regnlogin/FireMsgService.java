package com.example.w028006g.regnlogin;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FireMsgService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = "Wayfarer";
    String lat;
    String lon;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        final Map<String, String> data = remoteMessage.getData();
        if (!data.containsKey("NoCoord")) {
            String datastring = data.toString();
            String[] parts = datastring.split("\\=");
            StringBuilder builder = new StringBuilder(parts[0]);
            StringBuilder builder1 = new StringBuilder(parts[1]);
            builder.deleteCharAt(0);
            builder1.deleteCharAt(builder1.length() - 1);
            lat = builder.toString();
            lon = builder1.toString();
        }


        String click_action = remoteMessage.getNotification().getClickAction();

        Map<String, String> action = remoteMessage.getData();
        if (action.containsKey("click_action")) {
            ClickActionHelper.startActivity(action.get("click_action"), null, this);
        }

        //Calling method to generate notification
        sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), lat, lon, data);
    }

    //This method is only generating push notification
    private void sendNotification(String title, String messageBody, String lat, String lon, Map data) {
        Intent intent = new Intent(this, MapsActivityNew.class);
        if (!data.containsKey("NoCoord")) {
            intent.putExtra("Latitude", lat);
            intent.putExtra("Longitude", lon);
        }
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.lock)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.lock));

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}