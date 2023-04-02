package com.doc24x7.doctor.Chat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.gcacace.signaturepad.views.SignaturePad;

import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Utils.Loader;
import com.doc24x7.doctor.Utils.UtilMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;

public class ManualPrescription extends AppCompatActivity {
  TextView tvprescription;
  ImageView imgsetpresc;
  SignaturePad mSignaturePad;
  Button btnClear,btnSave;
    ImageView add, viewImage;
    String file_name = "";
    String profile_image = "1";
    ZoomControls zoomControls;
    String gender=" ";
    String patientname="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_prescription);
        btnClear=findViewById(R.id.btn_clear);
        mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);
        zoomControls=findViewById(R.id.zoomcontrol);
        TextView tv_synptoms = findViewById(R.id.tv_synptoms);
        TextView  tv_patientName = findViewById(R.id.tv_patientName);
        TextView  tv_date = findViewById(R.id.tv_date);
         tv_date.setText("Today: " + UtilMethods.INSTANCE.getCurrentDate());
        ImageView profileimage = findViewById(R.id.iv_patient);
        tv_synptoms.setText("" + getIntent().getStringExtra("mobile"));
        String page="";
        try {
            JSONObject obj=new JSONObject(getIntent().getStringExtra("age"));
             gender=obj.getString("Gender");
         
            page=obj.getString("PatientAge");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tv_patientName.setText("" + getIntent().getStringExtra("name")+ " / Age : "+page+"\n"+"Gender : "+gender);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.customer_support);
        requestOptions.error(R.drawable.customer_support);
        Glide.with(this)
                .load(getIntent().getStringExtra("profileimageStr") == null ? "" : getIntent().getStringExtra("profileimageStr"))
                .apply(requestOptions)
                .into(profileimage);

        btnSave=findViewById(R.id.btn_savesignature);

        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   zoomInfun();
                float x=mSignaturePad.getScaleX();
                float y=mSignaturePad.getScaleY();
                mSignaturePad.setScaleX((float) (x+0.5));
                mSignaturePad.setScaleY((float) (y+0.5));
            }
        });

zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       //  zoomOutfun();
        float x=mSignaturePad.getScaleX();
        float y=mSignaturePad.getScaleY();
        if(x==0.5 && y==0.5)
        {
              mSignaturePad.setScaleX(x);
              mSignaturePad.setScaleY(y);
        }
        else {
            mSignaturePad.setScaleX((float) (x-0.5));
            mSignaturePad.setScaleY((float) (y-0.5));
        }
    }
});



        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                btnSave.setEnabled(true);
                btnSave.setEnabled(true);
                //Event triggered when the pad is signed
            }

            @Override
            public void onClear() {
                btnSave.setEnabled(false);
                btnSave.setEnabled(false);
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignaturePad.clear();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
                String path=addJpgSignatureToGallery(signatureBitmap);
                if (path!=null) {
                   Loader loader = new Loader(ManualPrescription.this, android.R.style.Theme_Translucent_NoTitleBar);
                    if (UtilMethods.INSTANCE.isNetworkAvialable(ManualPrescription.this)) {

                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);
                        UtilMethods.INSTANCE.SavePreciption(ManualPrescription.this, "" + getIntent().getStringExtra("userid"),"",
                                "" + getIntent().getStringExtra("appointment_id") , loader, ManualPrescription.this, null, null,path);
                    } else {
                        UtilMethods.INSTANCE.NetworkError(ManualPrescription.this, getResources().getString(R.string.network_error_title),
                                getResources().getString(R.string.network_error_message));
                    }
                    Toast.makeText(ManualPrescription.this, "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void zoomOutfun() {
    }

    private void zoomInfun() {


    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }
    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }
    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
      ManualPrescription.this.sendBroadcast(mediaScanIntent);
    }
    private String addJpgSignatureToGallery(Bitmap signatureBitmap) {
        boolean result = false;
        File photo=null;
        try {
             photo = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.jpg", System.currentTimeMillis()));
            saveBitmapToJPG(signatureBitmap, photo);
            scanMediaFile(photo);
            photo.getAbsolutePath();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photo.getAbsolutePath();
    }
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
}