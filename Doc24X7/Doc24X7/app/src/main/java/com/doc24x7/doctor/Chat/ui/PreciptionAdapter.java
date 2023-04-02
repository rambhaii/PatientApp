package com.doc24x7.doctor.Chat.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.doc24x7.doctor.Dashbord.dto.Datum;
import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.UtilMethods;

import java.util.ArrayList;


public class PreciptionAdapter extends RecyclerView.Adapter<PreciptionAdapter.MyViewHolder> {

    private ArrayList<Datum> transactionsList;
    private Context mContext;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name ,book_date,Preciptiontext,usermobile;
        ImageView prection_image;
        RecyclerView recycler_medicine;



        public MyViewHolder(View view) {
            super(view);

            name =  view.findViewById(R.id.name);
            book_date =  view.findViewById(R.id.book_date);
            Preciptiontext =  view.findViewById(R.id.Preciptiontext);
            usermobile =  view.findViewById(R.id.usermobile);
            prection_image =  view.findViewById(R.id.prection_image);
            recycler_medicine =  view.findViewById(R.id.recycler_medicine);

        }
    }

    public void filter(ArrayList<Datum> newlist) {
        transactionsList=new ArrayList<>();
        transactionsList.addAll(newlist);
        notifyDataSetChanged();
    }


    public PreciptionAdapter(ArrayList<Datum> transactionsList, Context mContext ) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
     }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.preciption_adapter, parent, false);

        return new MyViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Datum operator = transactionsList.get(position);

        holder.name.setText(""+operator.getName());
        holder.book_date .setText(""+operator.getBook_date());
        holder.Preciptiontext.setText(""+operator.getDescription());
        holder.usermobile.setText(""+operator.getUser_mobile());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.customer_support);
        requestOptions.error(R.drawable.customer_support);


        Glide.with(mContext)
                .load(operator.getPreciption_img())
                .apply(requestOptions)
                .into(holder.prection_image);



        UtilMethods.INSTANCE.GetMedicineById(mContext, operator.getMedicine_id() ,holder.recycler_medicine );






     }
    @Override
    public int getItemCount() {
        return transactionsList.size();
    }
}