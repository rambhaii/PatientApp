package com.doc24x7.doctor.Dashbord.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.doc24x7.doctor.Login.dto.Qualification;
import com.google.gson.Gson;
import com.doc24x7.doctor.DoctorDashboad.AccountDetailScreen;
import com.doc24x7.doctor.DoctorDashboad.QualificationScreen;
import com.doc24x7.doctor.DoctorDashboad.QualificationWithoutDeleteAdapter;
import com.doc24x7.doctor.Login.dto.Data;
import com.doc24x7.doctor.Login.ui.OtpActivityNotRegister;
import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.ApplicationConstant;
import com.doc24x7.doctor.Utils.Loader;
import com.doc24x7.doctor.Utils.UtilMethods;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    LinearLayout aboutus, Privacypolicy, logout, li_payment, updateProfile;
    CircleImageView customerimage;
    ImageView editprofile_im;
    RecyclerView rv_qualification;
    Loader loader;
    Data balanceCheckResponse;
    TextView Name, email, Phoneno,onlinefees,offlinefees ,qualification, experience, spetiality, address,updateQuaification,updateAccDetail;
    TextView beneficiaryname, bankname,branchname,accountnumber,ifsc,upi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_profile, container, false);

        Getid(v);

        return v;

    }

    private void Getid(View v) {

        loader = new Loader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);

        Name = v.findViewById(R.id.name_tv);
        email = v.findViewById(R.id.email_tv);
        Phoneno = v.findViewById(R.id.contentnumber_tv);
        editprofile_im = v.findViewById(R.id.editprofile_im);
        customerimage = v.findViewById(R.id.userpicccc);
        qualification = v.findViewById(R.id.qualification);
        experience = v.findViewById(R.id.Experience_tv);
        spetiality = v.findViewById(R.id.Speciality_tv);
        updateAccDetail = v.findViewById(R.id.Bank_Account_Detail);
        updateQuaification = v.findViewById(R.id.Qualification_tv);
        rv_qualification = v.findViewById(R.id.rv_qualification);
        address = v.findViewById(R.id.City_tv);
        onlinefees = v.findViewById(R.id.Online_fee_tv);
        offlinefees = v.findViewById(R.id.onClinic_tv);

        bankname = v.findViewById(R.id.banckname);
        beneficiaryname = v.findViewById(R.id.beneficiaryname);
        branchname = v.findViewById(R.id.branchname);
        accountnumber = v.findViewById(R.id.accuntnumber);
        ifsc = v.findViewById(R.id.ifsccode);
        upi = v.findViewById(R.id.upi);


        aboutus = v.findViewById(R.id.aboutus);
        Privacypolicy = v.findViewById(R.id.Privacypolicy);
        logout = v.findViewById(R.id.massagebox);
        updateProfile = v.findViewById(R.id.updateProfile);
        li_payment = v.findViewById(R.id.li_payment);

      //  customerimage = v.findViewById(R.id.customerimage);
        customerimage.setOnClickListener(this);

        li_payment.setOnClickListener(this);
        logout.setOnClickListener(this);
        updateProfile.setOnClickListener(this);
        updateQuaification.setOnClickListener(this);
        updateAccDetail.setOnClickListener(this);
        editprofile_im.setOnClickListener(this);

        SetData();

    }


    private void SetData() {

        SharedPreferences myPreferences = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.customer_support);
        requestOptions.error(R.drawable.customer_support);

        Glide.with(this)
                .load(balanceCheckResponse.getProfile_pic())
                .apply(requestOptions)
                .into(customerimage);

        Name.setText("" + balanceCheckResponse.getName());
        email.setText("" + balanceCheckResponse.getEmail());
        Phoneno.setText("" + balanceCheckResponse.getMobile());
        experience.setText("" + balanceCheckResponse.getExperience() + "Years");
        onlinefees.setText(""+balanceCheckResponse.getDoctor_fees()+"   Doctor Fees");
        offlinefees.setText(""+balanceCheckResponse.getClinic_fees()+"  On Clinic Doctor Fees");
        address.setText("" + balanceCheckResponse.getAddress() + "," + balanceCheckResponse.getState_name() + "," + balanceCheckResponse.getDistrict_name());
        //qualification.setText("" + new Gson().toJson(balanceCheckResponse.getQualification()));
        spetiality.setText("" + balanceCheckResponse.getSpecialization());
        QualificationWithoutDeleteAdapter mAdapter = new QualificationWithoutDeleteAdapter(balanceCheckResponse.getQualification(), getActivity());
        rv_qualification.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_qualification.setItemAnimator(new DefaultItemAnimator());
        rv_qualification.setAdapter(mAdapter);
        bankname.setText(""+(balanceCheckResponse.getBank_name()==null?" ":balanceCheckResponse.getBank_name().replace('"', ' ')));
        accountnumber.setText("" + (balanceCheckResponse.getAccount_no()==null?" ":balanceCheckResponse.getAccount_no().replace('"', ' ')));
        ifsc.setText("" + (balanceCheckResponse.getIfsc_code()==null?" ":balanceCheckResponse.getIfsc_code().replace('"', ' ')));
        branchname.setText("" + (balanceCheckResponse.getBranch_name()==null?" ":balanceCheckResponse.getBranch_name().replace('"', ' ')));
        upi.setText("" + (balanceCheckResponse.getUpi()==null?" ":balanceCheckResponse.getUpi().replace('"', ' ')));
        beneficiaryname.setText("" + (balanceCheckResponse.getAccount_holder_name()==null?" ":balanceCheckResponse.getAccount_holder_name().replace('"', ' ')));
    }


    @Override
    public void onClick(View view) {

        if (view == logout) {

            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewpop = inflater.inflate(R.layout.logout_pop, null);

            Button okButton = (Button) viewpop.findViewById(R.id.okButton);
            Button Cancel = (Button) viewpop.findViewById(R.id.Cancel);
            TextView msg = (TextView) viewpop.findViewById(R.id.msg);

            final Dialog dialog = new Dialog(getActivity());

            dialog.setCancelable(false);
            dialog.setContentView(viewpop);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    UtilMethods.INSTANCE.logout(getActivity());

                    dialog.dismiss();

                }
            });

            Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });

            dialog.show();


           /* final SweetAlertDialog alertDialog = new SweetAlertDialog(getActivity());
            alertDialog.setTitle("Alert!");
            alertDialog.setContentText("Do you want to logout?");
            alertDialog.setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    alertDialog.dismiss();
                }
            });

            alertDialog.setConfirmButton("Yes", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {

                    UtilMethods.INSTANCE.logout(getActivity());
                }
            });

            alertDialog.show();*/

        } if (view == editprofile_im) {
            Intent intent = new Intent(getActivity(), OtpActivityNotRegister.class);
            intent.putExtra("number", "" + balanceCheckResponse.getMobile());
            intent.putExtra("otp", "" + "otp_user");
            intent.putExtra("Status_get", "" + "2");
            intent.putExtra("iduser", "" + balanceCheckResponse.getId());
            getActivity().startActivity(intent);
        }
        if(view==updateAccDetail)
        {
              Intent i=new Intent(getActivity(),AccountDetailScreen.class);
                 i.putExtra("bank_status","1");
                 i.putExtra("benificaryname",beneficiaryname.getText().toString());
                 i.putExtra("bankname",bankname.getText().toString());
                 i.putExtra("branchname",branchname.getText().toString());
                 i.putExtra("accountno",accountnumber.getText().toString());
                 i.putExtra("ifsc",ifsc.getText().toString());
                 i.putExtra("upi",upi.getText().toString());
                 startActivity(i);

        }
        if(view==updateQuaification)
        {

           Intent i=new Intent(getActivity(), QualificationScreen.class);
            i.putExtra("qualification_status","1");
          //  i.putExtra("qualificationdata", new Gson().fromJson(balanceResponse, Data.class));
            startActivity(i);
           // startActivity(new Intent(getActivity(), QualificationScreen.class));
        }
    }


}