package com.doc24x7.doctor.Chat.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.doc24x7.doctor.Dashbord.dto.Datum;
import com.doc24x7.doctor.Dashbord.dto.GalleryListResponse;
import com.doc24x7.doctor.Login.dto.Data;
import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.ApplicationConstant;
import com.doc24x7.doctor.Utils.DETAIL;
import com.doc24x7.doctor.Utils.FragmentActivityMessage;
import com.doc24x7.doctor.Utils.GlobalBus;
import com.doc24x7.doctor.Utils.Loader;
import com.doc24x7.doctor.Utils.MeicineModel;
import com.doc24x7.doctor.Utils.UtilMethods;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;

public class UploadPrescriptionActivity extends AppCompatActivity implements View.OnClickListener {
   // TextView addSignature;
    CircleImageView profileimage;
    EditText desc,qty;
    DETAIL detail;
    String profileimageStr = "";
    RecyclerView recycler_view, GetMedicine,medicinename;
    EditText follow_us,etcomplain,etdaignos,etinvest,etremark,etaddmore;
    GalleryListResponse sliderImage;
    GalleryListResponse MedicineNameObject;
    ArrayList<Datum> sliderLists = new ArrayList<>();
    ArrayList<Datum> sliderListsmedicin = new ArrayList<>();
    ArrayList<String> sliderListsmed = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    ChatlistAdapter mAdapter;
    MedicineAdapter medicineAdapter;
    PreciptionAdapter mAdapterPreciption;
    ImageView  signature;
    AutoCompleteTextView msgedittext;
    ImageView add, viewImage;
    String appointment_id = "";
    String Symtom = "";
    String mobile = "";
    String gender="";
    String userid = "", patientName = "dfsdfsd";
    String profile_image = "1";
    Button btncomplain,btnremark,btndaignos,btnrx,btnaddmore,btninvest;
    String file_name = "";
    String page="";
    String gen="";
    String description="";
    String PatientName="";
    String type="";
    String medicinelist;
    String PatientMobile="";
    LinearLayout linerrx,lineardaignos,linearinvestigation,linearremark,linearcomplain,linearaddmore;
    Loader loader;
    TextView iamgeseletc,name,sendbtn;
    TextView  tv_date, tv_synptoms, tv_patientName, days,tv_mobile;
    TextView contentnumber,btn_preview;
    ArrayList medicine_name = new ArrayList();
    MeicineModel medicimeDetail=new MeicineModel();
    ArrayList<DETAIL> arrayListDetail = new ArrayList<>(); ;
    Spinner alltype, how, dayssp;
    String complain="";

    String[] frequency = {"In morning","Before sleep","Once in day", "Twice a day", "Thrice in day", "as needed", "every hour", "every four hours"};
    String[] whenToUse = {"take before a meal", "take at bedtime.", "take after a meal"};
    String[] howToUse = {"by mouth", "right ear", "left ear", "each ear", "right eye", "left eye", "subcutaneously", "suppository"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        name = findViewById(R.id.name);
        linerrx=findViewById(R.id.linerrx);
        linearcomplain=findViewById(R.id.linearcomplain);
        linearinvestigation=findViewById(R.id.linearinvestigation);
        lineardaignos=findViewById(R.id.lineardaignos);
        linearremark=findViewById(R.id.linearremark);
        signature = findViewById(R.id.signature);
        btncomplain=findViewById(R.id.btncomplain);
        btnaddmore=findViewById(R.id.btnaddmore);
        tv_mobile=findViewById(R.id.tv_mobile);
        qty=findViewById(R.id.qty);
        detail = new DETAIL();
        etaddmore=findViewById(R.id.etaddmore);
        linearaddmore=findViewById(R.id.linearaddmore);
        btndaignos=findViewById(R.id.btndaignos);
        btnrx=findViewById(R.id.btnrx);
        btnremark=findViewById(R.id.btnremark);
        btninvest=findViewById(R.id.btninvest);
        btn_preview=findViewById(R.id.btn_preview);
        etcomplain=findViewById(R.id.etcomplain);
        etdaignos=findViewById(R.id.etdaignos);
        etinvest=findViewById(R.id.etinvestigation);
        etremark=findViewById(R.id.etremark);
        type=getIntent().getStringExtra("type");
      contentnumber = findViewById(R.id.contentnumber);
        SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        name.setText("Dr. " + balanceCheckResponse.getName());
        contentnumber.setText("" + balanceCheckResponse.getMobile());
        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
        Gson gson = new Gson();
      //  testreport = findViewById(R.id.test);
        follow_us = findViewById(R.id.follow_us);
        follow_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fallowusdatepicker();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "-" + month + "-" + year;
                follow_us.setText(date);
            }


        };
        add = findViewById(R.id.add);
        desc = findViewById(R.id.desc);
        medicinename = findViewById(R.id.medicinename);
        tv_synptoms = findViewById(R.id.tv_synptoms);
        days = findViewById(R.id.days);
        add.setOnClickListener(this);

//        iamgeseletc.setOnClickListener(this);
        loader = new Loader(this, android.R.style.Theme_Translucent_NoTitleBar);
        appointment_id = getIntent().getStringExtra("appointment_id");
        Symtom = getIntent().getStringExtra("Symtom");
        userid = getIntent().getStringExtra("userid");
        patientName = getIntent().getStringExtra("name");
        mobile = getIntent().getStringExtra("mobile");
        medicinelist=getIntent().getStringExtra("medicinedetails");
        String age=getIntent().getStringExtra("age");
        try {
            JSONObject obj=new JSONObject(age);
            page=obj.getString("PatientAge");
            gen=obj.getString("Gender");
            PatientName=obj.getString("PatientName");
            PatientMobile=obj.getString("PatientMobile");
            tv_synptoms.setText(obj.getString("PatientSymption")==null?"":obj.getString("PatientSymption"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        profileimageStr = getIntent().getStringExtra("profileimageStr") == null ? "" : getIntent().getStringExtra("profileimageStr");
        sendbtn = findViewById(R.id.sendbtn);
        sendbtn = findViewById(R.id.sendbtn);
        msgedittext = findViewById(R.id.msgedittext);
        msgedittext.setThreshold(1);
        ArrayAdapter<String> MedicineStradapter = null;
        try {
            String str = prefs.getString("Medicine", "");
            Log.e("MedicineStr", str);
            MedicineNameObject = gson.fromJson(str, GalleryListResponse.class);
            Log.e("MedicineStr", new Gson().toJson(MedicineNameObject));
            Log.e("MedicineStr", MedicineNameObject.getData().get(0).getMedicine_name());
            for (int i = 0; i < MedicineNameObject.getData().size(); i++)
            {
                sliderListsmed.add(MedicineNameObject.getData().get(i).getMedicine_name());
            }
            MedicineStradapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item, sliderListsmed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        msgedittext.setAdapter(MedicineStradapter);//setting the adapter data into the AutoCompleteTextView

        msgedittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("beforeTextChanged", String.valueOf(s));
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("onTextChanged", String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("afterTextChanged", String.valueOf(s));
            }
        });
        tv_date = findViewById(R.id.tv_date);
        viewImage = findViewById(R.id.viewImage);
        tv_patientName = findViewById(R.id.tv_patientName);
        tv_date.setText("Today: " + UtilMethods.INSTANCE.getCurrentDate());
        profileimage = findViewById(R.id.iv_patient);
       // recycler_view = findViewById(R.id.chatlist);
        GetMedicine = findViewById(R.id.GetMedicine);
        alltype = findViewById(R.id.alltype);
        ArrayAdapter aa = new ArrayAdapter(this, R.layout.simple_spinner_item, frequency);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        alltype.setAdapter(aa);
        dayssp = findViewById(R.id.dayssp);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.simple_spinner_item, whenToUse);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        dayssp.setAdapter(arrayAdapter);
        how = findViewById(R.id.how);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, R.layout.simple_spinner_item, howToUse);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        how.setAdapter(arrayAdapter2);
        tv_mobile.setText(PatientMobile.equals("")?("" + mobile+" / Gender : "+gen):PatientMobile+ " / Gender : "+gen);
        tv_patientName.setText(   PatientName.equals("")?("" + patientName+ " / Age : "+page):PatientName + " / Age : "+page);
        sendbtn.setOnClickListener(this);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.customer_support);
        requestOptions.error(R.drawable.customer_support);
        Glide.with(this)
                .load(profileimageStr)
                .apply(requestOptions)
                .into(profileimage);

   if(type.equals("2")) {
       medicimeDetail = new Gson().fromJson(getIntent().getStringExtra("medicimeDetail") == null ? "" : getIntent().getStringExtra("medicimeDetail"), MeicineModel.class);
       description = getIntent().getStringExtra("description");
       Log.e("description: ", description);
       etcomplain.setText(description.split(":")[1].replace("\nDaignos", ""));
       etdaignos.setText(description.split(":")[2].replace("\nInvestigation", ""));
       etinvest.setText(description.split(":")[3].replace("\nRemark", ""));
       etremark.setText(description.split(":")[4].replace("\nMore Details", ""));
       etaddmore.setText(description.split(":")[5]);
       arrayListDetail = medicimeDetail.getMedicineMode();
   }
        medicineAdapter = new MedicineAdapter(arrayListDetail, this);
        mLayoutManager = new GridLayoutManager(this, 1);
        medicinename.setLayoutManager(mLayoutManager);
        medicinename.setItemAnimator(new DefaultItemAnimator());
        medicineAdapter.notifyDataSetChanged();
        medicinename.setAdapter(medicineAdapter);


    }

    private void fallowusdatepicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(UploadPrescriptionActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("GetMedicine")) {
            dataParse(activityFragmentMessage.getFrom());
        } else if (activityFragmentMessage.getMessage().equalsIgnoreCase("GetPreciption")) {
            dataParsePreciption(activityFragmentMessage.getFrom());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

    public void dataParse(String response) {
        Gson gson = new Gson();
        sliderImage = gson.fromJson(response, GalleryListResponse.class);
        sliderListsmedicin = sliderImage.getData();
        if (sliderListsmedicin.size() > 0) {
            mAdapter = new ChatlistAdapter(sliderListsmedicin, this);
            mLayoutManager = new GridLayoutManager(this, 1);
            GetMedicine.setLayoutManager(mLayoutManager);
            GetMedicine.setItemAnimator(new DefaultItemAnimator());
            GetMedicine.setAdapter(mAdapter);
            GetMedicine.setVisibility(View.GONE);
        } else {
            GetMedicine.setVisibility(View.GONE);
        } }
    public void dataParsePreciption(String response) {

        Gson gson = new Gson();
        sliderImage = gson.fromJson(response, GalleryListResponse.class);
        sliderLists = sliderImage.getData();

        if (sliderLists.size() > 0) {
            mAdapterPreciption = new PreciptionAdapter(sliderLists, this);
            mLayoutManager = new GridLayoutManager(this, 1);
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapterPreciption);
            recycler_view.setVisibility(View.VISIBLE);
        } else {
            recycler_view.setVisibility(View.GONE);
        }

    }
    String medicine_name_str = "";
    int number = 1;
    @Override
    public void onClick(View view) {
        if (view == add) {
            if (msgedittext.getText().toString().isEmpty()) {
                msgedittext.setError("Please fill medicine name");
                return;
            }

            detail.setMedicimeDays(days.getText().toString());
            detail.setMedicimeFreQuency(alltype.getSelectedItem().toString());
            detail.setWhentouse(dayssp.getSelectedItem().toString());
            detail.setHowtouse(how.getSelectedItem().toString());
            Log.d(" hbahbvhabva",how.getSelectedItem().toString()+"\n"+dayssp.getSelectedItem().toString()+"\n"
            +alltype.getSelectedItem().toString()+"\n"+days.getText().toString()+"\n"+msgedittext.getText().toString()
            +qty.getText().toString()
            );
            detail.setQuantity(qty.getText().toString());
            detail.setMedicineName(msgedittext.getText().toString());
            arrayListDetail.add(detail);
            medicineAdapter = new MedicineAdapter(arrayListDetail, this);
            mLayoutManager = new GridLayoutManager(this, 1);
            medicinename.setLayoutManager(mLayoutManager);
            medicinename.setItemAnimator(new DefaultItemAnimator());
            medicineAdapter.notifyDataSetChanged();
            medicinename.setAdapter(medicineAdapter);
            msgedittext.setText("");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        viewImage.setVisibility(View.VISIBLE);
        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    ExifInterface exif = new ExifInterface(f.getAbsolutePath());
                    int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    switch (rotation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            bitmap = rotateImage(bitmap, 90);
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            bitmap = rotateImage(bitmap, 180);
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            bitmap = rotateImage(bitmap, 270);
                        default:
                            bitmap = rotateImage(bitmap, 0);
                    }
                    viewImage.setImageBitmap(bitmap);
                    file_name = System.currentTimeMillis() + ".png";
                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        profile_image = file.toString();
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                try {
                    Uri selectedImage = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();
                    profile_image = picturePath;
                    Bitmap thumbnail = BitmapFactory.decodeFile(picturePath);
                    Log.e("xc", picturePath + "");
                   viewImage.setImageBitmap(thumbnail);
                    file_name = System.currentTimeMillis() + ".png";
                } catch (Exception e) {
                    e.printStackTrace();
                    BitmapFactory.Options options;
                    options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    Uri selectedImage = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();
                    Bitmap bitmap = BitmapFactory.decodeFile(picturePath, options);
                    Log.e("xc", picturePath + "");
                    viewImage.setImageBitmap(bitmap);
                    file_name = System.currentTimeMillis() + ".png";
                }
            }
        }
    }

    private void showSignatureBox() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.signaturelayout, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);
        TextView clear = deleteDialogView.findViewById(R.id.tv_clear);
        TextView tv_submitsignature = deleteDialogView.findViewById(R.id.tv_submitsignature);
        final com.github.gcacace.signaturepad.views.SignaturePad signature_pad = deleteDialogView.findViewById(R.id.signature_pad);
        tv_submitsignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signature.setImageBitmap(signature_pad.getSignatureBitmap());
                deleteDialog.dismiss();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signature_pad.clear();
            }
        });
        deleteDialog.show();
    }

    public void ItemClick(String samemedicine) {
        medicine_name.add(samemedicine);
    }
    public void btncomplain(View v)
    {
        btncomplain.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception2));
        btncomplain.setTextColor(Color.WHITE);
        linearcomplain.setVisibility(View.VISIBLE);
        linearremark.setVisibility(View.GONE);
        linerrx.setVisibility(View.GONE);
        lineardaignos.setVisibility(View.GONE);
        linearinvestigation.setVisibility(View.GONE);

        linearaddmore.setVisibility(View.GONE);
        etcomplain.requestFocus();
          btnrx.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
          btnrx.setTextColor(getResources().getColor(R.color.colorPrimary));
        btndaignos.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btndaignos.setTextColor(getResources().getColor(R.color.colorPrimary));
        btninvest.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btninvest.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnaddmore.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btnaddmore.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnremark.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btnremark.setTextColor(getResources().getColor(R.color.colorPrimary));

    }
    public void btnrx(View v)
    {

        btnrx.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception2));
        btnrx.setTextColor(Color.WHITE);
        linearcomplain.setVisibility(View.GONE);
        linearremark.setVisibility(View.GONE);
        linerrx.setVisibility(View.VISIBLE);
        lineardaignos.setVisibility(View.GONE);
        linearinvestigation.setVisibility(View.GONE);

         linearaddmore.setVisibility(View.GONE);
       //changing color of button
        btncomplain.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btncomplain.setTextColor(getResources().getColor(R.color.colorPrimary));
        btndaignos.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btndaignos.setTextColor(getResources().getColor(R.color.colorPrimary));
        btninvest.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btninvest.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnaddmore.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btnaddmore.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnremark.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btnremark.setTextColor(getResources().getColor(R.color.colorPrimary));

    }

    public  void btndaignos(View v)
    {
        btndaignos.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception2));
        btndaignos.setTextColor(Color.WHITE);
        linearcomplain.setVisibility(View.GONE);
        linearremark.setVisibility(View.GONE);
        linerrx.setVisibility(View.GONE);
        lineardaignos.setVisibility(View.VISIBLE);
        linearinvestigation.setVisibility(View.GONE);
        linearaddmore.setVisibility(View.GONE);
        etdaignos.requestFocus();
        //changing color of button
        btncomplain.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btncomplain.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnrx.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btnrx.setTextColor(getResources().getColor(R.color.colorPrimary));
        btninvest.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btninvest.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnaddmore.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btnaddmore.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnremark.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btnremark.setTextColor(getResources().getColor(R.color.colorPrimary));
    }
    public  void btninvest(View v)
    {
        btninvest.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception2));
        btninvest.setTextColor(Color.WHITE);
        linearcomplain.setVisibility(View.GONE);
        linearremark.setVisibility(View.GONE);
        linerrx.setVisibility(View.GONE);
        lineardaignos.setVisibility(View.GONE);
        linearinvestigation.setVisibility(View.VISIBLE);

        linearaddmore.setVisibility(View.GONE);
        etinvest.requestFocus();
        //changing color of button
        btncomplain.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btncomplain.setTextColor(getResources().getColor(R.color.colorPrimary));
        btndaignos.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btndaignos.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnrx.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btnrx.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnaddmore.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btnaddmore.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnremark.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btnremark.setTextColor(getResources().getColor(R.color.colorPrimary));
    }
    public  void btnremark(View v)
    {

        btnremark.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception2));
        btnremark.setTextColor(Color.WHITE);
        linearcomplain.setVisibility(View.GONE);
        linearremark.setVisibility(View.VISIBLE);
        linerrx.setVisibility(View.GONE);
        lineardaignos.setVisibility(View.GONE);
        linearinvestigation.setVisibility(View.GONE);
        linearaddmore.setVisibility(View.GONE);
         etremark.requestFocus();
        //changing color of button
        btncomplain.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btncomplain.setTextColor(getResources().getColor(R.color.colorPrimary));
        btndaignos.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btndaignos.setTextColor(getResources().getColor(R.color.colorPrimary));
        btninvest.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btninvest.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnaddmore.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btnaddmore.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnrx.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btnrx.setTextColor(getResources().getColor(R.color.colorPrimary));
    }
    public void btnaddmore(View view)
    {
        btnaddmore.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception2));
        btnaddmore.setTextColor(Color.WHITE);
        linearcomplain.setVisibility(View.GONE);
        linearremark.setVisibility(View.GONE);
        linerrx.setVisibility(View.GONE);
        lineardaignos.setVisibility(View.GONE);
        linearinvestigation.setVisibility(View.GONE);
        linearaddmore.setVisibility(View.VISIBLE);
        etaddmore.requestFocus();
        //changing color of button
        btncomplain.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btncomplain.setTextColor(getResources().getColor(R.color.colorPrimary));
        btndaignos.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btndaignos.setTextColor(getResources().getColor(R.color.colorPrimary));
        btninvest.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btninvest.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnrx.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btnrx.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnremark.setBackground(this.getResources().getDrawable(R.drawable.btnbackpreception));
        btnremark.setTextColor(getResources().getColor(R.color.colorPrimary));
    }
    public  void btn_preview(View view)
    {

            medicimeDetail.setMedicineMode(arrayListDetail);
            Intent i = new Intent(UploadPrescriptionActivity.this, PreviewPrescription.class);
            // i.putExtra("description","Description :  "+desc.getText().toString());
            i.putExtra("complain", "<b>Complain : </b>" + etcomplain.getText().toString());
            i.putExtra("daignos", "<b>Daignos : </b>" + etdaignos.getText().toString());
            i.putExtra("invest", "<b>Investigation :</b> " + etinvest.getText().toString());
            i.putExtra("remark", "<b>Remark :</b> " + etremark.getText().toString());
            i.putExtra("more", "<b>More Details :</b> " + etaddmore.getText().toString());
            i.putExtra("patientname", "<b>Patient Name :</b> " + tv_patientName.getText().toString());
            i.putExtra("mobile", "<b>Mobile Number :</b> " + tv_mobile.getText().toString());
            i.putExtra("Desc", "<b>Mobile Number :</b> " + tv_synptoms.getText().toString());
            i.putExtra("medicimeDetail", new Gson().toJson(medicimeDetail));
            i.putExtra("appointment_id", appointment_id);
            i.putExtra("userid", userid);
//            i.putExtra("days",days.getText().toString());
//            i.putExtra("alltype",alltype.getSelectedItem().toString());
//            i.putExtra("dayssp",dayssp.getSelectedItem().toString());
//            i.putExtra("how",how.getSelectedItem().toString());
//            i.putExtra("msgedit",msgedittext.getText().toString());
            startActivity(i);


    }
}