package com.doc24x7.doctor.DoctorDashboad;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.doc24x7.doctor.Dashbord.dto.Datum;
import com.doc24x7.doctor.Dashbord.dto.GalleryListResponse;
import com.doc24x7.doctor.Login.dto.Data;
import com.doc24x7.doctor.R;
import com.doc24x7.doctor.ShowleaveActivity;
import com.doc24x7.doctor.Utils.ApplicationConstant;
import com.doc24x7.doctor.Utils.FragmentActivityMessage;
import com.doc24x7.doctor.Utils.GlobalBus;
import com.doc24x7.doctor.Utils.Loader;
import com.doc24x7.doctor.Utils.UtilMethods;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;


public class SchedulingFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    Button leavebydate, leavebyschedule;
    EditText selectdate, starttime, endtime, startdate, enddate, dayofdate;
    TextView show_day_leave,show_date_leave,notAvailable;
    RadioButton rad_by_schedule, rad_by_date;
    LinearLayout Li_rad_by_schedule,li_rad_by_date;
    CheckBox monch, tuech, wedch, thusch, frich, satch, sunch;
    RadioButton patientrd, radiord;
    Calendar calendar;
    private int CalendarHour, CalendarMinute;
    TimePickerDialog timepickerdialog;
    String format;
    Loader loader;
    LinearLayout Logout, ll_add;
    CheckBox selectedAll_check;
    Spinner spinner_day;
    LinearLayout li_starttime , li_endtime  ,  li_spin_day;
    ImageView show_leave_im , set_Leave_im , add_new_im ;
    TextView name, contentnumber, mon, tue, wed, thurs, fri, sat, sun, add_new, deleteselected, set_Leave, selectedAll,showleave;
    private DatePickerDialog.OnDateSetListener dateSetListener, dateSetListenerend, dateSetListenerday;
    Spinner spin_day;
    String dayevalue = "";
    int type = 1;
    String[] daye = {"Select Day", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    public static ArrayList<Integer> scheduleID = new ArrayList<>();
    EditText numberofpa;
    RecyclerView recycler_view, recycler_leavebyday;
    String day;
    ArrayList<String> setAvilabilityday = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_scheduling, container, false);

        scheduleID.clear();
        setAvilabilityday.clear();
        Getid(v);

        return v;


    }

    private void Getid(View v) {


        recycler_view = v.findViewById(R.id.recycler_view);

        // spin_day = v.findViewById(R.id.spin_day);
        mon = v.findViewById(R.id.mon);
        set_Leave = v.findViewById(R.id.set_Leave);
        selectedAll = v.findViewById(R.id.selectedAll);
        selectedAll_check = v.findViewById(R.id.selectedAll_check);
        tue = v.findViewById(R.id.tue);
        wed = v.findViewById(R.id.wed);
        add_new = v.findViewById(R.id.add_new);
        ll_add = v.findViewById(R.id.ll_add);
        thurs = v.findViewById(R.id.thurs);
        fri = v.findViewById(R.id.fri);
        sat = v.findViewById(R.id.sat);
        sun = v.findViewById(R.id.sun);
        notAvailable = v.findViewById(R.id.notAvailable);
        showleave=v.findViewById(R.id.show_leave);
        numberofpa = v.findViewById(R.id.numberofpa);
        deleteselected = v.findViewById(R.id.deleteselected);
        contentnumber = v.findViewById(R.id.contentnumber);

        show_leave_im = v.findViewById(R.id.show_leave_im);
        set_Leave_im = v.findViewById(R.id.set_Leave_im);
        add_new_im = v.findViewById(R.id.add_new_im);

        show_leave_im.setOnClickListener(this);
        set_Leave_im.setOnClickListener(this);
        add_new_im.setOnClickListener(this);
        selectedAll_check.setOnClickListener(this);

        name = v.findViewById(R.id.name);
        SharedPreferences myPreferences = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        name.setText("Dr. " + balanceCheckResponse.getName());
        contentnumber.setText("" + balanceCheckResponse.getMobile());
        loader = new Loader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        Logout = v.findViewById(R.id.Logout);
        Logout.setOnClickListener(this);
        mon.setOnClickListener(this);
        tue.setOnClickListener(this);
        wed.setOnClickListener(this);
        thurs.setOnClickListener(this);
        fri.setOnClickListener(this);
        sat.setOnClickListener(this);
        sun.setOnClickListener(this);
        add_new.setOnClickListener(this);
        set_Leave.setOnClickListener(this);
        showleave.setOnClickListener(this);
        flag = 0;
        HitApi("Monday");


    }


    private void HitApi(String day) {
        this.day = day;
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.GetAvailability(getActivity(), day, loader);

        } else {
            UtilMethods.INSTANCE.NetworkError(getActivity(), getActivity().getResources().getString(R.string.network_error_title),
                    getActivity().getResources().getString(R.string.network_error_message));
        }


    }

    public void Datepicker() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.datepicker_pop, null);

        Button tvLater = view.findViewById(R.id.tv_later);
        Button tv_ok = view.findViewById(R.id.tv_ok);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);


        final Dialog dialog = new Dialog(getActivity());

        dialog.setCancelable(false);
        dialog.setContentView(view);


        Calendar today = Calendar.getInstance();
        long now = today.getTimeInMillis();
        datePicker.setMinDate(now);


        Date currentTime = Calendar.getInstance().getTime();


        String timewah = currentTime.toString().replace(" ", ",");

        String[] recent;
        recent = timewah.split(",");


        Log.e("currentTime", "currentTime :   " + recent[3]);

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Toast.makeText(Verder_Detail_Selection.this, "Date  : " +  "Selected date: " + (datePicker.getMonth()+1) + "/" + datePicker.getDayOfMonth() + "/" + datePicker.getYear(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();

                selectdate.setText(datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth());


            }
        });


        tvLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public void setpicker() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.datepicker_pop, null);

        Button tvLater = view.findViewById(R.id.tv_later);
        Button tv_ok = view.findViewById(R.id.tv_ok);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);


        final Dialog dialog = new Dialog(getActivity());

        dialog.setCancelable(false);
        dialog.setContentView(view);


        Calendar today = Calendar.getInstance();
        long now = today.getTimeInMillis();
        datePicker.setMinDate(now);


        Date currentTime = Calendar.getInstance().getTime();


        String timewah = currentTime.toString().replace(" ", ",");

        String[] recent;
        recent = timewah.split(",");


        Log.e("currentTime", "currentTime :   " + recent[3]);

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Toast.makeText(Verder_Detail_Selection.this, "Date  : " +  "Selected date: " + (datePicker.getMonth()+1) + "/" + datePicker.getDayOfMonth() + "/" + datePicker.getYear(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();


            }
        });


        tvLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialog.dismiss();
            }
        });


        dialog.show();
    }

    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("getavalablelel")) {
            dataParse(activityFragmentMessage.getFrom());
        }

    }

    GalleryListResponse sliderImage;
    ArrayList<Datum> sliderLists = new ArrayList<>();
    DoctorAvalibalAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    int flag = 0;

    public void dataParse(String response) {

        Gson gson = new Gson();
        sliderImage = gson.fromJson(response, GalleryListResponse.class);
        sliderLists = sliderImage.getData();

        if (sliderLists.size() > 0) {
            if (flag == 0) {
                mAdapter = new DoctorAvalibalAdapter(sliderLists, getActivity(), day, deleteselected, selectedAll_check);
                mLayoutManager = new LinearLayoutManager(getActivity());
                recycler_view.setLayoutManager(mLayoutManager);
                recycler_view.setItemAnimator(new DefaultItemAnimator());
                recycler_view.setAdapter(mAdapter);
                recycler_view.setVisibility(View.VISIBLE);
                notAvailable.setVisibility(View.GONE);
            } else {
                mAdapter = new DoctorAvalibalAdapter(sliderLists, getActivity(), day, deleteselected, selectedAll_check);
                mLayoutManager = new LinearLayoutManager(getActivity());
                recycler_leavebyday.setLayoutManager(mLayoutManager);
                recycler_leavebyday.setItemAnimator(new DefaultItemAnimator());
                recycler_leavebyday.setAdapter(mAdapter);
                recycler_leavebyday.setLayoutManager(mLayoutManager);
                recycler_leavebyday.setItemAnimator(new DefaultItemAnimator());
                recycler_leavebyday.setAdapter(mAdapter);
                notAvailable.setVisibility(View.GONE);
            }
        } else {
            recycler_view.setVisibility(View.GONE);
            notAvailable.setVisibility(View.VISIBLE);
        }


        selectedAll_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {


                    deleteselected.setVisibility(View.VISIBLE);

                     // checked
                }
                else
                {

                    deleteselected.setVisibility(View.GONE);

                    // not checked
                }
            }

        });


    }


    @Override
    public void onClick(View view) {




        if (view == mon) {

            Toast.makeText(getActivity(), "   As   ", Toast.LENGTH_SHORT).show();

            setBackgroundColor(mon);
            setBackgroundColorW(tue);
            setBackgroundColorW(wed);
            setBackgroundColorW(thurs);
            setBackgroundColorW(fri);
            setBackgroundColorW(sat);
            setBackgroundColorW(sun);
            flag = 0;
            HitApi("Monday");
        } else if (view == tue) {
            setBackgroundColor(tue);
            setBackgroundColorW(mon);
            setBackgroundColorW(wed);
            setBackgroundColorW(thurs);
            setBackgroundColorW(fri);
            setBackgroundColorW(sat);
            setBackgroundColorW(sun);
            flag = 0;
            HitApi("Tuesday");
        } else if (view == wed) {
            setBackgroundColor(wed);
            setBackgroundColorW(mon);
            setBackgroundColorW(tue);
            setBackgroundColorW(thurs);
            setBackgroundColorW(fri);
            setBackgroundColorW(sat);
            setBackgroundColorW(sun);
            flag = 0;
            HitApi("Wednesday");
        } else if (view == thurs) {
            setBackgroundColor(thurs);
            setBackgroundColorW(mon);
            setBackgroundColorW(tue);
            setBackgroundColorW(wed);
            setBackgroundColorW(fri);
            setBackgroundColorW(sat);
            setBackgroundColorW(sun);
            flag = 0;
            HitApi("Thursday");
        } else if (view == fri) {
            setBackgroundColor(fri);
            setBackgroundColorW(mon);
            setBackgroundColorW(tue);
            setBackgroundColorW(wed);
            setBackgroundColorW(thurs);
            setBackgroundColorW(sat);
            setBackgroundColorW(sun);
            flag = 0;
            HitApi("Friday");
        } else if (view == sat) {
            setBackgroundColor(sat);
            setBackgroundColorW(mon);
            setBackgroundColorW(tue);
            setBackgroundColorW(wed);
            setBackgroundColorW(thurs);
            setBackgroundColorW(fri);
            setBackgroundColorW(sun);
            flag = 0;
            HitApi("Saturday");
        } else if (view == sun) {
            setBackgroundColor(sun);
            setBackgroundColorW(mon);
            setBackgroundColorW(tue);
            setBackgroundColorW(wed);
            setBackgroundColorW(thurs);
            setBackgroundColorW(fri);
            setBackgroundColorW(sat);
            flag = 0;
            HitApi("Sunday");
        }
        if (view == add_new_im) {

            final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setMessage("Add New Schedule")
                    .setView(R.layout.custom_layout_date).show();
            starttime = alertDialog.findViewById(R.id.starttime);
            endtime = alertDialog.findViewById(R.id.endtime);
            numberofpa = alertDialog.findViewById(R.id.numberofpa);
            monch = alertDialog.findViewById(R.id.mon);
            TextView save_schedule = alertDialog.findViewById(R.id.save_schedule);
            tuech = alertDialog.findViewById(R.id.tue);
            wedch = alertDialog.findViewById(R.id.wed);
            thusch = alertDialog.findViewById(R.id.thus);
            frich = alertDialog.findViewById(R.id.fri);
            satch = alertDialog.findViewById(R.id.sat);
            sunch = alertDialog.findViewById(R.id.sun);
            patientrd = alertDialog.findViewById(R.id.patient);
            radiord = alertDialog.findViewById(R.id.min);
            monch.setOnCheckedChangeListener(this);
            tuech.setOnCheckedChangeListener(this);
            wedch.setOnCheckedChangeListener(this);
            thusch.setOnCheckedChangeListener(this);
            frich.setOnCheckedChangeListener(this);
            satch.setOnCheckedChangeListener(this);
            sunch.setOnCheckedChangeListener(this);
            patientrd.setOnCheckedChangeListener(this);
            patientrd.isChecked();
            radiord.setOnCheckedChangeListener(this);
            starttime.setOnClickListener(this);
            endtime.setOnClickListener(this);
            save_schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Loader loader;
                    loader = new Loader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
                    if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
                        if (starttime.getText().toString().isEmpty()) {
                            starttime.setError("Time can't be empty");
                            // return;
                        } else if (endtime.getText().toString().isEmpty()) {
                            endtime.setError("Time can't be empty");
                            // return;
                        } else if (numberofpa.getText().toString().isEmpty()) {
                            numberofpa.setError("Patient can't be empty");
                            //return;
                        } else if (setAvilabilityday.size() == 0) {
                            UtilMethods.INSTANCE.Failed(getActivity(), "Please select at least one day", 0);
                            //numberofpa.setError("Please select at least one day");
                            //  return;
                        } else if (type == 0) {
                            UtilMethods.INSTANCE.Failed(getActivity(), "Please select type of schedule", 0);
                            //  return;
                        } else {
                            loader.show();
                            loader.setCancelable(false);
                            loader.setCanceledOnTouchOutside(false);
                            // long a = TimeUnit.MINUTES.toMillis(Long.parseLong(numberofpa.getText().toString().trim()));
                            //long s = new Date(endtime.getText().toString()).getTime() - new Date(starttime.getText().toString()).getTime();
                            //Log.e("numberofpa..", s + "");
                            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                            Date d1 = null;
                            Date d2 = null;
                            try {
                                d1 = sdf.parse(endtime.getText().toString().trim());
                                d2 = sdf.parse(starttime.getText().toString().trim());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            long diffMs = d1.getTime() - d2.getTime();
                            long diffSec = diffMs / 1000;
                            long min = diffSec / 60;
                            long num=(min/ Long.parseLong(numberofpa.getText().toString().trim()));
                            for (int i = 0; i < setAvilabilityday.size(); i++) {
                                UtilMethods.INSTANCE.SetAvailability(getActivity(), "", "" + starttime.getText().toString().trim(),
                                        "" + endtime.getText().toString().trim(), "", loader,
                                        setAvilabilityday.get(i), type==1? numberofpa.getText().toString().trim():num+"",setAvilabilityday);
                                if(i==setAvilabilityday.size()-1){
                                    alertDialog.dismiss();
                                }
                            }
                        }

                    } else {
                        UtilMethods.INSTANCE.NetworkError(getActivity(), getActivity().getResources().getString(R.string.network_error_title),
                                getActivity().getResources().getString(R.string.network_error_message));
                    }
                }
            });


        }


        if(view==show_leave_im)
        {
            Intent i=new Intent(getActivity(), ShowleaveActivity.class);
            startActivity(i);
        }
        if (view == Logout) {

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




          /*  final SweetAlertDialog alertDialog = new SweetAlertDialog(getActivity());
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


        if (view == selectdate) {


            Datepicker();


        }


     /*   if (view == set_Leave_im) {

            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setMessage("Choose Leave Type")
                    .setView(R.layout.chooseleaveview)
                    .setPositiveButton("", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Loader loader;
                            loader = new Loader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
                            if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
                                loader.show();
                                loader.setCancelable(false);
                                loader.setCanceledOnTouchOutside(false);
                            }


                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
//
            leavebydate = alertDialog.findViewById(R.id.leavebydate);
            leavebyschedule = alertDialog.findViewById(R.id.leavebyschedule);
            leavebydate.setOnClickListener(this);
            leavebyschedule.setOnClickListener(this);
//     recyclerleave.setLayoutManager(mLayoutManager);
//     recyclerleave.setItemAnimator(new DefaultItemAnimator());
            // recyclerleave.setAdapter(mAdapter);

            //numberofpa = alertDialog.findViewById(R.id.numberofpa);
//     startdate.setOnClickListener(this);
//     enddate.setOnClickListener(this);
//


            // setpicker();
        }*/



        if (view == set_Leave_im) {


            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setMessage("Set Leave ")
                    .setView(R.layout.setleaveview)
                    .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Loader loader;
                            loader = new Loader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
                            if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
                                loader.show();
                                loader.setCancelable(false);
                                loader.setCanceledOnTouchOutside(false);
                                try {
                                    UtilMethods.INSTANCE.LeaveDate(getActivity(), getDates(startdate.getText().toString(), enddate.getText().toString()), loader);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }


                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            startdate = alertDialog.findViewById(R.id.startdate);
            enddate = alertDialog.findViewById(R.id.enddate);

            show_day_leave = alertDialog.findViewById(R.id.show_day_leave);
            show_date_leave = alertDialog.findViewById(R.id.show_date_leave);
            rad_by_schedule = alertDialog.findViewById(R.id.rad_by_schedule);
            rad_by_date = alertDialog.findViewById(R.id.rad_by_date);
            Li_rad_by_schedule = alertDialog.findViewById(R.id.Li_rad_by_schedule);
            li_rad_by_date = alertDialog.findViewById(R.id.li_rad_by_date);

            li_starttime = alertDialog.findViewById(R.id.li_starttime);
            li_endtime = alertDialog.findViewById(R.id.li_endtime);
            li_spin_day = alertDialog.findViewById(R.id.li_spin_day);
            spinner_day = alertDialog.findViewById(R.id.spinner_day);


            spinner_day.setOnItemSelectedListener(this);

            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,daye);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            spinner_day.setAdapter(aa);

            startdate.setOnClickListener(this);
            enddate.setOnClickListener(this);


            rad_by_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    li_starttime.setVisibility(View.VISIBLE);
                    li_endtime.setVisibility(View.VISIBLE);
                    li_spin_day.setVisibility(View.GONE);

                    rad_by_date.setChecked(true);
                    rad_by_schedule.setChecked(false);

                }
            });


            rad_by_schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    li_starttime.setVisibility(View.GONE);
                    li_endtime.setVisibility(View.GONE);
                    li_spin_day.setVisibility(View.VISIBLE);

                    rad_by_date.setChecked(false);
                    rad_by_schedule.setChecked(true);

                }
            });



        }

        if (view == leavebyschedule) {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setMessage("Set Leave by schedule ")
                    .setView(R.layout.leavebyschedule)
                    .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Loader loader;
                            loader = new Loader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
                            if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
                                // Toast.makeText(getContext(), "hello jitendra", Toast.LENGTH_SHORT).show();
                                loader.show();
                                loader.setCancelable(false);
                                loader.setCanceledOnTouchOutside(false);

                                UtilMethods.INSTANCE.LeaveShedule(getActivity(), dayofdate.getText().toString(), scheduleID, loader);
                            }


                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            dayofdate = alertDialog.findViewById(R.id.dayofdate);
            dayofdate.setOnClickListener(this);
            recycler_leavebyday = alertDialog.findViewById(R.id.recycler_leaveday);
            if (dayofdate.getText().toString().isEmpty()) {

            } else {

            }
//      numberofpa = alertDialog.findViewById(R.id.numberofpa);
        }

        if (view == dayofdate) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                    android.R.style.Theme_Material_Light_Dialog, dateSetListenerday, year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            dialog.show();
        }

        dateSetListenerday = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int monthh=month+1;

                String mont,day;
                if(month<10)
                {
                    mont="0"+(monthh);
                }
                else
                {
                    mont=monthh+"";
                }
                if(dayOfMonth<10)
                {
                    day="0"+dayOfMonth;
                }
                else
                {
                    day=dayOfMonth+"";
                }


            //String datet = year + "-" + m + "-" +d;
                String datet = year + "-" +mont+ "-"+day;




                SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
                Date currentdate = new Date();
                String currentdatee= dfDate.format(currentdate);
                try {
                    if (dfDate.parse(currentdatee).before(dfDate.parse(datet))) {
                        //.setText(datet);
                        Date date = null;
                        try {
                            date = dfDate.parse(datet);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
                        String goal = outFormat.format(date);
                        dayofdate.setText(datet);
                        flag = 1;
                        HitApi(goal);
                    }
                    else if (dfDate.parse(currentdatee).equals(dfDate.parse(datet))) {
                        dayofdate.setText(datet);
                        Date date = null;
                        try {
                            date = dfDate.parse(datet);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
                        String goal = outFormat.format(date);
                        //dayofdate.setText(datet);
                        flag = 1;
                        HitApi(goal);
                        dayofdate.setText(datet);
                        //  Toast.makeText(getContext(), "End Date should be not equal to start date", Toast.LENGTH_SHORT).show();
                    } else if (dfDate.parse(datet).before(dfDate.parse(currentdatee))) {
                        Toast.makeText(getContext(), "Date should be grater than  to current date", Toast.LENGTH_SHORT).show();
                        dayofdate.setText("select date");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }




            }


        };


        if (view == startdate) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//              String dateformate = formatter.format(calendar.getTime());

            DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();


        }
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override

            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int monthh=month+1;

                String mont,day;
                if(month<10)
                {
                    mont="0"+(monthh);
                }
                else
                {
                    mont=monthh+"";
                }
                if(dayOfMonth<10)
                {
                    day="0"+dayOfMonth;
                }
                else
                {
                    day=dayOfMonth+"";
                }


                String date = year + "-" +mont+ "-"+day;

                //startdate.setText(date);

                SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
                Date currentdate = new Date();
               String currentdatee= dfDate.format(currentdate);
                try {
                    if (dfDate.parse(currentdatee).before(dfDate.parse(date))) {
                        startdate.setText(date);
                    }
                    else if (dfDate.parse(currentdatee).equals(dfDate.parse(date))) {
                        startdate.setText(date);
                      //  Toast.makeText(getContext(), "End Date should be not equal to start date", Toast.LENGTH_SHORT).show();
                    } else if (dfDate.parse(date).before(dfDate.parse(currentdatee))) {
                        Toast.makeText(getContext(), "Date should be grater than  to current date", Toast.LENGTH_SHORT).show();
                          startdate.setHint("Start date");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        };

        if (view == enddate) {
            if (startdate.getText().toString().isEmpty()) {
                startdate.setError("please select startdate");
                startdate.requestFocus();
            } else {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListenerend, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        }
        dateSetListenerend = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int monthh=month+1;

                String mont,day;
                if(month<10)
                {
                    mont="0"+(monthh);
                }
                else
                {
                    mont=monthh+"";
                }
                if(dayOfMonth<10)
                {
                    day="0"+dayOfMonth;
                }
                else
                {
                    day=dayOfMonth+"";
                }

                String date = year + "-" +mont+ "-"+day;

                SimpleDateFormat dfDate = new SimpleDateFormat("dd-MM-yyyy");

                // boolean b = false;
                try {
                    if (dfDate.parse(startdate.getText().toString()).before(dfDate.parse(date))) {
                         enddate.setText(date);

                    } else if (dfDate.parse(startdate.getText().toString()).equals(dfDate.parse(date))) {
                         enddate.setText(date);
                       // Toast.makeText(getContext(), "End Date should be not equal to start date", Toast.LENGTH_SHORT).show();
                    } else if (dfDate.parse(date).before(dfDate.parse(startdate.getText().toString()))) {
                        startdate.setHint("Start date");
                        Toast.makeText(getContext(), "End Date should be grater than  to Start date", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // return valid;

            }


        };


        if (view == endtime) {

            if (starttime.getText().toString().isEmpty()) {
                starttime.setError("Please select start time");
                return;
            }
            calendar = Calendar.getInstance();
            CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
            CalendarMinute = calendar.get(Calendar.MINUTE);
            timepickerdialog = new TimePickerDialog(getActivity(),AlertDialog.THEME_HOLO_LIGHT,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            if (hourOfDay == 0) {
                                hourOfDay += 12;
                                format = " AM";
                            } else if (hourOfDay == 12) {
                                format = " PM";
                            } else if (hourOfDay > 12) {
                                hourOfDay -= 12;
                                format = " PM";
                            } else {
                                format = " AM";
                            }
                            String time="0";
                            if (minute < 10)
                                time=hourOfDay + ":0" + minute + format;
                            else
                                time=hourOfDay + ":" + minute + format;
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                            Date d1 = null;
                            Date d2 = null;
                            try {
                                d1 = sdf.parse(time);
                                d2 = sdf.parse(starttime.getText().toString().trim());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if (d1.getTime() >= d2.getTime()) {
                                if (minute < 10)
                                    endtime.setText(hourOfDay + ":0" + minute + format);
                                else
                                    endtime.setText(hourOfDay + ":" + minute + format);
                            } else {
                                //it's before current'
                                Toast.makeText(getActivity(), "Invalid Time", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, CalendarHour, CalendarMinute, false);
            String[] str = starttime.getText().toString().split(":");
         timepickerdialog.updateTime(hourOfDaySt, minuteSt);
            timepickerdialog.show();


        }

        if (view == starttime) {
            calendar = Calendar.getInstance();
            CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
            CalendarMinute = calendar.get(Calendar.MINUTE);
            timepickerdialog = new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            hourOfDaySt=hourOfDay;
                            minuteSt=minute;
                            if (hourOfDay == 0) {
                                hourOfDay += 12;
                                format = " AM";
                            } else if (hourOfDay == 12) {
                                format = " PM";
                            } else if (hourOfDay > 12) {
                                hourOfDay -= 12;
                                format = " PM";
                            } else {
                                format = " AM";
                            }
                            if (minute < 10)
                                starttime.setText(hourOfDay + ":0" + minute + format);
                            else
                                starttime.setText(hourOfDay + ":" + minute + format);
                            starttime.setError(null);
                        }
                    }, CalendarHour, CalendarMinute, false);
            timepickerdialog.show();
        }
    }

    int hourOfDaySt,minuteSt;

    private void setBackgroundColorW(TextView mon) {
        mon.setBackground(getResources().getDrawable(R.drawable.rect));
        mon.setTextColor(getResources().getColor(R.color.black));

    }

    private void setBackgroundColor(TextView mon) {
        mon.setBackground(getResources().getDrawable(R.drawable.rect_blue_new));
        mon.setTextColor(getResources().getColor(R.color.white));

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Log.e("iiiidd", "i  " + i + "         id  " + daye[i]);

        if (i != 0) {

            dayevalue = "" + daye[i];


        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private static ArrayList<String> getDates(String dateString1, String dateString2) throws ParseException {
        ArrayList<String> dates = new ArrayList<String>();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1.parse(dateString1);
            date2 = df1.parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while (!cal1.after(cal2)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            String strDate = sdf.format(cal1.getTime());
            Log.e("strDate", strDate);
            dates.add(strDate);
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == monch) {
            if (isChecked) {
                if (!setAvilabilityday.contains("Monday")) {
                    setAvilabilityday.add("Monday");
                }
            } else {
                if (setAvilabilityday.contains("Monday")) {
                    setAvilabilityday.remove("Monday");
                }
            }
        } else if (buttonView == tuech) {
            if (isChecked) {
                if (!setAvilabilityday.contains("Tuesday")) {
                    setAvilabilityday.add("Tuesday");
                }
            } else {
                if (setAvilabilityday.contains("Tuesday")) {
                    setAvilabilityday.remove("Tuesday");
                }
            }

        } else if (buttonView == wedch) {
            if (isChecked) {
                if (!setAvilabilityday.contains("Wednesday")) {
                    setAvilabilityday.add("Wednesday");
                }
            } else {
                if (setAvilabilityday.contains("Wednesday")) {
                    setAvilabilityday.remove("Wednesday");
                }
            }
        } else if (buttonView == thusch) {
            if (isChecked) {
                if (!setAvilabilityday.contains("Thursday")) {
                    setAvilabilityday.add("Thursday");
                }
            } else {
                if (setAvilabilityday.contains("Thursday")) {
                    setAvilabilityday.remove("Thursday");
                }
            }
        } else if (buttonView == frich) {
            if (isChecked) {
                if (!setAvilabilityday.contains("Friday")) {
                    setAvilabilityday.add("Friday");
                }
            } else {
                if (setAvilabilityday.contains("Friday")) {
                    setAvilabilityday.remove("Friday");
                }
            }
        } else if (buttonView == satch) {
            if (isChecked) {
                if (!setAvilabilityday.contains("Saturday")) {
                    setAvilabilityday.add("Saturday");
                }
            } else {
                if (setAvilabilityday.contains("Saturday")) {
                    setAvilabilityday.remove("Saturday");
                }
            }
        } else if (buttonView == sunch) {
            if (isChecked) {
                if (!setAvilabilityday.contains("Sunday")) {
                    setAvilabilityday.add("Sunday");
                }
            } else {
                if (setAvilabilityday.contains("Sunday")) {
                    setAvilabilityday.remove("Sunday");
                }
            }
        } else if (buttonView == patientrd) {
            if (isChecked) {
                type = 1;
            }

        } else if (buttonView == radiord) {
            if (isChecked) {
                type = 2;
            }
        }
    }



}