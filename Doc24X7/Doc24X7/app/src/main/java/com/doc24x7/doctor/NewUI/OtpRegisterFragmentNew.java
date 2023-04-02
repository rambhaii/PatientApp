package com.doc24x7.doctor.NewUI;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.Loader;
import com.doc24x7.doctor.Utils.UtilMethods;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class OtpRegisterFragmentNew extends BottomSheetDialogFragment implements View.OnClickListener {

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
    public void onStart() {
        super.onStart();

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


                if (edMobile.getText().toString().length()<10) {
                    edMobile.setError("Please enter valid mobile Number");
                    return;
                }

                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);

                UtilMethods.INSTANCE.DoctorLogin(getActivity(), edMobile.getText().toString().trim(), loader, getActivity(),1);

            } else {

                UtilMethods.INSTANCE.NetworkError(getActivity(), getResources().getString(R.string.network_error_title),
                        getResources().getString(R.string.network_error_message));
            }
        }
    }

    public void show(Context context, String s, String s1, String s2) {




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
