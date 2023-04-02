package com.doc24x7.doctor.DoctorDashboad;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.doc24x7.doctor.Dashbord.dto.Datum;
import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.Loader;
import com.doc24x7.doctor.Utils.UtilMethods;
import com.google.gson.Gson;

import java.util.ArrayList;


public class DoctorPatientAdapter extends RecyclerView.Adapter<DoctorPatientAdapter.MyViewHolder> {

    private ArrayList<Datum> transactionsList;
    private Context mContext;
    String type;

    private int mYear, mMonth, mDay, mHour, mMinute;


    public void filter(ArrayList<Datum> newlist) {
        transactionsList = new ArrayList<>();
        transactionsList.addAll(newlist);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title, tv_discuraption, craetedate, enddate;
        RelativeLayout idCardView;


        public MyViewHolder(View view) {
            super(view);
            tv_title = view.findViewById(R.id.tv_title);
            tv_discuraption = view.findViewById(R.id.tv_discuraption);
            craetedate = view.findViewById(R.id.craetedate);
            enddate = view.findViewById(R.id.enddate);
            tv_discuraption = view.findViewById(R.id.tv_discuraption);
            idCardView = view.findViewById(R.id.idCardView);

        }
    }

    public DoctorPatientAdapter(ArrayList<Datum> transactionsList, Context mContext, String type) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.type = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patiend_booking_new_adapter, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Datum operator = transactionsList.get(position);
        if (type.equalsIgnoreCase("6")) {

            holder.enddate.setVisibility(View.VISIBLE);
            holder.enddate.setText("Chat");
            holder.craetedate.setText("View");

            holder.tv_title.setText("" + operator.getName());

            //  holder.tv_title.setText("Patient Name");
            holder.tv_discuraption.setText("" + operator.getMobile());


            holder.enddate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String url = "https://api.whatsapp.com/send?phone=" + "+91" + operator.getMobile();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    mContext.startActivity(i);


                }
            });
//            holder.enddate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    UtilMethods.INSTANCE.GetAccessToken(mContext,operator.getId()+"");
//
////                    Intent i = new Intent(mContext, CallActivity.class);
////                    i.putExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME, "group");
////                    i.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_KEY, "encryption");
////                    i.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_MODE, "AES-128-XTS");
////                    mContext.startActivity(i);
//
//                }
//            });

            holder.craetedate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext,DOctoeByIdActivity.class);
                    intent.putExtra("response", "");
                    intent.putExtra("type", "8");
                    intent.putExtra("pa_name", "" + operator.getName());
                    intent.putExtra("pa_mobile", "" + operator.getMobile());
                    intent.putExtra("status","1");
                    intent.putExtra("profilePIC", "" + operator.getProfilePic());
                    intent.putExtra("Appointment_id", "" +"");
                    intent.putExtra("useridpref", "" + operator.getId());
                    mContext.startActivity(intent);
//                    Loader loader;
//                    loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
//
//                    if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {
//
//                        loader.show();
//                        loader.setCancelable(false);
//                        loader.setCanceledOnTouchOutside(false);
//
//                        UtilMethods.INSTANCE.DrPatientDetails(mContext, operator.getId(),  operator.getName(), operator.getMobile(),operator.getProfilePic(),loader);
//
//                    } else {
//                        UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
//                                mContext.getResources().getString(R.string.network_error_message));
//                    }
                }
            });

        } else {

            holder.tv_title.setText("Doctor Name : " + operator.getDoctor_name());
            holder.enddate.setText("End time : " + operator.getEnd_time());
            holder.craetedate.setText("Start time : " + operator.getStart_time());
            holder.tv_discuraption.setText("Experience : " + operator.getExperience());

        }

    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}