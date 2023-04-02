package com.doc24x7.doctor.NewUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.ApplicationConstant;
import com.doc24x7.doctor.Utils.FragmentActivityMessage;
import com.doc24x7.doctor.Utils.GlobalBus;
import com.doc24x7.doctor.Utils.Loader;
import com.doc24x7.doctor.Utils.UtilMethods;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.regex.Pattern;


public class LoginFragmentNew extends BottomSheetDialogFragment implements View.OnClickListener {

    public TextView btLogin;
    Loader loader;
    EditText edMobile;
    ImageView goback;
    String type="";
    LinearLayout toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_login, container, false);

        getIds(v);

        return v;


    }

    public void getIds(View v) {

        loader = new Loader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        btLogin = v.findViewById(R.id.btLogin);
        edMobile = v.findViewById(R.id.edMobile);
        goback = v.findViewById(R.id.goback);
        toolbar = v.findViewById(R.id.toolbar);


        setListners();

        if(type.equalsIgnoreCase("2")){

            btLogin.setText("Register");

        }

    }

    public void setListners() {

        btLogin.setOnClickListener(this);
        goback.setOnClickListener(this);
        toolbar.setOnClickListener(this);



    }



    @Override
    public void onClick(View v) {

        if (v == goback) {

            dismiss();

        }


        if (v == toolbar) {

            dismiss();

        }



        if (v == btLogin) {
            if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {

                if (edMobile.getText().toString().isEmpty()) {
                    edMobile.setError("Please enter mobile Number");
                    return;
                }


                if (edMobile.getText().toString().length()<10)
                {
                    edMobile.setError("Please enter valid mobile Number");
                    return;
                }

                String phone="+91"+ edMobile.getText().toString().trim();
                if (!Pattern.matches("^(\\+91[\\-\\s]?)?[0]?(91)?[6789]\\d{9}$", phone))
                {
                    edMobile.setError("Enter a valid Phone Number");
                }
                    else
                {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);

                    UtilMethods.INSTANCE.DoctorLogin(getActivity(), edMobile.getText().toString().trim(), loader, getActivity(),1);

                }


            } else {

                UtilMethods.INSTANCE.NetworkError(getActivity(), getResources().getString(R.string.network_error_title),
                        getResources().getString(R.string.network_error_message));
            }
        }
    }


      /*  @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Are you sure you want to go back")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();

    }*/



}
