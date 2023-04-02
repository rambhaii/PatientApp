package com.doc24x7.assistant.DoctorDashboad;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.doc24x7.assistant.R;
import com.doc24x7.assistant.Utils.Loader;
import com.doc24x7.assistant.Utils.UtilMethods;

public class AccountDetailScreen extends AppCompatActivity {
    EditText Name, AccountNo, IfscCode, bankName, branch, upi;
    TextView btnUpdate;
    ImageView back;
    String bankstatus = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_detail_screen);
        Name = findViewById(R.id.Name);
        AccountNo = findViewById(R.id.accountNo);
        branch = findViewById(R.id.branch);
        bankName = findViewById(R.id.bankName);
        IfscCode = findViewById(R.id.IfscCode);
        btnUpdate = findViewById(R.id.btnUpdate);
        back = findViewById(R.id.back);
        upi = findViewById(R.id.upi);
        bankstatus = getIntent().getStringExtra("bank_status");
        if (bankstatus != null) {
            Name.setHint(getIntent().getStringExtra("benificaryname"));
            AccountNo.setHint(getIntent().getStringExtra("accountno"));
            branch.setHint(getIntent().getStringExtra("branchname"));
            bankName.setHint(getIntent().getStringExtra("bankname"));
            IfscCode.setHint(getIntent().getStringExtra("ifsc"));
            upi.setHint(getIntent().getStringExtra("upi"));
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Loader loader = new Loader(AccountDetailScreen.this, android.R.style.Theme_Translucent_NoTitleBar);
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                UtilMethods.INSTANCE.SaveDrAccountDetails(AccountDetailScreen.this,
                        Name.getText().toString(), bankName.getText().toString(), AccountNo.getText().toString(), IfscCode.getText().toString(), branch.getText().toString(), upi.getText().toString(), loader);
            }

        });
    }


}
