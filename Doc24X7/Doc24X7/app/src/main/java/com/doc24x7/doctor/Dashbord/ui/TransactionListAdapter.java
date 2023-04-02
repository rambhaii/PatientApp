package com.doc24x7.doctor.Dashbord.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.doc24x7.doctor.Dashbord.dto.Datum;
import com.doc24x7.doctor.R;

import java.util.ArrayList;


public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.MyViewHolder> {

    private ArrayList<Datum> transactionsList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView medicineName,medicineFrequency,medicinedays,medicineHowtouse,a,count,txnDate;
        LinearLayout idCardView;
        ImageView opImage;


        public MyViewHolder(View view) {
            super(view);
            medicineName =  view.findViewById(R.id.medicineName);
            medicineFrequency =  view.findViewById(R.id.medicineFrequency);
            medicinedays =  view.findViewById(R.id.medicinedays);
            medicineHowtouse =  view.findViewById(R.id.medicineHowtouse);
            a =  view.findViewById(R.id.a);
            count =  view.findViewById(R.id.count);
            txnDate =  view.findViewById(R.id.txnDate);


        }
    }

    public TransactionListAdapter(ArrayList<Datum> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transction_list_adapter, parent, false);

        return new MyViewHolder(itemView);

    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Datum operator = transactionsList.get(position);
        int count =position+1;
        holder.count.setText(""+count +". ");
        holder.medicineName.setText(" " + operator.getName().replace("\"",""));
        holder.medicinedays.setText(" " + operator.getMobile().replace("\"",""));
        holder.medicineFrequency.setText("\u20B9 " + operator.getAmount().replace("\"",""));
        holder.medicineHowtouse.setText(" " + operator.getTxt_id().replace("\"",""));
        holder.txnDate.setText(" " + operator.getTxt_date().replace("\"",""));
        holder.a.setText(" " + operator.getPayment_status().replace("\"",""));
    }
    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}