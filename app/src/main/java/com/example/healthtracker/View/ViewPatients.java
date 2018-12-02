package com.example.healthtracker.View;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.CareProvider;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.R;

import java.util.ArrayList;

/*
 * ViewPatients enables careproviders to view a list of all of their added patients. Careproviders can
 * select a patient from the list to then view that patient's problems by initiating the ViewPatients
 * problems activity.
 */
public class ViewPatients extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patients);

        ListView patientsListView = findViewById(R.id.patients_list_view);
        ArrayList<Patient> mPatients = new ArrayList<>();
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        // Fetch user data
        CareProvider careProvider = UserDataController.loadCareProviderData(this);

        // Fetch assigned patients
        if(careProvider.getPatientList().size() > 0) {
            mPatients.addAll(careProvider.getPatientList());

        }
        else{
            // Create an alert dialog
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ViewPatients.this);
            alertBuilder.setMessage("No Patients Assigned!");
            alertBuilder.setPositiveButton("OK",null);
            AlertDialog alertDialog = alertBuilder.create();
            alertDialog.show();
        }

        // Display all assigned patients

            // Create an instance of array adapter
        ArrayAdapter<Patient> mArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mPatients);
            // Combine the array adapter with the list view
        patientsListView.setAdapter(mArrayAdapter);


        // Event for selecting a Patient
        patientsListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(ViewPatients.this,ViewPatientsProblems.class);
            intent.putExtra("patientNum", position);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
