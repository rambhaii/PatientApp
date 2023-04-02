package com.doc24x7.assistant.Appointment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doc24x7.assistant.Dashbord.dto.Datum;
import com.doc24x7.assistant.Login.dto.Data;
import com.doc24x7.assistant.PatientDetail;
import com.doc24x7.assistant.PaymentGateway.ui.PaymentActivity;
import com.doc24x7.assistant.R;
import com.doc24x7.assistant.Utils.ApplicationConstant;
import com.doc24x7.assistant.Utils.Loader;
import com.doc24x7.assistant.Utils.UtilMethods;
import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;


public class DoctorBookingAdapterforAssistant extends RecyclerView.Adapter<DoctorBookingAdapterforAssistant.MyViewHolder> implements DatePickerDialog.OnDateSetListener {

    private ArrayList<Datum> transactionsList;
    private Activity mContext;
    String Symtom_id = "";
    RadioGroup selectpayment;
    AlertDialog alertDialog;
    int appDay = 0;
    int i;
    Calendar[] calendars = new Calendar[100];
    FragmentManager fragmentManager;
    private String currentDay;
    Datum doctorDetail;
    String paymentStatus;
    String appointment_id;
    String bookstatus;
    int payment_id;

    public void filter(ArrayList<Datum> newlist) {
        transactionsList = new ArrayList<>();
        transactionsList.addAll(newlist);
        notifyDataSetChanged();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public GridLayout ll;


        public MyViewHolder(View view) {
            super(view);
            tv_title = view.findViewById(R.id.tv_title);
            ll = view.findViewById(R.id.ll);
        }
    }

    public DoctorBookingAdapterforAssistant(ArrayList<Datum> transactionsList, Activity mContext, String Symtom_id, FragmentManager fragmentManager, String curentDay, Datum doctorDetail, String paymentStatus, int payment_id, String appointment_id, String bookstatus) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.Symtom_id = Symtom_id;
        this.fragmentManager = fragmentManager;
        this.currentDay = curentDay;
        this.doctorDetail = doctorDetail;
        this.paymentStatus = paymentStatus;
        this.payment_id = payment_id;
        this.appointment_id = appointment_id;
        this.bookstatus=bookstatus;
    }

    @Override
    public DoctorBookingAdapterforAssistant.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.finftimebooking_adapter_offline, parent, false);
        return new DoctorBookingAdapterforAssistant.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final DoctorBookingAdapterforAssistant.MyViewHolder holder, int position) {
        final Datum operator = transactionsList.get(position);
        int patient = Integer.parseInt(operator.getNo_of_patient());
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        Date CurrentTime = null;
        Date startdate = null;
        holder.ll.removeAllViews();
        try {
            startdate = sdf.parse(operator.getStart_time());
            CurrentTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = startdate.getTime();
        Log.e("time:", time + "");
        Date enddate = null;
        try {
            enddate = sdf.parse(operator.getEnd_time());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long endddate = enddate.getTime();
        Log.e("time:endddate:", endddate + "");
        long slot = (endddate - time) / patient;
        Log.e("time:slot:", slot + "");
        long minutes = TimeUnit.MILLISECONDS.toMinutes(slot);
        Log.e("time:minutes:", minutes + "");
        String sTime = "";
        long lotTime = startdate.getTime();
        // StaggeredGridLayoutManager linearLayout=new StaggeredGridLayoutManager(3,LinearLayout.VERTICAL);
        // linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//        try {
//            if(sdf.parse(operator.getStart_time()).after(CurrentTime));
//            {
//              holder.tv_title.setVisibility(View.INVISIBLE);
//           }
//
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        holder.tv_title.setText(operator.getStart_time() + "-" + operator.getEnd_time());
        for (int i = 0; i < operator.getSlot_id().size(); i++) {
            final int subSlotPosition = i;
            TextView textView = new TextView(mContext);
            textView.setBackground(mContext.getResources().getDrawable(R.drawable.rectsearch));
            textView.setPadding(10, 10, 10, 10);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT

            );
            params.setMargins(10, 5, 10, 5);
            textView.setLayoutParams(params);

            textView.setText("   " + operator.getSlot_id().get(i).getStart_time() + "-" + operator.getSlot_id().get(i).getEnd_time() + "     ");


            // textView.setText( "   "+     operator.getSlot_id().get(i).getStart_time()+"-"+operator.getSlot_id().get(i).getEnd_time() +"     "   );


            textView.setTextSize(mContext.getResources().getDimension(R.dimen._5sdp));
            textView.setTag(operator.getSlot_id().get(i).getStart_time());
            holder.ll.addView(textView);
            if (operator.getSlot_id().get(i).getBooked_status()) {
                textView.setEnabled(false);
                textView.setBackground(mContext.getResources().getDrawable(R.drawable.circular_grey_bordersolid));
            }
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ShowDailogforPayment(operator,subSlotPosition,textView.getText().toString());
                    //  Toast.makeText(mContext, "111111111111112112", Toast.LENGTH_SHORT).show();

//
//                    if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {
//                        Loader loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
//                        loader.show();
//                        loader.setCancelable(false);
//                        loader.setCanceledOnTouchOutside(false);
//                        if (paymentStatus != null) {
//                            Intent i = new Intent(mContext, PatientDetail.class);
//                            i.putExtra("currentDay", currentDay + "");
//                            i.putExtra("dateTime", "" + currentDay + " " + textView.getText().toString());
//                            i.putExtra("Doctordetail", new Gson().toJson(operator));
//                            i.putExtra("slot_id", operator.getSlot_id().get(subSlotPosition).getId());
//                            i.putExtra("parent_slot_id", "" + operator.getId());
//                            i.putExtra("payment_id", "" + payment_id);
//                            i.putExtra("AppintmentDetail", new Gson().toJson(operator));
//                            i.putExtra("appointment_id", appointment_id);
//                            i.putExtra("bookstatus",bookstatus);
//                            mContext.startActivity(i);
//                        } else {
//                            UtilMethods.INSTANCE.chkpayment(mContext, operator.getDoctor_id(), doctorDetail, loader, "",
//                                    "" + operator.getDoctor_id(), "" + operator.getSlot_id().get(subSlotPosition).getId(), "" + operator.getId(),
//                                    ""+(bookstatus.equals("1")?doctorDetail.getDoctor_fees():doctorDetail.getClinic_fees()), operator, currentDay + " " + textView.getText().toString(), currentDay, null,bookstatus);
//                        }
//                    } else {
//                        UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
//                                mContext.getResources().getString(R.string.network_error_message));
//                    }


                }
            });

        }


//        final Calendar today = Calendar.getInstance();
//        final Calendar twoDaysLater = (Calendar) today.clone();
//        twoDaysLater.add(Calendar.DATE, 15);
//
//        holder.date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                // Get Current Date
//                final Calendar c = Calendar.getInstance();
//                mYear = c.get(Calendar.YEAR);
//                mMonth = c.get(Calendar.MONTH);
//                mDay = c.get(Calendar.DAY_OF_MONTH);
//                int day = c.get(Calendar.DAY_OF_WEEK);
//                switch (day) {
//                    case Calendar.MONDAY:
//                        appDay = 2;
//                        break;
//                    case Calendar.TUESDAY:
//                        appDay = 3;
//                        break;
//                    case Calendar.WEDNESDAY:
//                        appDay = 4;
//                        break;
//                    case Calendar.THURSDAY:
//                        appDay = 5;
//                        break;
//                    case Calendar.FRIDAY:
//                        appDay = 6;
//                        break;
//                    case Calendar.SATURDAY:
//                        appDay = 7;
//                        break;
//                    case Calendar.SUNDAY:
//                        appDay = 1;
//                        break;
//                    default:
//                        appDay = 0;
//                        break;
//                }
//                Calendar now = Calendar.getInstance();
//                DatePickerDialog dpd = DatePickerDialog.newInstance(
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//                     holder.date.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth+"");
//                            }
//                        },
//                        now.get(Calendar.YEAR), // Initial year selection
//                        now.get(Calendar.MONTH), // Initial month selection
//                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
//                );                Calendar calendar1 = Calendar.getInstance();
//
//                int today = currentDay;
//                if ( appDay == today)
//                {
//                    int update = 0;
//                    for (int i = 0; i < calendars.length; i++)
//                    {
//                        final Calendar calendar2 = Calendar.getInstance();
//                        calendar2.get(Calendar.DAY_OF_WEEK);
//                        calendar2.add(Calendar.DATE, update);
//                        calendars[i] = calendar2;
//                        update += 7;
//                    }
//                }
//                else if ( today > appDay)
//                {
//                    int for1 = today - appDay;
//                    for (int i = 0; i < calendars.length; i++)
//                    {
//                        final Calendar calendar2 = Calendar.getInstance();
//                        calendar2.get(Calendar.DAY_OF_WEEK);
//                        calendar2.add(Calendar.DATE, for1);
//                        calendars[i] = calendar2;
//                        for1 += 7;
//                    }
//                }
//                else if ( today < appDay)
//                {
//                    int for2 = (7-appDay)+today;
//                    for (int i = 0; i < calendars.length; i++)
//                    {
//                        final Calendar calendar2 = Calendar.getInstance();
//                        calendar2.get(Calendar.DAY_OF_WEEK);
//                        calendar2.add(Calendar.DATE, for2);
//                        calendars[i] = calendar2;
//                        for2 += 7;
//                    }
//                }
//                dpd.setSelectableDays(calendars);
//                dpd.show(fragmentManager,"fragmentManager");
//
//
//
//
//
//            }
//        });
//
//
//        holder.bookcontinew.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!holder.date.getText().toString().trim().equalsIgnoreCase("")) {
//
//                    Loader loader;
//                    loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
//
//
//                    if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {
//                        loader.show();
//                        loader.setCancelable(false);
//                        loader.setCanceledOnTouchOutside(false);
//                        UtilMethods.INSTANCE.BookAppointment(mContext, operator.getDoctor_id(), operator.getId(),
//                                holder.date.getText().toString().trim(), loader, Symtom_id, operator.getNo_of_patient());
//                    } else {
//                        UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
//                                mContext.getResources().getString(R.string.network_error_message));
//                    }
//
//                } else {
//                    Toast.makeText(mContext, "Appointment Date Select", Toast.LENGTH_SHORT).show();
//
//                }
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }
    public void  ShowDailogforPayment(Datum operator,int subSlotPosition,String textView)
    {
        AlertDialog.Builder dailog = new AlertDialog.Builder(mContext);
        LayoutInflater inflator =mContext.getLayoutInflater();
        View dailogview = inflator.inflate(R.layout.view_payment_dailog, null);
        dailog.setView(dailogview);
        alertDialog = dailog.create();
        alertDialog.show();

        selectpayment=alertDialog.findViewById(R.id.selectpayment);

        RadioButton cod  =alertDialog.findViewById(R.id.cod);
        RadioButton online=alertDialog.findViewById(R.id.online);
        ImageView payamount=alertDialog.findViewById(R.id.payamount);

//        if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {
//            Loader loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
//            loader.show();
//            loader.setCancelable(false);
//            loader.setCanceledOnTouchOutside(false);
//
////            } else {
////                UtilMethods.INSTANCE.chkpayment(mContext, operator.getDoctor_id(), doctorDetail, loader, "",
////                        "" + operator.getDoctor_id(), "" + operator.getSlot_id().get(subSlotPosition).getId(), "" + operator.getId(),
////                        ""+(bookstatus.equals("1")?doctorDetail.getDoctor_fees():doctorDetail.getClinic_fees()), operator, currentDay + " " + textView.getText().toString(), currentDay, null,bookstatus);
////            }
//        } else {
//            UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
//                    mContext.getResources().getString(R.string.network_error_message));
//        }



        selectpayment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.cod)
                {
                    // Toast.makeText(PatientDetail.this, "Cash on delivery ", Toast.LENGTH_SHORT).show();
                    payamount.setVisibility(View.VISIBLE);
                    i=1;
                }
                else if(checkedId==R.id.online)
                {
                    // Toast.makeText(PatientDetail.this, " Online payment ", Toast.LENGTH_SHORT).show();
                    payamount.setVisibility(View.VISIBLE);
                    i=2;
                }
            }
        });
        // TextView tvamountee=alertDialog.findViewById(R.id.tvamounteeee);
        // ImageView cancele=alertDialog.findViewById(R.id.cancel);
//        cancele.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
        //  float balance=Float.parseFloat(amountrupee);
        //   tvamountee.setText("Pay Amount : ₹ "+balance/100);


        payamount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==1)
                {

                        Intent i = new Intent(mContext, PatientDetail.class);
                        i.putExtra("currentDay", currentDay + "");
                        i.putExtra("dateTime", "" + currentDay + " " + textView);
                        i.putExtra("Doctordetail", new Gson().toJson(operator));
                        i.putExtra("slot_id", operator.getSlot_id().get(subSlotPosition).getId());
                        i.putExtra("parent_slot_id", "" + operator.getId());
                        i.putExtra("payment_id", "" + "0");
                        i.putExtra("AppintmentDetail", new Gson().toJson(operator));
                        i.putExtra("appointment_id", appointment_id);
                        i.putExtra("bookstatus", bookstatus);
                        mContext.startActivity(i);
                        //Toast.makeText(mContext, "Your Appointment has been booked", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
                else if(i==2)
                {

                    SharedPreferences myPreferences =mContext.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                    String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
                    Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);

                    if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {
                        Loader loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);
                        if (paymentStatus != null) {
                            Intent i = new Intent(mContext, PatientDetail.class);
                            i.putExtra("currentDay", currentDay + "");
                            i.putExtra("dateTime", "" + currentDay + " " + textView);
                            i.putExtra("Doctordetail", new Gson().toJson(operator));
                            i.putExtra("slot_id", operator.getSlot_id().get(subSlotPosition).getId());
                            i.putExtra("parent_slot_id", "" + operator.getId());
                            i.putExtra("payment_id", "" + payment_id);
                            i.putExtra("AppintmentDetail", new Gson().toJson(operator));
                            i.putExtra("appointment_id", appointment_id);
                            i.putExtra("bookstatus",bookstatus);
                            mContext.startActivity(i);
                        } else {
                            UtilMethods.INSTANCE.chkpayment(mContext, operator.getDoctor_id(), doctorDetail, loader, "",
                                    "" + operator.getDoctor_id(), "" + operator.getSlot_id().get(subSlotPosition).getId(), "" + operator.getId(),
                                    ""+balanceCheckResponse.getClinic_fees(), operator, currentDay + " " + textView, currentDay, null,bookstatus);
                        }
                    } else {
                        UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
                                mContext.getResources().getString(R.string.network_error_message));
                    }

                    alertDialog.dismiss();

                }
            }
        });
    }


}