package com.leducanh.main.Notice;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leducanh.main.DataBase.DataBase;
import com.leducanh.main.DetectedObject.R;

import java.util.Date;

public class ServicePushNoti extends Service {
    Bitmap bmp ;
    public static int a = 0;
    DatabaseReference myRef = DataBase.myRef;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("service","push");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ischool);
        a = 1;
        check();
        startForeground(getNotiID(),
                new CreatedNoti().creatednotice(this,
                        "Cảnh báo","Ứng dụng cần chạy nền để có thể gửi cảnh báo tới bạn.",bmp,Notification.CHANNEL_ID2));
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.e("11","tat");
        super.onDestroy();
    }
    public void check(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()!=null) {
                    pushNoti("Thông Báo",snapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public int getNotiID(){
        return (int) new Date().getTime();
    }
    public void pushNoti(String title,String message){
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
// notificationId is a unique int for each notification that you must define
        notificationManager.notify(getNotiID(),new CreatedNoti().creatednotice(this,title,message,bmp,Notification.CHANNEL_ID));
    }
}
