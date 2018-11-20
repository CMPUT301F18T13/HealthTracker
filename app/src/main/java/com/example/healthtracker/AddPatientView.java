package com.example.healthtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * AddPatientView enables a CareProvider to add a patient to their patient list by entering their
 * user ID. A user ID is entered into a textview and a button is pressed to add that patient.
 * Shows a toast message to indicate if the patient was successfully added.
 *
 * @author Chenlin
 * @version 1.0
 * @since 2018-11-18
 */
public class AddPatientView extends AppCompatActivity {

    private ArrayList<Patient> patients;
    private Context context;
    private EditText inputId;
    private Patient mPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        // Get context
        context = this;

        inputId = (EditText) findViewById(R.id.editText4);

    }

    private Boolean ValidatePatientId(){
        // Read user input
        String patientId = inputId.getText().toString();
        boolean validID = false;

        // Read from the elastic search database, obtain a list of registered patient IDs
        ElasticsearchController.getAllPatients getAllMyPatients  = new ElasticsearchController.getAllPatients();
        getAllMyPatients.execute();

        try {
            patients = getAllMyPatients.get();
            System.out.println(patients);

            // Check whether the patient ID exists
            for(int i=0;i<patients.size();i++){
                if(patientId.equals(patients.get(i).getUserID())){
                    validID = true;
                    mPatient = patients.get(i);
                }
            }

            if (ElasticsearchController.testConnection(this)){
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddPatientView.this);
                alertBuilder.setMessage("Please connect to a network to add a patient.");
                alertBuilder.setPositiveButton("OK",null);
                AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();
                return false;
            } else if(!validID){
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddPatientView.this);
                alertBuilder.setMessage("The patient ID is not valid. Please try again.");
                alertBuilder.setPositiveButton("OK",null);
                AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();
                return false;
            }
        }catch (ExecutionException e1){
            Log.i("error","execution exception");
        }catch (InterruptedException e2){
            Log.i("error","interrupted exception");
        }

        // If valid.
        if (validID) {

            // Fetch user data (Care Provider)
            CareProvider careProvider = UserDataController.loadCareProviderData(this);

            // Fetch user data (Patient)
            String mPatientUserID = mPatient.getUserID();
            ElasticsearchController.GetPatient getPatient = new ElasticsearchController.GetPatient();
            getPatient.execute(mPatientUserID);
            try{
                mPatient = getPatient.get();
            }catch (ExecutionException e1){

            }catch (InterruptedException e2) {

            }

            System.out.println("reaches here!");
            // Check whether a patient is assigned to the same care provider twice
            Boolean careProviderExist = false;
            if(mPatient.getCareProviderString().contains(careProvider.getUserID())){
                careProviderExist = true;
            }


            System.out.println("careProviderExist"+careProviderExist);
            if (careProviderExist == false) {

                // Update Patient data
                mPatient.addToCareProviderString(careProvider.getUserID());
                UserDataController.savePatientData(this, mPatient);

                // Update Care Provider data
                careProvider.addPatient(mPatient);
                UserDataController.saveCareProviderData(this, careProvider);
                return true;
            }
            else{
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddPatientView.this);
                alertBuilder.setMessage("This patient has already been assigned you.");
                alertBuilder.setPositiveButton("OK", null);
                AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();
                return false;
            }

        }
        return true;

    }

    /**
     *If the user has entered a valid Patient ID then clicking the save button will add that patient
     *to the CareProviders patient list.
     *
     * @param view The view for the layout included for onClick methods in XML
     */
    public void Add(View view) {
        if(ValidatePatientId()) {
            Toast.makeText(this, "Patient Added", Toast.LENGTH_SHORT).show();
            // Create an intent object containing the bridge to between the two activities
            Intent intent = new Intent(AddPatientView.this, CareProviderHomeView.class);
            // Launch the browse emotions activity
            startActivity(intent);
        }
    }
}
