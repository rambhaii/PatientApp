package com.doc24x7.doctor.Dashbord.ui;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.doc24x7.doctor.Utils.Loader;
import com.doc24x7.doctor.Utils.MeicineModel;
import com.doc24x7.doctor.Utils.UtilMethods;
import com.google.gson.Gson;
import com.doc24x7.doctor.Chat.ui.ManualPrescription;
import com.doc24x7.doctor.Chat.ui.UploadPrescriptionActivity;
import com.doc24x7.doctor.Login.dto.Data;
import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.ApplicationConstant;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;

public class chooseprescription extends AppCompatActivity {
  Button digitalPricription,manualprescription,imageprescription ,uploadbtn;
    TextView contentnumber,name;
    AlertDialog alertDialog;
    byte[] byteArray;
    Bitmap bitmap;
    String profile_image = "1";
    String file_name = "";
   String  type="";
   String description;
    MeicineModel medicimeDetail;

    ImageView opencamera, opengallery,view_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseprescription);
        SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        name = findViewById(R.id.name);
        uploadbtn=findViewById(R.id.uploadbtn);
        view_image=findViewById(R.id.view_image);
        imageprescription=findViewById(R.id.imageprescription);
        contentnumber = findViewById(R.id.contentnumber);
        name.setText("Dr. " + balanceCheckResponse.getName());
        contentnumber.setText("" + balanceCheckResponse.getMobile());
        digitalPricription=findViewById(R.id.digitalprescription);
        manualprescription=findViewById(R.id.manualprescription);
              type=getIntent().getStringExtra("type");
              if(type.equals("2"))
              {
                  medicimeDetail=new Gson().fromJson(getIntent().getStringExtra("medicimeDetail"),MeicineModel.class);
                  description=getIntent().getStringExtra("description");
              }
              final String name=getIntent().getStringExtra("name");
        final String appointment_id=getIntent().getStringExtra("appointment_id");
      //  Toast.makeText(this, ""+appointment_id, Toast.LENGTH_SHORT).show();
        final String mobile=getIntent().getStringExtra("mobile");
        final String userid=getIntent().getStringExtra("userid");
        final String age=getIntent().getStringExtra("age");
        final String profileimageStr=getIntent().getStringExtra("profileimageStr");

        digitalPricription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(chooseprescription.this, UploadPrescriptionActivity.class);
                i.putExtra("name",name);
                i.putExtra("appointment_id",appointment_id);
                i.putExtra("mobile",mobile);
                i.putExtra("userid",userid);
                i.putExtra("age",age);
                i.putExtra("type",type);
                i.putExtra("medicimeDetail", new Gson().toJson(medicimeDetail));
                i.putExtra("description",description);
                i.putExtra("profileimageStr",profileimageStr);
                startActivity(i);
            }
        });
        manualprescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(chooseprescription.this, ManualPrescription.class);
                i.putExtra("name",name);
                i.putExtra("appointment_id",appointment_id);
                i.putExtra("mobile",mobile);
                i.putExtra("userid",userid);
                i.putExtra("age",age);
                i.putExtra("profileimageStr",profileimageStr);
                startActivity(i);

            }
        });
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loader loader = new Loader(chooseprescription.this, android.R.style.Theme_Translucent_NoTitleBar);
                if (UtilMethods.INSTANCE.isNetworkAvialable(chooseprescription.this)) {

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    UtilMethods.INSTANCE.SavePreciption(chooseprescription.this, "" + userid,"",
                            "" + appointment_id , loader, chooseprescription.this, null, null,profile_image);
                } else {
                    UtilMethods.INSTANCE.NetworkError(chooseprescription.this, getResources().getString(R.string.network_error_title),
                            getResources().getString(R.string.network_error_message));
                }
            }
        });
        imageprescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(chooseprescription.this)
                        .withPermissions(
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE

                        ).withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {

                        AlertDialog.Builder dailog = new AlertDialog.Builder(chooseprescription.this);
                        LayoutInflater inflator = chooseprescription.this.getLayoutInflater();
                        View dailogview = inflator.inflate(R.layout.custom_img_layout, null);
                        dailog.setView(dailogview);
                        alertDialog = dailog.create();
                        alertDialog.show();

                        opencamera = alertDialog.findViewById(R.id.camera);
                        opengallery = alertDialog.findViewById(R.id.gallery);
                        opencamera.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                                startActivityForResult(intent, 1);
                            }
                        });
                        opengallery.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent pickimage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickimage, 2);
                            }
                        });


                    }
                    @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();


            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
             alertDialog.dismiss();
            if (requestCode == 1) {
                uploadbtn.setVisibility(View.VISIBLE);
                view_image.setVisibility(View.VISIBLE);
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
                    view_image.setImageBitmap(bitmap);
                    file_name = System.currentTimeMillis() + ".JPEG";
                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Android";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".JPEG");
                    try {
                        profile_image = file.toString();
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, outFile);
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
            }
            else if (requestCode == 2) {
                try {
                    uploadbtn.setVisibility(View.VISIBLE);
                    view_image.setVisibility(View.VISIBLE);
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
                    view_image.setImageBitmap(thumbnail);

                    view_image.setBackground(null);
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
                    view_image.setImageBitmap(bitmap);

                    view_image.setBackground(null);
                }
            }
        }
    }

}