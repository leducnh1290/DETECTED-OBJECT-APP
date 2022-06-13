package com.leducanh.main.Notice;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.leducanh.main.DetectedObject.R;

public class Notification extends Application {
    public static  String CHANNEL_ID = "channel_1";
    public static  String CHANNEL_ID2 = "channel_2";
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }
    public void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.Push_noti_name);
            String description = "Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
          // channel name 2
            CharSequence name2 = getString(R.string.Push_noti_name2);
            String description2 = "Notification2";
            int importance2 = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel2 = new NotificationChannel(CHANNEL_ID2, name2, importance2);
            channel.setDescription(description2);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if(notificationManager!=null)
            notificationManager.createNotificationChannel(channel);
            notificationManager.createNotificationChannel(channel2);
        }
    }
}
