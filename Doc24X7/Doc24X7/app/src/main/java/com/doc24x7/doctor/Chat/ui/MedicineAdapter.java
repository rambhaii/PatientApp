

package com.doc24x7.doctor.Chat.ui;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.doc24x7.doctor.Dashbord.dto.Datum;
import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.DETAIL;

import java.util.ArrayList;
import java.util.Locale;


public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MyViewHolder> {

    private ArrayList<DETAIL> transactionsList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView detail ;
        ImageView cancel;
        public MyViewHolder(View view) {
            super(view);
            detail =  view.findViewById(R.id.detail);
            cancel =  view.findViewById(R.id.cancel);
        }
    }
    public MedicineAdapter(ArrayList<DETAIL> MeicineModel, Context mContext ) {
        this.transactionsList = MeicineModel;
        this.context = mContext;
        notifyDataSetChanged();
     }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mediline_adapter, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final DETAIL operator = transactionsList.get(position);
        String unspanned = String.format(Locale.US, "%s%s", (position+1)+". <b>"+operator.getMedicineName()
                +"</b> "+operator.getMedicimeFreQuency()
                +" "+operator.getQuantity()
                +" "+operator.getMedicimeDays()
                +" "+operator.getWhentouse()
                +" "+operator.getHowtouse(), "");

        Spanned spanned;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            spanned = Html.fromHtml(unspanned, Html.FROM_HTML_MODE_LEGACY);
        } else {
            spanned = Html.fromHtml(unspanned);
        }
        holder.detail.setText(spanned
        );
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transactionsList.remove(position)   ;
                notifyDataSetChanged();
            }
        });
     }
    @Override
    public int getItemCount() {

        return transactionsList.size();
    }
}