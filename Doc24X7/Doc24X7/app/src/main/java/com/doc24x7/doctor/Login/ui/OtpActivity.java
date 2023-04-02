package com.doc24x7.doctor.Login.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.doc24x7.doctor.Dashbord.dto.Datum;
import com.doc24x7.doctor.Dashbord.dto.GalleryListResponse;
import com.doc24x7.doctor.DoctorDashboad.AllSymtomsOtpAdapter;
import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.ApplicationConstant;
import com.doc24x7.doctor.Utils.Loader;
import com.doc24x7.doctor.Utils.UtilMethods;

import java.util.ArrayList;


public class OtpActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txt2;
    String number="";
    String otp="";
    String Status_get="";
    public RelativeLayout otp_submit;
    Loader loader;
    EditText otp_ed;
    LinearLayout doctorRegister;
    Spinner alltype;
    String typeid="";
    RecyclerView recycler_view;
    TextView chooseyourphoto;
    Button resendotp;
    String profile_image="";
    ArrayList jsonObj = new ArrayList();
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });



        chooseyourphoto=findViewById(R.id.chooseyourphoto);
        chooseyourphoto.setOnClickListener(this);


        recycler_view=findViewById(R.id.recycler_view);
        alltype=findViewById(R.id.alltype);
     resendotp=findViewById(R.id.resendotp);
   resendotp.setOnClickListener(this);
        doctorRegister=findViewById(R.id.doctorRegister);
        doctorRegister.setVisibility(View.GONE);

        number=getIntent().getStringExtra("number");
        otp=getIntent().getStringExtra("otp");
        Status_get=getIntent().getStringExtra("Status_get");

        otp_submit=findViewById(R.id.otp_submit);
        otp_ed=findViewById(R.id.otp_ed);
        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);

        otp_submit.setOnClickListener(this);

        txt2=findViewById(R.id.txt2);
        txt2.setText("We have sent you an SMS on +91-"+" "+ number +" \n with a 4 digit verification code");
       Toast.makeText(this, ""+otp, Toast.LENGTH_SHORT).show();
     //  otp_ed.setText(otp);


        if(Status_get.equalsIgnoreCase("1")){
            doctorRegister.setVisibility(View.GONE);
        }else if(Status_get.equalsIgnoreCase("2")){
            doctorRegister.setVisibility(View.VISIBLE);

            SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
            String str = prefs.getString(ApplicationConstant.INSTANCE.allCategoryDoctor, "");
            String recy = prefs.getString(ApplicationConstant.INSTANCE.qualification, "");
            dataParsesAlltype(str);
            getOperatorList(recy);


        }

    }


    GalleryListResponse sliderImage = new GalleryListResponse();
    ArrayList<Datum> operator = new ArrayList<>();
    AllSymtomsOtpAdapter mAdapter;


    public void getOperatorList(String response) {
        sliderImage = new Gson().fromJson(response, GalleryListResponse.class);
        operator = sliderImage.getData();

        if (operator != null && operator.size() > 0) {
            mAdapter = new AllSymtomsOtpAdapter(operator, this);
          //  RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);

            recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
          //  recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);
        } else {


        }


    }

    GalleryListResponse transactions = new GalleryListResponse();
    ArrayList<Datum> transactionsspinner = new ArrayList<>();
    ArrayList<String> bankList = new ArrayList<String>();


    public void dataParsesAlltype(String response) {

        bankList.add("Select Type");

        Gson gson = new Gson();
        transactions = gson.fromJson(response, GalleryListResponse.class);
        transactionsspinner = transactions.getData();

        if (transactionsspinner.size() > 0)
        {

            if (transactionsspinner != null && transactionsspinner.size() > 0)
            {

                for (int i = 0; i < transactionsspinner.size(); i++) {

                    bankList.add(transactionsspinner.get(i).getName());

                }
            }

            alltype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // TODO Auto-generated method stub
                    Log.e("spinner","  position   "+ position + "  ,  id  "+  id);

                    if (parent.getItemAtPosition(position).toString().equals("Select Type")) {
                        typeid="Select Type";
                    } else {
                        typeid = transactionsspinner.get(position-1).getId();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            });
            ArrayAdapter<String> countryAdapter;
            countryAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, bankList);
            alltype.setAdapter(countryAdapter);

        } else {

        }

    }


    @Override
    public void onClick(View view) {

        if(view==otp_submit) {
            if (otp_ed.getText().toString().isEmpty())
            {
                otp_ed.setError("Please enter your otp");
                otp_ed.requestFocus();
            } else {
                if (otp.equalsIgnoreCase(otp_ed.getText().toString().trim()))
                {

                    if (Status_get.equalsIgnoreCase("1"))
                    {


                        if (UtilMethods.INSTANCE.isNetworkAvialable(OtpActivity.this)) {

                            loader.show();
                            loader.setCancelable(false);
                            loader.setCanceledOnTouchOutside(false);


                            UtilMethods.INSTANCE.DoctorOtpVerify(OtpActivity.this, otp_ed.getText().toString().trim(), number, loader, this);

                        } else {
                            UtilMethods.INSTANCE.NetworkError(OtpActivity.this, getResources().getString(R.string.network_error_title),
                                    getResources().getString(R.string.network_error_message));
                        }


                    } else if (Status_get.equalsIgnoreCase("2")) {

                        Log.e("jsonObjff", "mm  :   " + jsonObj);


                 /*   if (UtilMethods.INSTANCE.isNetworkAvialable(OtpActivity.this)) {

                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);


                        UtilMethods.INSTANCE.DoctorOtpVerify(OtpActivity.this, otp_ed.getText().toString().trim(),number,loader,this);

                    } else {
                        UtilMethods.INSTANCE.NetworkError(OtpActivity.this, getResources().getString(R.string.network_error_title),
                                getResources().getString(R.string.network_error_message));
                    }*/


                    } else {


                    }


                } else {


                    Toast.makeText(this, "otp does not match", Toast.LENGTH_SHORT).show();
                }


            }


            if (view == chooseyourphoto) {


                showCameraGalleryDialog("1");


            }
        }
        if(view==resendotp)
        {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.DoctorLogin(OtpActivity.this,number, loader, this,2);

        }

    }


    public void showCameraGalleryDialog(String id) {

        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);

        dialog.setContentView(R.layout.camera_gallery_popup);

        dialog.show();

        RelativeLayout rrCancel = (RelativeLayout) dialog.findViewById(R.id.rl_close);
        LinearLayout llCamera = (LinearLayout) dialog.findViewById(R.id.ll_camera);
        LinearLayout llGallery = (LinearLayout) dialog.findViewById(R.id.ll_gallery);

        rrCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  captureImage();
                dialog.dismiss();
            }
        });

        llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(OtpActivity.this, FilePickerActivity.class);

                intent2.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                        .setCheckPermission(true)
                        .setShowImages(true)
                        .setShowAudios(false)
                        .setShowFiles(false)
                        .setShowVideos(false)
                        .enableImageCapture(true)
                        .enableVideoCapture(false)
                        .setMaxSelection(1)
                        .setSingleChoiceMode(true)
                        .setSkipZeroSizeFiles(true)
                        .build());

                //startActivityForResult(intent2, Constant.REQUEST_CODE_PICK_IMAGE);
//
                dialog.dismiss();
            }
        });
    }





    public void ItemClick(String id) {


        jsonObj.add(id);

    }
}