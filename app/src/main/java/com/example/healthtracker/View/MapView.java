package com.example.healthtracker.View;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.CareProvider;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.PatientRecord;
import com.example.healthtracker.EntityObjects.Problem;
import com.example.healthtracker.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* Code for testing the functionality of google maps within our program reused from a
3-part tutorial from 
*https://www.viralandroid.com/2016/04/google-maps-android-api-getting-started-tutorial.html, Apr 24, 2016, viewed: Oct 23, 2018* & 
*https://www.viralandroid.com/2016/04/get-current-location-google-maps-android-api-tutorial.html, posted: Apr 24, 2016, viewed: Oct 23, 2018* & 
*https://www.viralandroid.com/2016/04/google-maps-android-api-adding-search-bar-part-3.html, 
posted: Apr 25, 2016, viewed: Oct 23, 2018*
*/


/*
 * MapView will allow Patients to view all of the geolocations associated with their problems in
 * a map. Careproviders can view all of the geolocations associated with their patients'
 * problems.
 */
public class MapView extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final ArrayList<PatientRecord> mRecords = new ArrayList<>();
    private String profileType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        profileType = getIntent().getStringExtra("profileType");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    private void createRecordArray(){
        if(profileType.equals("Patient")){
            Patient user = UserDataController.loadPatientData(this);
            ArrayList<Problem> mProblems = user.getProblemList();
            for (int counter = 0; counter < mProblems.size(); counter++) {
                for (int counter_1 = 0; counter_1 < mProblems.get(counter).countRecords(); counter_1++) {
                    if(mProblems.get(counter).getRecords().get(counter_1) != null){
                        mRecords.add(mProblems.get(counter).getRecords().get(counter_1));
                    }
                }
            }
        } else{
            CareProvider user = UserDataController.loadCareProviderData(this);
            ArrayList<Patient> mPatients = user.getPatientList();
            for(Patient patient: mPatients){
                for(Problem problem: patient.getProblemList()){
                    for(PatientRecord record: problem.getRecords()){
                        mRecords.add(record);
                    }
                }
            }
        }

    }


    @Override
    /*
     * Override the onMapReady method in order to set what will apear when the map is initiated.
     */
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        List<Address> addressList = null;
        Double Lat;
        Double Lon;
        String CurrentLocation;
        createRecordArray();
        for (int counter = 0; counter < mRecords.size(); counter++) {
            if(mRecords.get(counter).getGeoLocation().size() != 0) {
                Lon = mRecords.get(counter).getGeoLocation().get(0);
                Lat =  mRecords.get(counter).getGeoLocation().get(1);
                if(Lat == null || Lon == null){
                    continue;
                }
                if(Lat <= -90 || Lon <= -180){
                    continue;
                }
                LatLng LatLng = new LatLng(Lat, Lon);
                Geocoder geocoder = new Geocoder(this);
                try {
                    addressList = geocoder.getFromLocation(Lat, Lon,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Address address = addressList.get(0);
                String city = address.getLocality();
                String state = address.getAdminArea();
                String country = address.getCountryName();
                String postalCode = address.getPostalCode();
                CurrentLocation = city+ " " + state + " " + country + " " + postalCode;
                mMap.addMarker(new MarkerOptions().position(LatLng).title(CurrentLocation));
            }
        }


        String location = "University of Alberta";
        List<Address> addressList_1 = null;



        Geocoder geocoder = new Geocoder(this);
        try {
            addressList_1 = geocoder.getFromLocationName(location,1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(addressList_1 != null) {
            Address address = addressList_1.get(0);
            String city = address.getLocality();
            String state = address.getAdminArea();
            String country = address.getCountryName();
            String postalCode = address.getPostalCode();
            CurrentLocation = city + " " + state + " " + country + " " + postalCode;
            Toast.makeText(getApplicationContext(), CurrentLocation, Toast.LENGTH_SHORT).show();

            goToLocation(address.getLatitude(), address.getLongitude(), 15);
        }
    }

    private void goToLocation(double latitude, double longitude, int zoom){
        LatLng LatLng = new LatLng(latitude, longitude );
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LatLng,zoom);
        //mMap.addMarker(new MarkerOptions().position(LatLng).title("Marker"));
        mMap.moveCamera(update);


    }
}
