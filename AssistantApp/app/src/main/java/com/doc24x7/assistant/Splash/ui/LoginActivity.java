package com.doc24x7.assistant.Splash.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.doc24x7.assistant.R;
import com.doc24x7.assistant.Utils.UtilMethods;

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
            Intent intent = new Intent(LoginActivity.this, com.doc24x7.assistant.Login.ui.LoginActivity.class);
            intent.putExtra("type","1");
            startActivity(intent);
           // finish();
           // overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

    }
}