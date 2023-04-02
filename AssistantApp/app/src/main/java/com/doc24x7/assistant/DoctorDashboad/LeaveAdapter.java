package com.doc24x7.assistant.DoctorDashboad;

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
import com.doc24x7.assistant.Dashbord.dto.LeaveData;
import com.doc24x7.assistant.R;
import com.doc24x7.assistant.Utils.Loader;
import com.doc24x7.assistant.Utils.UtilMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LeaveAdapter extends RecyclerView.Adapter<LeaveAdapter.mleaveDatalist> {
 public ArrayList<LeaveData> mleaveDatalist;
 public Context mcontext;
    private Boolean isSelected = false;
    TextView deleteAll;
    TextView deleteselected;
    private ArrayList<LeaveData> transactionsListNew;
    public LeaveAdapter(ArrayList<LeaveData> mleaveDatalist, Context mcontext, TextView deleteAll, TextView deleteselected) {
        this.mleaveDatalist = mleaveDatalist;
        this.mcontext = mcontext;
        transactionsListNew = new ArrayList<>();
        this.deleteAll=deleteAll;
        this.deleteselected=deleteselected;

    }

    @NonNull
    @Override
    public LeaveAdapter.mleaveDatalist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.doctorleaveview,parent,false);
        mleaveDatalist leaveDatalist=new mleaveDatalist(view);

        return leaveDatalist;
    }

    @Override
    public void onBindViewHolder(@NonNull final LeaveAdapter.mleaveDatalist holder, final int position) {
        final LeaveData currentitem=mleaveDatalist.get(position);
         holder.leavedate.setText(currentitem.getLeavedate());
        holder.Apponiment.setText("No. Appointment\n              "+currentitem.getAll_appointment());
        if (isSelected) {
            holder.checkBox.setChecked(true);
            if (!transactionsListNew.contains(currentitem))
                transactionsListNew.add(currentitem);
        } else {
            holder.checkBox.setChecked(false);
        }
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelected = true;
                notifyDataSetChanged();
            }
        });
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
                    if (UtilMethods.INSTANCE.isNetworkAvialable(mcontext)) {
                        String url="http://doc24x7.co.in/api/Doctors/DeleteLeaveByDate?id="+leaveID;
                        StringRequest stringRequest=new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(mcontext, "Data is Deleted", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                                loader.dismiss();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                loader.dismiss();
                            }
                        })
                        {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap<String, String> header = new HashMap<String, String>();
                                header.put("x-api-key", "apikey@animationmedia.org");

                                return header;
                            }

                            //   @Override
//                           protected Map<String, String> getPostParams() throws AuthFailureError {
//
//                               Map<String,String> hm=new HashMap<String, String>();
//                              hm.put("id",currentiteam.getId());
//                              return hm;
//                           }
                            //                           protected Map<String, String> getParams() throws AuthFailureError {
//                               Map<String,String> hm=new HashMap<String, String>();
//                               hm.put("id",currentiteam.getId());
//                               return hm;
//                           }
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

              AlertDialog.Builder builder=new AlertDialog.Builder(mcontext);
              builder.setTitle("Delete");
              builder.setMessage("Are you sure,You want to delete ?");
              builder.setCancelable(true);
              builder.setIcon(R.drawable.ic_cancel_black_24dp);

              builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      final Loader loader = new Loader(mcontext, android.R.style.Theme_Translucent_NoTitleBar);
                      loader.show();
                      loader.setCancelable(false);
                      loader.setCanceledOnTouchOutside(false);
                      String url="http://doc24x7.co.in/api/Doctors/DeleteLeaveByDate?id="+currentitem.getLeaveid();
                      StringRequest stringRequest=new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
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
                      })
                      {
                          @Override
                          public Map<String, String> getHeaders() throws AuthFailureError {
                              HashMap<String, String> header = new HashMap<String, String>();
                              header.put("x-api-key", "apikey@animationmedia.org");

                              return header;
                          }

                          //   @Override
//                           protected Map<String, String> getPostParams() throws AuthFailureError {
//
//                               Map<String,String> hm=new HashMap<String, String>();
//                              hm.put("id",currentiteam.getId());
//                              return hm;
//                           }
                          //                           protected Map<String, String> getParams() throws AuthFailureError {
//                               Map<String,String> hm=new HashMap<String, String>();
//                               hm.put("id",currentiteam.getId());
//                               return hm;
//                           }
                      };
                      RequestQueue requestQueue= Volley.newRequestQueue(mcontext);
                      requestQueue.add(stringRequest);
                  }

              });
              builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
//                       MDToast mdToast = MDToast.makeText(mcontext, "Da",MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING);
//                       mdToast.show();

                  }
              });
              AlertDialog alertDialog = builder.create();
              alertDialog.show();
         notifyDataSetChanged();
          }


      });


        // holder.leavedate.setText(currentitem.getStartdate()+"\n" +currentitem.getEnddate());
    }

    @Override
    public int getItemCount() {
        return mleaveDatalist.size();
    }

    public class mleaveDatalist extends RecyclerView.ViewHolder{
              TextView leavedate,selectedAll,Apponiment;
              ImageView remove;
        CheckBox checkBox;
        public mleaveDatalist(@NonNull View itemView) {
            super(itemView);
            leavedate=itemView.findViewById(R.id.leave_date);
           remove=itemView.findViewById(R.id.remove);
            checkBox = itemView.findViewById(R.id.checkbox);
            selectedAll = itemView.findViewById(R.id.selectedAll);
            Apponiment = itemView.findViewById(R.id.Apponiment);
        }
    }
}
