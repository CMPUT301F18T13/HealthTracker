package com.example.healthtracker.Activities;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthtracker.Contollers.PhotoController;
import com.example.healthtracker.R;

import java.io.FileNotFoundException;
import java.util.Objects;

public class TakePhotoActivity extends AppCompatActivity {

    Uri imageFileUri;
    private TextView textTargetUri;
    private ImageView imageView;
    String pathLoaded;
    String addPath = "";
    String problemTitle;
    String path = "/storage/self/primary/Download/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        ImageButton takePhotoButton = findViewById(R.id.take_photo_button);
        Button loadPhotoButton = findViewById(R.id.photo_from_library_button);
        textTargetUri = findViewById(R.id.textView);
        textTargetUri.setText(getExtraString());

        imageView = findViewById(R.id.imageView);
        getExtraString();
        takePhotoButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAPhoto();
            }
        });
        loadPhotoButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAPhoto();
            }
        });
    }

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private void takeAPhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    private void loadAPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 50);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            // Can't display the photo in a text view for whatever reason
            if (resultCode == RESULT_OK) {
                Bitmap image = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                assert image != null;
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(image, 300, 300, true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                addPath= PhotoController.imageToString(rotatedBitmap);
                imageView.setImageBitmap(PhotoController.stringToImage(addPath));


                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                pathLoaded=PhotoController.saveToInternalStorage(rotatedBitmap,cw);
                //SlideShowActivity.add(addPath);
               // Bitmap test1 = PhotoController.loadImageFromStorage(pathLoaded,imageName);
                //textTargetUri.setText(pathLoaded);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Photo Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == 50) {
            if (resultCode == RESULT_OK) {
                Uri targetUri = data.getData();
                assert targetUri != null;
                Bitmap bitmap;
                try {
                    // Rotate the imageView displayed since camera produces an incorrectly orientated image
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                    Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                     /*pathLoaded=PhotoController.saveToInternalStorage(rotatedBitmap);
                    Bitmap test1 = PhotoController.loadImageFromStorage(pathLoaded,imageName);
                    textTargetUri.setText(pathLoaded);*/
                    addPath= PhotoController.imageToString(rotatedBitmap);
                    imageView.setImageBitmap(PhotoController.stringToImage(addPath));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else{
                Toast.makeText(this, "Load Photo Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addPhotoToRecord(View view){
        textTargetUri = findViewById(R.id.textView);
        String test= textTargetUri.getText().toString();
        if (addPath.equals("")){
            Toast.makeText(this, "No Photo Selected", Toast.LENGTH_SHORT).show();
        }
        else if(SlideShowActivity.add(addPath,test)){
            Toast.makeText(this, "Photo Recorded", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Max Photo Limit Reached", Toast.LENGTH_SHORT).show();
        }
    }

    public String getExtraString(){
        Intent intent = getIntent();
        return intent.getStringExtra("ProblemTitle");
    }
}


