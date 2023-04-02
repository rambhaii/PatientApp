package com.doc24x7.doctor.DoctorDashboad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.doc24x7.doctor.Dashbord.dto.Datum;
import com.doc24x7.doctor.R;

import java.util.ArrayList;


public class AllSymtomsOtpAdapter extends RecyclerView.Adapter<AllSymtomsOtpAdapter.MyViewHolder> {

    private ArrayList<Datum> transactionsList;
    private Context mContext;
    private int row_index;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        ImageView image;
        CardView idCardView;
        CheckBox select_type;


        public MyViewHolder(View view) {
            super(view);
            tv_title =  view.findViewById(R.id.title);
              idCardView =  view.findViewById(R.id.idCardView);
            image =  view.findViewById(R.id.image);
            select_type =  view.findViewById(R.id.select_type);

        }
    }

    public AllSymtomsOtpAdapter(ArrayList<Datum> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.allsymtoms_otp_adapter, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Datum operator = transactionsList.get(position);

        holder.tv_title.setText("" + operator.getSymtom());

holder.select_type.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        row_index = position;
        notifyDataSetChanged();


      //  ((OtpActivityNotRegister) mContext).ItemClick(operator.getId());

    }
});


        if (row_index == position) {
            holder.select_type.setChecked(true);


        } else {
             holder.select_type.setChecked(false);
        }


    }
    @Override
    public int getItemCount() {
        return transactionsList.size();
    }


}