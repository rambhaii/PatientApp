package com.doc24x7.doctor.Dashbord.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.doc24x7.doctor.DoctorDashboad.SchedulingFragmentOffline;
import com.doc24x7.doctor.Utils.UtilMethods;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.doc24x7.doctor.DoctorDashboad.SchedulingFragment;
import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.GooglePlayStoreAppVersionNameLoader;
import com.rampo.updatechecker.UpdateChecker;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener , View.OnClickListener {

    String version = "";
    String versionName = "";
    int versionCode;
    CircleImageView userpic;


    ImageView home_im,offline_im,Appointmenthistory_im, Setting_im;
    TextView home_tv,offline_tv,Appointmenthistory_tv, Setting_tv;
    LinearLayout home_li,offline_li,Appointmenthistory_li, Setting_li;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);



        home_im=findViewById(R.id.home_im);
        offline_im=findViewById(R.id.offline_im);
        Appointmenthistory_im=findViewById(R.id.Appointmenthistory_im);
        Setting_im=findViewById(R.id.Setting_im);

        home_li=findViewById(R.id.home_li);
        offline_li=findViewById(R.id.offline_li);
        Appointmenthistory_li=findViewById(R.id.Appointmenthistory_li);
        Setting_li=findViewById(R.id.Setting_li);


        home_li.setOnClickListener(this);
        offline_li.setOnClickListener(this);
        Appointmenthistory_li.setOnClickListener(this);
        Setting_li.setOnClickListener(this);


        home_tv=findViewById(R.id.home_tv);
        offline_tv=findViewById(R.id.offline_tv);
        Appointmenthistory_tv=findViewById(R.id.Appointmenthistory_tv);
        Setting_tv=findViewById(R.id.Setting_tv);

        home_im.setOnClickListener(this);
        offline_im.setOnClickListener(this);
        Appointmenthistory_im.setOnClickListener(this);
        Setting_im.setOnClickListener(this);

        try {
            String a =getIntent().getStringExtra("a").toString();
            new AlertDialog.Builder(this).setMessage(a).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        startService();

        version = GooglePlayStoreAppVersionNameLoader.newVersion;

        getVersionInfo();

     //   PopUpdate();


        //loading the default fragment

        SetColour();
        home_im.setColorFilter(ContextCompat.getColor(this, R.color.appColor), android.graphics.PorterDuff.Mode.SRC_IN);
        home_tv.setTextColor(getResources().getColor(R.color.appColor));


        loadFragment(new DashboadFragment());

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);


    }

    private void setstatusonline() {

        UtilMethods.INSTANCE.setonlinestatus(MainActivity.this);

    }

    private void PopUpdate() {

        Log.e("version", "    versionName    " + versionName + "  version    " + version);

        if (version != null && !version.equalsIgnoreCase("")) {

            if (!versionName.equalsIgnoreCase(version)) {
                OpenUpdateDialog();

            }

        }
    }

    public void OpenUpdateDialog() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.update_available_pop, null);

        TextView tvLater = (TextView) view.findViewById(R.id.tv_later);
        TextView tvOk = (TextView) view.findViewById(R.id.tv_ok);

        final Dialog dialog = new Dialog(this);

        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tvLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMarket(MainActivity.this);
                dialog.dismiss();
                //UtilMethods.INSTANCE.goAnotherActivity((Activity) context,Splash.class);
            }
        });

        dialog.show();
    }

    private static void goToMarket(Context mContext) {
        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(UpdateChecker.ROOT_PLAY_STORE_DEVICE + mContext.getPackageName())));
    }

    private void getVersionInfo() {

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;

            Log.e("versionnn", "   versionName   " + versionName + "   versionCode  " + versionCode + "   version  " + version);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {

        if(view==home_li){

            loadFragment(new DashboadFragment());

            SetColour();
            home_im.setColorFilter(ContextCompat.getColor(this, R.color.appColor), android.graphics.PorterDuff.Mode.SRC_IN);
            home_tv.setTextColor(getResources().getColor(R.color.appColor));


        }

        if(view==offline_li){

            loadFragment(new SchedulingFragmentOffline());

            SetColour();
            offline_im.setColorFilter(ContextCompat.getColor(this, R.color.appColor), android.graphics.PorterDuff.Mode.SRC_IN);
            offline_tv.setTextColor(getResources().getColor(R.color.appColor));


        }

        if(view==Appointmenthistory_li){

            loadFragment(new SchedulingFragment());
            SetColour();
            Appointmenthistory_im.setColorFilter(ContextCompat.getColor(this, R.color.appColor), android.graphics.PorterDuff.Mode.SRC_IN);
            Appointmenthistory_tv.setTextColor(getResources().getColor(R.color.appColor));


        }

        if(view==Setting_li){

            loadFragment(new ProfileFragment());

            SetColour();
            Setting_im.setColorFilter(ContextCompat.getColor(this, R.color.appColor), android.graphics.PorterDuff.Mode.SRC_IN);
            Setting_tv.setTextColor(getResources().getColor(R.color.appColor));


        }

    }

    private void SetColour() {

        home_im.setColorFilter(ContextCompat.getColor(this, R.color.blackdask), android.graphics.PorterDuff.Mode.SRC_IN);
        offline_im.setColorFilter(ContextCompat.getColor(this, R.color.blackdask), android.graphics.PorterDuff.Mode.SRC_IN);
        Appointmenthistory_im.setColorFilter(ContextCompat.getColor(this, R.color.blackdask), android.graphics.PorterDuff.Mode.SRC_IN);
        Setting_im.setColorFilter(ContextCompat.getColor(this, R.color.blackdask), android.graphics.PorterDuff.Mode.SRC_IN);

        home_tv.setTextColor(getResources().getColor(R.color.blackdask));
        offline_tv.setTextColor(getResources().getColor(R.color.blackdask));
        Appointmenthistory_tv.setTextColor(getResources().getColor(R.color.blackdask));
        Setting_tv.setTextColor(getResources().getColor(R.color.blackdask));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {


            case R.id.home:

                fragment = new DashboadFragment();

                break;

            case R.id.Appointmenthistory:

                fragment = new SchedulingFragment();

                break;


            case R.id.offline:

                fragment = new SchedulingFragmentOffline();

                break;

            case R.id.Setting:

                fragment = new ProfileFragment();

                break;


        }


        return loadFragment(fragment);

    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        UtilMethods.INSTANCE.setoflinestatus(getApplicationContext());
        super.onDestroy();
    }

    public void startService() {
        startService(new Intent(getBaseContext(), StatusServices.class));
    }



//    // Method to stop the service
//    public void stopService() {
//        stopService(new Intent(getBaseContext(), StatusServices.class));
//    }




}


