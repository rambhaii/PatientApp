package com.doc24x7.doctor.DoctorDashboad;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doc24x7.doctor.Login.dto.Data;
import com.google.gson.Gson;
import com.doc24x7.doctor.Dashbord.dto.Datum;
import com.doc24x7.doctor.Dashbord.dto.GalleryListResponse;
import com.doc24x7.doctor.Login.dto.Qualification;
import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.ApplicationConstant;
import com.doc24x7.doctor.Utils.Loader;
import com.doc24x7.doctor.Utils.UtilMethods;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class QualificationScreen extends AppCompatActivity {
    RecyclerView rv_qualification;
    Spinner addQualification;
    EditText Name, passingYear,speciality;
    TextView btLogin, btnUpdate;
    String QuaficationID;
    String QuaficationName;
    ImageView back;
     int flag=0;
    String qualification_status=null;
    final Calendar myCalendar = Calendar.getInstance();
    ArrayList<Qualification> qualifications = new ArrayList<>();
    Data balanceCheckResponse;
     GalleryListResponse masterQualification;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qualification);
        rv_qualification = findViewById(R.id.rv_qualification);
        Name = findViewById(R.id.Name);
        passingYear = findViewById(R.id.passingYear);
        btnUpdate = findViewById(R.id.btnUpdate);
        addQualification = findViewById(R.id.addQualification);
        btLogin = findViewById(R.id.btLogin);
        speciality=findViewById(R.id.speciality);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        masterQualification = gson.fromJson(prefs.getString(ApplicationConstant.INSTANCE.qualification, ""), GalleryListResponse.class);
        addQualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                QuaficationID = masterQualification.getData().get(position).getId();
                QuaficationName = masterQualification.getData().get(position).getQualification();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        ArrayAdapter<Datum> countryAdapter;
        countryAdapter = new ArrayAdapter<Datum>(this, R.layout.spinner_item, R.id.tv_spname, masterQualification.getData());
        addQualification.setAdapter(countryAdapter);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);

               updateLabel();
            }

        };
        passingYear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                new DatePickerDialog(QualificationScreen.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.DAY_OF_YEAR),
//                        myCalendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)).show();
              
            }
        });
        qualification_status=getIntent().getStringExtra("qualification_status");
        if(qualification_status!=null)
        {
            SharedPreferences myPreferences = QualificationScreen.this.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
            balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        //   qualifications.add(new Qualification(balanceCheckResponse.getQualification()));
            qualifications=balanceCheckResponse.getQualification();
            QualificationAdapter mAdapter = new QualificationAdapter(qualifications, QualificationScreen.this);
            rv_qualification.setLayoutManager(new LinearLayoutManager(QualificationScreen.this, LinearLayoutManager.VERTICAL, false));
            rv_qualification.setItemAnimator(new DefaultItemAnimator());
            rv_qualification.setAdapter(mAdapter);
        //    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
    //        View view = getCurrentFocus();
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            flag=1;

        }
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Name.getText().toString().isEmpty()) {
                    Name.setError(("Please enter college name"));
                    return;
                }
                if (passingYear.getText().toString().isEmpty()) {
                    passingYear.setError(("Please enter year of passing"));
                    return;
                }

                    qualifications.add(new Qualification(QuaficationID, QuaficationName, Name.getText().toString(), passingYear.getText().toString(), speciality.getText().toString()));
                     Name.setText("");
                    passingYear.setText("");
                    speciality.setText("");
                    QualificationAdapter mAdapter = new QualificationAdapter(qualifications, QualificationScreen.this);
                    rv_qualification.setLayoutManager(new LinearLayoutManager(QualificationScreen.this, LinearLayoutManager.VERTICAL, false));
                    rv_qualification.setItemAnimator(new DefaultItemAnimator());
                    rv_qualification.setAdapter(mAdapter);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    View view = getCurrentFocus();
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qualifications.size() > 0) {
                    ArrayList<Integer> qId = new ArrayList<>();
                    ArrayList<Integer> qpayear = new ArrayList<>();
                    ArrayList<String> cName = new ArrayList<>();
                    ArrayList<String> qspecial = new ArrayList<>();
                    for (int i = 0; i < qualifications.size(); i++) {
                        qId.add(Integer.parseInt(qualifications.get(i).getQualification_id()==null?getQualification(qualifications.get(i).getQualification_name()):qualifications.get(i).getQualification_id()));
                        qpayear.add(Integer.parseInt(qualifications.get(i).getPassing_year()));
                        cName.add(qualifications.get(i).getCollege_name());
                        qspecial.add(qualifications.get(i).getSpeciality());
                    }
                    ;
                    Loader loader = new Loader(QualificationScreen.this, android.R.style.Theme_Translucent_NoTitleBar);
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    UtilMethods.INSTANCE.SaveDrQualificationDetails(QualificationScreen.this, qId, cName, qpayear,qspecial, loader,flag);
                } else {
                    UtilMethods.INSTANCE.Failed(QualificationScreen.this, "Please add atleast one qualification", 0);
                }
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        passingYear.setText(sdf.format(myCalendar.getTime()));
    }
private String getQualification(String name ){
        String index="0";
        for(int i=0;i<masterQualification.getData().size();i++){
            if(masterQualification.getData().get(i).getQualification().equalsIgnoreCase(name)){
                index=masterQualification.getData().get(i).getId()+"";
            }
        }
return index;
}
}
