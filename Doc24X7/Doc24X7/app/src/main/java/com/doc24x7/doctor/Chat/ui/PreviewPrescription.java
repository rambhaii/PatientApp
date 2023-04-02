package com.doc24x7.doctor.Chat.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.doc24x7.doctor.Dashbord.MedicineListAdapter;
import com.doc24x7.doctor.Dashbord.dto.Datum;
import com.doc24x7.doctor.Dashbord.dto.GalleryListResponse;
import com.doc24x7.doctor.Dashbord.dto.PatientDetailModel;
import com.doc24x7.doctor.Dashbord.ui.ViewPriscription;
import com.doc24x7.doctor.Login.dto.Data;
import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.ApplicationConstant;
import com.doc24x7.doctor.Utils.DETAIL;
import com.doc24x7.doctor.Utils.FragmentActivityMessage;
import com.doc24x7.doctor.Utils.Loader;
import com.doc24x7.doctor.Utils.MeicineModel;
import com.doc24x7.doctor.Utils.UtilMethods;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Locale;

public class PreviewPrescription extends AppCompatActivity {
    RecyclerView recycler_view_Preciption, GetMedicine, medicinename;
    Loader loader;
    TextView uploadprescription;
    String response = "";
    String doctorDetail = "";
    TextView clinic_name, name, mobile, email;
    //  Data operator;
    ImageView Preciption_img, share,imageConvertResult;
    TextView Preciption_description, registrarionNo;
    TextView PAtientNAme = null;
    TextView Patinetmobile = null;
    TextView bookdate = null;
    MedicineAdapter medicineAdapter;
    TextView tvdesc;

    PreciptionAdapter mAdapterPreciption;
    ArrayList medicine_name = new ArrayList();
    MeicineModel medicimeDetail = new MeicineModel();
    //ArrayList<DETAIL> arrayListDetail = new ArrayList<>();
    ArrayList<DETAIL> arrayList;
    LinearLayoutManager mLayoutManager;
    String userid,appointment_id;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_prescription);
        tvdesc = findViewById(R.id.Preciption_description);
        PAtientNAme = findViewById(R.id.Patientname);
        Patinetmobile = findViewById(R.id.Patinetmobile);
        bookdate = findViewById(R.id.bookdate);
        Preciption_img = findViewById(R.id.Preciption_img);
        registrarionNo = findViewById(R.id.registrarionNo);
        imageConvertResult = findViewById(R.id.imageConvertResult);
        recycler_view_Preciption = findViewById(R.id.recycler_view_Preciption);
        clinic_name = findViewById(R.id.clinic_name);
        share = findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Bitmap bitmap = takeScreenshot();

            }
        });
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        uploadprescription=findViewById(R.id.btn_uploadprescription);
        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(this);
        String previouslyEncodedImage = shre.getString("image_data", "");

        if( !previouslyEncodedImage.equalsIgnoreCase("") ){
            byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            imageConvertResult.setImageBitmap(bitmap);
        }
        SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);

        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        clinic_name.setText("" + balanceCheckResponse.getClinic_name());
        name.setText("Dr. " + balanceCheckResponse.getName());
        mobile.setText("Phone : " + balanceCheckResponse.getMobile());
        email.setText("Email : " + balanceCheckResponse.getEmail());
        registrarionNo.setText("Reg.No. : " + balanceCheckResponse.getRegistration_no());

        String unspanned = getIntent().getStringExtra("complain")
                + "<br />" + getIntent().getStringExtra("daignos")
                + "<br />" + getIntent().getStringExtra("invest")
                + "<br />" + getIntent().getStringExtra("remark")
                + "<br />" + getIntent().getStringExtra("more");

        Spanned spanned;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            spanned = Html.fromHtml(unspanned, Html.FROM_HTML_MODE_LEGACY);

        } else {
            spanned = Html.fromHtml(unspanned);
        }

        tvdesc.setText(spanned
        );
        //Toast.makeText(this, ""+getIntent().getStringExtra("recycle"), Toast.LENGTH_SHORT).show();

        PAtientNAme.setText(Html.fromHtml(getIntent().getStringExtra("patientname"), Html.FROM_HTML_MODE_LEGACY));
        Patinetmobile.setText(Html.fromHtml(getIntent().getStringExtra("mobile")));
        MeicineModel meicineModel = new Gson().fromJson(getIntent().getStringExtra("medicimeDetail")==null?"":getIntent().getStringExtra("medicimeDetail"), MeicineModel.class);
        arrayList = meicineModel.getMedicineMode();

        if (arrayList.size() > 0) {
            MedicineListAdapter  medicinelistadapter = new MedicineListAdapter(arrayList, this);
            LinearLayoutManager  mLayoutManager = new LinearLayoutManager(this);
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

         uploadprescription.setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View view) {
                 {
                     medicimeDetail.setMedicineMode(arrayList);
                     loader = new Loader(PreviewPrescription.this, android.R.style.Theme_Translucent_NoTitleBar);
                     if (UtilMethods.INSTANCE.isNetworkAvialable(PreviewPrescription.this)) {
                         medicimeDetail.setMedicineMode(arrayList);
                         loader.show();
                         loader.setCancelable(false);
                         loader.setCanceledOnTouchOutside(false);
                         UtilMethods.INSTANCE.SavePreciption(PreviewPrescription.this, "" + getIntent().getStringExtra("userid"),tvdesc.getText().toString(),
                                 "" + getIntent().getStringExtra("appointment_id") , loader, PreviewPrescription.this, medicine_name, medicimeDetail,"1");
                     } else {
                         UtilMethods.INSTANCE.NetworkError(PreviewPrescription.this, getResources().getString(R.string.network_error_title),
                                 getResources().getString(R.string.network_error_message));
                     }
                 }
             }
         });
    }
}

