package com.example.healthtracker.View;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.healthtracker.Contollers.ElasticsearchController;
import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.CareProviderComment;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.PatientRecord;
import com.example.healthtracker.EntityObjects.Problem;
import com.example.healthtracker.EntityObjects.search_results_problem;
import com.example.healthtracker.EntityObjects.search_results_record;

import com.example.healthtracker.R;

import java.util.ArrayList;

// SearchResultsView enables patients and careproviders to view the results of their search queries.
public class SearchResultsView extends AppCompatActivity {
    private Object[] hits;
    private ArrayList<Problem> userProblems;
    private ArrayList<PatientRecord> userRecords;
    private ArrayList<CareProviderComment> userComments;
    private ArrayList<Problem> foundProblems;
    private ArrayList<PatientRecord> foundRecords;
    private ArrayList<CareProviderComment> foundComments;
    private String profileType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        userComments = new ArrayList<CareProviderComment>();
        userProblems = new ArrayList<Problem>();
        userRecords = new ArrayList<PatientRecord>();
        Intent intent = getIntent();
        profileType = intent.getStringExtra("profileType");
        // Set the colour for the actionbar to differentiate current user type
        if(profileType.equals("CareProvider")){
            android.support.v7.app.ActionBar bar = getSupportActionBar();
            assert bar != null;
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
            ArrayList<Patient> patients = UserDataController
                    .loadCareProviderData(this)
                    .getPatientList();
            for(Patient patient: patients){
                userProblems.addAll(patient.getProblemList());
            }
            for(Problem problem: userProblems){
                userRecords.addAll(problem.getRecords());
                userComments.addAll(problem.getcaregiverRecords());
            }
        } else if(profileType.equals("Patient")){
            Patient patient = UserDataController.loadPatientData(this);
            userProblems = patient.getProblemList();
            for(Problem problem: userProblems){
                userRecords.addAll(problem.getRecords());
                userComments.addAll(problem.getcaregiverRecords());
            }
        }

        hits = UserDataController
                .unserializeObjectArray(this, getIntent().getStringExtra("hits"));

        foundProblems = (ArrayList<Problem>) hits[0];
        foundRecords = (ArrayList<PatientRecord>) hits[1];
        foundComments = (ArrayList<CareProviderComment>) hits[2];

        ArrayList<Problem> problemsToShow = new ArrayList<Problem>();
        ArrayList<PatientRecord> recordsToShow = new ArrayList<PatientRecord>();
        ArrayList<CareProviderComment> commentsToShow = new ArrayList<CareProviderComment>();

        for(Problem problem: foundProblems){
            if (userProblems.contains(problem)){
                problemsToShow.add(problem);
            }
        }

        for(PatientRecord record: foundRecords){
            if (userRecords.contains(record)){
                recordsToShow.add(record);
            }
        }

        for(CareProviderComment comment: foundComments){
            if (userComments.contains(comment)){
                commentsToShow.add(comment);
            }
        }

       ArrayAdapter<Problem> problemArrayAdapter =
                new ArrayAdapter<Problem>(this, android.R.layout.simple_list_item_1, problemsToShow);
        ArrayAdapter<PatientRecord> recordArrayAdapter =
                new ArrayAdapter<PatientRecord>(this, android.R.layout.simple_list_item_1, recordsToShow);
        ArrayAdapter<CareProviderComment> commentArrayAdapter =
                new ArrayAdapter<CareProviderComment>(this, android.R.layout.simple_list_item_1, commentsToShow);

        // Find the list views for each info type
        ListView problemList = findViewById(R.id.found_problems);
        ListView commentList = findViewById(R.id.found_comments);
        ListView recordList = findViewById(R.id.found_records);

        // Set the adapters for each information type
        problemList.setAdapter(problemArrayAdapter);
        commentList.setAdapter(commentArrayAdapter);
        recordList.setAdapter(recordArrayAdapter);

        // Load the list of problems gather by search into the adapter and show as selectable listView
        problemList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent1 = new Intent(SearchResultsView.this, search_results_problem.class);
            intent1.putExtra("problemIndex", position);
            intent1.putExtra("hits", UserDataController.serializeObjectArray(SearchResultsView.this, hits));
            startActivity(intent1);
        });

        // Load the list of records gathered by search into the adapter and show as selectable listView
        recordList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent12 = new Intent(SearchResultsView.this, search_results_record.class);
            intent12.putExtra("recordIndex", position);
            intent12.putExtra("hits", UserDataController.serializeObjectArray(SearchResultsView.this, hits));
            startActivity(intent12);
        });
    }

    // Form the CareProvider logo at the top right of the actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Intent intent = getIntent();
        profileType = intent.getStringExtra("profileType");
        if (profileType.equals("CareProvider")) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.actionbar, menu);
            return super.onCreateOptionsMenu(menu);
        } else {
            return false;
        }
    }
}