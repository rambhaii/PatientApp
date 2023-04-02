package com.doc24x7.assistant.Notification.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.doc24x7.assistant.Dashbord.ui.MainActivity;
import com.doc24x7.assistant.Notification.util.NotificationUtils;
import com.doc24x7.assistant.R;
import com.google.gson.Gson;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//
//        Log.e("documentSnapshot", "remoteMessage        " + remoteMessage);
//        Log.e("remoteMessage", "remoteMessage        " + new Gson().toJson(remoteMessage.getData().get("body")));
//        String str = "";
//        Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_launcher);
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        ModelNotification modelNotification = new Gson().fromJson(remoteMessage.getData().get("body"), ModelNotification.class);
//        if (modelNotification != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                Intent serviceIntent = new Intent(getApplicationContext(), HeadsUpNotificationService.class);
//                Bundle mBundle = new Bundle();
//                mBundle.putString("inititator", "name");
//                mBundle.putString("call_type","Video");
//                mBundle.putString("userId", modelNotification.getUserId());
//                mBundle.putString("request_id", modelNotification.getRequest_id());
//                serviceIntent.putExtras(mBundle);
//                ContextCompat.startForegroundService(getApplicationContext(), serviceIntent);
//            }else{
//                Intent serviceIntent = new Intent(getApplicationContext(), HeadsUpNotificationService.class);
//                Bundle mBundle = new Bundle();
//                mBundle.putString("inititator", "name");
//                mBundle.putString("call_type","Video");
//                mBundle.putString("userId", modelNotification.getUserId());
//                mBundle.putString("request_id", modelNotification.getRequest_id());
//                serviceIntent.putExtras(mBundle);
//                getApplicationContext().startService(serviceIntent);
//            }
//        }
////        Intent receiveCallAction = new Intent(getApplicationContext(), CallNotificationActionReceiver.class);
////        Bundle mBundle = intent.getExtras();
////
////        receiveCallAction.putExtra("userId", modelNotification.getUserId());
////        receiveCallAction.putExtra("request_id", modelNotification.getUserId());
////        receiveCallAction.putExtra("ConstantApp.CALL_RESPONSE_ACTION_KEY", "ConstantApp.CALL_RECEIVE_ACTION");
////        receiveCallAction.putExtra("ACTION_TYPE", "RECEIVE_CALL");
////        receiveCallAction.putExtra("NOTIFICATION_ID", 121);
////        receiveCallAction.setAction("RECEIVE_CALL");
////        receiveCallAction.setClass(this, CallNotificationActionReceiver.class);
////
////        Intent cancelCallAction = new Intent(getApplicationContext(), CallNotificationActionReceiver.class);
////        cancelCallAction.putExtra("ConstantApp.CALL_RESPONSE_ACTION_KEY", "ConstantApp.CALL_CANCEL_ACTION");
////        cancelCallAction.putExtra("ACTION_TYPE", "CANCEL_CALL");
////        cancelCallAction.putExtra("NOTIFICATION_ID", 121);
////        cancelCallAction.setAction("CANCEL_CALL");
////        cancelCallAction.setClass(this, CallNotificationActionReceiver.class);
////
////        Intent callDialogAction = new Intent(getApplicationContext(), CallNotificationActionReceiver.class);
//      PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
////        PendingIntent receiveCallPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1200, receiveCallAction, PendingIntent.FLAG_UPDATE_CURRENT);
////        PendingIntent cancelCallPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1201, cancelCallAction, PendingIntent.FLAG_UPDATE_CURRENT);
////        PendingIntent callDialogPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1202, callDialogAction, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "channel_id")
//                .setContentTitle(remoteMessage.getData().get("title"))
//                .setContentText("Patient is waiting please accept")
//                .setAutoCancel(true)
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//                .setContentIntent(pendingIntent)
//                .setContentInfo(remoteMessage.getData().get("message"))
//                .setLargeIcon(icon)
//                .setColor(Color.RED)
//                .setColorized(true)
//                .setColor(getApplicationContext().getResources().getColor(R.color.white))
//                .setLights(Color.WHITE, 1000, 300)
//                .setDefaults(Notification.DEFAULT_VIBRATE)
//                .setPriority(NotificationCompat.PRIORITY_MAX)
//                .setCategory(NotificationCompat.CATEGORY_CALL)
//                .setAutoCancel(true)
//                //.setSound(ringUri)
//                .setSmallIcon(R.mipmap.ic_launcher);
//        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//
//        // Notification Channel is required for Android O and above
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(
//                    "channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT
//            );
//            channel.setDescription("channel description");
//            channel.setShowBadge(true);
//            channel.canShowBadge();
//            channel.enableLights(true);
//            channel.setLightColor(Color.RED);
//            channel.enableVibration(true);
//            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        notificationManager.notify(0, notificationBuilder.build());
//
//
//    }


}

