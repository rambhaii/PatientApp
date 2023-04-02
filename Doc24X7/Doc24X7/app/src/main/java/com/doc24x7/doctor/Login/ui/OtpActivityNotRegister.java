package com.doc24x7.doctor.Login.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.doc24x7.doctor.Dashbord.dto.Datum;
import com.doc24x7.doctor.Dashbord.dto.GalleryListResponse;
import com.doc24x7.doctor.DoctorDashboad.AllSymtomsOtpAdapter;
import com.doc24x7.doctor.Login.dto.Data;
import com.doc24x7.doctor.Notification.app.Config;
import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.ActivityActivityMessage;
import com.doc24x7.doctor.Utils.ApplicationConstant;
import com.doc24x7.doctor.Utils.GlobalBus;
import com.doc24x7.doctor.Utils.Loader;
import com.doc24x7.doctor.Utils.UtilMethods;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class OtpActivityNotRegister extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView1, viewImage;
    private TextView tvadd, Camera, Gallery;
     EditText counsult_for;
    TextView txt2;
    String number = "";
    String otp = "";
    String Status_get = "";
    public RelativeLayout otp_submit, otp_Verification;
    Loader loader;
    EditText otp_ed, Name, Email, experience, pin, address, clinic_name, consultation_fees,clinic_fees, assistant_mobile, assistant_name,registration,fees_duration;
    LinearLayout doctorRegister;
    Spinner alltype, state, district;
    String typeid = "";
    TextView chooseyourphoto;
    String profile_image = "1";
    ImageView back;
    TextView YourPhoto;
    String iduser;
    TextView txt1,addSignature;
    byte[] byteArray;
    String file_name = "";
    ImageView signature;
    private void requestPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat
                    .requestPermissions(OtpActivityNotRegister.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 5);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_register);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        requestPermission();
        Name = findViewById(R.id.Name);
        viewImage = findViewById(R.id.viewImage);
        Camera = findViewById(R.id.Camera);
        Gallery = findViewById(R.id.Gallery);
        Email = findViewById(R.id.Email);
        experience = findViewById(R.id.experience);
        pin = findViewById(R.id.pin);
        district = findViewById(R.id.district);
        consultation_fees = findViewById(R.id.consultation_fees);
        clinic_fees = findViewById(R.id.clinic_fees);
        assistant_mobile = findViewById(R.id.assistant_mobile);
        assistant_name = findViewById(R.id.assistant_name);
        addSignature = findViewById(R.id.addSignature);
        signature = findViewById(R.id.signature);
        counsult_for=findViewById(R.id.counsult_for);
        state = findViewById(R.id.state);
        address = findViewById(R.id.address);
        clinic_name = findViewById(R.id.clinic_name);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        fees_duration=findViewById(R.id.validity_period);
        YourPhoto = findViewById(R.id.YourPhoto);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        alltype = findViewById(R.id.alltype);
        registration = findViewById(R.id.registration);

        doctorRegister = findViewById(R.id.doctorRegister);
        doctorRegister.setVisibility(View.GONE);

        number = getIntent().getStringExtra("number");
        otp = getIntent().getStringExtra("otp");
        Status_get = getIntent().getStringExtra("Status_get");
        iduser = getIntent().getStringExtra("iduser");



        if(otp.trim().equalsIgnoreCase("otp_user")){



        }else {
            Toast.makeText(this, ""+otp, Toast.LENGTH_LONG).show();

        }




        otp_Verification = findViewById(R.id.otp_Verification);

        otp_submit = findViewById(R.id.otp_submit);
        otp_ed = findViewById(R.id.otp_ed);
        loader = new Loader(this, android.R.style.Theme_Translucent_NoTitleBar);

        otp_submit.setOnClickListener(this);
        otp_Verification.setOnClickListener(this);

        txt2 = findViewById(R.id.txt2);
        txt2.setText("We have sent you an SMS on +91-" + " " + number + " \n with a 4 digit verification code");
      //  otp_ed.setText("" + otp);


        if (Status_get.equalsIgnoreCase("1")) {
            doctorRegister.setVisibility(View.GONE);
            otp_submit.setVisibility(View.GONE);
            otp_Verification.setVisibility(View.VISIBLE);
        } else if (Status_get.equalsIgnoreCase("2")) {
            //  doctorRegister.setVisibility(View.VISIBLE);
            doctorRegister.setVisibility(View.VISIBLE);
            otp_submit.setVisibility(View.VISIBLE);
            otp_Verification.setVisibility(View.GONE);
            otp_ed.setVisibility(View.GONE);
            txt2.setVisibility(View.GONE);
            txt1.setText("Edit Profile");
            SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
            String str = prefs.getString(ApplicationConstant.INSTANCE.allCategoryDoctor, "");
            String recy = prefs.getString(ApplicationConstant.INSTANCE.qualification, "");
            String State = prefs.getString(ApplicationConstant.INSTANCE.States, "");
            dataParsesAlltype(str);
            dataParsesState(State);
            SetData();

        }
        Camera.setOnClickListener(this);
        Gallery.setOnClickListener(this);
        addSignature.setOnClickListener(this);
    }
    Data balanceCheckResponse;
    private void SetData() {

        try {
            SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
            balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.customer_support);
            requestOptions.error(R.drawable.customer_support);
            Glide.with(this)
                    .load(balanceCheckResponse.getProfile_pic())
                    .apply(requestOptions)
                    .into(viewImage);
            SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(this);
            String previouslyEncodedImage = shre.getString("image_data", "");

            if( !previouslyEncodedImage.equalsIgnoreCase("") ){
                byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                signature.setImageBitmap(bitmap);
            }
           viewImage.setBackground(null);
            Name.setText("" + balanceCheckResponse.getName());
            Email.setText("" + balanceCheckResponse.getEmail());
            experience.setText("" + balanceCheckResponse.getExperience());
            pin.setText("" + balanceCheckResponse.getPin());
            assistant_name.setText("" + balanceCheckResponse.getAssistant_name());
            clinic_name.setText("" + balanceCheckResponse.getClinic_name());
            assistant_mobile.setText("" + balanceCheckResponse.getAssistant_mobile());
            consultation_fees.setText("" + balanceCheckResponse.getDoctor_fees());
            address.setText("" + balanceCheckResponse.getAddress());
            registration.setText("" + balanceCheckResponse.getRegistration_no());
            //counsult_for.setText(""+balanceCheckResponse.getConsult_for());
            clinic_fees.setText(""+balanceCheckResponse.getClinic_fees());
            fees_duration.setText(""+balanceCheckResponse.getFees_duration());
            //Toast.makeText(this, ""+balanceCheckResponse.getClinic_fees(), Toast.LENGTH_SHORT).show();
            alltype.setSelection(((ArrayAdapter<String>) alltype.getAdapter()).getPosition(balanceCheckResponse.getSpecialization()));
            state.setSelection(((ArrayAdapter<String>) state.getAdapter()).getPosition(balanceCheckResponse.getState_name()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    GalleryListResponse sliderImage = new GalleryListResponse();
    ArrayList<Datum> operator = new ArrayList<>();
    AllSymtomsOtpAdapter mAdapter;
    GalleryListResponse transactions = new GalleryListResponse();
    GalleryListResponse transactions2 = new GalleryListResponse();
    GalleryListResponse transactions3 = new GalleryListResponse();
    ArrayList<Datum> transactionsspinner = new ArrayList<>();
    ArrayList<Datum> transactionsspinner2 = new ArrayList<>();
    ArrayList<Datum> transactionsspinner3 = new ArrayList<>();
    ArrayList<String> bankList = new ArrayList<String>();
    ArrayList<String> StateList = new ArrayList<String>();
    ArrayList<String> DistrictList = new ArrayList<String>();
    ArrayList<String> DistrictId = new ArrayList<String>();
    ArrayList<String> StateID = new ArrayList<String>();
    ArrayList<String> symptonlist = new ArrayList<String>();

    String stateID;
    String districtID;

    public void dataParsesAlltype(String response) {
        Gson gson = new Gson();
        transactions = gson.fromJson(response, GalleryListResponse.class);
        transactionsspinner = transactions.getData();

        if (transactionsspinner.size() > 0) {

            if (transactionsspinner != null && transactionsspinner.size() > 0) {
                for (int i = 0; i < transactionsspinner.size(); i++) {
                    bankList.add(transactionsspinner.get(i).getName());
                }
            }

            alltype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // TODO Auto-generated method stub
                        typeid = transactionsspinner.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            });
            ArrayAdapter<String> countryAdapter;
            countryAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.tv_spname, bankList);
            alltype.setAdapter(countryAdapter);

        } else {

        }

    }

    public void dataParsesState(String response) {

        StateList.add("Select State");
        StateID.add("0");
        Gson gson = new Gson();
        transactions2 = gson.fromJson(response, GalleryListResponse.class);
        transactionsspinner2 = transactions2.getData();

        if (transactionsspinner2.size() > 0) {

            if (transactionsspinner2 != null && transactionsspinner2.size() > 0) {

                for (int i = 0; i < transactionsspinner2.size(); i++) {

                    StateList.add(transactionsspinner2.get(i).getName());
                    StateID.add(transactionsspinner2.get(i).getId());

                }
            }

            state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // TODO Auto-generated method stub
                    Log.e("spinner", "  position   " + position + "  ,  id  " + id);
                    if (parent.getItemAtPosition(position).toString().equals("Select State")) {
                    } else
                    {
                        stateID = StateID.get(position);
                        UtilMethods.INSTANCE.Districts(OtpActivityNotRegister.this, StateID.get(position) + "", loader);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            });
            ArrayAdapter<String> countryAdapter;
            countryAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.tv_spname, StateList);
            state.setAdapter(countryAdapter);

        } else {

        }

    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    public void dataParsesDistrct(String response) {
        DistrictList.clear();
        DistrictList.add("Select District");
        DistrictId.add("0");
        Gson gson = new Gson();
        transactions3 = gson.fromJson(response, GalleryListResponse.class);
        transactionsspinner3 = transactions3.getData();

        if (transactionsspinner3.size() > 0) {

            if (transactionsspinner3 != null && transactionsspinner3.size() > 0) {

                for (int i = 0; i < transactionsspinner3.size(); i++) {

                    DistrictList.add(transactionsspinner3.get(i).getName());
                    DistrictId.add(transactionsspinner3.get(i).getId());

                }
            }

            district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // TODO Auto-generated method stub
                    Log.e("spinner", "  position   " + position + "  ,  id  " + id);

                    if (parent.getItemAtPosition(position).toString().equals("Select District")) {

                    } else {
                        districtID = DistrictId.get(position);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            });
            ArrayAdapter<String> countryAdapter;
            countryAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.tv_spname, DistrictList);
            district.setAdapter(countryAdapter);

            district.setSelection(((ArrayAdapter<String>) district.getAdapter()).getPosition(balanceCheckResponse.getDistrict_name()));

        } else {

        }

    }

    @Override
    public void onClick(View view) {

        String phone="+91"+ assistant_mobile.getText().toString().trim();
        if (view == back) {
            onBackPressed();
        } if (view == addSignature) {
           showSignatureBox();
        }

        if (view == Gallery) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);
        }
        if (view == Camera) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            startActivityForResult(intent, 1);
        }


        if (view == otp_submit) {
            int i = 0;
            if (Name.getText().toString().trim().isEmpty()) {
                Name.setError("Name is required");
                i = 1;
            } else if (registration.getText().toString().trim().isEmpty()) {
                registration.setError("Registration No. is required");
                i = 1;
            } else if (pin.getText().toString().trim().isEmpty()) {
                pin.setError("Pin is required");
                i = 1;
            } else if (Email.getText().toString().trim().isEmpty()) {
                Email.setError("Email is required");
                i = 1;
            } else if (clinic_name.getText().toString().trim().isEmpty()) {
                clinic_name.setError("Clinic name is required");
                i = 1;
            }
            else if (fees_duration.getText().toString().trim().isEmpty())
            {
                fees_duration.setError("Validity period is required");
                i = 1;
            }
            else if (clinic_fees.getText().toString().trim().isEmpty()) {
                clinic_fees.setError("Clinic name is required");
                i = 1;
            } else if (consultation_fees.getText().toString().trim().isEmpty()) {
                consultation_fees.setError("Clinic name is required");
                i = 1;
//            } else if (address.getText().toString().trim().isEmpty()) {
//                address.setError("Clinic address is required");
//                i = 1;
//            } else if (!address.getText().toString().trim().isEmpty()) {
//                LatLng latLng = getLocationFromAddress(this, address.getText().toString());
//                if (latLng == null) {
//                    address.setError("Enter valid clinic address");
//                    i = 1;
//                } else {
//
//                }
            } else if (state.getSelectedItem().equals("Select State")) {
                UtilMethods.INSTANCE.Failed(this, "Please select State", 0);
                i = 1;
            }
            else if (district.getSelectedItem().equals("Select District")) {
                UtilMethods.INSTANCE.Failed(this, "Please select District", 0);
                i = 1;
            }
            else if (!Pattern.matches("^(\\+91[\\-\\s]?)?[0]?(91)?[6789]\\d{9}$", phone))
            {
                assistant_mobile.setError("Enter a valid Phone Number");
                i = 1;
            }


            else if (typeid.contains("Select"))
            {
                UtilMethods.INSTANCE.Failed(this, "Please select Speciatily", 0);
                i = 1;
            }
            if (i == 1) {
                return;
            }
            if (UtilMethods.INSTANCE.isNetworkAvialable(OtpActivityNotRegister.this)) {

                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                LatLng latLng = getLocationFromAddress(this, address.getText().toString());
                Log.e("Loaction", latLng + "");
                SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
                UtilMethods.INSTANCE.DoctorUpdateProfile(OtpActivityNotRegister.this,
                        number,
                        Name.getText().toString().trim(),
                        Email.getText().toString().trim(),
                        experience.getText().toString().trim(),
                        pin.getText().toString().trim(),
                        districtID,
                        stateID,
                        address.getText().toString().trim(),
                        clinic_name.getText().toString().trim(),
                        profile_image,
                        typeid,
                        latLng,
                        assistant_mobile.getText().toString(),
                        assistant_name.getText().toString(),
                        consultation_fees.getText().toString(),
                        registration.getText().toString(),
                        counsult_for.getText().toString(),
                        pref.getString("regId", ""),
                        iduser,
                        loader, OtpActivityNotRegister.this,
                        clinic_fees.getText().toString(),
                        fees_duration.getText().toString()
                );


            } else {
                UtilMethods.INSTANCE.NetworkError(OtpActivityNotRegister.this, getResources().getString(R.string.network_error_title),
                        getResources().getString(R.string.network_error_message));
            }

        }


        if (view == otp_Verification) {
            if (otp_ed.getText().toString().isEmpty()) {
                otp_ed.setError("Please enter your otp");
                otp_ed.requestFocus();
            } else {
                if (otp.equalsIgnoreCase(otp_ed.getText().toString().trim())) {
//                doctorRegister.setVisibility(View.VISIBLE);
//                otp_submit.setVisibility(View.VISIBLE);
//                otp_Verification.setVisibility(View.GONE);
//                otp_ed.setVisibility(View.GONE);
//                txt2.setVisibility(View.GONE);
//                txt1.setText("Please complete your profile");
                    if (UtilMethods.INSTANCE.isNetworkAvialable(OtpActivityNotRegister.this)) {

                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);


                        UtilMethods.INSTANCE.DoctorOtpVerify(OtpActivityNotRegister.this, otp_ed.getText().toString().trim(), number, loader, OtpActivityNotRegister.this);

                    } else {
                        UtilMethods.INSTANCE.NetworkError(OtpActivityNotRegister.this, getResources().getString(R.string.network_error_title),
                                getResources().getString(R.string.network_error_message));
                    }


                } else {


                    Toast.makeText(this, "Otp Does not Match", Toast.LENGTH_SHORT).show();
                }

            }
        }

    }

    private void showSignatureBox() {
         LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.signaturelayout, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);
        TextView clear=deleteDialogView.findViewById(R.id.tv_clear);
        TextView tv_submitsignature=deleteDialogView.findViewById(R.id.tv_submitsignature);
        final com.github.gcacace.signaturepad.views.SignaturePad signature_pad=deleteDialogView.findViewById(R.id.signature_pad);
        tv_submitsignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signature.setImageBitmap(signature_pad.getSignatureBitmap());
                Bitmap realImage = signature_pad.getSignatureBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                realImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();

                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(OtpActivityNotRegister.this);
                SharedPreferences.Editor edit=shre.edit();
                edit.putString("image_data",encodedImage);
                edit.commit();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 5:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
                    viewImage.setBackground(null);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                    byteArray = stream.toByteArray();
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

                    viewImage.setBackground(null);
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

                    viewImage.setBackground(null);
                }
            }
        }
    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        return rotatedImg;
    }

    @Subscribe
    public void onActivityActivityMessage(ActivityActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("Districts")) {
            dataParsesDistrct(activityFragmentMessage.getFrom());
        }
    }

    @Override
    public void onDestroy() {
        // Unregister the registered event.
        GlobalBus.getBus().unregister(this);

        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

}