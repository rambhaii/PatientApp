package com.doc24x7.assistant.DoctorDashboad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.doc24x7.assistant.Login.dto.Qualification;
import com.doc24x7.assistant.R;

import java.util.ArrayList;


public class QualificationAdapter extends RecyclerView.Adapter<QualificationAdapter.MyViewHolder> {

    private ArrayList<Qualification> transactionsList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title,tv_college,tv_passing,tv_speciality;
        LinearLayout idCardView;
        ImageView tv_cancel;

        public MyViewHolder(View view) {
            super(view);
            tv_title =  view.findViewById(R.id.tv_title);
            tv_passing =  view.findViewById(R.id.tv_passing);
            tv_college =  view.findViewById(R.id.tv_college);
            tv_cancel =  view.findViewById(R.id.tv_cancel);
            tv_speciality=view.findViewById(R.id.tv_speciality);
        }
    }

    public QualificationAdapter(ArrayList<Qualification> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.qualificatioq_adapter, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {
        final Qualification operator = transactionsList.get(position);
        holder.tv_title.setText("" + operator.getQualification_name());
        holder.tv_passing.setText("" + operator.getPassing_year());
        holder.tv_college.setText("" + operator.getCollege_name());
        holder.tv_speciality.setText(""+ operator.getSpeciality());
        holder.tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactionsList.remove(position);
                notifyDataSetChanged();
            }
        });

    }
    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}