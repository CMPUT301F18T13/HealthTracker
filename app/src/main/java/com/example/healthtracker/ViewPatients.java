package com.example.healthtracker;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewPatients extends AppCompatActivity {

    private ListView patientsListView;
    private ArrayAdapter<Patient> mArrayAdapter;
    private Patient mPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patients);

        patientsListView = (ListView) findViewById(R.id.patients_list_view);
        ArrayList<Patient> mPatients = new ArrayList<Patient>();

        // Fetch user data
        CareProvider careProvider = UserDataController.loadCareProviderData(this);

        // Fetch assigned patients
        if(careProvider.getPatientList().size() > 0) {
            for (int i = 0; i < careProvider.getPatientList().size(); i++) {
                mPatient = careProvider.getPatientList().get(i);
                mPatients.add(mPatient);
            }

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
        mArrayAdapter = new ArrayAdapter<Patient>(this,android.R.layout.simple_list_item_1,mPatients);
            // Combine the array adapter with the list view
        patientsListView.setAdapter(mArrayAdapter);


        // Event for selecting a Patient
        patientsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ViewPatients.this,ViewPatientsProblems.class);
                intent.putExtra("patientNum", position);
                startActivity(intent);
            }
        });


    }
}
