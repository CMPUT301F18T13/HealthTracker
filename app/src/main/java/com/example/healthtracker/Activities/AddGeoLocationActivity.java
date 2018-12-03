package com.example.healthtracker.Activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthtracker.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class AddGeoLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String CurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_geo_location);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /*
     * Search for a location by address.
     */
    public void onMapSearch(View view) {
        EditText locationSearch = findViewById(R.id.editText);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

        if (location != null || !location.equals("")) {
            try {
                Geocoder geocoder = new Geocoder(this);
                addressList = geocoder.getFromLocationName(location, 1);
                //Log.d("array",addressList.toString());
                if  (!addressList.isEmpty()){
                    Address address = addressList.get(0);
                    String city = address.getLocality();
                    String state = address.getAdminArea();
                    String country = address.getCountryName();
                    String postalCode = address.getPostalCode();
                    CurrentLocation = city+ " " + state + " " + country + " " + postalCode;
                    String Locality = address.getLocality();
                    Toast.makeText(getApplicationContext(),Locality,Toast.LENGTH_SHORT).show();
                    //LatLng LatLng = new LatLng(address.getLatitude(), address.getLongitude());
                    goToLocation(address.getLatitude(), address.getLongitude(),15);}
                else{
                    String no_address = "No address found";
                    Toast.makeText(getApplicationContext(),no_address,Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    @Override
    /*
     * Override the onMapReady method in order to set what will apear when the map is initiated.
     */
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        /*
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(27.700769, 85.300140);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marke"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);*/
        String location = "University of Alberta";
        List<Address> addressList = null;



        Geocoder geocoder = new Geocoder(this);
        try {
            addressList = geocoder.getFromLocationName(location,1);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Address address = addressList.get(0);
        String city = address.getLocality();
        String state = address.getAdminArea();
        String country = address.getCountryName();
        String postalCode = address.getPostalCode();
        CurrentLocation = city+ " " + state + " " + country + " " + postalCode;
        String Locality = address.getLocality();
        Toast.makeText(getApplicationContext(),Locality,Toast.LENGTH_SHORT).show();

        goToLocation(address.getLatitude(), address.getLongitude(),15);





        //goToLocation(27.700769, 85.300140, 15);
    }

    public void goToLocation(double latitude, double longitude, int zoom ){
        LatLng LatLng = new LatLng(latitude, longitude );
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LatLng,15);
        mMap.addMarker(new MarkerOptions().position(LatLng).title("Marker"));
        mMap.moveCamera(update);


    }

    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("result", CurrentLocation);
        AddGeoLocationActivity.this.setResult(0, intent);
        AddGeoLocationActivity.this.finish();
    }

    public void Save_current(View view){
        Intent intent = new Intent();
        intent.putExtra("result", CurrentLocation);
        AddGeoLocationActivity.this.setResult(RESULT_OK, intent);
        AddGeoLocationActivity.this.finish();
    }
}
