package com.doc24x7.doctor.Login.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.Loader;
import com.doc24x7.doctor.Utils.UtilMethods;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public TextView btLogin;
    Loader loader;
    EditText edMobile;
    ImageView goback;
    String type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        type=getIntent().getStringExtra("type");

        getId();


    }

    public void getId() {

        loader = new Loader(this, android.R.style.Theme_Translucent_NoTitleBar);
        btLogin = findViewById(R.id.btLogin);
        edMobile = findViewById(R.id.edMobile);
        goback = findViewById(R.id.goback);


        setListners();

        if(type.equalsIgnoreCase("2")){

            btLogin.setText("Register");
        }

    }


    public void setListners() {

        btLogin.setOnClickListener(this);
        goback.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if (v == goback) {

           /* new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Closing Activity")
                    .setMessage("Are you sure you want to close this activity?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
*/

            finish();

            //onBackPressed();

        }


        if (v == btLogin) {
            if (UtilMethods.INSTANCE.isNetworkAvialable(LoginActivity.this))
            {
                if (edMobile.getText().toString().isEmpty())
                {
                    edMobile.setError("Please enter mobile Number");
                    return;
                }
                if (edMobile.getText().toString().length()<10)
                {
                    edMobile.setError("Please enter valid mobile Number");
                    return;
                }
                Log.d("jh", edMobile.getText().toString().trim());
                String phone="+91"+ edMobile.getText().toString().trim();
                if (!Pattern.matches("^(\\+91[\\-\\s]?)?[0]?(91)?[6789]\\d{9}$", phone))
                {
                    edMobile.setError("Enter a valid Phone Number");
                }else
                {

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    UtilMethods.INSTANCE.DoctorLogin(LoginActivity.this, edMobile.getText().toString().trim(), loader, this,1);

                }


               }
            else {

                UtilMethods.INSTANCE.NetworkError(LoginActivity.this, getResources().getString(R.string.network_error_title),
                        getResources().getString(R.string.network_error_message));
            }
        }
    }

    @Override
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

    }


}
