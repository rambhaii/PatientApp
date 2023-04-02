package com.doc24x7.doctor.Dashbord.ui;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.doc24x7.doctor.Dashbord.dto.Datum;
import com.doc24x7.doctor.R;
import com.zolad.zoominimageview.ZoomInImageView;
import com.zolad.zoominimageview.ZoomInImageViewAttacher;

import java.util.ArrayList;

public class ViewReportAdapter extends RecyclerView.Adapter<ViewReportAdapter.transactionsList>{
    public ArrayList<String> transactionsListv;
    private Activity mContext;
    public ViewReportAdapter(ArrayList<String> transactionsList, Activity mContext)
    {
        this.transactionsListv=transactionsList;
        this.mContext=mContext;

    }

    @NonNull
    @Override
    public ViewReportAdapter.transactionsList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewreport,parent,false);
        transactionsList transactionsList=new transactionsList(view);
        return transactionsList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewReportAdapter.transactionsList holder, int position) {

        final RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.noavailable);
        requestOptions.error(R.drawable.doctordemo);
        //Toast.makeText(mContext, "transactionsListv.get(position)", Toast.LENGTH_SHORT).show();
        Log.d("urrrrrrrrrrrrrrrrrrr: ",transactionsListv.get(position));
        Glide.with(mContext)
                .load(transactionsListv.get(position))
                .apply(requestOptions)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZoomInImageViewAttacher mIvAttacter = new ZoomInImageViewAttacher(holder.imageView);
               }
        });
    }


    @Override
    public int getItemCount() {
        return transactionsListv.size();
    }
   public class transactionsList extends RecyclerView.ViewHolder{
       ZoomInImageView imageView;
      // ImageView zoomable;
      // ZoomInImageView zoomable = new ZoomInImageView(mContext);
       ZoomInImageViewAttacher mIvAttacter = new ZoomInImageViewAttacher();

       public transactionsList(@NonNull View itemView) {
           super(itemView);
           imageView=itemView.findViewById(R.id.imgreport);
           // zoomable=itemView.findViewById(R.id.iv_zoominpic);
       }
   }

}
