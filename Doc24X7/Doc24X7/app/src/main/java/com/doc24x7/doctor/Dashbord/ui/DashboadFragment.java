package com.doc24x7.doctor.Dashbord.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.doc24x7.doctor.Utils.ForceUpdateChecker;
import com.google.gson.Gson;
import com.doc24x7.doctor.DoctorDashboad.AccountDetailScreen;
import com.doc24x7.doctor.DoctorDashboad.QualificationScreen;
import com.doc24x7.doctor.Login.dto.Data;
import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.ApplicationConstant;
import com.doc24x7.doctor.Utils.Loader;
import com.doc24x7.doctor.Utils.UtilMethods;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

import java.util.Date;


public class DashboadFragment extends Fragment implements View.OnClickListener, ForceUpdateChecker.OnUpdateNeededListener, CompoundButton.OnCheckedChangeListener {

    LinearLayout Logout;
    TextView name, contentnumber,tvonline,tvstatusmessage,tvtime,due_value,upcomming_appointment_value,validityvalue;
    TextView income, comission, PatientAppointment,PatientAppointment_offline, doctorappointment, total_patient, updateQuaification, updateAccDetail;
    LinearLayout income_li,validityvalue_li,upcomming_appointment_value_li, comission_li,TotalPatient_li, doctorappointment_li  ,due_value_li , PatientAppointment_li , PatientAppointment_offline_li ;
    Switch sw_status;
    Loader loader;
    CircleImageView userpic;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboad, container, false);

        ForceUpdateChecker.with(getActivity()).onUpdateNeeded(this).check();

        GetId(v);

       // getOnlinestatus();

        tvonline.setText("On");
        sw_status.setChecked(true);
        Date d = new Date();
        CharSequence s  = DateFormat.format("d MMMM, yyyy ", d.getTime());
        tvtime.setText(s);
        return v;

    }

    private void getOnlinestatus() {
        UtilMethods.INSTANCE.Getonlinestatus(getActivity());
    }

    private void GetId(View v) {
        TotalPatient_li = v.findViewById(R.id.TotalPatient_li);
        total_patient = v.findViewById(R.id.TotalPatient);
        doctorappointment = v.findViewById(R.id.doctorappointment);
        PatientAppointment_li = v.findViewById(R.id.PatientAppointment_li);
        PatientAppointment = v.findViewById(R.id.PatientAppointment);
        PatientAppointment_offline = v.findViewById(R.id.PatientAppointment_offline);
        PatientAppointment_offline_li = v.findViewById(R.id.PatientAppointment_offline_li);
        comission = v.findViewById(R.id.comission);
        tvtime=v.findViewById(R.id.tvtime);
        income = v.findViewById(R.id.income);


        due_value_li=v.findViewById(R.id.due_value_li);
        upcomming_appointment_value=v.findViewById(R.id.upcomming_appointment_value);
        upcomming_appointment_value_li=v.findViewById(R.id.upcomming_appointment_value_li);
        due_value=v.findViewById(R.id.due_value);
        validityvalue=v.findViewById(R.id.validityvalue);
        validityvalue_li=v.findViewById(R.id.validityvalue_li);

        updateQuaification = v.findViewById(R.id.updateQuaification);
        updateAccDetail = v.findViewById(R.id.updateAccDetail);
        income_li = v.findViewById(R.id.income_li);
        comission_li = v.findViewById(R.id.comission_li);
        userpic = v.findViewById(R.id.userpic);

        tvonline = v.findViewById(R.id.tvonline);
         sw_status = v.findViewById(R.id.sw_status);
        sw_status.setOnCheckedChangeListener(this);
        total_patient.setOnClickListener(this);
        TotalPatient_li.setOnClickListener(this);
        PatientAppointment.setOnClickListener(this);
        PatientAppointment_li.setOnClickListener(this);
        comission.setOnClickListener(this);
        due_value_li.setOnClickListener(this);
        validityvalue_li.setOnClickListener(this);
        PatientAppointment_offline_li.setOnClickListener(this);

        income.setOnClickListener(this);
        updateQuaification.setOnClickListener(this);
        updateAccDetail.setOnClickListener(this);
        income_li.setOnClickListener(this);
        comission_li.setOnClickListener(this);
        upcomming_appointment_value_li.setOnClickListener(this);

        contentnumber = v.findViewById(R.id.contentnumber);
        name = v.findViewById(R.id.name);
        SharedPreferences myPreferences = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        name.setText("Dr. " + balanceCheckResponse.getName());
        contentnumber.setText("Referral Code:  " + balanceCheckResponse.getReferral_code());
        Log.d("sddfgh",balanceCheckResponse.getReferral_code());


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.userprofiledash);
        requestOptions.error(R.drawable.userprofiledash);

        Glide.with(this)
                .load(""+balanceCheckResponse.getProfile_pic())
                .apply(requestOptions)
                .into(userpic);

        if (balanceCheckResponse.getQualification() != null) {
            if (balanceCheckResponse.getQualification().size() == 0) {
                updateQuaification.setVisibility(View.VISIBLE);
            } else {
                updateQuaification.setVisibility(View.GONE);
            }
        }
        if (balanceCheckResponse.getAccount_no()==null) {
            updateAccDetail.setVisibility(View.VISIBLE);
        } else {
            updateAccDetail.setVisibility(View.GONE);
        }
//        if (balanceCheckResponse.getAccount_no().si) {
//            updateAccDetail.setVisibility(View.VISIBLE);
//        } else {
//            updateAccDetail.setVisibility(View.GONE);
//        }

        loader = new Loader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);


        Logout = v.findViewById(R.id.Logout);
        Logout.setOnClickListener(this);
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.Dashboard(getActivity(), total_patient, doctorappointment, PatientAppointment, comission, income,PatientAppointment_offline,due_value,upcomming_appointment_value,validityvalue);
            UtilMethods.INSTANCE.GetMedicine(getActivity(), loader);
        } else {
            UtilMethods.INSTANCE.NetworkError(getActivity(), getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message));
        }




    }

    @Override
    public void onClick(View view) {


        if (view == income_li) {

            startActivity(new Intent(getActivity(), ViewTransaction.class));


        }

      else  if (view == PatientAppointment_li)
      {
          if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {

                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                //    UtilMethods.INSTANCE.PatientList(getActivity(),loader);
                UtilMethods.INSTANCE.GetTodayDoctorAppointment(getActivity(), loader);
          } else {
              UtilMethods.INSTANCE.NetworkError(getActivity(), getResources().getString(R.string.network_error_title),
                        getResources().getString(R.string.network_error_message));
            }


        }
        else  if (view == PatientAppointment_offline_li) {
            if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {

                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                //    UtilMethods.INSTANCE.PatientList(getActivity(),loader);
                UtilMethods.INSTANCE.GetTodayDoctorAppointmentOffline(getActivity(), loader);
            } else {
                UtilMethods.INSTANCE.NetworkError(getActivity(), getResources().getString(R.string.network_error_title),
                        getResources().getString(R.string.network_error_message));
            }


        }
        else if(view==validityvalue_li){
            if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                UtilMethods.INSTANCE.GetPatientUnderValidityDoctorAppointment(getActivity(), loader);


            } else {

                UtilMethods.INSTANCE.NetworkError(getActivity(), getResources().getString(R.string.network_error_title),
                        getResources().getString(R.string.network_error_message));
            }
        }
        else if(view==upcomming_appointment_value_li){
            if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                UtilMethods.INSTANCE.GetUpcommingDoctorAppointment(getActivity(), loader);


            } else {

                UtilMethods.INSTANCE.NetworkError(getActivity(), getResources().getString(R.string.network_error_title),
                        getResources().getString(R.string.network_error_message));
            }
        }
        else if(view==due_value_li){
            if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                UtilMethods.INSTANCE.GetDueDoctorAppointment(getActivity(), loader);


            } else {

                UtilMethods.INSTANCE.NetworkError(getActivity(), getResources().getString(R.string.network_error_title),
                        getResources().getString(R.string.network_error_message));
            }
        }
       else if (view == TotalPatient_li) {

            if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                  UtilMethods.INSTANCE.PatientList(getActivity(),loader);


            } else {

                UtilMethods.INSTANCE.NetworkError(getActivity(), getResources().getString(R.string.network_error_title),
                        getResources().getString(R.string.network_error_message));
            }
       } else if (view == updateQuaification) {
            startActivity(new Intent(getActivity(), QualificationScreen.class));
        } else if (view == updateAccDetail) {
        //   Intent i=new Intent(getActivity(),AccountDetailScreen.class);
        startActivity(new Intent(getActivity(), AccountDetailScreen.class));
        } else if (view == Logout) {


            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewpop = inflater.inflate(R.layout.logout_pop, null);

            Button okButton = (Button) viewpop.findViewById(R.id.okButton);
            Button Cancel = (Button) viewpop.findViewById(R.id.Cancel);
            TextView msg = (TextView) viewpop.findViewById(R.id.msg);

            final Dialog dialog = new Dialog(getActivity());

            dialog.setCancelable(false);
            dialog.setContentView(viewpop);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    UtilMethods.INSTANCE.logout(getActivity());

                    dialog.dismiss();

                }
            });

            Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });

            dialog.show();


           /* final SweetAlertDialog alertDialog = new SweetAlertDialog(getActivity());
            alertDialog.setTitle("Alert!");
            alertDialog.setContentText("Do you want to logout?");
            alertDialog.setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    alertDialog.dismiss();
                }
            });

            alertDialog.setConfirmButton("Yes", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {

                    UtilMethods.INSTANCE.logout(getActivity());

                }
            });

            alertDialog.show();*/

        }

    }

    @Override
    public void onUpdateNeeded(final String updateUrl) {

        Log.e("updateUrl",""+ updateUrl);

        if (updateUrl.equals("")) {

        } else {
            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle("New version available")
                    .setCancelable(false)
                    .setMessage("Please, update our app to new version to continue for better experiance.")
                    .setPositiveButton("Update",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    redirectStore(updateUrl);
                                }
                            })
                    .create();
            dialog.show();



        }
    }


        private void redirectStore(String updateUrl) {
            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView==sw_status) {
            if (isChecked) {
               setstatusonline();
               tvonline.setText("ON");
            } else {
                setstatusofline();
                tvonline.setText("OFF");
            }
        }

    }

    @Override
    public void onDestroy() {
       
        super.onDestroy();
    }

    private void setstatusofline() {
        UtilMethods.INSTANCE.setoflinestatus(getActivity());

    }

    private void setstatusonline() {
        UtilMethods.INSTANCE.setonlinestatus(getActivity());
    }
}