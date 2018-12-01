package com.example.healthtracker.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.R;
import com.example.healthtracker.Activities.SearchActivity;
import com.example.healthtracker.Activities.UserSettingsActivity;


/*
 * CareProviderHomeView acts as a home screen interface for CareProvider users to navigate to different app activities.
 *
 */
public class CareProviderHomeView extends AppCompatActivity {


    /*
     * onCreate launched on activity creation
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_careprovider_home);
    }

    /*
     * Method containing the new intent which will bring user to the search activity layout
     */
    public void Search(View view) {
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(CareProviderHomeView.this, SearchActivity.class);
        // Launch the browse emotions activity
        startActivity(intent);
    }

    /*
     * Method containing the new intent which will bring user to the add patient activity layout
     */
    public void AddPatient(View view) {
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(CareProviderHomeView.this, AddPatientView.class);
        // Launch the browse emotions activity
        startActivity(intent);
    }

    /*
     * finish's the current activity and returns the user the main login screen, logging out the user
     */
    public void Logout(View view){
        finish();
    }

    /*
     * Method containing the new intent which will bring user to the ViewPatients activity layout
     */
    public void ViewPatients(View view) {
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(CareProviderHomeView.this, ViewPatients.class);
        // Launch the browse emotions activity
        startActivity(intent);
    }

    /*
     * Method containing the new intent which will bring user to the user settings activity layout
     */
    public void Settings(View view) {
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(CareProviderHomeView.this, UserSettingsActivity.class);
        intent.putExtra("profileType", "CareProvider");
        // Launch the browse emotions activity
        startActivity(intent);
    }

    public void Sync(View view){
        UserDataController.syncCareProviderData(this);
    }

    /*
     * Method containing the new intent which will bring user to the geo map activity layout
     */
    public void careViewMap(View view) {
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(CareProviderHomeView.this, MapView.class);
        // Launch the browse emotions activity
        startActivity(intent);
    }
}