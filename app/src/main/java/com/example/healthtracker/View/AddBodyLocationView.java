package com.example.healthtracker.View;

import android.Manifest;
import android.bluetooth.le.AdvertiseData;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.healthtracker.Activities.AddGeoLocationActivity;
import com.example.healthtracker.Contollers.PhotoController;
import com.example.healthtracker.EntityObjects.BodyLocation;
import com.example.healthtracker.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class AddBodyLocationView extends AppCompatActivity  implements EasyPermissions.PermissionCallbacks{

    private ImageView bodyLocPhoto;
    //private EditText bodyLoc_text;
    private int i;
    private int a = 0;
    private float x;
    private float y;
    private String bodyLocText = null;
    private Spinner chooseLoc;
    private ArrayAdapter<String> locAdapter;
    private ArrayList<String> bodyLocsText;
    //private ArrayList<BodyLocation> bodyLocs;
    private BodyLocation bodyLoc;


    private int SELECT_IMAGE_RESULT_CODE = 6;
    private static final String TAG = "AddBodyLocationView";
    private static final int REQUEST_CODE_SAVE_IMG = 10;
    private String addPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "BodyLoc";
    Bitmap rotatedBitmap;

    private Bitmap showPicFront;
    private Bitmap showPicBack;

    private Bitmap bodyGraphic;



    //private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 101;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_body_location_view);

        bodyLocPhoto = (ImageView) findViewById(R.id.bodylocphoto);
        chooseLoc = (Spinner) findViewById(R.id.choose_spinner);
        bodyLocsText = new ArrayList<String>();
        //bodyLocs = new ArrayList<BodyLocation>();
        showPicFront = BitmapFactory.decodeResource(getResources(), R.drawable.bodylocationfront);
        showPicBack = BitmapFactory.decodeResource(getResources(), R.drawable.bodylocationback);
        bodyGraphic = BitmapFactory.decodeResource(getResources(), R.drawable.bodylocationfront);

        bodyLocPhoto.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                x = event.getX();
                y = event.getY();
                Toast.makeText(AddBodyLocationView.this, "lable at x = "+x+" y = "+y, Toast.LENGTH_LONG).show();
                addPin(v);
                return false;
            }
        });


        //Prompt user choose a body location
        bodyLocsText.add(0, "Choose a Body Location");
        bodyLocsText.add("+Add New BodyLocation+");

        //chooseLoc.setPrompt("Choose a Body Location");
        //chooseLoc.setSelected(true);
        chooseLoc.setFocusable(true);
        chooseLoc.setFocusableInTouchMode(true);
        locAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,bodyLocsText);
        chooseLoc.setAdapter(locAdapter);
        locAdapter.notifyDataSetChanged();
        chooseLoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if(bodyLocsText.get(pos).equals("Choose a Body Location")){
                    Toast.makeText(AddBodyLocationView.this, "Please choose a Body Location!!" , Toast.LENGTH_LONG).show();
                    //locAdapter.notifyDataSetChanged();
                    //chooseLoc.setAdapter(locAdapter);
                }
                else if(bodyLocsText.get(pos).equals("+Add New BodyLocation+")){
                    //locAdapter.notifyDataSetChanged();
                    addNewLoc();
                    locAdapter.notifyDataSetChanged();
                    //chooseLoc.setAdapter(locAdapter);
                }
                else {
                    //locAdapter.notifyDataSetChanged();
                     //= getResources().getStringArray(R.array.body_location_options);
                    Toast.makeText(AddBodyLocationView.this, "Your choice:" + bodyLocsText.get(pos), Toast.LENGTH_LONG).show();
                    bodyLocText = bodyLocsText.get(pos);
                }
                locAdapter.notifyDataSetChanged();
                //chooseLoc.setAdapter(locAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Toast.makeText(AddBodyLocationView.this, "Please choose a body location!",Toast.LENGTH_LONG).show();
            }
        });


        // Take photo & Open album
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }


    @Override
    public void onBackPressed() {
        // Create an intent object containing the bridge to between the two activities
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
            ab.setNegativeButton("Return to Record BodyLocation", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            // show the alert dialog on the screen
            ab.show();
    }


    public void addNewLoc(){
        AlertDialog.Builder ab = new AlertDialog.Builder(AddBodyLocationView.this);
        ab.setTitle("Please enter a new Body Location!");
        ab.setCancelable(true);
        EditText newLoc = new EditText(this);
        ab.setView(newLoc);
        // Set a button to return to the Home screen and don't save changes
        ab.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int size = bodyLocsText.size();
                bodyLocsText.add(size-1,newLoc.getText().toString());
                locAdapter.notifyDataSetChanged();
            }
        });

        // set a button which will close the alert dialog
        ab.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                locAdapter.notifyDataSetChanged();
            }
        });
        // show the alert dialog on the screen
        ab.show();
    }


    public void changeBP(View view){
        //Intent intent = new Intent(Intent.ACTION_PICK);
        //intent.setType("image/*");
       // startActivityForResult(intent, SELECT_IMAGE_RESULT_CODE);
        AlertDialog.Builder ab = new AlertDialog.Builder(AddBodyLocationView.this);
        ab.setTitle("Change Body Graphic");
        ab.setCancelable(true);
        // Set a button to return to the Home screen and don't save changes
        ab.setPositiveButton("Load from Library", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (ContextCompat.checkSelfPermission(AddBodyLocationView.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddBodyLocationView.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 51);

                }else {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");
                    startActivityForResult(intent, 51);
                }

            }
        });

        // set a button which will close the alert dialog
        ab.setNeutralButton("Take a new photo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (ContextCompat.checkSelfPermission(AddBodyLocationView.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddBodyLocationView.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            101);
                }else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");
                    startActivityForResult(intent, 101);

                }

            }
        });
        // show the alert dialog on the screen
        ab.show();
    }

    public void savePhotoButton(View view){
        requestPermission();
    }

    public void saveButton(View view){
        //graphic
        if(bodyGraphic!=null && bodyLocsText.contains(bodyLocText)) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bodyGraphic.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] gra_byteArray = stream.toByteArray();

            Intent intent = new Intent();
            intent.putExtra("graphic", gra_byteArray);
            intent.putExtra("text", bodyLocText);


            AddBodyLocationView.this.setResult(88, intent);
            AddBodyLocationView.this.finish();
        }
        else{
            Toast.makeText(AddBodyLocationView.this, "You must choose a body location and add pin on your uploaded photo!", Toast.LENGTH_LONG).show();
        }
    }


    public void switchForB(View view){
        switch (i) {
            case 0:bodyLocPhoto.setImageBitmap(showPicBack);i++;break;
            case 1:bodyLocPhoto.setImageBitmap(showPicFront);i--;break;
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

        String name = String.valueOf(a);
        if(bodyLocsText.contains(bodyLocText)) {
            if (i == 0) {
                name = bodyLocText + "_Front" + ".jpg";
            } else if (i == 1) {
                name = bodyLocText + "_Back" + ".jpg";
            }
            //a++;

            if (BitmapFactory.decodeFile(storePath + "/" + name) == null) {
                Toast.makeText(this, "Failed! Saved photo first!", Toast.LENGTH_SHORT).show();
            } else {
                Bitmap bitmap = BitmapFactory.decodeFile(storePath + "/" + name)
                        .copy(Bitmap.Config.ARGB_8888, true);

                float bitmapWidth = bitmap.getWidth();
                float bitmapHeight = bitmap.getHeight();
                float floorWidth = bodyLocPhoto.getWidth();
                float floorHeight = bodyLocPhoto.getHeight();
                float proportionateWidth = bitmapWidth / floorWidth;
                float proportionateHeight = bitmapHeight / floorHeight;

                Canvas canvas = new Canvas(bitmap);//new canvas
                Paint p = new Paint();
                p.setColor(0xffff0000);//set color
                p.setAntiAlias(true);
                canvas.drawCircle(x * proportionateWidth, y * proportionateHeight, 20, p);

                boolean isSaveSuccess = saveImageToGallery(this, bitmap, "Labeled_" + name);
                if (isSaveSuccess) {
                    Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed! Try again!", Toast.LENGTH_SHORT).show();
                }

                bodyLocPhoto.setImageBitmap(bitmap);//Set new photo
                bodyGraphic = bitmap;
            }
        }
        else{
            Toast.makeText(this, "No Body Location found! Please choose one!", Toast.LENGTH_SHORT).show();
        }

    }


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
        //int imgId;
        Bitmap bitmap;
        if (i == 0) {
            //imgId = R.drawable.bodylocationfront;
            bitmap = showPicFront;

        }
        else {
            //imgId = R.drawable.bodylocationback;
            bitmap = showPicBack;
        }

        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgId);

        // Name the pic
        String name = String.valueOf(a);
        if(bodyLocsText.contains(bodyLocText)) {
            if (i == 0) {
                name = bodyLocText + "_Front" + ".jpg";
            } else if (i == 1) {
                name = bodyLocText + "_Back" + ".jpg";
            }

            boolean isSaveSuccess = saveImageToGallery(this, bitmap, name);
            if (isSaveSuccess) {
                Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
                Log.d("saved", name);
            } else {
                Toast.makeText(this, "Failed! Try again!", Toast.LENGTH_SHORT).show();
                Log.d(" not saved", name);
            }
        }
        else{
            Toast.makeText(this, "No Body Location found! Please choose one!", Toast.LENGTH_SHORT).show();
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

    public static boolean saveImageToGallery(Context context, Bitmap bmp, String name) {
        // First save the picture
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "BodyLoc";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            Log.i(TAG, "onPermissionsDenied:------>BACK TO APP");
            saveImage();
        } else if (requestCode == 101) {
            Log.d("!!!!!!!!!!!!!!!!", "Take!!!!!!!!!");
            if (resultCode == RESULT_OK) {
                Bitmap image = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                Matrix matrix = new Matrix();
                assert image != null;
                int width = bodyLocPhoto.getWidth();
                int height = bodyLocPhoto.getHeight();
                Log.d("!!!!!!!!!!!!!!!!", String.valueOf(width)+" and "+String.valueOf(height));

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(image,width,height,true);
                rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                addPath= PhotoController.imageToString(rotatedBitmap);
                bodyLocPhoto.setImageBitmap(PhotoController.stringToImage(addPath));

                // Name the pic
                String name = String.valueOf(a);
                if(i == 0){
                    name = bodyLocText + "_Front" + ".jpg";
                    showPicFront = PhotoController.stringToImage(addPath);
                }
                else if(i == 1){
                    name = bodyLocText + "_Back" + ".jpg";
                    showPicBack = PhotoController.stringToImage(addPath);
                }

                boolean isSaveSuccess = saveImageToGallery(this, PhotoController.stringToImage(addPath),name);
                if (isSaveSuccess) {
                    Log.d("Take photo saved", name);
                    Toast.makeText(AddBodyLocationView.this, "Save Photo First", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("Failed! Try again!", name);
                }



            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(AddBodyLocationView.this, "Photo Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddBodyLocationView.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == 51){
            Log.d("!!!!!!!!!!!!!!!!", "load!!!!!!!!!");
            if (resultCode == RESULT_OK) {
                Uri targetUri = data.getData();
                assert targetUri != null;
                Bitmap bitmap;
                try{
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                    Matrix matrix = new Matrix();
                    int width = bodyLocPhoto.getWidth();
                    int height = bodyLocPhoto.getHeight();
                    Log.d("!!!!!!!!!!!!!!!!", String.valueOf(width)+" and "+String.valueOf(height));
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,width,height,true);
                    rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                    addPath= PhotoController.imageToString(rotatedBitmap);
                    bodyLocPhoto.setImageBitmap(PhotoController.stringToImage(addPath));

                    // Name the pic
                    String name = String.valueOf(a);
                    if(i == 0){
                        name = bodyLocText + "_Front" + ".jpg";
                        showPicFront = PhotoController.stringToImage(addPath);
                    }
                    else if(i == 1){
                        name = bodyLocText + "_Back" + ".jpg";
                        showPicBack = PhotoController.stringToImage(addPath);
                    }

                    boolean isSaveSuccess = saveImageToGallery(this, PhotoController.stringToImage(addPath),name);
                    if (isSaveSuccess) {
                        Log.d("Load from library saved", name);
                    } else {
                        Log.d("Failed! Try again!", name);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else{
                Toast.makeText(this, "Load Photo Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }



}
