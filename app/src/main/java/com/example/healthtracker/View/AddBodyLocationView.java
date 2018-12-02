package com.example.healthtracker.View;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.healthtracker.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class AddBodyLocationView extends AppCompatActivity  implements EasyPermissions.PermissionCallbacks  {

    private ImageView bodyLocPhoto;
    private EditText bodyLoc_text;
    private int i;
    private float x;
    private float y;
    private String bodyLoc;


    private int SELECT_IMAGE_RESULT_CODE = 6;
    private static final String TAG = "AddBodyLocationView";
    private static final int REQUEST_CODE_SAVE_IMG = 10;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_body_location_view);

        bodyLocPhoto = (ImageView) findViewById(R.id.bodylocphoto);
        bodyLoc_text = (EditText) findViewById(R.id.bodyloc_text);


        bodyLocPhoto.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                x = event.getX();
                y = event.getY();
                Toast.makeText(AddBodyLocationView.this, "lable at x = "+x+" y = "+y, Toast.LENGTH_LONG).show();
                addPin(v);
                return true;
            }
        });








    }

    @Override
    /*
     * Overrides the android back button in order to warn user that their record will not be saved.
     * Also sets the result state to RESULT_CANCELED so that the previous activity can determine no
     * record has been added or edited.
     */
    public void onBackPressed() {
        // Create an intent object containing the bridge to between the two activities
        if (!bodyLoc_text.getText().toString().equals("")) {
            AlertDialog.Builder ab = new AlertDialog.Builder(AddBodyLocationView.this);
            ab.setMessage("Warning. Changes have been made to the body Loc." + "\n" + "Returning to the home screen will not save changes.");
            ab.setCancelable(true);
            // Set a button to return to the Home screen and don't save changes
            ab.setNeutralButton("Exit And Lose Changes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            // set a button which will close the alert dialog
            ab.setNegativeButton("Return to Record", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            // show the alert dialog on the screen
            ab.show();
        } else {
            finish();
        }
    }


    public void changeBP(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_IMAGE_RESULT_CODE);
    }

    public void savePhotoButton(View view){
        requestPermission();
    }

    public void saveButton(View view){
        bodyLoc = bodyLoc_text.getText().toString();
    }

    public void switchForB(View view){
        switch (i) {
            case 0:bodyLocPhoto.setImageResource(R.drawable.bodylocationback);i++;break;
            case 1:bodyLocPhoto.setImageResource(R.drawable.bodylocationfront);i--;break;
        }
        if(i == 0) {
            Toast.makeText(AddBodyLocationView.this, "Front side.", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(AddBodyLocationView.this, "Back side.", Toast.LENGTH_LONG).show();
        }
    }

    public void addPin(View view){
        Log.d("(x,y): ","("+String.valueOf(x)+", "+String.valueOf(y)+")");
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "BodyLoc";
        Bitmap bitmap=BitmapFactory.decodeFile(storePath+"/defult.jpg")
                .copy(Bitmap.Config.ARGB_8888, true);//从文件得到一个位图对象。要调用copy函数重新生成位图，不然会报错

        float bitmapWidth = bitmap.getWidth();
        float bitmapHeight = bitmap.getHeight();
        float floorWidth = bodyLocPhoto.getWidth();
        float floorHeight = bodyLocPhoto.getHeight();
        float proportionateWidth = bitmapWidth / floorWidth;
        float proportionateHeight = bitmapHeight / floorHeight;

        Canvas canvas=new Canvas(bitmap);//new canvas
        Paint p = new Paint();
        p.setColor(0xffff0000);//set color
        p.setAntiAlias(true);
        canvas.drawCircle(x*proportionateWidth,y*proportionateHeight,20,p);

        boolean isSaveSuccess = saveImageToGallery(this, bitmap,"Labeled");
        if (isSaveSuccess) {
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed! Try again!", Toast.LENGTH_SHORT).show();
        }

        bodyLocPhoto.setImageBitmap(bitmap);//Set new photo
    }




    //TODO SAVE IMAGE TO LOCAL (NEED TO BE FIXED)
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            // read SD card permission
            String[] mPermissionList = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (EasyPermissions.hasPermissions(this, mPermissionList)) {
                //PASS
                saveImage();
            } else {
                //DENY
                EasyPermissions.requestPermissions(
                        this,
                        "need SD card permission",
                        REQUEST_CODE_SAVE_IMG,
                        mPermissionList
                );
            }
        } else {
            saveImage();
        }
    }

    private void saveImage() {
        int imgId;
        if (i == 0) {
            imgId = R.drawable.bodylocationfront;

        } else {
            imgId = R.drawable.bodylocationback;
        }

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgId);
        boolean isSaveSuccess = saveImageToGallery(this, bitmap,"defult");
        if (isSaveSuccess) {
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed! Try again!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        Log.i(TAG, "onPermissionsGranted:" + requestCode + ":" + list.size());
        saveImage();
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.i(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            Log.i(TAG, "onPermissionsDenied:------>BACK TO APP");
            saveImage();
        }
        else if(requestCode == SELECT_IMAGE_RESULT_CODE){
            //bodyLocPhoto.setImageBitmap();
        }
    }

    public static boolean saveImageToGallery(Context context, Bitmap bmp, String name) {
        // First save the picture
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "BodyLoc";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}
