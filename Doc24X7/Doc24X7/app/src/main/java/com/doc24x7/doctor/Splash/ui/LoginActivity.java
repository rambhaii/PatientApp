package com.doc24x7.doctor.Splash.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.doc24x7.doctor.NewUI.LoginFragmentNew;
import com.doc24x7.doctor.NewUI.OtpRegisterFragmentNew;
import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.UtilMethods;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt_login,signup;
    String fundType ="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account);


        bt_login=findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);
        UtilMethods.INSTANCE.doctorlist(this,null);
        UtilMethods.INSTANCE.AllQualifiation( this, null);
        UtilMethods.INSTANCE.States( this, null);

    }

    @Override
    public void onClick(View v) {
        if(bt_login==v){

            UtilMethods.INSTANCE.SetType(this, fundType);

            new LoginFragmentNew().show(getSupportFragmentManager(), "1");


            /*  Intent intent = new Intent(LoginActivity.this, com.doc24x7.doctor.Login.ui.LoginActivity.class);
            intent.putExtra("type","1");
            startActivity(intent);*/


            // finish();
           // overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        }

    }
}