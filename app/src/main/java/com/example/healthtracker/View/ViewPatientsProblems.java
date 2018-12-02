package com.example.healthtracker.View;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.CareProvider;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.Problem;
import com.example.healthtracker.R;

import java.util.ArrayList;

/*
 * ViewPatientsProblems enable careproviders to view a list of all of the problems belonging to a particular
 * patient. Careproviders can view more details of the problems including their records by selecting
 * any of the problems from the list.
 */
public class ViewPatientsProblems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patients_problems);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        // get patient#
        Intent intent = getIntent();
        CareProvider careProvider = UserDataController.loadCareProviderData(this);
        int patientNum = intent.getIntExtra("patientNum", -1);
        Patient myPatient = careProvider.getPatientList().get(patientNum);

        //Load patient data
        ArrayList<Problem> pProblems = myPatient.getProblemList();
        TextView idText = findViewById(R.id.selected_patient_ID);
        TextView phoneText = findViewById(R.id.patient_phone);
        TextView emailText = findViewById(R.id.patient_email);

        String patientID = myPatient.getUserID();
        idText.setText("Patient: " + patientID);
        phoneText.setText("Patient Phone Number: " + myPatient.getPhone());
        emailText.setText("Patient Email Address: " + myPatient.getEmail());

        // Create an instance of an array adapter
        ArrayAdapter<Problem> pArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pProblems);

        // Set an adapter for the list view
        ListView pListView = findViewById(R.id.pProblem_list_view);
        pListView.setAdapter(pArrayAdapter);

        // Select a PatientProblem to view
        pListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent1 = new Intent(ViewPatientsProblems.this,CareProviderProblemView.class);

            Bundle bd = new Bundle();
            bd.putInt("patientNum", patientNum);
            bd.putInt("problemNum", position);
            intent1.putExtras(bd);


            startActivity(intent1);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
