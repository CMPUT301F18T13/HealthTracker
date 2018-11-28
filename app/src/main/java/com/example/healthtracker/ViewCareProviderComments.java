package com.example.healthtracker;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewCareProviderComments extends Activity {

    Patient patient;
    CareProvider careProvider;
    ArrayList<CareProviderComment> comments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_care_provider_comments);
        comments = new ArrayList<CareProviderComment>();

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        String profileType = bd.getString("profileType");
        int problemNum = bd.getInt("problemNum");

        if(profileType.equals("Patient")){
            patient = UserDataController.loadPatientData(this);
            comments = patient.getProblem(problemNum).getcaregiverRecords();
        } else{
            careProvider = UserDataController.loadCareProviderData(this);
            int patientNum = bd.getInt("patientNum");
            comments = careProvider.
                    getPatient(patientNum).
                    getProblem(problemNum).
                    getcaregiverRecords();
        }

        ArrayAdapter adapter = new ArrayAdapter<CareProviderComment>
                (this, android.R.layout.simple_list_item_1, comments);

        ListView list = findViewById(R.id.comment_list);
        list.setAdapter(adapter);
    }

}
