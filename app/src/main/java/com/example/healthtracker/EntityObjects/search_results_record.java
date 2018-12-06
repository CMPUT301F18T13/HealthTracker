package com.example.healthtracker.EntityObjects;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthtracker.Contollers.UserDataController;

import com.example.healthtracker.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.healthtracker.Contollers.PhotoController.stringToImage;

/**
 * search_results_record is the generated object from calls to the search activity when
 * a user attempts search functionality resulting in record hits matching the input parameters
 *
 * @author Michael Boisvert
 * @version 1.0
 * @since 2018-11-29
 */
public class search_results_record extends Activity {

    private PatientRecord record;
    private BodyLocation bodyLoc;
    private ImageView locGraph;
    private TextView saved_bodyLocation;
    private TextView saved_geoLocation;
    private Double Lon;
    private Double Lat;

    /**
     * On creation or launch the record object is populated with the appropriate data to display
     * a records contents
     *
     * @param savedInstanceState related to bundle in onCreate
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_record_view);

        Object[] hits = UserDataController
                .unserializeObjectArray(this, getIntent().getStringExtra("hits"));

        ArrayList<PatientRecord> records = (ArrayList<PatientRecord>) hits[1];
        record = records.get(getIntent().getIntExtra("recordIndex", -1));

        TextView titleText = findViewById(R.id.care_record_title);
        TextView desText = findViewById(R.id.care_record_comment);
        TextView timestampText = findViewById(R.id.care_record_timestamp);
        saved_bodyLocation = findViewById(R.id.body_text);
        locGraph = findViewById(R.id.body_pic);
        saved_geoLocation = findViewById(R.id.geo_text);

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
}