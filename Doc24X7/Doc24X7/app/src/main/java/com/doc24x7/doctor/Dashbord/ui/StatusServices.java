package com.doc24x7.doctor.Dashbord.ui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.doc24x7.doctor.Utils.UtilMethods;

public class StatusServices extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
      UtilMethods.INSTANCE.setonlinestatus(getApplicationContext());

        return START_STICKY;
    }


    public void onTaskRemoved(Intent rootIntent) {

        //unregister listeners
        //do any other cleanup if required
        UtilMethods.INSTANCE.setoflinestatus(getApplicationContext());
        Toast.makeText(this, "Your ofline", Toast.LENGTH_SHORT).show();
        //stop service
        stopSelf();
    }
}
