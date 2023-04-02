package com.doc24x7.doctor.Dashbord.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doc24x7.doctor.Dashbord.dto.Datum;
import com.doc24x7.doctor.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ViewReport extends AppCompatActivity {
  TextView name,mobile;
RecyclerView recyclerView;
ViewReportAdapter viewReportAdapter;
ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);
      recyclerView=findViewById(R.id.recyclerview);
      back=findViewById(R.id.back);
      back.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
             onBackPressed();
          }
      });
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> imageArray= new Gson().fromJson(getIntent().getStringExtra("images"),type);
        name=findViewById(R.id.name);
        mobile=findViewById(R.id.contentnumber);
        name.setText(getIntent().getStringExtra("name"));
        mobile.setText(getIntent().getStringExtra("mobile"));
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        viewReportAdapter = new ViewReportAdapter(imageArray, this);
        recyclerView.setAdapter(viewReportAdapter);
    }
}