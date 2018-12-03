package com.example.healthtracker.View;

import android.content.Context;
import android.content.ContextWrapper;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthtracker.Activities.AddGeoLocationActivity;
import com.example.healthtracker.Activities.SlideShowActivity;
import com.example.healthtracker.Contollers.PhotoController;
import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.BodyLocation;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.PatientRecord;
import com.example.healthtracker.R;
import com.example.healthtracker.Activities.TakePhotoActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

import static com.example.healthtracker.Contollers.PhotoController.imageToString;
import static com.example.healthtracker.Contollers.PhotoController.stringToImage;

/*
 * AddorEditRecordView enables a patient to add a new record to one of their problems or edit an
 * existing record. This activity will finish with a positive result code if a record was successfully
 * saved. The current Record data will be displayed if a record is being edited. The new
 * or changed record can be saved by selecting the save button.
 */
public class AddorEditRecordView extends AppCompatActivity {

    private EditText titleText, descriptionText;
    private int index;
    private TextView timestampText;
    private Context context;

    String problemTitle;
    File capturedImages;

    private ArrayList<Bitmap> takenPhoto = new ArrayList<Bitmap>();
    private ArrayList<String> timeStamps = new ArrayList<String>();
    private String oldTitle = "";

    private PatientRecord record;
    private TextView saved_geoLocation;
    private TextView saved_bodyLocation;
    private Double Lat;
    private Double Lon;

    //private Spinner chooseLoc;
    //private String bodyLocText;
    private BodyLocation bodyLoc;
    private ImageView locGraph;

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
        locGraph = findViewById(R.id.show_graph);

        context = this;
        record = new PatientRecord();
        getExtraString();

        // if editing a record show its current details
        Intent intent = getIntent();
        index = intent.getIntExtra("Index", -1);
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
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            PhotoController.removePhotosFromTemporaryStorage(cw);
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
        oldTitle = record.getTitle();

        if(record.getGeoLocation().size()<2){
            return;
        }

        List<Address> addressList = null;
        String CurrentLocation;
        Lon = record.getGeoLocation().get(0);
        Lat = record.getGeoLocation().get(1);
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

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        takenPhoto = PhotoController.loadImagesByRecord(cw, this.getExtraString(), oldTitle);
        timeStamps = PhotoController.getTimestampsByRecord(cw, this.getExtraString(), oldTitle);
        for (int i = 0; i < takenPhoto.size(); i++) {
            System.out.println("Adding back to temp photos!!!");
            Bitmap photo = takenPhoto.get(i);
            PhotoController.saveToTemporaryStorage(photo, cw, timeStamps.get(i));
        }

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
    }

    /*
     * Update record with user entered data.
     * Save record by serializing it and setting it as the result of this activity.
     */
    private void saveRecord(){
        // get Record info
        String title = titleText.getText().toString();
        String comment = descriptionText.getText().toString();

        // fetch user data
        Patient patient = UserDataController.loadPatientData(context);

        // add record
        record.setComment(comment);
        record.setTitle(title);

        if(index == -1){
            record.addGeoLocation(Lon, Lat);
        } else{
            record.setGeoLocation(Lon,Lat);
        }

        record.setBodyLoc(bodyLoc);

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        for (int i = 0; i < takenPhoto.size(); i++) {
            Bitmap photo = takenPhoto.get(i);
            String pathLoaded= PhotoController.saveToInternalStorage(photo,cw, this.getExtraString(), title, timeStamps.get(i));
        }
        PhotoController.removePhotosFromTemporaryStorage(cw);
        System.out.println(this.getExtraString());
        record.setPhotos(takenPhoto, timeStamps);

        // Remove photos if title has changed...
        System.out.println("Old title is... " + oldTitle + '\n' + "New title is... " + title);
        if (!oldTitle.equals(title)) {
            System.out.println("Removing old photos...");
            PhotoController.removePhotosFromInternalStorage(cw, this.getExtraString(), oldTitle);
        }

        // return to add problem with record result
        Intent intent = getIntent();
        intent.putExtra("Record", UserDataController
                .serializeRecord(AddorEditRecordView.this, record));
        setResult(RESULT_OK, intent);
        finish();
    }

    // Intent initiated to add a photo to a record
    public void addPhoto(View view) {
        if (takenPhoto.size() >= 10) {
            Toast.makeText(this, "Error, number of photos cannot exceed 10.", Toast.LENGTH_LONG).show();
        }
        else {

            Intent intent = new Intent(AddorEditRecordView.this, TakePhotoActivity.class);
            intent.putExtra("ProblemTitle",getExtraString());
            if (takenPhoto.size() != 0) {
                intent.putExtra("OldPhoto", PhotoController.imageToString(takenPhoto.get(takenPhoto.size() - 1)));
            }
            else {
                intent.putExtra("OldPhoto", "");
            }

            startActivityForResult(intent, 50);
        }
    }

    // Get the string put into the intent from the previous activity
    private String getExtraString(){
        Intent intent = getIntent();
        return intent.getStringExtra("ProblemTitle");
    }

    public void viewPhotos(View view) {
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(AddorEditRecordView.this, SlideShowActivity.class);
        intent.putExtra("ProblemTitle", this.getExtraString());
        intent.putExtra("isProblem", "Record");
        // Launch the browse emotions activity
        startActivity(intent);
    }

    // Add a geo-location to a record
    public void addGeoLocation(View view) {
        Intent intent = new Intent(AddorEditRecordView.this, AddGeoLocationActivity.class);
        startActivityForResult(intent,1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        List<Address> addressList = null;
        String CurrentLocation;
        if(resultCode==1) {
            Lat = Objects.requireNonNull(data.getExtras()).getDouble("Lat");
            Lon = data.getExtras().getDouble("Lon");
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocation(Lat, Lon, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert addressList != null;
            Address address = addressList.get(0);
            String city = address.getLocality();
            String state = address.getAdminArea();
            String country = address.getCountryName();
            String postalCode = address.getPostalCode();
            CurrentLocation = city + " " + state + " " + country + " " + postalCode;
            saved_geoLocation.setText(CurrentLocation);
        }
        if (resultCode == 50) {
            byte[] byteArray = data.getByteArrayExtra("image");
            takenPhoto.add(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.CANADA).format(new Date());
            PhotoController.saveToTemporaryStorage(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length), cw, timeStamp);
            timeStamps.add(timeStamp);
        } else if(requestCode ==4){
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
