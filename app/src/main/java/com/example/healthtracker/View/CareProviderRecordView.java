package com.example.healthtracker.View;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.BodyLocation;
import com.example.healthtracker.EntityObjects.CareProvider;
import com.example.healthtracker.EntityObjects.PatientRecord;
import com.example.healthtracker.R;

import java.io.IOException;
import java.util.List;

import static com.example.healthtracker.Contollers.PhotoController.stringToImage;

public class CareProviderRecordView extends Activity {
    private TextView titleText;
    private TextView desText;
    private TextView timestampText;
    private PatientRecord record;
    private BodyLocation bodyLoc;
    private ImageView locGraph;
    private TextView saved_bodyLocation;
    private TextView saved_geoLocation;
    private Double Lon;
    private Double Lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_provider_record_view);
        titleText = findViewById(R.id.care_record_title);
        desText = findViewById(R.id.care_record_comment);
        timestampText = findViewById(R.id.care_record_timestamp);
        saved_bodyLocation = findViewById(R.id.Bodyloc_text);
        locGraph = findViewById(R.id.Bodyloc_image);
        saved_geoLocation = findViewById(R.id.Geo_text);


        Bundle bd = getIntent().getExtras();
        assert bd != null;
        int patientNum = bd.getInt("patientNum");
        int problemNum = bd.getInt("problemNum");
        int recordNum = bd.getInt("recordNum");

        CareProvider careProvider = UserDataController.loadCareProviderData(this);
        record = careProvider
                .getPatient(patientNum)
                .getProblem(problemNum)
                .getPatientRecord(recordNum);
        displayRecord();
    }

    // Set the text fields
    @SuppressLint("SetTextI18n")
    private void displayRecord(){
        desText.setText("Comment: \n" + record.getComment());
        titleText.setText("Title: \n" + record.getTitle());
        timestampText.setText("Timestamp: \n" + record.getTimestamp().toString());

        if (record.getBodyLoc() != null) {
            //show bodyLoc
            String bodyText;
            Bitmap bodygraphic;
            bodyLoc = new BodyLocation();
            String bodygraphicText = record.getBodyLoc().getGraphic();
            bodygraphic = stringToImage(bodygraphicText);
            bodyText = record.getBodyLoc().getLoc();
            saved_bodyLocation.setText("Body Location: " + bodyText);
            if (bodyText.equals("")|| bodyText == null){
                saved_bodyLocation.setVisibility(View.INVISIBLE);
            }
            locGraph.setImageBitmap(bodygraphic);
        } else{
            saved_bodyLocation.setVisibility(View.INVISIBLE);
            locGraph.setVisibility(View.INVISIBLE);
        }

        if(record.getGeoLocation().size()>1 &&  -180 <= record.getGeoLocation().get(0)  && -90 <= record.getGeoLocation().get(1)){
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
            saved_geoLocation.setText("GeoLocation: " + CurrentLocation);
        } else{
            saved_geoLocation.setVisibility(View.INVISIBLE);
        }
    }

    // Form the CareProvider logo at the top right of the actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
