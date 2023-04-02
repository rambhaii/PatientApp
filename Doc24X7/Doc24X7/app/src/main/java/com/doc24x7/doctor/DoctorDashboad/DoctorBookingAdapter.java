package com.doc24x7.doctor.DoctorDashboad;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.doc24x7.doctor.Dashbord.dto.Datum;
import com.doc24x7.doctor.Dashbord.dto.GalleryListResponse;
import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.ApplicationConstant;
import com.doc24x7.doctor.Utils.Loader;
import com.doc24x7.doctor.Utils.UtilMethods;

import java.util.ArrayList;
import java.util.Calendar;


public class DoctorBookingAdapter extends RecyclerView.Adapter<DoctorBookingAdapter.MyViewHolder> {

    private ArrayList<Datum> transactionsList;
    private Context mContext;

    private int mYear, mMonth, mDay, mHour, mMinute;


    public void filter(ArrayList<Datum> newlist) {
        transactionsList = new ArrayList<>();
        transactionsList.addAll(newlist);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title, tv_discuraption, craetedate;
        EditText date;
        Button bookcontinew;


        public MyViewHolder(View view) {
            super(view);
            tv_title = view.findViewById(R.id.tv_title);
            tv_discuraption = view.findViewById(R.id.tv_discuraption);
            craetedate = view.findViewById(R.id.craetedate);
            bookcontinew = view.findViewById(R.id.bookcontinew);
            date = view.findViewById(R.id.datecrate);

        }
    }

    public DoctorBookingAdapter(ArrayList<Datum> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.finftimebooking_adapter, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Datum operator = transactionsList.get(position);
        SharedPreferences prefs = mContext.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
        String appointments = prefs.getString(ApplicationConstant.INSTANCE.Appointment, "");
        Gson gson = new Gson();
        GalleryListResponse sliderImage = gson.fromJson(appointments, GalleryListResponse.class);
      //  ArrayList<Datum> sliderLists = sliderImage.getData();
        holder.tv_title.setText("Start Time : " + operator.getStart_time());
        holder.tv_discuraption.setText("End Time : " + operator.getEnd_time());
        holder.craetedate.setText("Date : " + operator.getCreated_date());

        final Calendar today = Calendar.getInstance();
        final Calendar twoDaysLater = (Calendar) today.clone();
        twoDaysLater.add(Calendar.DATE, 15);
        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                int in = (monthOfYear + 1);

                                int dd = dayOfMonth;

                                int b = Integer.toString(in).length();
                                int ddd = Integer.toString(dd).length();


                                if (b == 2) {

                                    if (ddd == 2) {
                                        holder.date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                    } else if (ddd == 1) {

                                        holder.date.setText(year + "-" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);

                                    }

                                } else if (b == 1) {
                                    if (ddd == 2) {
                                        holder.date.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);

                                    } else if (ddd == 1) {

                                        holder.date.setText(year + "-0" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);

                                    }
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(twoDaysLater.getTimeInMillis());
                datePickerDialog.getDatePicker().setMinDate(today.getTimeInMillis());

                datePickerDialog.show();

            }
        });


        holder.bookcontinew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.date.getText().toString().trim().equalsIgnoreCase("")) {

                    Loader loader;
                    loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);


                    if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {

                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);

//                        UtilMethods.INSTANCE.BookAppointment(mContext, operator.getDoctor_id(), operator.getId(),
//                                holder.date.getText().toString().trim(), loader);

                    } else {
                        UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
                                mContext.getResources().getString(R.string.network_error_message));
                    }

                } else {
                    Toast.makeText(mContext, "Appointment Date Select", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}