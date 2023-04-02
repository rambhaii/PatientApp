package com.doc24x7.doctor.Dashbord.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.doc24x7.doctor.Dashbord.dto.Datum;
import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.Loader;
import com.doc24x7.doctor.Utils.UtilMethods;
import com.doc24x7.doctor.Voice.VoiceChatViewActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static androidx.core.content.ContextCompat.getDrawable;


public class DoctorListDashboadAdapter extends RecyclerView.Adapter<DoctorListDashboadAdapter.MyViewHolder> {
    private ArrayList<Datum> transactionsList;
    private Activity mContext;
    String type;
    String mobile;
    String name, symptoms;
    String Appointment_id;
    String useridpref;
    Loader loader;
    String profilePIC;
     String status;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,bookingby,specialities,alldata,address,qualification, Starttime,Endtime, book_date, Preciptiontext, id_chatnow,chat,voicecall,viewreport,emergency_time;
        ImageView opImage, prection;
        TextView idCardView;
        LinearLayout liii, ll;
        TextView tv_gender,tv_symptoms,consultdate_date;
        RatingBar ratingBar;
        ImageView locat;
        LinearLayout Preciptiontextli,lifirst,linear_time;
        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            specialities = view.findViewById(R.id.specialities);
            qualification = view.findViewById(R.id.qualification);
            address = view.findViewById(R.id.address);
            opImage = view.findViewById(R.id.opImage);
            prection = view.findViewById(R.id.prection);
            idCardView = view.findViewById(R.id.idCardView);
            id_chatnow = view.findViewById(R.id.enddate);
            chat = view.findViewById(R.id.chat);
            voicecall = view.findViewById(R.id.voicecall);
            ratingBar = view.findViewById(R.id.ratingBar);
            Preciptiontextli = view.findViewById(R.id.Preciptiontextli);
            Preciptiontext = view.findViewById(R.id.Preciptiontext);
            Starttime = view.findViewById(R.id.Starttime);
            Endtime = view.findViewById(R.id.Endtime);
            book_date = view.findViewById(R.id.book_date);
            liii = view.findViewById(R.id.liii);
            ll = view.findViewById(R.id.ll);
            locat = view.findViewById(R.id.locat);
            lifirst=view.findViewById(R.id.lifirst);
            viewreport=view.findViewById(R.id.viewreport);
            ll=view.findViewById(R.id.ll);
           // tv_gender=view.findViewById(R.id.tv_gender);
            tv_symptoms=view.findViewById(R.id.tv_synptoms);
            bookingby=view.findViewById(R.id.bookingby);
            alldata=view.findViewById(R.id.alldata);
            consultdate_date=view.findViewById(R.id.cunsultdate_date);
            linear_time=view.findViewById(R.id.linear_time);
            emergency_time=view.findViewById(R.id.Emergency);
        }
    }
    public DoctorListDashboadAdapter(ArrayList<Datum> transactionsList, Activity mContext, String type, String name, String mobile, String Appointment_id, String useridpref, String profilePIC,String status) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.type = type;
        this.name = name;
        this.Appointment_id = Appointment_id;
        this.useridpref = useridpref;
        this.mobile = mobile;
        this.symptoms = symptoms;
        this.profilePIC = profilePIC;
        this.status=status;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_dashboad_adapter, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Datum operator = transactionsList.get(position);
        holder.ratingBar.setVisibility(View.GONE);
        holder.Preciptiontextli.setVisibility(View.GONE);
       // Toast.makeText(mContext, ""+operator.getAppointment_id(), Toast.LENGTH_SHORT).show();
        String Age = "";
        String Gender = "";
        String Symptoms = "";
        String gender = "";
        String PatientName="";
        String PatientMobile="";
        String Type="";
        String Weight="";
        String Height="";
        String Bp="";
        String Sugar="";
        try {
            Log.d("PatientDetails: ",operator.getPatient_details());
            JSONObject object=new JSONObject(operator.getPatient_details());
            Log.e( "modallllllll: ", operator.getPatient_details());
            Age=object.getString("PatientAge");
            Gender=object.getString("Gender");
            Type=object.getString("Type");
            String  ReportUrl=object.getString("ReportUrl");
            PatientName=object.getString("PatientName");
            PatientMobile=object.getString("PatientMobile");
            Weight=object.getString("Weight");
            Height=object.getString("Height");
            Bp=object.getString("Bp");
            Sugar=object.getString("Sugar");

            //Toast.makeText(mContext, ""+Appointment_id, Toast.LENGTH_SHORT).show();
            if(!Weight.equals("")||!Height.equals("")||!Bp.equals("")||!Sugar.equals(""))
            {
                holder.alldata.setVisibility(View.VISIBLE);
                  holder.alldata.setText((!Weight.equals("")?("Weight : "+Weight):" ")+
                                  (!Height.equals("")?("  Height : "+Height):" ")+
                                  (!Bp.equals("")?("  B P : "+Bp):" ")+
                                  (!Sugar.equals("")?("  Sugar : "+Sugar):" ")
                  );
            }


            if(!ReportUrl.equals("[]"))
            {
                holder.viewreport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext,ViewReport.class).putExtra("images",ReportUrl)
                                .putExtra("name",holder.name.getText().toString())
                                .putExtra("mobile",holder.qualification.getText().toString())
                        );
                    }
                });
            }
            else
            {
                holder.viewreport.setVisibility(View.GONE);
            }
             if(!Gender.equals(""))
             {
                gender= String.valueOf(Gender.charAt(0));
             }
             else {
                 //Toast.makeText(mContext, ""+Gender, Toast.LENGTH_SHORT).show();
               //  gender=" ";
             }
             Symptoms=object.getString("PatientSymption");

        } catch (JSONException e) {
            e.printStackTrace();
        }
      // Toast.makeText(mContext, ""+ReportUrl, Toast.LENGTH_SHORT).show();
        holder.tv_symptoms.setText(Symptoms);
       // holder.tv_gender.setText(Gender);
        holder.prection.setVisibility(View.VISIBLE);
        holder.locat.setVisibility(View.GONE);
          if(status.equals("2"))
          {
              if(Type.equals("2"))
              {
                  holder.bookingby.setVisibility(View.VISIBLE);
                  holder.bookingby.setText("Booked By : Patient");
              }
              if(Type.equals("3"))
              {
                  holder.bookingby.setVisibility(View.VISIBLE);
                  holder.bookingby.setText("Booked By : Assistant");
              }
              holder.id_chatnow.setVisibility(View.GONE);
              holder.chat.setVisibility(View.GONE);
              holder.idCardView.setVisibility(View.GONE);
              holder.prection.setVisibility(View.INVISIBLE);
              holder.ll.setVisibility(View.GONE);
          }
        holder.idCardView.setText("Prescription");
        holder.Preciptiontext.setText(operator.getPreciption_description() == null ? "No prescription uploaded" : operator.getPreciption_description());
        holder.Starttime.setText("" + operator.getStart_time());
        holder.Endtime.setText("" + operator.getEnd_time());
       // String strdate=operator.getBook_date().split(" ")[0]+" & Time "+operator.getBook_date().split(" ")[1]+" "+operator.getBook_date().split(" ")[2];
//        if(operator.getEnd_time().equalsIgnoreCase("Emergency"))
//        {
//            holder.emergency_time.setText("Time : "+operator.getBook_date().split(" ")[1]);
//        }
        holder.book_date.setText("" + operator.getBook_date());
        holder.consultdate_date.setText(""+operator.getConsult_date());
        holder.name.setText(PatientName.equals("") ? operator.getName()+"  "+Age+"/"+gender.toString():  PatientName+"  "+Age+"/"+gender.toString());
        holder.qualification.setText(PatientMobile.equals("") ? operator.getMobile() : PatientMobile);
        holder.specialities.setText("" + operator.getTypeName());
        final RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.noavailable);
        requestOptions.error(R.drawable.doctordemo);
        Glide.with(mContext)
                .load(operator.getPreciption_img())
                .apply(requestOptions)
                .into(holder.prection);
        if (operator.getPreciption_description() == null) {
            holder.idCardView.setText("  Upload prescription  ");
            holder.lifirst.setBackground(getDrawable(mContext,R.drawable.rectbackgra));
        } else {
            holder.idCardView.setText("  View prescription  ");
            holder.lifirst.setBackground(getDrawable(mContext,R.drawable.viewprescriptiondash));
        }
        holder.idCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uID = useridpref.isEmpty() ? operator.getId() : useridpref;
                if (operator.getPreciption_description() == null) {
                    Intent intent = new Intent(mContext, chooseprescription.class);
                    intent.putExtra("appointment_id", "" +operator.getAppointment_id());
                    intent.putExtra("name",  name.isEmpty() ? operator.getName() : name);
                    intent.putExtra("type","1");
                    intent.putExtra("profileimageStr", "" + profilePIC);
                    intent.putExtra("mobile",  mobile.isEmpty() ? operator.getMobile() : mobile);
                    intent.putExtra("userid", "" + uID);
                    intent.putExtra("age",operator.getPatient_details());
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, ViewPriscription.class);
                    intent.putExtra("appointment_id", "" + operator.getAppointment_id());
                    intent.putExtra("name",  name.isEmpty() ? operator.getName() : name);
                    intent.putExtra("mobile",  mobile.isEmpty() ? operator.getMobile() : mobile);
                    intent.putExtra("userid", "" + uID);
                    intent.putExtra("type","1");
                    intent.putExtra("profileimageStr", "" + profilePIC);
                    mContext.startActivity(intent);
                }


            }
        });


        holder.prection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {


                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.singimageview, null);

                ImageView cancelButton = view.findViewById(R.id.cancelButton);
                ImageView imageprec = view.findViewById(R.id.imageprec);


                final Dialog dialog = new Dialog(mContext);

                dialog.setCancelable(false);
                dialog.setContentView(view);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                Glide.with(mContext)
                        .load(operator.getPreciption_img())
                        .apply(requestOptions)
                        .into(imageprec);


                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();


            }
        });
   if(operator.getId()==null)
   {
    holder.id_chatnow.setVisibility(View.VISIBLE);
    holder.chat.setVisibility(View.VISIBLE);
    holder.voicecall.setVisibility(View.GONE);
}

        holder.id_chatnow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                   Log.d("dfbjfbh",",mncvn");
                UtilMethods.INSTANCE.GetAccessToken(mContext,operator.getId()+"",operator.getAppointment_id());
            }
        });
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                UtilMethods.INSTANCE.CreateGetRTMAccessToken(mContext,operator.getId()+"",operator.getName(),loader);
            }
        });
        holder.voicecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    UtilMethods.INSTANCE.GetAccessToken(mContext,operator.getId()+"",operator.getAppointment_id());
//                Intent i=new Intent(mContext, VoiceChatViewActivity.class);
//                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}