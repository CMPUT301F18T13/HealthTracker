package com.example.healthtracker.View;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.healthtracker.R;

import java.util.ArrayList;

public class AddBodyLocationView extends AppCompatActivity {

    private ImageView bodyLocPhoto;
    private Spinner chooseLoc;
    private int i;
    private float x;
    private float y;
    private String bodyLoc;
    private ArrayList<String> lable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_body_location_view);

        bodyLocPhoto = (ImageView) findViewById(R.id.bodylocphoto);
        chooseLoc = (Spinner) findViewById(R.id.choose_body_loc);


        bodyLocPhoto.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                x = event.getX();
                y = event.getY();
                Toast.makeText(AddBodyLocationView.this, "lable at x = "+x+" y = "+y, Toast.LENGTH_LONG).show();
                return true;
            }
        });

        //Prompt user choose a body location
        chooseLoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if(pos == 0){
                    Toast.makeText(AddBodyLocationView.this, "Please choose a body location!",Toast.LENGTH_LONG).show();
                }
                else {
                    String[] bodyLocs = getResources().getStringArray(R.array.body_location_options);
                    Toast.makeText(AddBodyLocationView.this, "Your choice:" + bodyLocs[pos], Toast.LENGTH_LONG).show();
                    bodyLoc = bodyLocs[pos];
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddBodyLocationView.this, "Please choose a body location!",Toast.LENGTH_LONG).show();
            }
        });


    }


    public void changeBP(View view){

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
