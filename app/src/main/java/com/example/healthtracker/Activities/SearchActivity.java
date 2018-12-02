package com.example.healthtracker.Activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import org.elasticsearch.common.geo.GeoPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.CareProviderComment;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.PatientRecord;
import com.example.healthtracker.EntityObjects.Problem;
import com.example.healthtracker.R;
import com.example.healthtracker.View.SearchResultsView;
import com.example.healthtracker.View.CareProviderHomeView;


/**
 * SearchActivity will enable patients and careproviders to search for problems and records.
 */
public class SearchActivity extends AppCompatActivity {

    private String searchType;
    private Spinner spinner;
    private EditText keywords;
    private EditText distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        spinner = findViewById(R.id.search_type_dropdown);
        keywords = findViewById(R.id.search_terms);
        distance = findViewById(R.id.distance_edittext);
        distance.setVisibility(View.INVISIBLE);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position == 0){
                    searchType = "keyword";
                    distance.setVisibility(View.INVISIBLE);
                } else if(position == 1){
                    searchType = "geoLocation";
                    distance.setVisibility(View.VISIBLE);
                } else{
                    searchType = "bodyLocation";
                    distance.setVisibility(View.INVISIBLE);
                }
                keywords.setHint(searchType);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                searchType = "keyword";

            }

        });
    }

    public void Search(View view) {
        Object[] hits = null;

        System.out.println("Search type is "+searchType);
        Boolean addressFound = true;

        if(searchType.equals("keyword")){
            hits = UserDataController.searchForKeywords(keywords.getText().toString());

        } else if(searchType.equals("geoLocation")){

            String address = keywords.getText().toString();

            hits = new Object[3];
            hits[0] = new ArrayList<Problem>();
            hits[1] = new ArrayList<PatientRecord>();
            hits[2] = new ArrayList<CareProviderComment>();

            ArrayList<PatientRecord> allReceivedRecords = new ArrayList<PatientRecord>();

            Object[] preHits = null;

            if (getLocationFromAddress(address) == null) {

                addressFound = false;

                // Use an alert dialog to let the user try again
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SearchActivity.this);
                alertBuilder.setMessage("The Internet connection is poor or the address is not valid. Please try again.");
                alertBuilder.setPositiveButton("OK",null);
                AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();
            }
            else {
                Double latitude = getLocationFromAddress(keywords.getText().toString()).getLat() / 1E6;
                Double longitude = getLocationFromAddress(keywords.getText().toString()).getLon() / 1E6;

                // Retrieve all records associated with this Patient and provide titles of all records for geoLocationQuery

                    // Fetch user data
                Patient mPatient = UserDataController.loadPatientData(this);

                System.out.println("mPatient is "+mPatient.toString());

                    // Find all problems and then find all records for each problem
                ArrayList<Problem> mPatientProblems = mPatient.getProblemList();
                System.out.println("mPatient Problems is "+mPatientProblems);

                    // Go through each problem and find all records of each problem
                ArrayList<PatientRecord> mPatientRecords = new ArrayList<PatientRecord>();
                for(int i=0;i<mPatientProblems.size();i++){
                    Problem mPatientProblem = mPatientProblems.get(i);
                    for(int j=0;j<mPatientProblem.countRecords();j++){
                        mPatientRecords.add(mPatientProblem.getPatientRecord(j));
                    }
                }

                System.out.println("mPatient Record is "+mPatientRecords);

                // For each record, check whether the geo location fits the search REQUEST

                for(int k=0;k<mPatientRecords.size();k++){
                    String identifier = mPatientRecords.get(k).getTitle();
                    System.out.println("identifier is "+identifier);

                    preHits = UserDataController.searchForGeoLocations(distance.getText().toString(),latitude,longitude,identifier);

                    // Add all valid results to an arrayList allReceivedRecords
                    ArrayList<PatientRecord> temp;
                    temp = (ArrayList<PatientRecord>) preHits[1];

                    if(temp.size() != 0){
                       for(int m=0;m<temp.size();m++){
                           allReceivedRecords.add(temp.get(m));
                       }
                    }
                }

                hits[1] = allReceivedRecords;

            }

        } else if(searchType.equals("bodyLocation")){

        }

        if (addressFound) {
            // Create an intent object containing the bridge to between the two activities
            Intent intent = new Intent(SearchActivity.this, SearchResultsView.class);
            intent.putExtra("hits", UserDataController.serializeObjectArray(this, hits));

            // Launch the browse activity
            startActivity(intent);
        }


    }

    public GeoPoint getLocationFromAddress(String strAddress){

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new GeoPoint((double) (location.getLatitude() * 1E6),
                    (double) (location.getLongitude() * 1E6));


        }catch (IOException e){
            e.printStackTrace();

        }

        return p1;
    }
}