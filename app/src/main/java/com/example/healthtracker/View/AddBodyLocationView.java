package com.example.healthtracker.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.healthtracker.R;

import java.util.ArrayList;

public class AddBodyLocationView extends AppCompatActivity {

    private ImageView bodyLocPhoto;
    private EditText bodyLoc_text;
    private int i;
    private float x;
    private float y;
    private String bodyLoc;
    private ArrayList<String> lable;


    private int SELECT_IMAGE_RESULT_CODE = 6;



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

    public void saveButton(View view){

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
        lable = new ArrayList<>();
        lable.add(bodyLoc);
        lable.add(String.valueOf(x));
        lable.add(String.valueOf(y));
    }

}
