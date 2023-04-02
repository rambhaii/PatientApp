package com.doc24x7.doctor.Notification.service;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.doc24x7.doctor.Dashbord.ui.MainActivity;
import com.doc24x7.doctor.Login.dto.Data;
import com.doc24x7.doctor.Utils.ApplicationConstant;
import com.doc24x7.doctor.Utils.UtilMethods;
import com.doc24x7.doctor.Video.CallActivity;
import com.doc24x7.doctor.Video.model.ConstantApp;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class CallNotificationActionReceiver extends BroadcastReceiver {


    Context mContext;




    @Override
    public void onReceive(Context context, Intent intent)
    {
        this.mContext=context;
        if (intent != null && intent.getExtras() != null) {

            String action ="";
            action=intent.getStringExtra("ACTION_TYPE");
            Log.d("dfhgjdf","dfjhjg");
            if (action != null&& !action.equalsIgnoreCase(""))
            {

                performClickAction(context, action,intent);
            }

            // Close the notification after the click action is performed.
            Intent iclose = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            context.sendBroadcast(iclose);
            context.stopService(new Intent(context, HeadsUpNotificationService.class));

        }


    }
    private void performClickAction(Context context, String action, Intent intent)
    {
        if(action.equalsIgnoreCase("RECEIVE_CALL")) {
            SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
            Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
            String doctor_id = balanceCheckResponse.getId();
           Log.e("dasdasdasdasdas",intent.getExtras().getString("request_id"));
            FirebaseFirestore.getInstance().collection("OnlineRequest").document(intent.getExtras().getString("request_id"))
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        if(documentSnapshot.get("Status").toString().equals("false")){
                            HashMap<String, Object> value=new HashMap<>();
                            value.put("DoctorId",doctor_id);
                            value.put("userId",intent.getExtras().getString("request_id"));
                            value.put("Status",true);
                            FirebaseFirestore.getInstance().collection("OnlineRequest").document(intent.getExtras().getString("request_id"))
                                    .update(value).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent i = new Intent(context, MainActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    i.putExtra("a"," accepted");
                                    context.startActivity(i);
                                }
                            });
                        } else{
                            Intent i = new Intent(context, MainActivity.class);
                            i.putExtra("a","Already accepted");
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }
                    } else {
                    }
                }
            });

        }
        else if(action.equalsIgnoreCase("CANCEL_CALL")){

            Intent i = new Intent(context, MainActivity.class);
            i.putExtra("a","Rejected");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(i);

        }

        else {
            context.stopService(new Intent(context, HeadsUpNotificationService.class));
            Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            context.sendBroadcast(it);
        }
    }

    private Boolean checkAppPermissions() {
        return hasReadPermissions() && hasWritePermissions() && hasCameraPermissions() && hasAudioPermissions();
    }

    private boolean hasAudioPermissions() {
        return (ContextCompat.checkSelfPermission(mContext, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }
    private boolean hasCameraPermissions() {
        return (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }
}