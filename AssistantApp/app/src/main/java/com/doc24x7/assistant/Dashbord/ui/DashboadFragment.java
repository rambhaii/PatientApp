package com.doc24x7.assistant.Dashbord.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.doc24x7.assistant.Appointment.DOctoeByIdActivityOffline;
import com.doc24x7.assistant.PatientDetail;
import com.doc24x7.assistant.Utils.ForceUpdateChecker;
import com.google.gson.Gson;
import com.doc24x7.assistant.DoctorDashboad.AccountDetailScreen;
import com.doc24x7.assistant.DoctorDashboad.QualificationScreen;
import com.doc24x7.assistant.Login.dto.Data;
import com.doc24x7.assistant.R;
import com.doc24x7.assistant.Utils.ApplicationConstant;
import com.doc24x7.assistant.Utils.Loader;
import com.doc24x7.assistant.Utils.UtilMethods;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;


public class DashboadFragment extends Fragment implements View.OnClickListener, ForceUpdateChecker.OnUpdateNeededListener, CompoundButton.OnCheckedChangeListener {


    LinearLayout Logout;
    TextView name, contentnumber,tvonline,tvstatusmessage;
    TextView income, comission, PatientAppointment,PatientAppointment_offline, doctorappointment, total_patient, updateQuaification, updateAccDetail;
    LinearLayout income_li, comission_li, PatientAppointment_li,PatientAppointment_li_offline, doctorappointment_li, total_patient_li,addnewappointment,viewappoinrment;
   Switch sw_status;
    Loader loader;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboad, container, false);
        ForceUpdateChecker.with(getActivity()).onUpdateNeeded(this).check();


        GetId(v);
       // getOnlinestatus();
        tvonline.setText("Online");
        sw_status.setChecked(true);
        return v;

    }

    private void getOnlinestatus() {
        UtilMethods.INSTANCE.Getonlinestatus(getActivity());
    }

    private void GetId(View v) {
        viewappoinrment=v.findViewById(R.id.viewappontment);
        addnewappointment=v.findViewById(R.id.addappointment);
        total_patient = v.findViewById(R.id.TotalPatient);
        doctorappointment = v.findViewById(R.id.doctorappointment);
        PatientAppointment = v.findViewById(R.id.PatientAppointment);
        PatientAppointment_offline = v.findViewById(R.id.PatientAppointment_offline);
        comission = v.findViewById(R.id.comission);
        income = v.findViewById(R.id.income);
        updateQuaification = v.findViewById(R.id.updateQuaification);
        updateAccDetail = v.findViewById(R.id.updateAccDetail);
        income_li = v.findViewById(R.id.income_li);
        comission_li = v.findViewById(R.id.comission_li);
        PatientAppointment_li = v.findViewById(R.id.PatientAppointment_li);
        PatientAppointment_li_offline = v.findViewById(R.id.PatientAppointment_li_offline);
        tvonline = v.findViewById(R.id.tvonline);
        total_patient_li = v.findViewById(R.id.TotalPatient_li);
        sw_status = v.findViewById(R.id.sw_status);
        sw_status.setOnCheckedChangeListener(this);
        total_patient.setOnClickListener(this);
        PatientAppointment.setOnClickListener(this);
        comission.setOnClickListener(this);
        income.setOnClickListener(this);
        updateQuaification.setOnClickListener(this);
        updateAccDetail.setOnClickListener(this);
        income_li.setOnClickListener(this);
        comission_li.setOnClickListener(this);
        viewappoinrment.setOnClickListener(this);
        addnewappointment.setOnClickListener(this);
        PatientAppointment_li.setOnClickListener(this);
        PatientAppointment_li_offline.setOnClickListener(this);

        total_patient_li.setOnClickListener(this);
        contentnumber = v.findViewById(R.id.contentnumber);
        name = v.findViewById(R.id.name);
        SharedPreferences myPreferences = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        name.setText("As. " + balanceCheckResponse.getAssistant_name());
        contentnumber.setText("" + balanceCheckResponse.getAssistant_mobile());
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


        UtilMethods.INSTANCE.Dashboard(getActivity(), total_patient, doctorappointment, PatientAppointment, comission, income,PatientAppointment_offline);
        UtilMethods.INSTANCE.GetMedicine(getActivity(), loader);

    }

    @Override
    public void onClick(View view) {


        if (view == income_li) {

            startActivity(new Intent(getActivity(), ViewTransaction.class));


        }

      else  if (view == PatientAppointment_li) {
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
        else  if (view == PatientAppointment_li_offline) {
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
       else if (view == total_patient_li) {
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


            final SweetAlertDialog alertDialog = new SweetAlertDialog(getActivity());
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

            alertDialog.show();

        }
       else if(view==addnewappointment)
        {
            SharedPreferences myPreferences = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
            Data operator = new Gson().fromJson(balanceResponse, Data.class);
          // startActivity(new Intent(getContext(), PatientDetail.class));
            Intent i = new Intent(getContext(), DOctoeByIdActivityOffline.class);
           i.putExtra("response", "" + new Gson().toJson(operator));
            i.putExtra("type", "1");
            i.putExtra("doctorDetail", new Gson().toJson(operator));
            i.putExtra("bookstatus","2");
            i.putExtra("offline_fee",operator.getClinic_fees());
            startActivity(i);

        }
        else if(view==viewappoinrment)
        {
            Toast.makeText(getContext(), "View  Activity", Toast.LENGTH_SHORT).show();
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
               tvonline.setText("Online");
            } else {
                setstatusofline();
                tvonline.setText("Offline");
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