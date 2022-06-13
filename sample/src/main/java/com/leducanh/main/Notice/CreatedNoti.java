package com.leducanh.main.Notice;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import androidx.core.app.NotificationCompat;

import com.leducanh.main.DetectedObject.R;
import com.leducanh.main.DetectedObject.SampleActivity;

import java.util.Date;

public class CreatedNoti {
    public android.app.Notification creatednotice(Context context, String title, String message, Bitmap bmp,String id){
        Intent intent = new Intent(context, SampleActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
       android.app.Notification notification = new NotificationCompat.Builder(context,id)
                .setContentTitle(title)
                .setContentText(message)
               .setContentIntent(pendingIntent)
                .setWhen(new Date().getTime())
                .setSmallIcon(R.drawable.ischool)
                .setLargeIcon(bmp)
                .build();
       return notification;
    }
}
