package com.doc24x7.doctor.DoctorDashboad;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.doc24x7.doctor.Dashbord.dto.Datum;
import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.Loader;
import com.doc24x7.doctor.Utils.UtilMethods;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class DoctorAvalibalAdapter extends RecyclerView.Adapter<DoctorAvalibalAdapter.MyViewHolder> implements CompoundButton.OnCheckedChangeListener {

    private ArrayList<Datum> transactionsList;
    private ArrayList<Datum> transactionsListNew;
   public Context mContext;
    private String day;
    int type = 0;
    private Boolean isSelected = false;
    String format;
    EditText starttime, endtime, numberofpa;
    TextView deleteselected;
    CheckBox  selectedAll;
    CheckBox monch, tuech, wedch, thusch, frich, satch, sunch;
    ArrayList<String> updateAvilabilityday = new ArrayList<>();
    RadioButton patientrd, radiord;
    public void filter(ArrayList<Datum> newlist) {
        transactionsList = new ArrayList<>();
        transactionsList.addAll(newlist);
        notifyDataSetChanged();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title, tv_discuraption,craetedate_more, craetedate, enddate, noofpatient, selectedAll,numberofpa;

        ImageView remove, edit;
        CheckBox checkBox;

        public MyViewHolder(View view) {
            super(view);
            tv_title = view.findViewById(R.id.tv_title);
            tv_discuraption = view.findViewById(R.id.tv_discuraption);
            craetedate = view.findViewById(R.id.craetedate);
            craetedate_more = view.findViewById(R.id.craetedate_more);
            enddate = view.findViewById(R.id.enddate);
            noofpatient = view.findViewById(R.id.noofpatient);
            tv_discuraption = view.findViewById(R.id.tv_discuraption);
            remove = view.findViewById(R.id.remove);
            edit = view.findViewById(R.id.edit);
            checkBox = view.findViewById(R.id.checkbox);
            selectedAll = view.findViewById(R.id.selectedAll);
           // numberofpa=view.findViewById(R.id.numberofpa);
        }
    }

    public DoctorAvalibalAdapter(ArrayList<Datum> transactionsList, Context mContext, String day,
                                 TextView deleteselected, CheckBox selectedAll) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.day = day;
        transactionsListNew = new ArrayList<>();
        this.deleteselected = deleteselected;
        this.selectedAll = selectedAll;
        this.deleteselected.setVisibility(View.GONE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_avalabel_adapter, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Datum operator = transactionsList.get(position);

        holder.craetedate.setText(operator.getStart_time() +"");
        holder.craetedate_more.setText( ""+ operator.getEnd_time());

        holder.noofpatient.setText("Duration/Patient :\n" + operator.getNo_of_patient() + " Patients");
        if (isSelected) {
            holder.checkBox.setChecked(true);
            if (!transactionsListNew.contains(operator))
                transactionsListNew.add(operator);
        } else {
            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SchedulingFragment.scheduleID.add(Integer.parseInt(operator.getId()));
                    transactionsListNew.add(operator);
                    if (transactionsListNew.size() > 0) {
                        deleteselected.setVisibility(View.VISIBLE);
                    } else {
                        deleteselected.setVisibility(View.GONE);
                    }
                } else {

                    try {
                        SchedulingFragment.scheduleID.remove(position);
                        transactionsListNew.remove(position);
                        if (transactionsListNew.size() > 0) {
                            deleteselected.setVisibility(View.VISIBLE);
                        } else {
                            deleteselected.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        deleteselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loader loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                for (int i = 0; i < transactionsListNew.size(); i++) {
                    if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {
                        UtilMethods.INSTANCE.DeleteAvailability(mContext, transactionsListNew.get(i).getId(), day, loader, transactionsListNew.size(), i + 1);

                    } else {
                        UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
                                mContext.getResources().getString(R.string.network_error_message));
                    }
                }
            }
        });


        selectedAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    isSelected = true;
                    notifyDataSetChanged();
                    deleteselected.setVisibility(View.VISIBLE);

                    // checked
                }
                else
                {
                    isSelected = false;
                    notifyDataSetChanged();
                    deleteselected.setVisibility(View.GONE);

                    // not checked
                }
            }

        });


       /* selectedAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelected = true;
                notifyDataSetChanged();
            }
        });*/



        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(mContext).setMessage("Do you want to Edit?")


                        .setView(R.layout.custom_layout)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Loader loader;
                                loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
                                if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {
                                    if (starttime.getText().toString().isEmpty()) {
                                        starttime.setError("Time can't be empty");
                                        // return;
                                    } else if (endtime.getText().toString().isEmpty()) {
                                        endtime.setError("Time can't be empty");
                                        // return;
                                    } else if (numberofpa.getText().toString().isEmpty()) {
                                        numberofpa.setError("Patient can't be empty");
                                        //return;
                                    } else if (updateAvilabilityday.size() == 0) {
                                        UtilMethods.INSTANCE.Failed(mContext, "Please select at least one day", 0);
                                        //numberofpa.setError("Please select at least one day");
                                        //  return;
                                    } else if (type == 0) {
                                        UtilMethods.INSTANCE.Failed(mContext, "Please select type of schedule", 0);
                                          return;
                                    }else {
                                        loader.show();
                                        loader.setCancelable(false);
                                        loader.setCanceledOnTouchOutside(false);
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
                                      // long num = (min / Long.parseLong(numberofpa.getText().toString().trim()));


                                        for (int i = 0; i < updateAvilabilityday.size(); i++) {
                                            if(updateAvilabilityday.get(i).equalsIgnoreCase(day)){
                                                UtilMethods.INSTANCE.UpdateAvailability(mContext, "" + starttime.getText().toString(),
                                                        numberofpa.getText().toString().trim(),
                                                        "" + endtime.getText().toString(),
                                                        "" + updateAvilabilityday.get(i).toString(),
                                                        "1",
                                                        operator.getId(), loader);
                                            }else{
                                                try {
                                                    d1 = sdf.parse(endtime.getText().toString().trim());
                                                    d2 = sdf.parse(starttime.getText().toString().trim());
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                long num=(min/ Long.parseLong(numberofpa.getText().toString().trim()));
                                                UtilMethods.INSTANCE.SetAvailability(mContext, "", "" + starttime.getText().toString().trim(),
                                                        "" + endtime.getText().toString().trim(), "", loader,
                                                        updateAvilabilityday.get(i), type==1? numberofpa.getText().toString().trim():num+"",updateAvilabilityday);
                                            }
                                            Log.e("daysssss:",updateAvilabilityday.get(i));
                                            if (i == updateAvilabilityday.size() - 1) {
                                                UtilMethods.INSTANCE.GetAvailability(mContext, day, loader);
                                                dialog.dismiss();
                                            }
                                        }
                                    }
                                } else {
                                    UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
                                            mContext.getResources().getString(R.string.network_error_message));
                                }
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                starttime = dialog.findViewById(R.id.starttime);
                endtime = dialog.findViewById(R.id.endtime);
                numberofpa = dialog.findViewById(R.id.numberofpa);
                numberofpa.setText(operator.getNo_of_patient());
                tuech =dialog.findViewById(R.id.tue);
                wedch =dialog.findViewById(R.id.wed);
                thusch =dialog.findViewById(R.id.thus);
                frich = dialog.findViewById(R.id.fri);
                satch =dialog.findViewById(R.id.sat);
                sunch = dialog.findViewById(R.id.sun);
                monch = dialog.findViewById(R.id.monnn);
                patientrd = dialog.findViewById(R.id.patient);
                radiord = dialog.findViewById(R.id.min);
                patientrd.setChecked(true);
                type=1;
              //  monch.setOnClickListener(this);

                monch.setOnCheckedChangeListener(DoctorAvalibalAdapter.this::onCheckedChanged);
                tuech.setOnCheckedChangeListener(DoctorAvalibalAdapter.this::onCheckedChanged);
                wedch.setOnCheckedChangeListener(DoctorAvalibalAdapter.this::onCheckedChanged);
                thusch.setOnCheckedChangeListener(DoctorAvalibalAdapter.this::onCheckedChanged);
                frich.setOnCheckedChangeListener(DoctorAvalibalAdapter.this::onCheckedChanged);
                satch.setOnCheckedChangeListener(DoctorAvalibalAdapter.this::onCheckedChanged);
                sunch.setOnCheckedChangeListener(DoctorAvalibalAdapter.this::onCheckedChanged);
                patientrd.setOnCheckedChangeListener(DoctorAvalibalAdapter.this::onCheckedChanged);
                radiord.setOnCheckedChangeListener(DoctorAvalibalAdapter.this::onCheckedChanged);

//                 tuech.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                     @Override
//                     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                     }
//                 });
                endtime.setText(operator.getEnd_time());
                starttime.setText(operator.getStart_time());
                endtime.setOnClickListener(this);
                starttime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                        int CalendarMinute = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timepickerdialog = new TimePickerDialog(mContext, AlertDialog.THEME_HOLO_LIGHT,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {
                                        if (hourOfDay == 0) {
                                            hourOfDay += 12;
                                            format = " AM";
                                            format = "";
                                        } else if (hourOfDay == 12) {
                                            format = "";
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
                                    }
                                }, CalendarHour, CalendarMinute, false);
                        String[] str = starttime.getText().toString().split(":");
                        timepickerdialog.updateTime(Integer.parseInt(str[0]), Integer.parseInt(str[1].replace(" AM", "").replace(" PM", "")));
                        timepickerdialog.show();
                    }
                });
                endtime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                        int CalendarMinute = calendar.get(Calendar.MINUTE);
                        if (starttime.getText().toString().isEmpty()) {
                            starttime.setError("Please select start time");
                            return;
                        }
                        calendar = Calendar.getInstance();
                        CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                        CalendarMinute = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timepickerdialog = new TimePickerDialog(mContext, AlertDialog.THEME_HOLO_LIGHT,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {
                                        if (hourOfDay == 0) {
                                            hourOfDay += 12;
                                            format = " AM";
                                            format = "";
                                        } else if (hourOfDay == 12) {
                                            format = "";
                                            format = " PM";
                                        } else if (hourOfDay > 12) {
                                            hourOfDay -= 12;
                                            format = " PM";
                                        } else {
                                            format = " AM";
                                        }
                                        Calendar startTime = Calendar.getInstance();
                                        Calendar endTime = Calendar.getInstance();
                                        endTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        endTime.set(Calendar.MINUTE, minute);
                                        String[] str = starttime.getText().toString().split(":");
                                        startTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(str[0]));
                                        startTime.set(Calendar.MINUTE, Integer.parseInt(str[1].replace(" AM", "").replace(" PM", "")));
                                        Log.e("tile ", startTime.getTimeInMillis() + "," + endTime.getTimeInMillis());
                                        if (endTime.getTimeInMillis() > startTime.getTimeInMillis()) {
                                            if (minute < 10)
                                                endtime.setText(hourOfDay + ":0" + minute + format);
                                            else
                                                endtime.setText(hourOfDay + ":" + minute + format);
                                        } else {
                                            //it's before current'
                                            Toast.makeText(mContext, "Invalid Time", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }, CalendarHour, CalendarMinute, false);
                        String[] str = endtime.getText().toString().split(":");
                        timepickerdialog.updateTime(Integer.parseInt(str[0]), Integer.parseInt(str[1].replace(" AM", "").replace(" PM", "")));
                        timepickerdialog.show();
                    }
                });

            }

        });




        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(mContext).setMessage("Do you really want to delete?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Loader loader;
                        loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
                       //Toast.makeText(mContext, ""+operator.getId(), Toast.LENGTH_SHORT).show();
                        if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {
                            loader.show();
                            loader.setCancelable(false);
                            loader.setCanceledOnTouchOutside(false);
                            UtilMethods.INSTANCE.DeleteAvailability(mContext, operator.getId(), day, loader, 1, 1);
                        } else {
                            UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
                                    mContext.getResources().getString(R.string.network_error_message));
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });

    }



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == monch) {
            if (isChecked) {
                if (!updateAvilabilityday.contains("Monday")) {
                    updateAvilabilityday.add("Monday");

                }
            } else {
                if (updateAvilabilityday.contains("Monday")) {
                    updateAvilabilityday.remove("Monday");

                }
            }
        } else if (buttonView == tuech) {
            if (isChecked) {
                if (!updateAvilabilityday.contains("Tuesday")) {
                    updateAvilabilityday.add("Tuesday");

                }
            } else {
                if (updateAvilabilityday.contains("Tuesday")) {
                    updateAvilabilityday.remove("Tuesday");
                }
            }

        } else if (buttonView == wedch) {
            if (isChecked) {
                if (!updateAvilabilityday.contains("Wednesday")) {
                    updateAvilabilityday.add("Wednesday");
                }
            } else {
                if (updateAvilabilityday.contains("Wednesday")) {
                    updateAvilabilityday.remove("Wednesday");
                }
            }
        } else if (buttonView == thusch) {
            if (isChecked) {
                if (!updateAvilabilityday.contains("Thursday")) {
                    updateAvilabilityday.add("Thursday");
                }
            } else {
                if (updateAvilabilityday.contains("Thursday")) {
                    updateAvilabilityday.remove("Thursday");
                }
            }
        } else if (buttonView == frich) {
            if (isChecked) {
                if (!updateAvilabilityday.contains("Friday")) {
                    updateAvilabilityday.add("Friday");
                }
            } else {
                if (updateAvilabilityday.contains("Friday")) {
                    updateAvilabilityday.remove("Friday");
                }
            }
        } else if (buttonView == satch) {
            if (isChecked) {
                if (!updateAvilabilityday.contains("Saturday")) {
                    updateAvilabilityday.add("Saturday");
                }
            } else {
                if (updateAvilabilityday.contains("Saturday")) {
                    updateAvilabilityday.remove("Saturday");
                }
            }
        } else if (buttonView == sunch) {
            if (isChecked) {
                if (!updateAvilabilityday.contains("Sunday")) {
                    updateAvilabilityday.add("Sunday");
                }
            } else {
                if (updateAvilabilityday.contains("Sunday")) {
                    updateAvilabilityday.remove("Sunday");
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

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}