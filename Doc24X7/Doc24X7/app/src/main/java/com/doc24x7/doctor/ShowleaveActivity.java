package com.doc24x7.doctor;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.doc24x7.doctor.Dashbord.dto.LeaveData;
import com.doc24x7.doctor.DoctorDashboad.LeaveAdapter;
import com.doc24x7.doctor.DoctorDashboad.LeaveAdapterday;
import com.doc24x7.doctor.Login.dto.Data;
import com.doc24x7.doctor.Utils.ApplicationConstant;
import com.doc24x7.doctor.Utils.Loader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowleaveActivity extends AppCompatActivity {
int i;
RequestQueue requestQueue;
RecyclerView recyclerView, recycleleavebyday;
    TextView contentnumber,name;
TextView show_date_leave,show_day_leave,selectedAll,deleteselected;
CheckBox selectedAll_check;
Loader loader;
    String id="";
    LeaveAdapter adapter;
    LeaveAdapterday adapterday;
ArrayList<LeaveData> leaveDatalist=new ArrayList<>();
ArrayList<LeaveData> leaveDatalistschedule=new ArrayList<>();
RadioButton rad_show_day_leave ,rad_show_date_leave;
ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showleave);

        back=findViewById(R.id.back);
        selectedAll_check=findViewById(R.id.selectedAll_check);
        rad_show_day_leave=findViewById(R.id.rad_show_day_leave);
        rad_show_date_leave=findViewById(R.id.rad_show_date_leave);
        rad_show_date_leave.setChecked(true);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        name = findViewById(R.id.name);
        contentnumber = findViewById(R.id.contentnumber);
        selectedAll = findViewById(R.id.selectedAll);
        deleteselected=findViewById(R.id.deleteselected);
        name.setText("Dr. " + balanceCheckResponse.getName());
        contentnumber.setText("" + balanceCheckResponse.getMobile());


        selectedAll_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {


                    deleteselected.setVisibility(View.VISIBLE);

                    // checked
                }
                else
                {

                    deleteselected.setVisibility(View.GONE);

                    // not checked
                }
            }

        });




//
      id=  balanceCheckResponse.getId();

        //Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();


        recyclerView=findViewById(R.id.recycleleavebydate);
        recycleleavebyday=findViewById(R.id.recycleleavebyday);
        requestQueue= Volley.newRequestQueue(getApplicationContext());
       loader=new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        loader.setCancelable(false);
        loader.setCanceledOnTouchOutside(false);
        show_date_leave=findViewById(R.id.show_date_leave);
        show_day_leave=findViewById(R.id.show_day_leave);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new LeaveAdapter(leaveDatalist, com.doc24x7.doctor.ShowleaveActivity.this,selectedAll_check,deleteselected);
        recyclerView.setAdapter(adapter);
        showleavebyDate();

       // recyclerView=findViewById(R.id.dealerrecycler);
       // showleavebyDate();

        rad_show_date_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rad_show_date_leave.setChecked(true);
                rad_show_day_leave.setChecked(false);

              //  show_day_leave.setBackground(getResources().getDrawable(R.drawable.rect));
             //   show_day_leave.setTextColor(getResources().getColor(R.color.black));
            //    show_date_leave.setBackground(getResources().getDrawable(R.drawable.rect_blue));
                // mon.setBackground(getResources().getDrawable(R.drawable.rect));
             //   show_date_leave.setTextColor(getResources().getColor(R.color.white));

                recycleleavebyday.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                showleavebyDate();

            }
        });

        rad_show_day_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rad_show_day_leave.setChecked(true);
                rad_show_date_leave.setChecked(false);

                recyclerView.setVisibility(View.GONE);
                StaggeredGridLayoutManager mlayoutManager=new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL);
                recycleleavebyday.setLayoutManager(mlayoutManager);



            //   show_date_leave.setBackground(getResources().getDrawable(R.drawable.rect));
             //   show_date_leave.setTextColor(getResources().getColor(R.color.black));
             //   show_day_leave.setBackground(getResources().getDrawable(R.drawable.rect_blue));
               // mon.setBackground(getResources().getDrawable(R.drawable.rect));
             //   show_day_leave.setTextColor(getResources().getColor(R.color.white));


                showleavebyschedule();
                recycleleavebyday.setVisibility(View.VISIBLE);


            }
        });
    }

    public void  showleavebyDate()
    {
        leaveDatalist.clear();
       loader.isShowing();
        loader.show();
        loader.setCancelable(false);
        loader.setCanceledOnTouchOutside(false);
        String Url="http://doc24x7.co.in/api/Doctors/GetDoctorLeaveByDate?doctor_id="+id;
        StringRequest request=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             //    Toast.makeText(ShowleaveActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("Data");
                    if(jsonArray.isNull(0)) {
                        Toast.makeText(com.doc24x7.doctor.ShowleaveActivity.this, "No Leave", Toast.LENGTH_SHORT).show();
                        loader.dismiss();
                    }
                    // Toast.makeText(ShowAllDealerActivity.this, ""+jsonArray, Toast.LENGTH_SHORT).show();
                    for(i=0; i < jsonArray.length() ; i++)
                    {
                        JSONObject object=jsonArray.getJSONObject(i);

                     LeaveData leaveData=new LeaveData(
                         object.getString("id"),
                         object.getString("leave_date"),
                         object.getString("created_date"),
                             object.getString("all_appointment")
                     );
                        loader.dismiss();
                        leaveDatalist.add(leaveData);

                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    loader.dismiss();
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(com.doc24x7.doctor.ShowleaveActivity.this, "Network is slow", Toast.LENGTH_SHORT).show();
                loader.dismiss();
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("x-api-key", "apikey@animationmedia.org");
                return header;
            }

        };
        requestQueue.add(request);
    }
    public void  showleavebyschedule()
    {
        leaveDatalistschedule.clear();
        loader.isShowing();
        loader.show();
        loader.setCancelable(false);
        loader.setCanceledOnTouchOutside(false);
        String Url="http://doc24x7.co.in/api/Doctors/GetDoctorLeaveBySlot?doctor_id="+id;
        StringRequest request=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(ShowleaveActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("Data");
                    if(jsonArray.isNull(0)) {
                        Toast.makeText(com.doc24x7.doctor.ShowleaveActivity.this, "No Leave", Toast.LENGTH_SHORT).show();
                        loader.dismiss();
                    }
                    // Toast.makeText(ShowAllDealerActivity.this, ""+jsonArray, Toast.LENGTH_SHORT).show();
                    for(i=0; i < jsonArray.length() ; i++)
                    {
                        JSONObject object=jsonArray.getJSONObject(i);
                        LeaveData leaveData=new LeaveData(
                                object.getString("id"),
                                object.getString("leave_date"),
                                object.getString("created_date"),
                                object.getString("start_time"),
                                object.getString("end_time"),
                                object.getString("all_appointment")
                        );

                     leaveDatalistschedule.add(leaveData);
                    }
                    adapterday=new LeaveAdapterday(leaveDatalistschedule, com.doc24x7.doctor.ShowleaveActivity.this,selectedAll_check,deleteselected);
                    adapterday.notifyDataSetChanged();
                    recycleleavebyday.setAdapter(adapterday);

                    loader.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    loader.dismiss();
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(com.doc24x7.doctor.ShowleaveActivity.this, "Network is Slow", Toast.LENGTH_SHORT).show();
                loader.dismiss();
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("x-api-key", "apikey@animationmedia.org");
                return header;
            }

        };
        requestQueue.add(request);
    }
}