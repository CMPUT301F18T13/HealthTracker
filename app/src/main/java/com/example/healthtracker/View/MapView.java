package com.example.healthtracker.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;

import com.example.healthtracker.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    /*
     * Search for a location by address.
     */
    public void onMapSearch(View view) {
        EditText locationSearch = findViewById(R.id.editText);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

        Geocoder geocoder = new Geocoder(this);
        try {
            addressList = geocoder.getFromLocationName(location, 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        assert addressList != null;
        Address address = addressList.get(0);
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    /*
     * Override the onMapReady method in order to set what will apear when the map is initiated.
     */
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(27.700769, 85.300140);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marke"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);
    }
}
