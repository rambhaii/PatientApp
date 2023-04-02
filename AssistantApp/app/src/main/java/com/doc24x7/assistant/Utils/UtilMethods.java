package com.doc24x7.assistant.Utils;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.doc24x7.assistant.Appointment.PatientDetailModel;
import com.doc24x7.assistant.Dashbord.ui.DashboadFragment;
import com.doc24x7.assistant.Dashbord.ui.ProfileFragment;
import com.doc24x7.assistant.PatientDetail;
import com.doc24x7.assistant.PaymentGateway.ui.PaymentActivity;
import com.google.android.gms.maps.model.LatLng;
import com.doc24x7.assistant.Chat.ui.MedicinePreciptionAdapter;
import com.doc24x7.assistant.Dashbord.ui.ViewPriscription;
import com.doc24x7.assistant.DoctorDashboad.DOctoeByIdActivity;
import com.doc24x7.assistant.Dashbord.dto.Datum;
import com.doc24x7.assistant.Dashbord.dto.GalleryListResponse;
import com.doc24x7.assistant.Dashbord.ui.MainActivity;
import com.doc24x7.assistant.DoctorDashboad.AccountDetailScreen;
import com.doc24x7.assistant.DoctorDashboad.QualificationScreen;
import com.doc24x7.assistant.Login.dto.Data;
import static com.doc24x7.assistant.PatientDetail.reporturl;
import com.doc24x7.assistant.Login.ui.OtpActivity;
import com.doc24x7.assistant.Login.ui.OtpActivityNotRegister;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.doc24x7.assistant.Login.dto.secureLoginResponse;
import com.doc24x7.assistant.Login.ui.LoginActivity;
import com.doc24x7.assistant.R;

import com.doc24x7.assistant.Splash.ui.Splash;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
import static com.doc24x7.assistant.PatientDetail.getDataColumn;
import static com.doc24x7.assistant.PatientDetail.isDownloadsDocument;
import static com.doc24x7.assistant.PatientDetail.isExternalStorageDocument;
import static com.doc24x7.assistant.PatientDetail.isGooglePhotosUri;
import static com.doc24x7.assistant.PatientDetail.isMediaDocument;
import static com.google.android.gms.internal.zzahg.runOnUiThread;


public enum UtilMethods {

    INSTANCE;
    //Upload files for viseo call
    public void locationreposeval(Context context, String locationreposeval) {

        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.locationreposeval, locationreposeval);
        editor.commit();

    }


    public void Successful(final Context context, final String message, final int i, final Activity register) {

        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        pDialog.setTitleText(context.getResources().getString(R.string.successful_title));
        pDialog.setContentText(message);
        pDialog.setCustomImage(AppCompatResources.getDrawable(context, R.drawable.ic_tick));
        pDialog.setCancelable(false);
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if (i == 1) {
                    context.startActivity(new Intent(context, LoginActivity.class));
                    sweetAlertDialog.dismiss();
                    register.finish();
                } else if (i == 2) {
                    sweetAlertDialog.dismiss();
                    register.finish();
                } else if (i == 10) {
                    Intent i=new Intent(context,AccountDetailScreen.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(i);
                    sweetAlertDialog.dismiss();
                }
                else if(i==3)
                {
                    Intent i =new Intent(context, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(i);
                   // register.finish();
                    sweetAlertDialog.dismiss();
                }
                else if (i == 11) {
                    context.startActivity(new Intent(context, ViewPriscription.class));
                    sweetAlertDialog.dismiss();
                    register.finish();

                } if (i == 19) {
                    Intent i=new Intent(context, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(i);
                    sweetAlertDialog.dismiss();
                }
                else {

                    sweetAlertDialog.dismiss();

                }

            }
        });
        pDialog.show();
    }

    public void Failed(final Context context, final String message, final int i) {

        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        pDialog.setTitleText(context.getResources().getString(R.string.attention_error_title));
        pDialog.setContentText(message);
        pDialog.setCustomImage(AppCompatResources.getDrawable(context, R.drawable.ic_cancel_black_24dp));
        pDialog.setCancelable(false);
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if (i == 1) {
                    sweetAlertDialog.dismiss();
                } else {
                    sweetAlertDialog.dismiss();
                }
            }
        });
        pDialog.show();
    }

    public void NetworkError(final Context context, String title, final String message) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setCustomImage(AppCompatResources.getDrawable(context, R.drawable.ic_connection_lost_24dp))
                .show();
    }

    public boolean isNetworkAvialable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    TextView textotp, resendotp, resend;
    public EditText edMobileFwp;
    public TextInputLayout tilMobileFwp;

    public Button FwdokButton;
    public Button cancelButton;
    private static CountDownTimer countDownTimer;

    public void setLoginrespose(Context context, String Loginrespose, String one) {

        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.Loginrespose, Loginrespose);
        editor.putString(ApplicationConstant.INSTANCE.one, one);
        editor.commit();

    }

    public void setnumber(Context context, String number) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.number, number);
        editor.commit();
    }

    public void SetType(Context context, String SetType) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.SetType, SetType);
        editor.commit();
    }

    public void SavePreciption(final Activity context, String userid, String description, String appointment_id,
                               final Loader loader, final Activity loginActivity, ArrayList medicine_name, MeicineModel medicineDetail, String userimage) {

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String doctor_id = balanceCheckResponse.getId();

        // Map is used to multipart the file using okhttp3.RequestBody

        File file = new File(userimage);


        MultipartBody.Part fileToUpload1;


        if (userimage.equalsIgnoreCase("1")) {
            fileToUpload1 = MultipartBody.Part.createFormData("preciption_img", "");

        } else {
            RequestBody requestBody1 = RequestBody.create(MediaType.parse("*image/*"), file);
            fileToUpload1 = MultipartBody.Part.createFormData("preciption_img", file.getName(), requestBody1);
        }

        RequestBody doctor_idval = RequestBody.create(MediaType.parse("text/plain"), doctor_id);
        RequestBody descriptionval = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody userIdval = RequestBody.create(MediaType.parse("text/plain"), userid);
        RequestBody medicineDetailval = RequestBody.create(MediaType.parse("text/plain"), new Gson().toJson(medicineDetail));
        RequestBody appointment_idval = RequestBody.create(MediaType.parse("text/plain"), appointment_id);

        Log.e("UserProfileupload", "useridre   " + doctor_idval + "   namere     " + descriptionval + "  " +
                " userid   " + userid + "   doctor_id  " + doctor_id + "   appointment_id  " + appointment_id + "   uuuid  " + userid);

        String header = ApplicationConstant.INSTANCE.Headertoken;


        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<secureLoginResponse> call = git.SavePreciption(header, Integer.parseInt(doctor_id), descriptionval, userIdval, fileToUpload1, medicine_name, medicineDetailval, appointment_idval);

            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("UserProfileuploadres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getStatus().equalsIgnoreCase("1")) {

                                UtilMethods.INSTANCE.Successful(context, "" + response.body().getMessage(), 11, loginActivity);

                                UtilMethods.INSTANCE.GetMedicine(context, loader);

                                context.finish();
                            } else {

                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getMessage(), 0);

                            }

                        } catch (Exception ex) {
                            Log.e("useruploadexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {


                    Log.e("useruploadonfali", "userupload    " + t.getMessage());
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SaveDrQualificationDetails(final Activity context, ArrayList<Integer> qID, ArrayList<String> cName, ArrayList<Integer> pYear, ArrayList<String> qspeciality, final Loader loader,Integer flag) {
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String doctor_id = balanceCheckResponse.getId();
        String header = ApplicationConstant.INSTANCE.Headertoken;
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<QualificationResponse> call = git.SaveDrQualificationDetails(header, Integer.parseInt(doctor_id), qID, cName, pYear, qspeciality);
            call.enqueue(new Callback<QualificationResponse>() {

                @Override
                public void onResponse(Call<QualificationResponse> call, final retrofit2.Response<QualificationResponse> response) {
                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {
                            Log.e("QualificationResponse", "is : " + new Gson().toJson(response.body()).toString());
                            if (response.body().getData().getStatus() == 1) {
                                GetDrProfileData(context, null);
                                if(flag==1)
                                {
                                    UtilMethods.INSTANCE.Successful(context, "Qualification updated succesfull", 19, context);
                                }
                                else {
                                    UtilMethods.INSTANCE.Successful(context, "Qualification updated succesfull", 10, context);
                                }

                            } else {
                                //    context.startActivity(new Intent(context, AccountDetailScreen.class));
                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getData().getMessage(), 0);
                            }
                        } catch (Exception ex) {
                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);
                        }
                    }
                }

                @Override
                public void onFailure(Call<QualificationResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LeaveDate(final Activity context, ArrayList<String> date, final Loader loader) {
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String doctor_id = balanceCheckResponse.getId();
        String header = ApplicationConstant.INSTANCE.Headertoken;
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<QualificationResponse> call = git.Leave(header, Integer.parseInt(doctor_id), date);
            call.enqueue(new Callback<QualificationResponse>() {

                @Override
                public void onResponse(Call<QualificationResponse> call, final retrofit2.Response<QualificationResponse> response) {
                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {
                            Log.e("QualificationResponse", "is : " + new Gson().toJson(response.body()).toString());
                            if (response.body().getData().getStatus() == 1) {
                                UtilMethods.INSTANCE.Successful(context, "Leave Saved successful", 0, context);
                            } else {
                                //    context.startActivity(new Intent(context, AccountDetailScreen.class));
                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getData().getMessage(), 0);
                            }
                        } catch (Exception ex) {
                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);
                        }
                    }
                }

                @Override
                public void onFailure(Call<QualificationResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LeaveShedule(final Activity context, String date, ArrayList<Integer> slotid, final Loader loader) {
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String doctor_id = balanceCheckResponse.getId();
        String header = ApplicationConstant.INSTANCE.Headertoken;
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<QualificationResponse> call = git.LeaveBySchedule(header, Integer.parseInt(doctor_id), date, slotid);
            call.enqueue(new Callback<QualificationResponse>() {

                @Override
                public void onResponse(Call<QualificationResponse> call, final retrofit2.Response<QualificationResponse> response) {
                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {
                            Log.e("QualificationResponse", "is : " + new Gson().toJson(response.body()).toString());
                            if (response.body().getData().getStatus() == 1) {
                                UtilMethods.INSTANCE.Successful(context, "Leave Saved successful", 0, context);

                            } else {
                                //    context.startActivity(new Intent(context, AccountDetailScreen.class));
                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getData().getMessage(), 0);
                            }
                        } catch (Exception ex) {
                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);
                        }
                    }
                }

                @Override
                public void onFailure(Call<QualificationResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SaveDrAccountDetails(final Activity context,
                                     String account_holder_name,
                                     String college_name,
                                     String account_no,
                                     String ifsc_code,
                                     String branch_name,
                                     String upi, final Loader loader) {
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String doctor_id = balanceCheckResponse.getId();
        String header = ApplicationConstant.INSTANCE.Headertoken;
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<QualificationResponse> call = git.SaveDrAccountDetails(header, Integer.parseInt(doctor_id), account_holder_name,
                    college_name,
                    account_no,
                    ifsc_code,
                    branch_name,
                    upi);
            call.enqueue(new Callback<QualificationResponse>() {

                @Override
                public void onResponse(Call<QualificationResponse> call, final retrofit2.Response<QualificationResponse> response) {
                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {
                            Log.e("QualificationResponse", "is : " + new Gson().toJson(response.body()).toString());
                            if (response.body().getData().getStatus() == 1) {
                                // UtilMethods.INSTANCE.Successful(context, "" + response.body().getData().getMessage(), 0, context);
                                GetDrProfileData(context, loader);
                                Intent intent = new Intent(context, MainActivity.class);
                                  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                 context.startActivity(intent);
                                context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                            } else {
                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getData().getMessage(), 0);
                            }
                        } catch (Exception ex) {
                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);
                        }
                    }
                }

                @Override
                public void onFailure(Call<QualificationResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "2Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void DoctorUpdateProfile(final Context context, final String Mobile, String Name, String Email, String experience, String pin,
                                    String district, String state, String address, String clinic_name,
                                    String profile_image, String typeid, LatLng latLng, String assistant_mobile, String assistant_name, String consultation_fees, String registration,String consult_for, String firebaseToken, String doctor_id, final Loader loader, final Activity activity,String clinic_fee) {

        Log.e( "clinicfeeeeeeee: ", clinic_fee);
        File file = new File(profile_image);

        Log.e("profileimage", "" + profile_image);

        MultipartBody.Part fileToUpload1;


        if (profile_image.equalsIgnoreCase("1")) {
            fileToUpload1 = MultipartBody.Part.createFormData("profile_pic", "");

        } else {
            RequestBody requestBody1 = RequestBody.create(MediaType.parse("*image/*"), file);
            fileToUpload1 = MultipartBody.Part.createFormData("profile_pic", file.getName(), requestBody1);
        }

        RequestBody idval = RequestBody.create(MediaType.parse("text/plain"), Mobile);
        RequestBody nameval = RequestBody.create(MediaType.parse("text/plain"), Name);
        RequestBody Emailval = RequestBody.create(MediaType.parse("text/plain"), Email);
        RequestBody experienceval = RequestBody.create(MediaType.parse("text/plain"), experience);
        RequestBody pinval = RequestBody.create(MediaType.parse("text/plain"), pin);
        RequestBody districtval = RequestBody.create(MediaType.parse("text/plain"), district);
        RequestBody stateval = RequestBody.create(MediaType.parse("text/plain"), state);
        RequestBody addressval = RequestBody.create(MediaType.parse("text/plain"), address);
        RequestBody clinic_nameval = RequestBody.create(MediaType.parse("text/plain"), clinic_name);
        RequestBody typeidval = RequestBody.create(MediaType.parse("text/plain"), typeid);

        RequestBody assistant_mobileval = RequestBody.create(MediaType.parse("text/plain"), assistant_mobile);
        RequestBody assistant_nameval = RequestBody.create(MediaType.parse("text/plain"), assistant_name);
        RequestBody latitudeval = RequestBody.create(MediaType.parse("text/plain"),"26.8466937 "/*latLng.latitude + ""*/);
        RequestBody longitudeval = RequestBody.create(MediaType.parse("text/plain"), "80.94616599999999"/*latLng.longitude + ""*/);
        RequestBody consultation_feesval = RequestBody.create(MediaType.parse("text/plain"), consultation_fees);
        RequestBody clinic_fees = RequestBody.create(MediaType.parse("text/plain"), clinic_fee);
        RequestBody registrationval = RequestBody.create(MediaType.parse("text/plain"), registration);
        RequestBody consult_forval = RequestBody.create(MediaType.parse("text/plain"), consult_for);
        RequestBody firebaseTokenval = RequestBody.create(MediaType.parse("text/plain"), firebaseToken);
        RequestBody doctor_idval = RequestBody.create(MediaType.parse("text/plain"), doctor_id);
        Log.e("UserProfileupload", "useridre   " + clinic_name + "   namere     " + clinic_name);
        Log.e("lanlong",latitudeval+ "\n" +longitudeval);
        String header = ApplicationConstant.INSTANCE.Headertoken;
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<secureLoginResponse> call = git.DoctorRegister(header, idval, nameval, Emailval, typeidval,
                    experienceval, pinval, districtval, stateval, addressval, clinic_nameval, assistant_nameval,
                    assistant_mobileval, consultation_feesval, firebaseTokenval, latitudeval, longitudeval, doctor_idval, registrationval,
                    consult_forval,clinic_fees,fileToUpload1);

            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("UserProfileuploadres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData().getStatus().equalsIgnoreCase("1")) {
                                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("")
                                        .setContentText("" + "" + response.body().getData().getMessage())
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog s) {
                                                UtilMethods.INSTANCE.setLoginrespose(context, "" + new Gson().toJson(response.body().getData()).toString(), "1");
                                                SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
                                                UtilMethods.INSTANCE.SaveToken(context, prefs.getString(ApplicationConstant.INSTANCE.token, ""));
                                                UtilMethods.INSTANCE.setnumber(context, Mobile);
                                                if (response.body().getData().getQualification().size() > 0) {
                                                    Intent intent = new Intent(activity, MainActivity.class);
                                                    // Toast.makeText(context, ""+response.body().getData().getClinic_fees(), Toast.LENGTH_SHORT).show();
                                                    activity.startActivity(intent);
                                                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                                    activity.finish();
                                                } else {
                                                    Intent intent = new Intent(activity, QualificationScreen.class);
                                                     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    activity.startActivity(intent);
                                                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                                                }
                                                s.dismiss();
                                            }
                                        }).show();


                                //  UtilMethods.INSTANCE.Successful(context,""+response.body().getData().getMessage(),0,null);
                                //  UtilMethods.INSTANCE.DoctorLogin(context, otp, loader, activity);


                            } else {

                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getData().getMessage(), 0);

                            }

                        } catch (Exception ex) {
                            Log.e("useruploadexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {


                    Log.e("useruploadonfali", "userupload    " + t.getMessage());
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    //UtilMethods.INSTANCE.Successful(context,"Data Updated Successfully" ,0,null);

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void DoctorLogin(final Context context, final String edMobile, final Loader loader, final Activity loginActivity,int flag) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("login", " , email : " + edMobile);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.DoctorLogin(header, edMobile);
            call.enqueue(new Callback<secureLoginResponse>() {

                //8574857485

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("loginres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {
                            if (flag==1) {
                                if (response.body().getData() != null) {

                                    if (response.body().getData().getStatus().equalsIgnoreCase("1")) {
                                        Intent intent = new Intent(context, OtpActivity.class);
                                        //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.putExtra("number", "" + edMobile);
                                        intent.putExtra("otp", "" + response.body().getData().getOtp());
                                        intent.putExtra("Status_get", "" + response.body().getData().getStatus());
                                        context.startActivity(intent);


                                    } else if (response.body().getData().getStatus().equalsIgnoreCase("2")) {
                                        Toast.makeText(context, "Please Enter Registerd Doctor Number", Toast.LENGTH_SHORT).show();
                                    } else {

                                        UtilMethods.INSTANCE.Failed(context, response.body().getData().getMessage(), 0);
                                    }


                                }
                            }
                           else if(flag==2)
                            {
                                if (response.body().getData() != null) {
                                    Toast.makeText(context, "OTP Resend Sucessful", Toast.LENGTH_SHORT).show();
                                } else {

                                        UtilMethods.INSTANCE.Failed(context, response.body().getData().getMessage(), 0);
                                    }

                                }

                            else {

                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getMessage(), 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            // UtilMethods.INSTANCE.Failed(context,"Sorry Wrong Password or Email...",0);

                            Intent intent = new Intent(context, OtpActivity.class);
                            intent.putExtra("number", "" + edMobile);
                            intent.putExtra("otp", "" + response.body().getData().getOtp());
                            context.startActivity(intent);
                            loginActivity.finish();

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    //  UtilMethods.INSTANCE.Failed(context,"please check login id and password",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SaveToken(final Context context, String token) {

        String header = ApplicationConstant.INSTANCE.Headertoken;
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getId();

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.SaveToken(header, member_id, token, "2");
            call.enqueue(new Callback<secureLoginResponse>() {

                //8574857485

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("SaveToken", "is : " + new Gson().toJson(response.body()).toString());


                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void DrPatientDetails(final Context context, final String userId,  final Loader loader) {
        String header = ApplicationConstant.INSTANCE.Headertoken;

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getId();
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.DrPatientDetails(header, Integer.parseInt(userId), Integer.parseInt(member_id));
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("loginres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {

                                if (response.body().getData().size() > 0) {

                                    FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("online_tabs_patient",
                                            "" + new Gson().toJson(response.body()).toString());
                                    GlobalBus.getBus().post(fragmentActivityMessage);


//                                    Intent intent = new Intent(context, DOctoeByIdActivity.class);
//                                    //TODO
//                                    intent.putExtra("response", "" + new Gson().toJson(response.body()).toString());
//                                    intent.putExtra("type", "8");
//                                    intent.putExtra("pa_name", "" + pa_name);
//                                    intent.putExtra("pa_mobile", "" + pa_mobile);
//                                    intent.putExtra("status","1");
//                                    intent.putExtra("profilePIC", "" + profilePIC);
//                                    intent.putExtra("Appointment_id", "" + response.body().getData().get(0).getAppointment_id());
//                                    intent.putExtra("useridpref", "" + userId);
//                                    context.startActivity(intent);
                                } else {
                                    UtilMethods.INSTANCE.Failed(context, "No Patient found", 0);
                                }




                               /* Intent intent = new Intent(context, PatientDetailsActivity.class);
                                 intent.putExtra("respose", "" + new Gson().toJson(response.body()).toString());
                                context.startActivity(intent);
*/





                                /*



                                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View view = inflater.inflate(R.layout.singleguzal, null);

                                ImageView cancelButton =  view.findViewById(R.id.cancelButton);
                                TextView Shayarname =  view.findViewById(R.id.Shayarname);
                                TextView misra =  view.findViewById(R.id.misra);
                                TextView gazal =  view.findViewById(R.id.gazal);
                                TextView join_date =  view.findViewById(R.id.join_date);

                                final Dialog dialog = new Dialog(context);

                                dialog.setCancelable(false);
                                dialog.setContentView(view);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                Shayarname.setText(""+response.body().getData().getMobile());
                                misra.setText(""+response.body().getData().getName());
                                gazal.setText(""+response.body().getData().getEmail());
                                join_date.setText(""+response.body().getData().getJoin_date());

                                cancelButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });



                                dialog.show();


*/


                            } else {

                                UtilMethods.INSTANCE.Failed(context, "No Data Found", 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    //  UtilMethods.INSTANCE.Failed(context,"please check login id and password",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void DrPatientDetailsOffline(final Context context, final String userId,  final Loader loader) {
        String header = ApplicationConstant.INSTANCE.Headertoken;

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getId();
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.DrPatientDetailsoffline(header, Integer.parseInt(userId), Integer.parseInt(member_id));
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("loginres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {

                                if (response.body().getData().size() > 0) {
                                    FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("offline_tabs_patient",
                                            "" + new Gson().toJson(response.body()).toString());
                                    GlobalBus.getBus().post(fragmentActivityMessage);


//                                    Intent intent = new Intent(context, DOctoeByIdActivity.class);
//                                    //TODO
//                                    intent.putExtra("response", "" + new Gson().toJson(response.body()).toString());
//                                    intent.putExtra("type", "8");
//                                    intent.putExtra("pa_name", "" + pa_name);
//                                    intent.putExtra("pa_mobile", "" + pa_mobile);
//                                    intent.putExtra("status","2");
//                                    intent.putExtra("profilePIC", "" + profilePIC);
//                                    intent.putExtra("Appointment_id", "" + response.body().getData().get(0).getAppointment_id());
//                                    intent.putExtra("useridpref", "" + userId);
//                                    context.startActivity(intent);
                                } else {
                                    UtilMethods.INSTANCE.Failed(context, "No Patient found", 0);
                                }




                               /* Intent intent = new Intent(context, PatientDetailsActivity.class);
                                 intent.putExtra("respose", "" + new Gson().toJson(response.body()).toString());
                                context.startActivity(intent);
*/





                                /*



                                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View view = inflater.inflate(R.layout.singleguzal, null);

                                ImageView cancelButton =  view.findViewById(R.id.cancelButton);
                                TextView Shayarname =  view.findViewById(R.id.Shayarname);
                                TextView misra =  view.findViewById(R.id.misra);
                                TextView gazal =  view.findViewById(R.id.gazal);
                                TextView join_date =  view.findViewById(R.id.join_date);

                                final Dialog dialog = new Dialog(context);

                                dialog.setCancelable(false);
                                dialog.setContentView(view);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                Shayarname.setText(""+response.body().getData().getMobile());
                                misra.setText(""+response.body().getData().getName());
                                gazal.setText(""+response.body().getData().getEmail());
                                join_date.setText(""+response.body().getData().getJoin_date());

                                cancelButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });



                                dialog.show();


*/


                            } else {

                                UtilMethods.INSTANCE.Failed(context, "No Data Found", 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    //  UtilMethods.INSTANCE.Failed(context,"please check login id and password",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GetTodayDoctorAppointment(final Context context, final Loader loader) {
        String header = ApplicationConstant.INSTANCE.Headertoken;
        String date = "" + UtilMethods.INSTANCE.getCurrentDate();
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getId();
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.GetTodayDoctorAppointment(header, date, Integer.parseInt(member_id));
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                   //-+ Log.e("GetTodayDoctorAppointment", "is : " + new Gson().toJson(response.body()).toString());
                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {

                                if (response.body().getData().size() > 0) {
                                    Intent intent = new Intent(context, DOctoeByIdActivity.class);
                                    //TODO
                                    intent.putExtra("response", "" + new Gson().toJson(response.body()).toString());
                                    intent.putExtra("type", "7");
                                    intent.putExtra("status", "1");
                                    intent.putExtra("pa_name", "");
                                    intent.putExtra("pa_mobile", "");
                                    intent.putExtra("profilePIC", "");
                                    intent.putExtra("mobileno",response.body().getData().get(0).getUser_mobile());
                                    intent.putExtra("Appointment_id", "" + response.body().getData().get(0).getAppointment_id());
                                    intent.putExtra("useridpref", "");
                                    context.startActivity(intent);
                                } else {
                                    UtilMethods.INSTANCE.Failed(context, "No patient found", 0);
                                }




                               /* Intent intent = new Intent(context, PatientDetailsActivity.class);
                                 intent.putExtra("respose", "" + new Gson().toJson(response.body()).toString());
                                context.startActivity(intent);
*/





                                /*



                                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View view = inflater.inflate(R.layout.singleguzal, null);

                                ImageView cancelButton =  view.findViewById(R.id.cancelButton);
                                TextView Shayarname =  view.findViewById(R.id.Shayarname);
                                TextView misra =  view.findViewById(R.id.misra);
                                TextView gazal =  view.findViewById(R.id.gazal);
                                TextView join_date =  view.findViewById(R.id.join_date);

                                final Dialog dialog = new Dialog(context);

                                dialog.setCancelable(false);
                                dialog.setContentView(view);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                Shayarname.setText(""+response.body().getData().getMobile());
                                misra.setText(""+response.body().getData().getName());
                                gazal.setText(""+response.body().getData().getEmail());
                                join_date.setText(""+response.body().getData().getJoin_date());

                                cancelButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });



                                dialog.show();


*/


                            } else {

                                UtilMethods.INSTANCE.Failed(context, "No Data Found", 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    //  UtilMethods.INSTANCE.Failed(context,"please check login id and password",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void GetTodayDoctorAppointmentOffline(final Context context, final Loader loader) {
        String header = ApplicationConstant.INSTANCE.Headertoken;
        String date = "" + UtilMethods.INSTANCE.getCurrentDate();
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getId();
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.GetTodayDoctorAppointmentoffline(header, date, Integer.parseInt(member_id));
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    //-+ Log.e("GetTodayDoctorAppointment", "is : " + new Gson().toJson(response.body()).toString());
                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {

                                if (response.body().getData().size() > 0) {
                                    Intent intent = new Intent(context, DOctoeByIdActivity.class);
                                    //TODO
                                    intent.putExtra("response", "" + new Gson().toJson(response.body()).toString());
                                    intent.putExtra("type", "7");
                                    intent.putExtra("status", "2");
                                    intent.putExtra("pa_name", "");
                                    intent.putExtra("pa_mobile", "");
                                    intent.putExtra("profilePIC", "");
                                    intent.putExtra("mobileno",response.body().getData().get(0).getUser_mobile());
                                    intent.putExtra("Appointment_id", "" + response.body().getData().get(0).getAppointment_id());
                                    intent.putExtra("useridpref", "");
                                    context.startActivity(intent);
                                } else {
                                    UtilMethods.INSTANCE.Failed(context, "No patient found", 0);
                                }




                               /* Intent intent = new Intent(context, PatientDetailsActivity.class);
                                 intent.putExtra("respose", "" + new Gson().toJson(response.body()).toString());
                                context.startActivity(intent);
*/





                                /*



                                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View view = inflater.inflate(R.layout.singleguzal, null);

                                ImageView cancelButton =  view.findViewById(R.id.cancelButton);
                                TextView Shayarname =  view.findViewById(R.id.Shayarname);
                                TextView misra =  view.findViewById(R.id.misra);
                                TextView gazal =  view.findViewById(R.id.gazal);
                                TextView join_date =  view.findViewById(R.id.join_date);

                                final Dialog dialog = new Dialog(context);

                                dialog.setCancelable(false);
                                dialog.setContentView(view);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                Shayarname.setText(""+response.body().getData().getMobile());
                                misra.setText(""+response.body().getData().getName());
                                gazal.setText(""+response.body().getData().getEmail());
                                join_date.setText(""+response.body().getData().getJoin_date());

                                cancelButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });



                                dialog.show();


*/


                            } else {

                                UtilMethods.INSTANCE.Failed(context, "No Data Found", 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    //  UtilMethods.INSTANCE.Failed(context,"please check login id and password",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DoctorOtpVerify(final Activity context, final String otp_user, final String number, final Loader loader, final Activity loginActivity) {

        String header = ApplicationConstant.INSTANCE.Headertoken;


        Log.e("login", " , email : " + otp_user + "  " + number);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.DoctorOtpVerify(header, number, otp_user);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("loginresfdffdf", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {


                                if (response.body().getData().getStatus().equalsIgnoreCase("3")) {


                                    UtilMethods.INSTANCE.Failed(context, "" + response.body().getData().getMessage(), 0);


                                } else {
                                    if (response.body().getData().getName().isEmpty()) {
                                        Intent intent = new Intent(context, OtpActivityNotRegister.class);
                                        intent.putExtra("number", "" + number);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                        intent.putExtra("otp", "" + otp_user);
                                        intent.putExtra("Status_get", "" + "2");
                                        intent.putExtra("iduser", "" + response.body().getData().getId());
                                        context.startActivity(intent);

                                    } else {
                                        UtilMethods.INSTANCE.setLoginrespose(context, "" + new Gson().toJson(response.body().getData()).toString(), "1");
                                        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
                                        UtilMethods.INSTANCE.SaveToken(context, prefs.getString(ApplicationConstant.INSTANCE.token, ""));
                                        UtilMethods.INSTANCE.setnumber(context, number);
                                        Intent intent = new Intent(context, MainActivity.class);
                                           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        context.startActivity(intent);
                                        context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                                    }

                                }


                            } else {

                                UtilMethods.INSTANCE.Failed(context, "Failure", 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, "Sorry Wrong Password or Email...", 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "please check login id and password", 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void AllQualifiation(final Context context, final Loader loader) {
        String header = ApplicationConstant.INSTANCE.Headertoken;
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.GetAllQualification(header);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("getbannerres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {

                                if (response.body().getData().size() > 0) {
                                    SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString(ApplicationConstant.INSTANCE.qualification, new Gson().toJson(response.body()).toString());
                                    editor.commit();
                                }
                            } else {

                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getMessage(), 0);

                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GetDrProfileData(final Context context, final Loader loader) {
        String header = ApplicationConstant.INSTANCE.Headertoken;
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getId();
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.GetDrProfileData(header, member_id);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("GetDrProfileData", "is : " + new Gson().toJson(response.body()).toString());
                    //Toast.makeText(context, ""+new Gson().toJson(response.body()).toString(), Toast.LENGTH_SHORT).show();

                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {
                                if (!response.body().getData().getName().isEmpty()) {
                                    UtilMethods.INSTANCE.setLoginrespose(context, "" + new Gson().toJson(response.body().getData()).toString(), "1");
                                }
                            } else {

                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getMessage(), 0);

                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DrIncome(final Context context, final Loader loader) {
        String header = ApplicationConstant.INSTANCE.Headertoken;
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getId();
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.DrIncome(header, member_id);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("DrIncome", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {

                                FragmentActivityMessage
                                        fragmentActivityMessage =
                                        new FragmentActivityMessage("GetPreciption", "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);

                            } else {

                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getMessage(), 0);

                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doctorlist(final Context context, final Loader loader) {

        String header = ApplicationConstant.INSTANCE.Headertoken;


        Log.e("specialities", "   name   ");

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.AllTypes(header);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("specialitiesres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {
                                if (response.body().getData().size() > 0) {
                                    SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString(ApplicationConstant.INSTANCE.allCategoryDoctor, new Gson().toJson(response.body()).toString());
                                    editor.commit();
                                }
                                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("specialities",
                                        "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);


                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void PatientList(final Context context, final Loader loader) {
        String header = ApplicationConstant.INSTANCE.Headertoken;
        String date = "" + UtilMethods.INSTANCE.getCurrentDate();
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String doctor_id = balanceCheckResponse.getId();

        Log.e("getbanner", "   name   ");

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.DrPatientList(header, doctor_id);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("getbannerres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {


                                if (response.body().getData().size() > 0) {
                                    Intent intent = new Intent(context, DOctoeByIdActivity.class);
                                    intent.putExtra("response", "" + new Gson().toJson(response.body()).toString());
                                    intent.putExtra("type", "6");
                                    context.startActivity(intent);
                                } else {
                                    UtilMethods.INSTANCE.Failed(context, "No Data found", 0);
                                }


                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GetAllDoctorAppointmentbyDate(final Context context, final Loader loader) {

        String header = ApplicationConstant.INSTANCE.Headertoken;
        String date = "" + UtilMethods.INSTANCE.getCurrentDate();

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String doctor_id = balanceCheckResponse.getId();


        Log.e("getbanner", "   name   ");

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.GetAllDoctorAppointment(header, date, doctor_id);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("getbannerres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {


                                if (response.body().getData().size() > 0) {
                                    Intent intent = new Intent(context, DOctoeByIdActivity.class);
                                    intent.putExtra("response", "" + new Gson().toJson(response.body()).toString());
                                    intent.putExtra("type", "6");
                                    context.startActivity(intent);
                                } else {
                                    UtilMethods.INSTANCE.Failed(context, "No Data found", 0);
                                }


                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GetAllDoctorAppointment(final Context context, final Loader loader) {

        String header = ApplicationConstant.INSTANCE.Headertoken;
        String date = "" + UtilMethods.INSTANCE.getCurrentDate();

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String doctor_id = balanceCheckResponse.getId();


        Log.e("getbanner", "   name   ");

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.GetAllDoctorAppointment(header, Integer.parseInt(doctor_id));
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("getbannerres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {


                                if (response.body().getData().size() > 0) {
                                    Intent intent = new Intent(context, DOctoeByIdActivity.class);
                                    intent.putExtra("response", "" + new Gson().toJson(response.body()).toString());
                                    intent.putExtra("type", "6");
                                    context.startActivity(intent);
                                } else {
                                    UtilMethods.INSTANCE.Failed(context, "No Data found", 0);
                                }


                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getbanner(final Context context, final Loader loader) {

        String header = ApplicationConstant.INSTANCE.Headertoken;


        Log.e("getbanner", "   name   ");

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.getbanner(header);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("getbannerres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {

                                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("GalleryList",
                                        "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);

                            } else {

                                // UtilMethods.INSTANCE.Failed(context,""+response.body().getMessage(),0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void Doctors(final Context context, String id, final Loader loader) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("Doctorsaaaaaaaaaa", "   name   " + id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.Doctors(header, id);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("Doctorsres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {
                                if (response.body().getData().size() > 0) {
                                    Intent intent = new Intent(context, DOctoeByIdActivity.class);
                                    intent.putExtra("response", "" + new Gson().toJson(response.body()).toString());
                                    intent.putExtra("type", "2");
                                    context.startActivity(intent);
                                } else {
                                    UtilMethods.INSTANCE.Failed(context, "No doctor found", 0);
                                }

                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCurrentDate() {
        Calendar c = Calendar.getInstance();
       // System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        //  String formattedDate = "2020-10-24";
        // formattedDate have current date/time
        Log.d("get_current_date :", formattedDate);

        return formattedDate;
    }

    public void DeleteAvailability(final Context context, String id, final String day, final Loader loader, final int count, final int countleft) {

        String header = ApplicationConstant.INSTANCE.Headertoken;


        Log.e("Doctors", "   name   " + id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.DeleteAvailability(header, id);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {
                    Log.e("Doctorsres", "is : " + new Gson().toJson(response.body()).toString());
                    if (response != null) {
                        if (count == countleft) {
                            if (loader != null) {
                                if (loader.isShowing())
                                    loader.dismiss();
                            }
                        }
                        try {
                            if (response.body().getData() != null) {
                                if (response.body().getData().getStatus().equalsIgnoreCase("1")) {
                                    if (count == countleft) {
                                        UtilMethods.INSTANCE.Successful(context, response.body().getData().getMessage(), 0, null);
                                        UtilMethods.INSTANCE.GetAvailability(context, day, loader);
                                    }
                                } else {
                                    if (count == countleft) {
                                        UtilMethods.INSTANCE.Failed(context, response.body().getData().getMessage(), 0);
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());
                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);
                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void DeleteAvailabilityOffline(final Context context, String id, final String day, final Loader loader, final int count, final int countleft) {

        String header = ApplicationConstant.INSTANCE.Headertoken;


        Log.e("Doctorsssidd", "   name   " + id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.DeleteAvailabilityOffline(header, id);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {
                    Log.e("Doctorsres", "is : " + new Gson().toJson(response.body()).toString());
                    if (response != null) {
                        if (count == countleft) {
                            if (loader != null) {
                                if (loader.isShowing())
                                    loader.dismiss();
                            }
                        }
                        try {
                            if (response.body().getData() != null) {
                                if (response.body().getData().getStatus().equalsIgnoreCase("1")) {
                                    if (count == countleft) {
                                        UtilMethods.INSTANCE.Successful(context, response.body().getData().getMessage(), 0, null);
                                        UtilMethods.INSTANCE.GetAvailability(context, day, loader);
                                    }
                                } else {
                                    if (count == countleft) {
                                        UtilMethods.INSTANCE.Failed(context, response.body().getData().getMessage(), 0);
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());
                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);
                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void GetAvailability(final Context context, String day, final Loader loader) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getId();


        Log.e("Doctors", "   name   " + member_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.GetAvailability(header, member_id, day);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("Doctorsres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {

                                FragmentActivityMessage
                                        fragmentActivityMessage =
                                        new FragmentActivityMessage("getavalablelel", "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);

                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void GetAvailabilityOffline(final Context context, String day, final Loader loader) {
        String header = ApplicationConstant.INSTANCE.Headertoken;
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getId();
        Log.e("Doctorsoffline", "   name   " + member_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.GetAvailabilityOffline(header, member_id, day);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("Doctorsressssss", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {

                                FragmentActivityMessage
                                        fragmentActivityMessage =
                                        new FragmentActivityMessage("getavalablelelOffline", "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);

                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void GetAllPatientAppointmentoffline(final Context context, final Loader loader) {

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id ="0";

        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("Doctors", "   name   " + member_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.GetAllPatientAppointmentOffline(header, member_id);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("Doctorsres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null && response.body().getData().size() > 0) {

                                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("offline_tabs_appointment",
                                        "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);

                            } else {

                                UtilMethods.INSTANCE.Failed(context, "no data found", 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void GetAvailabilityOfflineass(final Context context, String id, String date, String day, final Loader loader,TextView tv) {
        loader.show();
        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("GetAvailabilityoffline", "   name   " + id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.GetAvailabilityOffline(header, id, date, day);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("GetAvailabilityresggfg", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null && response.body().getData().size() > 0) {

                                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("AppointmentOffline",
                                        "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);
                            } else {
                                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("AvailableOffline",
                                        "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);
                                // Toast.makeText(context, "Doctor is not Available today", Toast.LENGTH_SHORT).show();
                                tv.setVisibility(View.VISIBLE);
                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void BookAppointmentoffline(final Context context, String doctor_id, String slot_id, String parent_slot_id, String date, final Loader loader,
                                       String no_of_patient, Activity activity, PatientDetailModel patientDetail, int payment_id, int AppointId,String mobileNo,String name,String email) {


        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = "0";
        //   Toast.makeText(context, ""+slot_id+"\n"+parent_slot_id, Toast.LENGTH_SHORT).show();
        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("Paymentsussesbooking", "   member_id   " + member_id + "   doctor_id   " + doctor_id + "    slot_id   " + slot_id + "  date    " +
                date +"parent_slot_id    "+parent_slot_id+"   ");

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.BookAppointmentoffline(header, doctor_id, slot_id, parent_slot_id, new Gson().toJson(patientDetail), no_of_patient, payment_id, date,mobileNo,name,email);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("Paymentsussesbookingres", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {

                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }

                        try {

                            if (response.body().getData().getStatus().equalsIgnoreCase("1")) {
                              //  UtilMethods.INSTANCE.rescheduleAppointmentoffline(context, Integer.parseInt(doctor_id), AppointId);
                                UtilMethods.INSTANCE.Successful(context, response.body().getData().getMessage(), 19, activity);
                            } else {

                                // activity.finish();
                                UtilMethods.INSTANCE.Failed(context, response.body().getData().getMessage(), 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rescheduleAppointmentoffline(final Context context, int doctor_Id, int AppointId) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        //  GlobalData.latitude, GlobalData.longitude


       // Log.e("Createghazal", "usertoken   :  " + "token" + "     :   vall     " + GlobalData.longitude + "  latitude   " + GlobalData.latitude);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.rescheduleAppointmentoffline(header, doctor_Id, AppointId);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {
                    Log.e("Createghazalres", "is : " + new Gson().toJson(response.body()).toString());
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void Paymentsusses(final Context context, final String DOctorid, String id, final Loader loader, String amount,
                              final Datum operator, final String Symtom_id, String slot_id, String parent_slot_id,
                              Datum datum, String tv_datetime, Activity activity, String DateTile, String bookstatus) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = "62";

        Log.e("Paymentsusses", "   name   " + member_id + "  slot_id  " + slot_id + "  doctor_id  " + DOctorid + "  amount   " + amount);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            //  Call<secureLoginResponse> call = git.Paymentsusses(header, "" + DOctorid, "" + amount, "" + member_id, "xyxyxyrgcv ", "1");
            Call<secureLoginResponse> call = git.Paymentsusses(header, "" + DOctorid, "" + DOctorid, "" + slot_id,
                    "1 ", " Assistant Booking Payment", amount);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("Paymentsussesres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {

                                if (response.body().getData().getStatus().equalsIgnoreCase("1")) {
                                    activity.finish();
                                   // Toast.makeText(context, "" + response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(context, PatientDetail.class);
                                    i.putExtra("currentDay", DateTile + "");
                                    i.putExtra("dateTime", "" + tv_datetime);
                                    i.putExtra("Doctordetail", new Gson().toJson(operator));
                                    i.putExtra("slot_id", slot_id);
                                    i.putExtra("doctorid",DOctorid);
                                    i.putExtra("bookstatus",bookstatus);
                                    i.putExtra("payment_id", response.body().getData().getPayment_id() + "");
                                    i.putExtra("parent_slot_id", parent_slot_id);
                                  //  i.putExtra("AppintmentDetail", 0);
                                    context.startActivity(i);
                                } else {
                                    UtilMethods.INSTANCE.Failed(context, response.body().getData().getMessage(), 0);

                                }


                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UpdateAvailability(final Context context, String start_time,
                                   String no_of_patient,
                                   String end_timeid,
                                   final String day,
                                   String status,
                                   String availability_id, final Loader loader) {

        String header = ApplicationConstant.INSTANCE.Headertoken;
        no_of_patient = no_of_patient.replace(" Patients", "");
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getId();


        Log.e("Doctors", "   name   " + member_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.UpdateAvailability(header, start_time,
                    no_of_patient,
                    end_timeid,
                    day,
                    status,
                    availability_id);

            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("Doctorsres", "is : " + new Gson().toJson(response.body()).toString());
                  //  Toast.makeText(context, ""+ new Gson().toJson(response.body()).toString(), Toast.LENGTH_SHORT).show();

                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getStatus().equals("1")) {


                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UpdateAvailabilityOffline(final Context context, String start_time,
                                          String no_of_patient,
                                          String end_timeid,
                                          final String day,
                                          String status,
                                          String availability_id, final Loader loader) {

        String header = ApplicationConstant.INSTANCE.Headertoken;
        no_of_patient = no_of_patient.replace(" Patients", "");
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getId();


        Log.e("Doctors", "   name   " + member_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.UpdateAvailabilityOffline(header, start_time,
                    no_of_patient,
                    end_timeid,
                    day,
                    status,
                    availability_id);

            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("Doctorsres", "is : " + new Gson().toJson(response.body()).toString());
                    //  Toast.makeText(context, ""+ new Gson().toJson(response.body()).toString(), Toast.LENGTH_SHORT).show();

                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getStatus().equals("1")) {


                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void GetMedicine(final Context context, final Loader loader) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        final SharedPreferences.Editor editor = myPreferences.edit();
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getId();


        Log.e("Doctors", "   name   " + member_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.GetMedicine(header, member_id);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("Medicine", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {
                                editor.putString("Medicine", new Gson().toJson(response.body()).toString()).apply();
                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void chkpayment(final Activity context, final String id, final Datum operator,
                           final Loader loader, final String Symtom_id, String doctor_id, String slot_id, String parent_slot_id,
                           String amount, Datum patAppointmentDetail, String tv_datetime, String DateTile, Activity activity,String bookstatus) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id ="0";



        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            // Call<secureLoginResponse> call = git.chkpayment(header, id, member_id);
            Call<secureLoginResponse> call = git.chkpayments(header, member_id, doctor_id, slot_id, DateTile);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    //  Toast.makeText(context, ""+id, Toast.LENGTH_SHORT).show();
                    Log.e("Paymentsusses", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {

                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {
                            if (response.body().getData() != null) {
                                if (response.body().getData().getStatus().equalsIgnoreCase("0")) {

                                    Intent intent = new Intent(context, PaymentActivity.class);
                                    intent.putExtra("amount", "" + amount);
                                    intent.putExtra("namepack", "1");
                                    intent.putExtra("doctorid", id);
                                    intent.putExtra("Symtom_id", Symtom_id);
                                    intent.putExtra("slot_id", slot_id);
                                    intent.putExtra("parent_slot_id", parent_slot_id);
                                    intent.putExtra("payment_id", response.body().getData().getPayment_id() + "");
                                    intent.putExtra("tv_datetime", tv_datetime);
                                    intent.putExtra("DateTile", DateTile);
                                    intent.putExtra("bookstatus",bookstatus);
                                    intent.putExtra("doctorDetail", new Gson().toJson(operator));
                                    intent.putExtra("AppintmentDetail", new Gson().toJson(patAppointmentDetail));
                                    context.startActivity(intent);
                                } else {
                                    Intent i = new Intent(context, PatientDetail.class);
                                    i.putExtra("currentDay", DateTile + "");
                                    i.putExtra("dateTime", "" + tv_datetime);
                                    i.putExtra("slot_id", slot_id);
                                    i.putExtra("bookstatus",bookstatus);
                                    i.putExtra("doctorid", id);
                                    i.putExtra("parent_slot_id", parent_slot_id);
                                    i.putExtra("Doctordetail", new Gson().toJson(operator));
                                    i.putExtra("payment_id", response.body().getData().getPayment_id() + "");
                                    i.putExtra("AppintmentDetail", new Gson().toJson(patAppointmentDetail));
                                    context.startActivity(i);


                                  /*  UtilMethods.INSTANCE.BookAppointment(context,""+ doctor_id,""+ slot_id,
                                            ""+tv_datetime , loader,""+
                                            patAppointmentDetail.getNo_of_patient(),activity);*/
                                    /* Intent intent = new Intent(context, DOctoeByIdActivity.class);
                                    intent.putExtra("response", "" + new Gson().toJson(operator));
                                    intent.putExtra("type", "1");
                                    intent.putExtra("doctorDetail", new Gson().toJson(operator));
                                    context.startActivity(intent);
                                    context.finish();*/


                                }


                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
















    public void GetPreciption(final Context context, final Loader loader, String appointment_id) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getId();


        Log.e("GetPreciption", "   name   " + member_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.GetPreciption(header, Integer.parseInt(member_id), Integer.parseInt(appointment_id));
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("GetPreciptionres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {

                                FragmentActivityMessage
                                        fragmentActivityMessage =
                                        new FragmentActivityMessage("GetPreciption", "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);

                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    ArrayList<Datum> transactionsObjects = new ArrayList<>();
    GalleryListResponse transactions = new GalleryListResponse();
    LinearLayoutManager mLayoutManager;
    MedicinePreciptionAdapter mAdapter;

    public void GetMedicineById(final Context context, String medicine_id, final RecyclerView recycler_view) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("Doctors", "   name   ");

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.GetMedicineById(header, medicine_id);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("Doctorsres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {

                        try {

                            if (response.body().getData() != null) {


                                String reeee = new Gson().toJson(response.body()).toString();

                                Gson gson = new Gson();
                                transactions = gson.fromJson(reeee, GalleryListResponse.class);
                                transactionsObjects = transactions.getData();

                                if (transactionsObjects.size() > 0) {
                                    mAdapter = new MedicinePreciptionAdapter(transactionsObjects, context);
                                    mLayoutManager = new LinearLayoutManager(context);
                                    recycler_view.setLayoutManager(mLayoutManager);
                                    recycler_view.setItemAnimator(new DefaultItemAnimator());
                                    recycler_view.setAdapter(mAdapter);
                                    recycler_view.setVisibility(View.VISIBLE);
                                    recycler_view.setNestedScrollingEnabled(false);

                                } else {
                                    recycler_view.setVisibility(View.GONE);
                                }


                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {


                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void Dashboard(final Context context, final TextView total_patient, final TextView doctorappointment, final TextView patientAppointment,
                          final TextView comission, final TextView income,TextView offline) {

        String header = ApplicationConstant.INSTANCE.Headertoken;
        String date = "" + UtilMethods.INSTANCE.getCurrentDate();
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getId();

        Log.e("Doctors", "   name   " + member_id + "  date  " + date);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.Dashboard(header, member_id, date);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("Dashboard", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {
                        try {
                            if (response.body().getData() != null) {
                                Log.e("DFDfsfd", response.body().getData().getDr_income() + "");
                                total_patient.setText("" + response.body().getData().getTotal_patient());
                                doctorappointment.setText("" + response.body().getData().getDr_appoinment());
                                offline.setText(""+response.body().getData().getDr_appoinmentOffline());
                                patientAppointment.setText("" + response.body().getData().getDr_appoinment() == null ? "0" : response.body().getData().getDr_appoinment());
                                comission.setText("" + response.body().getData().getDr_comission() == null ? "\u20B9 0" : "\u20B9" + response.body().getData().getDr_comission());
                                comission.setVisibility(View.GONE);
                                income.setText(response.body().getData().getDr_income() == null ? "\u20B9 0" : "\u20B9 " + response.body().getData().getDr_income());
                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {


                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void GetAllPatientAppointment(final Context context, final Loader loader) {

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getId();

        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("Doctors", "   name   " + member_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.GetAllPatientAppointment(header, member_id);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("Doctorsres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null && response.body().getData().size() > 0) {
                                Intent intent = new Intent(context, DOctoeByIdActivity.class);
                                intent.putExtra("response", "" + new Gson().toJson(response.body()).toString());
                                intent.putExtra("type", "4");
                                context.startActivity(intent);
                            } else {

                                UtilMethods.INSTANCE.Failed(context, "No Data Found", 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void SetAvailability(final Context context, String id, String start_time, String end_time, String date, final Loader loader, final String dayevalue, String number_of_patient) {
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getId();
        String header = ApplicationConstant.INSTANCE.Headertoken;
        Log.e("Doctorsvvv", "   name   " + member_id + "   vv   " + start_time + "    vv   " + end_time + "  dd    " + date);
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.SetAvailability(header, member_id, start_time, end_time, dayevalue, number_of_patient, date);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {
                    Log.e("Doctorsvvvres", "is : " + new Gson().toJson(response.body()).toString());
                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {
                            if (response.body().getData().getStatus().equalsIgnoreCase("1")) {
                                UtilMethods.INSTANCE.GetAvailability(context, dayevalue, loader);
                            } else {
                                UtilMethods.INSTANCE.Failed(context, response.body().getData().getMessage(), 0);
                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());
                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);
                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void SetAvailabilityOffline(final Context context, String id, String start_time, String end_time, String date, final Loader loader, final String dayevalue, String number_of_patient) {
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getId();
        String header = ApplicationConstant.INSTANCE.Headertoken;
        Log.e("Doctorsvvv", "   name   " + member_id + "   vv   " + start_time + "    vv   " + end_time + "  dd    " + date);
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.SetAvailabilityOffline(header, member_id, start_time, end_time, dayevalue, number_of_patient, date);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {
                    Log.e("Doctorsvvvres", "is : " + new Gson().toJson(response.body()).toString());
                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {
                            if (response.body().getData().getStatus().equalsIgnoreCase("1")) {
                                UtilMethods.INSTANCE.GetAvailability(context, dayevalue, loader);
                            } else {
                                UtilMethods.INSTANCE.Failed(context, response.body().getData().getMessage(), 0);
                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());
                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);
                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void States(final Context context, final Loader loader) {

        String header = ApplicationConstant.INSTANCE.Headertoken;


        Log.e("specialities", "   name   ");

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.States(header);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("specialitiesres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {
                                if (response.body().getData().size() > 0) {
                                    SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString(ApplicationConstant.INSTANCE.States, new Gson().toJson(response.body()).toString());
                                    editor.commit();

                                    FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("States",
                                            "" + new Gson().toJson(response.body()).toString());
                                    GlobalBus.getBus().post(fragmentActivityMessage);


                                }
                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Districts(final Context context, String state_id, final Loader loader) {

        String header = ApplicationConstant.INSTANCE.Headertoken;


        Log.e("specialities", "   name   ");

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.Districts(header, state_id);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("specialitiesres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {
                                if (response.body().getData().size() > 0) {
                                    SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString(ApplicationConstant.INSTANCE.Districts, new Gson().toJson(response.body()).toString());
                                    editor.commit();
                                    ActivityActivityMessage fragmentActivityMessage = new ActivityActivityMessage("Districts",
                                            "" + new Gson().toJson(response.body()).toString());
                                    GlobalBus.getBus().post(fragmentActivityMessage);
                                }


                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout(final Context context) {
        setoflinestatus(context);
        UtilMethods.INSTANCE.setLoginrespose(context, "", "");
        Intent startIntent = new Intent(context, Splash.class);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(startIntent);

    }

    public boolean isValidMobile(String mobile) {

        String mobilePattern = "^(?:0091|\\\\+91|0)[6-9][0-9]{9}$";
        String mobileSecPattern = "[6-9][0-9]{9}$";

        if (mobile.matches(mobilePattern) || mobile.matches(mobileSecPattern)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isValidEmail(String email) {

        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    public void setgetKeyId(Context context, String token) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.token, token);
        editor.commit();
    }
//    public void Getonlinestatus(final Context context,final Loader loader)
//    {
//        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
//        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
//        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
//        String member_id = balanceCheckResponse.getId();
//
//        String header = ApplicationConstant.INSTANCE.Headertoken;
//        EndPointInterface git=ApiClient.getClient().create(EndPointInterface.class);
//        Call<GalleryListResponse> call =git.Getonlinestatus(header,member_id);
//        call.enqueue(new Callback<GalleryListResponse>() {
//            @Override
//            public void onResponse(Call<GalleryListResponse> call, Response<GalleryListResponse> response) {
//                Log.e("Doctorsres", "is : " + new Gson().toJson(response.body()).toString());
//
//                if (response != null) {
//                    if (loader != null) {
//                        if (loader.isShowing())
//                            loader.dismiss();
//                    }
//                    try {
//
//                        if (response.body().getData() != null && response.body().getData().size() > 0) {
//
//                            Toast.makeText(context, ""+new Gson().toJson(response.body()).toString(), Toast.LENGTH_SHORT).show();
//
//                        } else {
//
//                            UtilMethods.INSTANCE.Failed(context, "No Data Found", 0);
//
//
//                        }
//
//
//                    } catch (Exception ex) {
//                        Log.e("signupexception", ex.getMessage());
//
//                        UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);
//
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GalleryListResponse> call, Throwable t) {
//                if (loader != null) {
//                    if (loader.isShowing())
//                        try {
//                            loader.dismiss();
//                        } catch (Exception e) {
//
//
//                        }
//                }
//
//                UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);
//
//
//            }
//
//        });
//
//
//
//    }

    public void Getonlinestatus(final Context context)
    {
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getId();
      //  Toast.makeText(context, ""+member_id, Toast.LENGTH_SHORT).show();
        String headers = ApplicationConstant.INSTANCE.Headertoken;
        StringRequest request=new StringRequest(Request.Method.GET, "http://doc24x7.co.in/api/DoctorOnline/getOnlineStatus?doctor_id="+member_id, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                  // Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("x-api-key",headers);
                return header;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(request);

    }
    public void setonlinestatus(final Context context)
    {
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getId();

        String headers = ApplicationConstant.INSTANCE.Headertoken;
        StringRequest request=new StringRequest(Request.Method.POST, "http://doc24x7.co.in/api/DoctorOnline/SetOnlineStatus", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             //  Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("x-api-key",headers);
                return header;
            }


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> hm=new HashMap<>();
                hm.put("doctor_id",member_id);
                hm.put("status","1");
                return hm;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(request);

    }
    public void setoflinestatus(final Context context)
    {
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getId();

        String headers = ApplicationConstant.INSTANCE.Headertoken;
        StringRequest request=new StringRequest(Request.Method.POST, "http://doc24x7.co.in/api/DoctorOnline/SetOnlineStatus", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("x-api-key",headers);
                return header;
            }


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> hm=new HashMap<>();
                hm.put("doctor_id",member_id);
                hm.put("status","0");
                return hm;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(request);

    }
    public void UploadFile(final Context context, ArrayList<Uri> userimage, Loader loader, String path, int flag) {
        String header = ApplicationConstant.INSTANCE.Headertoken;

        ArrayList<MultipartBody.Part> fileToUpload1 = new ArrayList<>();
        if(flag==2)
        {
            for (int i = 0; i < userimage.size(); i++) {
                File file = new File(getPathFromUri(context, userimage.get(i)));
                RequestBody requestBody1 = RequestBody.create(MediaType.parse("*image/*"), file);
                fileToUpload1.add(MultipartBody.Part.createFormData("files[]", file.getName(), requestBody1));
            }
        }


//        ArrayList<MultipartBody.Part> fileToUpload1 = new ArrayList<>();
//        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*"), file);
//       // fileToUpload1 = MultipartBody.Part.createFormData("files[]", file.getName(), requestBody1);
//        fileToUpload1.add(MultipartBody.Part.createFormData("files[]", file.getName(), requestBody1));
//      //  Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.UploadFile(header, fileToUpload1);
            call.enqueue(new Callback<GalleryListResponse>() {
                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {
                    Log.e("UploadRecording", "is : " + new Gson().toJson(response.body()));
                    // Toast.makeText(context, "" + new Gson().toJson(response.body()).toString(), Toast.LENGTH_SHORT).show();

                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response != null)
                        {
                            //Toast.makeText(context, "Recording Uploaded", Toast.LENGTH_SHORT).show();
                            Log.e("UploadRecordingggg", "is : " + response.body().getFilePath());
                            reporturl.clear();
                            PatientDetail.reporturl= response.body().getFilePath();
                        }



                        try {

                        } catch (Exception ex) {
                            Log.e("useruploadexception", ex.getMessage());
                            loader.dismiss();
                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);
                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }
                    Log.e("useruploadonfali", "userupload    " + t.getMessage());
                    //  UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            loader.dismiss();
        }
    }
    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return uri.toString();
    }

}
