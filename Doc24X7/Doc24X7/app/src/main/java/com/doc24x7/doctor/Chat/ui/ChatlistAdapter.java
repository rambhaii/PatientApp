package com.doc24x7.doctor.Chat.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.doc24x7.doctor.Dashbord.dto.Datum;
import com.doc24x7.doctor.R;

import java.util.ArrayList;


public class ChatlistAdapter extends RecyclerView.Adapter<ChatlistAdapter.MyViewHolder> {

    private ArrayList<Datum> transactionsList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView medicine_name ,created_date;
        RelativeLayout main;


        public MyViewHolder(View view) {
            super(view);

            medicine_name =  view.findViewById(R.id.medicine_name);
            created_date =  view.findViewById(R.id.created_date);
            main =  view.findViewById(R.id.main);

        }
    }

    public void filter(ArrayList<Datum> newlist) {
        transactionsList=new ArrayList<>();
        transactionsList.addAll(newlist);
        notifyDataSetChanged();
    }


    public ChatlistAdapter(ArrayList<Datum> transactionsList, Context mContext ) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
     }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_adapter, parent, false);

        return new MyViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Datum operator = transactionsList.get(position);


        holder.medicine_name.setText(""+operator.getMedicine_name().replace("U+0022",""));
        holder.created_date.setText(""+operator.getCreated_date().replace("U+0022",""));


        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((UploadPrescriptionActivity) mContext).ItemClick(operator.getMedicine_name());



            }
        });


     }
    @Override
    public int getItemCount() {
        return transactionsList.size();
    }
}