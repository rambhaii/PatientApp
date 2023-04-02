package com.doc24x7.assistant.Dashbord.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.doc24x7.assistant.Dashbord.MedicineListAdapter;
import com.doc24x7.assistant.Dashbord.dto.Datum;
import com.doc24x7.assistant.Dashbord.dto.GalleryListResponse;
import com.doc24x7.assistant.Dashbord.dto.PatientDetailModel;
import com.doc24x7.assistant.Login.dto.Data;
import com.doc24x7.assistant.R;
import com.doc24x7.assistant.Utils.ApplicationConstant;
import com.doc24x7.assistant.Utils.DETAIL;
import com.doc24x7.assistant.Utils.FragmentActivityMessage;
import com.doc24x7.assistant.Utils.GlobalBus;
import com.doc24x7.assistant.Utils.Loader;
import com.doc24x7.assistant.Utils.MeicineModel;
import com.doc24x7.assistant.Utils.UtilMethods;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ViewPriscription extends AppCompatActivity {
    RecyclerView recycler_view_Preciption;
    String response = "";
    String doctorDetail = "";
    String Patientname="";
    String Patientappointment="";
    TextView clinic_name, name, mobile, email;
    //  Data operator;
    ImageView Preciption_img,share,download;
    TextView Preciption_description,registrarionNo;
    File imagePath,imagePathspdf;
    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }
    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        saveBitmap(rootView.getDrawingCache());
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
       imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
       String v=imagePath.getAbsolutePath();
       Log.e("vvvvvv",v);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath.getAbsolutePath());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            shareIt();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }
    private void shareIt() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Uri uri = Uri.fromFile(imagePath);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBody = "";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "My Tweecher score");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preciption);

        Preciption_description = findViewById(R.id.Preciption_description);

        Preciption_img = findViewById(R.id.Preciption_img);
        registrarionNo = findViewById(R.id.registrarionNo);
        download=findViewById(R.id.download);
        clinic_name = findViewById(R.id.clinic_name);
        share = findViewById(R.id.share);
        Patientappointment=getIntent().getStringExtra("appointment_id");
      //  Toast.makeText(this, ""+Patientappointment, Toast.LENGTH_SHORT).show();
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = takeScreenshot();

            }
        });
        download.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            createPdf();
                                        }
                                    }
                            );
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);

        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);

        clinic_name.setText("" + balanceCheckResponse.getClinic_name());
        name.setText("Dr. " + balanceCheckResponse.getName());
        mobile.setText("Phone : " + balanceCheckResponse.getMobile());
        email.setText("Email : " + balanceCheckResponse.getEmail());
        registrarionNo.setText("Reg.No. : " + balanceCheckResponse.getRegistration_no());
        // customerimage
        HitApi();

    }
    public Bitmap takeScreenshotforpdf() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        saveBitmappdf(rootView.getDrawingCache());
        return rootView.getDrawingCache();
    }
    public void saveBitmappdf(Bitmap bitmap) {
        imagePathspdf = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePathspdf.getAbsolutePath());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    private void createPdf() {
        takeScreenshotforpdf();
        Document document = new Document();
        File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"Patient Prescription");
        directory.mkdirs();
        String directoryPath = android.os.Environment.getExternalStorageDirectory().toString();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(directoryPath + "/Patient Prescription/"+Patientname+Patientappointment+".pdf")); //  Change pdf's name.
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();

        Image image = null;  // Change image's name and extension.
        try {
            image = Image.getInstance(imagePathspdf.getAbsolutePath());
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                - document.rightMargin() - 0) / image.getWidth()) * 100; // 0 means you have no indentation. If you have any, change it.
        image.scalePercent(scaler);
        image.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);

        try {
            document.add(image);
            Toast.makeText(this, "Prescription is downloaded", Toast.LENGTH_SHORT).show();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();



    }

    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage) {
        dataParsePreciption(activityFragmentMessage.getFrom());
    }

    private void dataParsePreciption(String from) {
        Gson gson = new Gson();
        GalleryListResponse sliderImage = gson.fromJson(from, GalleryListResponse.class);
        final ArrayList<Datum> sliderLists = sliderImage.getData();
       final RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.customer_support);
        requestOptions.error(R.drawable.customer_support);
        Glide.with(this)
                .load(sliderLists.get(0).getPreciption_img())
                .apply(requestOptions)
                .into(Preciption_img);
        recycler_view_Preciption = findViewById(R.id.recycler_view_Preciption);
        PatientDetailModel patientDetailModel = null;
        TextView PAtientNAme= null;
        TextView Patinetmobile= null;
        TextView bookdate= null;
        try {
            String pacientDatail=sliderLists.get(0).getPatient_details();
            patientDetailModel = new Gson().fromJson(pacientDatail, PatientDetailModel.class);
            PAtientNAme = findViewById(R.id.Patientname);
            Patientname=patientDetailModel.getPatientName();
            Patinetmobile = findViewById(R.id.Patinetmobile);
            bookdate = findViewById(R.id.bookdate);
            PAtientNAme.setText("Patient Name: "+patientDetailModel.getPatientName()+" Age: "+patientDetailModel.getPatientAge()+" years");
            Patinetmobile.setText("Mobile Number: "+patientDetailModel.getPatientMobile());
            bookdate.setText("Booking Date: "+sliderLists.get(0).getBook_date());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        Preciption_description.setText(sliderLists.get(0).getDescription());
        String str=sliderLists.get(0).getMedicine_details();
        str.replace("\\\\","");
        str.replace("\"","");
        MeicineModel meicineModel = new Gson().fromJson(str, MeicineModel.class);
        ArrayList<DETAIL> arrayList = meicineModel.getMedicineMode();
        if (arrayList.size() > 0) {
            MedicineListAdapter  medicinelistadapter = new MedicineListAdapter(arrayList, this);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            recycler_view_Preciption.setLayoutManager(mLayoutManager);
            recycler_view_Preciption.setItemAnimator(new DefaultItemAnimator());
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler_view_Preciption.getContext(),
                    mLayoutManager.getOrientation());
            recycler_view_Preciption.addItemDecoration(dividerItemDecoration);
            recycler_view_Preciption.setAdapter(medicinelistadapter);
            recycler_view_Preciption.setVisibility(View.VISIBLE);
        } else {
            recycler_view_Preciption.setVisibility(View.GONE);
        }
        Preciption_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewpopup = inflater.inflate(R.layout.popupfullimage, null);
                Button okButton = (Button) viewpopup.findViewById(R.id.okButton);
                ImageView popPreciption_img = (ImageView) viewpopup.findViewById(R.id.popPreciption_img);
                final Dialog dialog = new Dialog(ViewPriscription.this);
                dialog.setCancelable(false);
                dialog.setContentView(viewpopup);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Glide.with(ViewPriscription.this)
                        .load(sliderLists.get(0).getPreciption_img())
                        .apply(requestOptions)
                        .into(popPreciption_img);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    private void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
           Loader loader = new Loader(this, android.R.style.Theme_Translucent_NoTitleBar);
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.GetPreciption(this, loader,getIntent().getStringExtra("appointment_id"));
        } else {
            UtilMethods.INSTANCE.NetworkError(this, this.getResources().getString(R.string.network_error_title),
                    this.getResources().getString(R.string.network_error_message));
        }
    }
}
