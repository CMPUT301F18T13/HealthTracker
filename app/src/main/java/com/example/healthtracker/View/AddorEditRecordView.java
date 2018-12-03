package com.example.healthtracker.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthtracker.Activities.AddGeoLocationActivity;
import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.BodyLocation;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.PatientRecord;
import com.example.healthtracker.R;
import com.example.healthtracker.Activities.TakePhotoActivity;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.healthtracker.Contollers.PhotoController.imageToString;
import static com.example.healthtracker.Contollers.PhotoController.stringToImage;

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
    private String title, comment;
    private Context context;
    private Patient patient;
    File capturedImages;
    private PatientRecord record;
    //private Button geoLocation;
    private TextView saved_geoLocation;
    private TextView saved_bodyLocation;
    private Double Lat;
    private Double Lon;

    //private Spinner chooseLoc;
    //private String bodyLocText;
    private BodyLocation bodyLoc;
    private ImageView locGraph;
    private Bitmap showPic;
    //private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addor_edit_record);
        titleText = findViewById(R.id.title_edit_text);
        descriptionText = findViewById(R.id.description_edit_text);
        timestampText = findViewById(R.id.patient_record_timestamp);
        //geoLocation = findViewById(R.id.add_geolocation);
        //chooseLoc = findViewById(R.id.choose_body_location);

        saved_geoLocation = findViewById(R.id.show_geo);
        saved_bodyLocation = findViewById(R.id.show_body);
        saved_bodyLocation.setText("No BodyLocation.");
        saved_bodyLocation.setClickable(true);
        locGraph = findViewById(R.id.show_graph);
        showPic = BitmapFactory.decodeResource(getResources(), R.drawable.bodylocationfront);

        context = this;
        record = new PatientRecord();

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


        saved_bodyLocation.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                if(record.getBodyLoc() != null) {
                    AlertDialog.Builder ab = new AlertDialog.Builder(AddorEditRecordView.this);
                    ab.setMessage("Do you want to delete this bodyLocation?");
                    ab.setCancelable(true);
                    // Set a button to return to the Home screen and don't save changes
                    ab.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            record.setBodyLoc(null);
                            saved_bodyLocation.setText("No BodyLocation.");
                            locGraph.setImageBitmap(showPic);
                        }
                    });
                    ab.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    ab.show();
                }

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
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setMessage("Warning. You are about to go back without saving the record.");
        ab.setCancelable(true);
        // Set a button to return to the Home screen and don't save changes
        ab.setNeutralButton("Exit And Lose Record", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = getIntent();
                setResult(RESULT_CANCELED, intent);
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
    }

    /*
     * When clicked the save button will save or update the record as long as the record at least
     * has a title.
     */
    public void saveButton(View view) {
        if (titleText.getText().toString().equals("")) {
            Toast.makeText(this, "Error, a title is required.", Toast.LENGTH_LONG).show();
        } else {
            saveRecord();
        }
    }

    /*
     * If a record is being edited this method is called to display its current data.
     */
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

        if (record.getBodyLoc() != null) {
            //show bodyLoc
            String bodyText;
            Bitmap bodygraphic;
            bodyLoc = new BodyLocation();
            String bodygraphicText = record.getBodyLoc().getGraphic();
            bodygraphic = stringToImage(bodygraphicText);
            bodyText = record.getBodyLoc().getLoc();
            saved_bodyLocation.setText(bodyText);
            locGraph.setImageBitmap(bodygraphic);
        }
        else{
            saved_bodyLocation.setText("No BodyLocation.");
            locGraph.setImageBitmap(showPic);
        }


    }

    /*
     * Update record with user entered data.
     * Save record by serializing it and setting it as the result of this activity.
     */
    private void saveRecord(){
        // get Record info
        title = titleText.getText().toString();
        comment = descriptionText.getText().toString();
        //geo_location = saved_geoLocation.getText().toString();

        // fetch user data
        patient = UserDataController.loadPatientData(context);

        // add record
        record.setComment(comment);
        record.setTitle(title);
        record.setGeoLocation(Lat,Lon);
        record.setBodyLoc(bodyLoc);

        // TODO set photos, geomap, bodylocation once they are implemented

        // return to add problem with record result
        Intent intent = getIntent();
        intent.putExtra("Record", UserDataController
                .serializeRecord(AddorEditRecordView.this, record));
        setResult(RESULT_OK, intent);
        finish();
    }

    public void addPhoto(View view) {
        Intent intent = new Intent(AddorEditRecordView.this, TakePhotoActivity.class);
        startActivity(intent);
    }
    public void addGeoLocation(View view) {
        Intent intent = new Intent(AddorEditRecordView.this, AddGeoLocationActivity.class);
        startActivityForResult(intent,1);
    }

   // @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            List<Address> addressList = null;
            String CurrentLocation;
            if (resultCode == RESULT_OK) {
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
        else if(requestCode ==4){
            //Save Body Location
            String bodyText;
            Bitmap bodygraphic;
            bodyLoc = new BodyLocation();
            if (resultCode == 88) {
                byte[] byteArray = data.getByteArrayExtra("graphic");
                bodygraphic = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                String bodygraphicText = imageToString(bodygraphic);
                bodyText = data.getStringExtra("text");
                bodyLoc.setLoc(bodyText);
                bodyLoc.addGraphic(bodygraphicText);
                saved_bodyLocation.setText(bodyText);
                locGraph.setImageBitmap(bodygraphic);
            }


        }
    }

    public void addBodyLocation(View view) {
        Intent intent = new Intent(AddorEditRecordView.this, AddBodyLocationView.class);
        startActivityForResult(intent,4);

    }

}
