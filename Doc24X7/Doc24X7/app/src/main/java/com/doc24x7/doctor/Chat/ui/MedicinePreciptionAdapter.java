package com.doc24x7.doctor.Chat.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.doc24x7.doctor.Dashbord.dto.Datum;
import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.UtilMethods;

import java.util.ArrayList;


public class MedicinePreciptionAdapter extends RecyclerView.Adapter<MedicinePreciptionAdapter.MyViewHolder> {

    private ArrayList<Datum> transactionsList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView medicine_name  ;



        public MyViewHolder(View view) {
            super(view);

            medicine_name =  view.findViewById(R.id.medicine_name);

        }
    }

    public void filter(ArrayList<Datum> newlist) {
        transactionsList=new ArrayList<>();
        transactionsList.addAll(newlist);
        notifyDataSetChanged();
    }


    public MedicinePreciptionAdapter(ArrayList<Datum> transactionsList, Context mContext ) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
     }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_preciption_adapter, parent, false);

        return new MyViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Datum operator = transactionsList.get(position);

        int cooo =position+1;


        String mmm=operator.getMedicine_name().toString().trim().replace("U+005C"," ");
        String  mm=mmm.replace("U+0022","");

        Log.e("medicine"," Medicine  :    "+ mmm+"   mm   :  "+ mm);

        holder.medicine_name.setText(""+ cooo +" .   "  +operator.getMedicine_name().replace("U+005C"," "));



     }
    @Override
    public int getItemCount() {
        return transactionsList.size();
    }
}