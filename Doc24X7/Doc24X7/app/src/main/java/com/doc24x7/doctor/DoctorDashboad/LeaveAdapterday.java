package com.doc24x7.doctor.DoctorDashboad;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.doc24x7.doctor.Dashbord.dto.LeaveData;
import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.Loader;
import com.doc24x7.doctor.Utils.UtilMethods;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaveAdapterday extends RecyclerView.Adapter<LeaveAdapterday.mleaveDatalist> {
    public List<LeaveData> mleaveDatalist;
    public Context mcontext;
    CheckBox deleteAll;
    private Boolean isSelected = false;
    TextView deleteselected;
    private ArrayList<LeaveData> transactionsListNew;
    public LeaveAdapterday(List<LeaveData> mleaveDatalist, Context mcontext,CheckBox deleteAll, TextView deleteselected) {
        this.mleaveDatalist = mleaveDatalist;
        this.mcontext = mcontext;
        this.deleteAll=deleteAll;
        transactionsListNew = new ArrayList<>();
        this.deleteselected=deleteselected;
    }

    @NonNull
    @Override
    public LeaveAdapterday.mleaveDatalist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.doctorleaveview,parent,false);
        mleaveDatalist leaveDatalist=new mleaveDatalist(view);
        return leaveDatalist;
    }

    @Override
    public void onBindViewHolder(@NonNull LeaveAdapterday.mleaveDatalist holder, int position) {
        final LeaveData currentitem = mleaveDatalist.get(position);
        //  holder.leavedate.setText(currentitem.getLeavedate());
        holder.leavedate.setText(currentitem.getStartdate() + "\n" + currentitem.getEnddate());
        holder.Apponiment.setText("No. Appointment : "+currentitem.getAll_appointment()+"\n"+"       "+currentitem.getLeavedate());
        if (isSelected) {
            holder.checkBox.setChecked(true);
            if (!transactionsListNew.contains(currentitem))
                transactionsListNew.add(currentitem);
        } else {
            holder.checkBox.setChecked(false);
        }


        deleteAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    deleteselected.setVisibility(View.VISIBLE);

                    isSelected = true;
                    notifyDataSetChanged();
                }
                else
                {
                    deleteselected.setVisibility(View.GONE);

                    isSelected = false;
                    notifyDataSetChanged();
                }
            }

        });



    /*    deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelected = true;
                notifyDataSetChanged();
            }
        });*/
        String datet = currentitem.getLeavedate();
        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = inFormat.parse(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
        String goal = outFormat.format(date);
        holder.nameofday.setText(currentitem.getLeavedate());
        deleteselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Loader loader = new Loader(mcontext, android.R.style.Theme_Translucent_NoTitleBar);
                if(transactionsListNew.size()>0){
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                }
                for (int i = 0; i < transactionsListNew.size(); i++) {
                    String leaveID=transactionsListNew.get(i).getLeaveid();
                    mleaveDatalist.remove(mleaveDatalist.indexOf(transactionsListNew.get(i)));
                    notifyDataSetChanged();
                    if (UtilMethods.INSTANCE.isNetworkAvialable(mcontext)) {
                        String url = "http://doc24x7.co.in/api/Doctors/DeleteLeaveBySlot?id=" + leaveID;
                        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(mcontext, "Data is Deleted", Toast.LENGTH_SHORT).show();

                                loader.dismiss();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                loader.dismiss();
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap<String, String> header = new HashMap<String, String>();
                                header.put("x-api-key", "apikey@animationmedia.org");

                                return header;
                            }


                        };
                        RequestQueue requestQueue= Volley.newRequestQueue(mcontext);
                        requestQueue.add(stringRequest);
                    } else {
                        UtilMethods.INSTANCE.NetworkError(mcontext, mcontext.getResources().getString(R.string.network_error_title),
                                mcontext.getResources().getString(R.string.network_error_message));
                    }
                }
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //  SchedulingFragment.scheduleID.add(Integer.parseInt(currentitem.getId()));
                    transactionsListNew.add(currentitem);
                    if (transactionsListNew.size() > 0) {
                        deleteselected.setVisibility(View.VISIBLE);
                    } else {
                        deleteselected.setVisibility(View.GONE);
                    }
                } else {

                    try {
                        //   SchedulingFragment.scheduleID.remove(position);
                        transactionsListNew.remove(position);
                        if (transactionsListNew.size() > 0) {
                            deleteselected.setVisibility(View.VISIBLE);
                        } else {
                            deleteselected.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure,You want to delete leave ?");
                builder.setCancelable(true);
                builder.setIcon(R.drawable.ic_cancel_black_24dp);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Loader loader = new Loader(mcontext, android.R.style.Theme_Translucent_NoTitleBar);
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);
                        String url = "http://doc24x7.co.in/api/Doctors/DeleteLeaveBySlot?id=" + currentitem.getLeaveid();
                        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(mcontext, "Data is Deleted", Toast.LENGTH_SHORT).show();
                                mleaveDatalist.remove(position);
                                notifyDataSetChanged();
                                loader.dismiss();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                         loader.dismiss();
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap<String, String> header = new HashMap<String, String>();
                                header.put("x-api-key", "apikey@animationmedia.org");

                                return header;
                            }


                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(mcontext);
                        requestQueue.add(stringRequest);
                    }

                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                notifyDataSetChanged();
            }


        });


    }







    @Override
    public int getItemCount() {
        return mleaveDatalist.size();
    }

    public class mleaveDatalist extends RecyclerView.ViewHolder{
        TextView leavedate,nameofday,Apponiment;
        ImageView remove;
        CheckBox checkBox;
        public mleaveDatalist(@NonNull View itemView) {
            super(itemView);
            leavedate=itemView.findViewById(R.id.leave_date);
            nameofday=itemView.findViewById(R.id.nameofday);
            checkBox = itemView.findViewById(R.id.checkbox);
            remove=itemView.findViewById(R.id.remove);
            Apponiment=itemView.findViewById(R.id.Apponiment);
        }
    }
}
