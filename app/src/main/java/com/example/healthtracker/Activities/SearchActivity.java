package com.example.healthtracker.Activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;


import org.elasticsearch.common.geo.GeoPoint;

import java.io.IOException;
import java.util.List;

import com.example.healthtracker.Contollers.UserDataController;
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
                System.out.println("I am here aaaaaaaaaaaa");
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
        if(searchType.equals("keyword")){
            hits = UserDataController.searchForKeywords(keywords.getText().toString());
        } else if(searchType.equals("geoLocation")){

            /*
            System.out.println("Reaches here!!!!!!!!!!!!!!!!");
            System.out.println("The address is "+keywords.getText().toString());
            */
            Double latitude = getLocationFromAddress(keywords.getText().toString()).getLat() / 1E6;
            Double longitude = getLocationFromAddress(keywords.getText().toString()).getLon() / 1E6;

            /*
            System.out.println("The latitude is "+latitude.toString());
            System.out.println("The longitude is "+longitude.toString());
            System.out.println("The distance is "+distance.getText().toString());
            */

            hits = UserDataController.searchForGeoLocations(distance.getText().toString(),latitude,longitude);

        } else if(searchType.equals("bodyLocation")){

        }
        /*
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(SearchActivity.this, SearchResultsView.class);
        intent.putExtra("hits", UserDataController.serializeObjectArray(this, hits));

        // Launch the browse activity
        startActivity(intent);
        */
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