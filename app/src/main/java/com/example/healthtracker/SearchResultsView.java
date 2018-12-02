package com.example.healthtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.CareProviderComment;
import com.example.healthtracker.EntityObjects.PatientRecord;
import com.example.healthtracker.EntityObjects.Problem;

import java.util.ArrayList;

/**
 * SearchResultsView enables patients and careproviders to view the results of their search queries.
 */
public class SearchResultsView extends AppCompatActivity {
    private Object[] hits;
    private ArrayList<Problem> problems;
    private ArrayList<PatientRecord> records;
    private ArrayList<CareProviderComment> comments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        hits = UserDataController
                .unserializeObjectArray(this, getIntent().getStringExtra("hits"));

        problems = (ArrayList<Problem>) hits[0];
        records = (ArrayList<PatientRecord>) hits[1];
        comments = (ArrayList<CareProviderComment>) hits[2];

        ArrayAdapter<Problem> problemArrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, problems);
        ArrayAdapter<PatientRecord> recordArrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, records);
        ArrayAdapter<CareProviderComment> commentArrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, comments);

        ListView problemList = findViewById(R.id.found_problems);
        ListView commentList = findViewById(R.id.found_comments);
        ListView recordList = findViewById(R.id.found_records);

        problemList.setAdapter(problemArrayAdapter);
        commentList.setAdapter(commentArrayAdapter);
        recordList.setAdapter(recordArrayAdapter);

        problemList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(SearchResultsView.this, search_results_problem.class);
            intent.putExtra("problemIndex", position);
            intent.putExtra("hits", UserDataController.serializeObjectArray(SearchResultsView.this, hits));
            startActivity(intent);
        });

        recordList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(SearchResultsView.this, search_results_record.class);
            intent.putExtra("recordIndex", position);
            intent.putExtra("hits", UserDataController.serializeObjectArray(SearchResultsView.this, hits));
            startActivity(intent);
        });

    }
}