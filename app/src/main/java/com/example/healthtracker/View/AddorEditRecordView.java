package com.example.healthtracker.View;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthtracker.Activities.AddGeoLocationActivity;
import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.PatientRecord;
import com.example.healthtracker.R;
import com.example.healthtracker.Activities.TakePhotoActivity;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * AddorEditRecordView enables a patient to add a new record to one of their problems or edit an
 * existing record. This activity will finish with a positive result code if a record was successfully
 * saved. The current Record data will be displayed if a record is being edited. The new
 * or changed record can be saved by selecting the save button.
 *
 */
public class AddorEditRecordView extends AppCompatActivity {

    private EditText titleText, descriptionText;
    private TextView timestampText;
    private Context context;
    String problemTitle;
    File capturedImages;
    private PatientRecord record;
    //private Button geoLocation;
    private TextView saved_geoLocation;
    private Double Lat;
    private Double Lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addor_edit_record);
        titleText = findViewById(R.id.title_edit_text);
        descriptionText = findViewById(R.id.description_edit_text);
        timestampText = findViewById(R.id.patient_record_timestamp);
        //geoLocation = findViewById(R.id.add_geolocation);
        saved_geoLocation = findViewById(R.id.show_geo);
        context = this;
        record = new PatientRecord();
        getExtraString();

        // if editing a record show its current details
        Intent intent = getIntent();
        int index = intent.getIntExtra("Index", -1);
        if (index != -1) {
            String recordString = intent.getStringExtra("Record");
            record = UserDataController.unSerializeRecord(this, recordString);
            showRecord();
        } else{
            record.setTimestamp();
            timestampText.setText(record.getTimestamp().toString());
        }
    }

    @Override
    /*
     * Overrides the android back button in order to warn user that their record will not be saved.
     * Also sets the result state to RESULT_CANCELED so that the previous activity can determine no
     * record has been added or edited.
     */
    public void onBackPressed() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setMessage("Warning. You are about to go back without saving the record.");
        ab.setCancelable(true);
        // Set a button to return to the Home screen and don't save changes
        ab.setNeutralButton("Exit And Lose Record", (dialog, which) -> {
            Intent intent = getIntent();
            setResult(RESULT_CANCELED, intent);
            finish();
        });

        // set a button which will close the alert dialog
        ab.setNegativeButton("Return to Record", (dialog, which) -> {

        });
        // show the alert dialog on the screen
        ab.show();
    }

    // When clicked,  save button will save or update the record as long as the record at least has a title.
    public void saveButton(View view) {
        if (titleText.getText().toString().equals("")) {
            Toast.makeText(this, "Error, a title is required.", Toast.LENGTH_LONG).show();
        } else {
            saveRecord();
        }
    }

    // If a record is being edited this method is called to display its current data.
    private void showRecord(){

        titleText.setText(record.getTitle());
        descriptionText.setText(record.getComment());
        timestampText.setText(record.getTimestamp().toString());
        //saved_geoLocation.setText(geo_location);
            List<Address> addressList = null;
            String CurrentLocation;
            Lat = record.getGeoLocation().get(0);
            Lon = record.getGeoLocation().get(1);
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocation(Lat, Lon, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            String city = address.getLocality();
            String state = address.getAdminArea();
            String country = address.getCountryName();
            String postalCode = address.getPostalCode();
            CurrentLocation = city + " " + state + " " + country + " " + postalCode;
            saved_geoLocation.setText(CurrentLocation);

        //TODO show geomap, photos, bodlocation
    }

    /*
     * Update record with user entered data.
     * Save record by serializing it and setting it as the result of this activity.
     */
    private void saveRecord(){
        // get Record info


        String title = titleText.getText().toString();
        String comment = descriptionText.getText().toString();
        //geo_location = saved_geoLocation.getText().toString();


        // fetch user data
        Patient patient = UserDataController.loadPatientData(context);

        // add record
        record.setComment(comment);
        record.setTitle(title);
        record.setGeoLocation(Lon,Lat);

        // TODO set photos, geomap, bodylocation once they are implemented

        // return to add problem with record result
        Intent intent = getIntent();
        intent.putExtra("Record", UserDataController
                .serializeRecord(AddorEditRecordView.this, record));
        setResult(RESULT_OK, intent);
        finish();
    }

    // Intent initiated to add a photo to a record
    public void addPhoto(View view) {
        Intent intent = new Intent(AddorEditRecordView.this, TakePhotoActivity.class);
        intent.putExtra("ProblemTitle",getExtraString());
        startActivity(intent);
    }


    private String getExtraString(){
        Intent intent = getIntent();
        return intent.getStringExtra("ProblemTitle");
    }

    public void addGeoLocation(View view) {
        Intent intent = new Intent(AddorEditRecordView.this, AddGeoLocationActivity.class);
        startActivityForResult(intent,1);
    }

   // @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        List<Address> addressList = null;
        String CurrentLocation;
        if(resultCode==RESULT_OK) {
            Lat = data.getExtras().getDouble("Lat");
            Lon = data.getExtras().getDouble("Lon");
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocation(Lat, Lon, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            String city = address.getLocality();
            String state = address.getAdminArea();
            String country = address.getCountryName();
            String postalCode = address.getPostalCode();
            CurrentLocation = city + " " + state + " " + country + " " + postalCode;
            saved_geoLocation.setText(CurrentLocation);
        }
        //saved_geoLocation.setText(geo_location);
    }


}
