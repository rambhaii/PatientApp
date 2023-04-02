package com.doc24x7.doctor.Dashbord.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.doc24x7.doctor.Dashbord.dto.Datum;
import com.doc24x7.doctor.Dashbord.dto.GalleryListResponse;
import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.FragmentActivityMessage;
import com.doc24x7.doctor.Utils.GlobalBus;
import com.doc24x7.doctor.Utils.Loader;
import com.doc24x7.doctor.Utils.UtilMethods;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class ViewTransaction extends AppCompatActivity {
    RecyclerView rv_viewTansaction;
    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        HitApi();

    }

    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage) {


        dataParsePreciption(activityFragmentMessage.getFrom());


    }

    private void dataParsePreciption(String from) {
        Gson gson = new Gson();
        GalleryListResponse sliderImage = gson.fromJson(from, GalleryListResponse.class);
        final ArrayList<Datum> arrayList = sliderImage.getData();
RecyclerView rv_viewTansaction=findViewById(R.id.rv_viewTansaction);
        if (arrayList.size() > 0) {
            TransactionListAdapter  medicinelistadapter = new TransactionListAdapter(arrayList, this);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            rv_viewTansaction.setLayoutManager(mLayoutManager);
            rv_viewTansaction.setItemAnimator(new DefaultItemAnimator());
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_viewTansaction.getContext(),
                    mLayoutManager.getOrientation());
            rv_viewTansaction.addItemDecoration(dividerItemDecoration);
            rv_viewTansaction.setAdapter(medicinelistadapter);
            rv_viewTansaction.setVisibility(View.VISIBLE);
        } else {
            rv_viewTansaction.setVisibility(View.GONE);
        }

    }

    private void HitApi() {

        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
           Loader loader = new Loader(this, android.R.style.Theme_Translucent_NoTitleBar);
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.DrIncome(this, loader);
        } else {
            UtilMethods.INSTANCE.NetworkError(this, this.getResources().getString(R.string.network_error_title),
                    this.getResources().getString(R.string.network_error_message));
        }
    }
}
