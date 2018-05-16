package com.example.mansopresk21.vedio;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;




public class Camera extends AppCompatActivity {
    ImageView imageView;
    public static final int CAM_REQ_CODE = 123;
    public static final int GAL_REQ_CODE = 321;

    public static final int CAM_PERMISSION_ACCESS_CODE = 111;
    public static final String CAM_PERMISSION_NAME[] = {android.Manifest.permission.CAMERA};
    public static final int GAL_PERMISSION_ACCESS_CODE = 222;
    public static final String GAL_PERMISSION_NAME[] = {android.Manifest.permission.READ_EXTERNAL_STORAGE};

    Bitmap bit = null;

    String choice[] = {"CAMERA", "GALLERY"};
    SharedPreferences shre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        imageView = findViewById(R.id.imageTest);

//        Bitmap realImage = BitmapFactory.decodeStream(stream);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        realImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] b = baos.toByteArray();
//
//        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
//        shre.setText(encodedImage);
//
//        shre = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences.Editor edit=shre.edit();
//        edit.putString("image_data",encodedImage);
//        edit.commit();
//
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String previouslyEncodedImage = shre.getString("image_data", "");
//
//        if( !previouslyEncodedImage.equalsIgnoreCase("") )
//        {
//            byte[] b1 = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(b1, 0, b.length);
//            imageView.setImageBitmap(bitmap);
        }


    public void imageShare(View view)
    {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        // adb.setIcon(R.drawable.camera);
        adb.setTitle(" Select One ");
        adb.setItems(choice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        int res = ContextCompat.checkSelfPermission(Camera.this, android.Manifest.permission.CAMERA);
                        if (res == PackageManager.PERMISSION_GRANTED) {
                            Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cam, CAM_REQ_CODE);
                        } else {
                            ActivityCompat.requestPermissions(Camera.this, CAM_PERMISSION_NAME, CAM_PERMISSION_ACCESS_CODE);
                        }
                        break;
                    case 1:
                        int res1 = ContextCompat.checkSelfPermission(Camera.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);

                        if (res1 == PackageManager.PERMISSION_GRANTED) {
                            Intent gal = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(gal, GAL_REQ_CODE);
                        } else {
                            ActivityCompat.requestPermissions(Camera.this, GAL_PERMISSION_NAME, GAL_PERMISSION_ACCESS_CODE);
                        }

                        break;
                }
            }
        });
        adb.create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAM_PERMISSION_ACCESS_CODE:
                if (CAM_PERMISSION_NAME.equals(permissions[0]) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cam, CAM_REQ_CODE);
                }
                break;

            case GAL_PERMISSION_ACCESS_CODE:
                if (GAL_PERMISSION_NAME.equals(permissions[0]) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent gal = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(gal, GAL_REQ_CODE);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAM_REQ_CODE:
                if (resultCode == RESULT_OK)
                {
                    Bundle b = data.getExtras();
                    bit = (Bitmap) b.get("data");
                    imageView.setImageBitmap(bit);

                }
                break;

            case GAL_REQ_CODE:
                if (resultCode == RESULT_OK) {
                    Uri img = data.getData();
                    try {
                        bit = MediaStore.Images.Media.getBitmap(this.getContentResolver(), img);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageView.setImageBitmap(bit);
                }
                break;
        }
    }
}
