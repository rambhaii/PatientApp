package com.doc24x7.doctor.DoctorDashboad;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doc24x7.doctor.Dashbord.dto.Datum;
import com.doc24x7.doctor.Dashbord.dto.GalleryListResponse;
import com.doc24x7.doctor.Dashbord.ui.DoctorListDashboadAdapter;
import com.doc24x7.doctor.Utils.Loader;
import com.doc24x7.doctor.Utils.UtilMethods;
import com.google.gson.Gson;
import com.doc24x7.doctor.Login.dto.Data;
import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.ApplicationConstant;
import com.doc24x7.doctor.Utils.FragmentActivityMessage;
import com.doc24x7.doctor.Utils.GlobalBus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class DOctoeByIdActivity extends AppCompatActivity {
    RecyclerView recycler_view_doctor;
    String response = "";
    String type = "";
    String pa_name = "";
    String pa_mobile = "";
    String Appointment_id = "";
    String useridpref = "";
    String status="";
    String doctorDetail = "";
    LinearLayout tabs;
    public TextView name, specialities, address, qualification,online_tab,offline_tabs;
    LinearLayout Logout;
    TextView contentnumber;
    ImageView opImage;
    LinearLayout lifirst;
    String profilePIC;
    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("Doctorsres")) {
            SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(ApplicationConstant.INSTANCE.Appointment, activityFragmentMessage.getFrom());
            editor.commit();


            dataParsedoctorbook(response);



        } else if (activityFragmentMessage.getMessage().equalsIgnoreCase("online_tabs_patient") && type.equals("8")) {
            SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(ApplicationConstant.INSTANCE.Appointment, activityFragmentMessage.getFrom());
            editor.commit();
            dataParsePatientDetailss(activityFragmentMessage.getFrom(),"1");

        }
        else if (activityFragmentMessage.getMessage().equalsIgnoreCase("offline_tabs_patient") && type.equals("8")) {
            SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(ApplicationConstant.INSTANCE.Appointment, activityFragmentMessage.getFrom());
            editor.commit();
            dataParsePatientDetailss(activityFragmentMessage.getFrom(),"2");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_octoe_by_id);
        recycler_view_doctor = findViewById(R.id.recycler_view_doctor);
     //   response = getIntent().getStringExtra("response");
        status=getIntent().getStringExtra("status");
        type = getIntent().getStringExtra("type");
        pa_name = getIntent().getStringExtra("pa_name");
        pa_mobile = getIntent().getStringExtra("pa_mobile");
      //  Appointment_id = getIntent().getStringExtra("Appointment_id");
        useridpref = getIntent().getStringExtra("useridpref");

        tabs=findViewById(R.id.tabs);
        online_tab=findViewById(R.id.online_tab);
        offline_tabs=findViewById(R.id.offline_tab);
        profilePIC = getIntent().getStringExtra("profilePIC");
       if(type.equals("8"))
       {
           Loader loader;
           loader = new Loader(DOctoeByIdActivity.this, android.R.style.Theme_Translucent_NoTitleBar);

           if (UtilMethods.INSTANCE.isNetworkAvialable(DOctoeByIdActivity.this)) {

               loader.show();
               loader.setCancelable(false);
               loader.setCanceledOnTouchOutside(false);
               UtilMethods.INSTANCE.DrPatientDetails(DOctoeByIdActivity.this, useridpref,loader);

           } else {
               UtilMethods.INSTANCE.NetworkError(DOctoeByIdActivity.this, getResources().getString(R.string.network_error_title),
                       getResources().getString(R.string.network_error_message));
           }
           tabs.setVisibility(View.VISIBLE);
       }

       online_tab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               offline_tabs.setBackground(getResources().getDrawable(R.drawable.rect));
               offline_tabs.setTextColor(getResources().getColor(R.color.black));
               online_tab.setBackground(getResources().getDrawable(R.drawable.rect_blue));
               online_tab.setTextColor(getResources().getColor(R.color.white));
         Loader loader;
           loader = new Loader(DOctoeByIdActivity.this, android.R.style.Theme_Translucent_NoTitleBar);

           if (UtilMethods.INSTANCE.isNetworkAvialable(DOctoeByIdActivity.this)) {

               loader.show();
               loader.setCancelable(false);
               loader.setCanceledOnTouchOutside(false);
               UtilMethods.INSTANCE.DrPatientDetails(DOctoeByIdActivity.this, useridpref,loader);

           } else {
               UtilMethods.INSTANCE.NetworkError(DOctoeByIdActivity.this, getResources().getString(R.string.network_error_title),
                       getResources().getString(R.string.network_error_message));
           }
           tabs.setVisibility(View.VISIBLE);
           }
       });
       offline_tabs.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               online_tab.setBackground(getResources().getDrawable(R.drawable.rect));
               online_tab.setTextColor(getResources().getColor(R.color.black));
               offline_tabs.setBackground(getResources().getDrawable(R.drawable.rect_blue));
               offline_tabs.setTextColor(getResources().getColor(R.color.white));
               Loader loader;
           loader = new Loader(DOctoeByIdActivity.this, android.R.style.Theme_Translucent_NoTitleBar);

           if (UtilMethods.INSTANCE.isNetworkAvialable(DOctoeByIdActivity.this)) {

               loader.show();
               loader.setCancelable(false);
               loader.setCanceledOnTouchOutside(false);
               UtilMethods.INSTANCE.DrPatientDetailsOffline(DOctoeByIdActivity.this, useridpref,loader);

           } else {
               UtilMethods.INSTANCE.NetworkError(DOctoeByIdActivity.this, getResources().getString(R.string.network_error_title),
                       getResources().getString(R.string.network_error_message));
           }
           tabs.setVisibility(View.VISIBLE);
          }
       });
        TextView toolbar = findViewById(R.id.toolbar);
        if (type.equalsIgnoreCase("6")) {

            toolbar.setText("Patient List");

        } else if (type.equalsIgnoreCase("7")) {

            toolbar.setText("Patient Details");


        } else {

            toolbar.setText("Doctor");


        }
        toolbar.setVisibility(View.GONE);

        name = findViewById(R.id.name);
        contentnumber = findViewById(R.id.contentnumber);
        SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);

        name.setText("Dr. " + balanceCheckResponse.getName());
        contentnumber.setText("" + balanceCheckResponse.getMobile());
        specialities = findViewById(R.id.specialities);
        qualification = findViewById(R.id.qualification);
        address = findViewById(R.id.address);
        opImage = findViewById(R.id.opImage);
        lifirst = findViewById(R.id.lifirst);
        lifirst.setVisibility(View.GONE);
        if (type.equalsIgnoreCase("6")) {
            response = getIntent().getStringExtra("response");
            Appointment_id = getIntent().getStringExtra("Appointment_id");
            dataParsePatientAppointment(response);
        } else if(type.equals("7")) {
            response = getIntent().getStringExtra("response");
            Appointment_id = getIntent().getStringExtra("Appointment_id");
       dataParsePatientDetail(response,status);
        }
        else if(type.equals("8"))
        {

        }


    }

    GalleryListResponse sliderImage;
    ArrayList<Datum> sliderLists = new ArrayList<>();
    DoctorListDashboadAdapter doctormAdapter;
    DoctorBookingAdapter DoctorBookingAdapterboo;
    DoctorPatientAdapter DoctorPatient;
    LinearLayoutManager mLayoutManager;


    public void dataParsePatientDetail(String response,String status) {
        Gson gson = new Gson();
        sliderImage = gson.fromJson(response, GalleryListResponse.class);
        sliderLists = sliderImage.getData();
        if (sliderLists.size() > 0) {
            doctormAdapter = new DoctorListDashboadAdapter(sliderLists, this, type, pa_name, pa_mobile,Appointment_id, useridpref,profilePIC,status);
            mLayoutManager = new LinearLayoutManager(this);
            recycler_view_doctor.setLayoutManager(mLayoutManager);
            recycler_view_doctor.setItemAnimator(new DefaultItemAnimator());
            recycler_view_doctor.setAdapter(doctormAdapter);
            recycler_view_doctor.setVisibility(View.VISIBLE);
        } else {
            recycler_view_doctor.setVisibility(View.GONE);
        }
    }
    public void dataParsePatientDetailss(String response,String status) {
        Gson gson = new Gson();
        sliderImage = gson.fromJson(response, GalleryListResponse.class);
        sliderLists = sliderImage.getData();
        if (sliderLists.size() > 0) {
            doctormAdapter = new DoctorListDashboadAdapter(sliderLists, this, type, pa_name, pa_mobile,sliderLists.get(0).getAppointment_id() , useridpref,profilePIC,status);
            mLayoutManager = new LinearLayoutManager(this);
            recycler_view_doctor.setLayoutManager(mLayoutManager);
            recycler_view_doctor.setItemAnimator(new DefaultItemAnimator());
            recycler_view_doctor.setAdapter(doctormAdapter);
            recycler_view_doctor.setVisibility(View.VISIBLE);
        } else {
            recycler_view_doctor.setVisibility(View.GONE);
        }
    }
    public void dataParsedoctorbook(String response) {

        Gson gson = new Gson();
        sliderImage = gson.fromJson(response, GalleryListResponse.class);
        sliderLists = sliderImage.getData();

        if (sliderLists.size() > 0) {
            DoctorBookingAdapterboo = new DoctorBookingAdapter(sliderLists, this);
            mLayoutManager = new LinearLayoutManager(this);
            recycler_view_doctor.setLayoutManager(mLayoutManager);
            recycler_view_doctor.setItemAnimator(new DefaultItemAnimator());
            recycler_view_doctor.setAdapter(DoctorBookingAdapterboo);
            recycler_view_doctor.setVisibility(View.VISIBLE);
        } else {
            recycler_view_doctor.setVisibility(View.GONE);
        }

    }
    public void dataParsePatientAppointment(String response) {

        Gson gson = new Gson();
        sliderImage = gson.fromJson(response, GalleryListResponse.class);
        sliderLists = sliderImage.getData();

        if (sliderLists.size() > 0) {
            DoctorPatient = new DoctorPatientAdapter(sliderLists, this, type);
            mLayoutManager = new LinearLayoutManager(this);
            recycler_view_doctor.setLayoutManager(mLayoutManager);
            recycler_view_doctor.setItemAnimator(new DefaultItemAnimator());
            recycler_view_doctor.setAdapter(DoctorPatient);
            recycler_view_doctor.setVisibility(View.VISIBLE);
        } else {
            recycler_view_doctor.setVisibility(View.GONE);
        }

    }


}