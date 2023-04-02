package com.doc24x7.doctor.Notification.service;

import android.annotation.SuppressLint;
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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.doc24x7.doctor.RTM.activity.MessageActivity;
import com.doc24x7.doctor.RTM.utils.MessageUtil;
import com.doc24x7.doctor.Utils.UtilMethods;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.doc24x7.doctor.Dashbord.ui.MainActivity;
import com.doc24x7.doctor.Notification.util.NotificationUtils;
import com.doc24x7.doctor.R;
import com.google.gson.Gson;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @SuppressLint("SuspiciousIndentation")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {

        Log.e("documentSnapshot", "remoteMessage        " + remoteMessage);
        Log.e("remoteMessage", "remoteMessage        " + new Gson().toJson(remoteMessage.getData().get("body")));
        Log.e("remoteMessage", "tftft        " + remoteMessage.getData().get("body").contains("activityType"));
        Intent i;


        String str = "";
        boolean checknoti;
        checknoti=remoteMessage.getData().get("body").contains("activityType");

        Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_launcher);
        CallModel callModel = null;
        ModelNotification modelNotification = null;
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        try {
            modelNotification = new Gson().fromJson(remoteMessage.getData().get("body"), ModelNotification.class);
           // checknoti=remoteMessage.getData().get("body").contains("activityType");
            Log.e("remoteMessage", "checknoti        " + checknoti);

            if(remoteMessage.getData().get("body").toString().equalsIgnoreCase("Hello Dr!!. You Have Received a Appointment From A Patient"))
            {
                callModel=null;
            }
            else{



                if(checknoti)
                {
                    Log.d("fgjhh","dfdfgggj");
                    callModel = new Gson().fromJson(remoteMessage.getData().get("body"), CallModel.class);
                }
                else {
                    Log.d("fgjhh","dfgj");
                    if (modelNotification.getRequest_id() != null)
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                        {
                            Log.d("fgjhh","dfgj1");
                            Intent serviceIntent = new Intent(getApplicationContext(), HeadsUpNotificationService.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString("inititator", "name");
                            mBundle.putString("call_type", "Video");
                            mBundle.putString("userId", modelNotification.getUserId());
                            mBundle.putString("request_id", modelNotification.getRequest_id());
                            serviceIntent.putExtras(mBundle);
                            ContextCompat.startForegroundService(getApplicationContext(), serviceIntent);
                        }
                        else
                        {
                            Log.d("fgjhh","dfgj2");
                            Intent serviceIntent = new Intent(getApplicationContext(), HeadsUpNotificationService.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString("inititator", "name");
                            mBundle.putString("call_type", "Video");
                            mBundle.putString("userId", modelNotification.getUserId());
                            mBundle.putString("request_id", modelNotification.getRequest_id());
                            serviceIntent.putExtras(mBundle);
                            getApplicationContext().startService(serviceIntent);
                        }

                    }
                }


            }

         //  Log.d("hgjdshgfsdgfdg: ",remoteMessage.getData().get("body").toString());
        } catch (Exception e) {
            Log.e("gyyhg",e.getMessage()+"" );
        }


        if (callModel!=null)
        {   Log.d("fgjhh","dfgjghkfdghkjfdhj");
            i = new Intent(getApplicationContext(), MessageActivity.class);
                i.putExtra(MessageUtil.INTENT_EXTRA_IS_PEER_MODE, true);
                i.putExtra(MessageUtil.INTENT_EXTRA_TARGET_NAME, callModel.getTarget());
                i.putExtra(MessageUtil.INTENT_EXTRA_USER_ID, callModel.getUserId());
                UtilMethods.INSTANCE.GetRTMAccessToken(getApplicationContext(), callModel.getTarget(), callModel.getUserId(), null, i);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "channel_id")
                        .setContentTitle(remoteMessage.getData().get("title"))
                        .setContentText(str)
                        .setAutoCancel(true)
                        .setSound(Uri.parse("android.resource://" + getPackageName() + "/raw/incoming"))
                        //.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                        .setContentIntent(pendingIntent)
                        .setContentInfo(remoteMessage.getData().get("message"))
                        .setLargeIcon(icon)
                        .setColor(Color.RED)
                        .setColorized(true)
                        .setColor(getApplicationContext().getResources().getColor(R.color.white))
                        .setLights(Color.WHITE, 1000, 300)
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setSmallIcon(R.mipmap.ic_launcher);
                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                // Notification Channel is required for Android O and above
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(
                            "channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT
                    );
                    channel.setDescription("channel description");
                    channel.setShowBadge(true);
                    channel.canShowBadge();
                    channel.enableLights(true);
                    channel.setLightColor(Color.RED);
                    channel.enableVibration(true);
                    channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
                    notificationManager.createNotificationChannel(channel);
                }
                notificationManager.notify(0, notificationBuilder.build());
            }
        else {
            Log.d("fgjhh","dfgrtiu5767j");
            Intent callDialogAction = new Intent(getApplicationContext(), CallNotificationActionReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
//        PendingIntent receiveCallPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1200, receiveCallAction, PendingIntent.FLAG_UPDATE_CURRENT);
//        PendingIntent cancelCallPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1201, cancelCallAction, PendingIntent.FLAG_UPDATE_CURRENT);
//        PendingIntent callDialogPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1202, callDialogAction, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "channel_id")
                        .setContentTitle(remoteMessage.getData().get("title"))
                        .setContentText(remoteMessage.getData().get("title"))
                        .setAutoCancel(true)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pendingIntent)
                        .setContentInfo(remoteMessage.getData().get("message"))
                        .setLargeIcon(icon)
                        .setColor(Color.RED)
                        .setColorized(true)
                        .setColor(getApplicationContext().getResources().getColor(R.color.white))
                        .setLights(Color.WHITE, 1000, 300)
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setCategory(NotificationCompat.CATEGORY_CALL)
                        .setAutoCancel(true)
                        //.setSound(ringUri)
                        .setSmallIcon(R.mipmap.ic_launcher);
                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                // Notification Channel is required for Android O and above
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT
                    );
                    channel.setDescription("channel description");
                    channel.setShowBadge(true);
                    channel.canShowBadge();
                    channel.enableLights(true);
                    channel.setLightColor(Color.RED);
                    channel.enableVibration(true);
                    channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
                    notificationManager.createNotificationChannel(channel);
                }

                notificationManager.notify(0, notificationBuilder.build());


            }



    }


}

