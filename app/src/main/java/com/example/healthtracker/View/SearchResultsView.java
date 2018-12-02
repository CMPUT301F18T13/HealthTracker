package com.example.healthtracker.View;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.CareProviderComment;
import com.example.healthtracker.EntityObjects.PatientRecord;
import com.example.healthtracker.EntityObjects.Problem;
import com.example.healthtracker.EntityObjects.search_results_problem;
import com.example.healthtracker.EntityObjects.search_results_record;
import com.example.healthtracker.R;

import java.util.ArrayList;

/**
 * SearchResultsView enables patients and careproviders to view the results of their search queries.
 */
public class SearchResultsView extends AppCompatActivity {
    Object[] hits;
    ArrayList<Problem> problems;
    ArrayList<PatientRecord> records;
    ArrayList<CareProviderComment> comments;

    private String profileType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Intent intent = getIntent();
        profileType = intent.getStringExtra("profileType");
        // Set the colour for the actionbar to differentiate current user type
        if(profileType.equals("CareProvider")){
            android.support.v7.app.ActionBar bar = getSupportActionBar();
            assert bar != null;
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        }

        hits = UserDataController
                .unserializeObjectArray(this, getIntent().getStringExtra("hits"));

        problems = (ArrayList<Problem>) hits[0];
        records = (ArrayList<PatientRecord>) hits[1];
        comments = (ArrayList<CareProviderComment>) hits[2];

        ArrayAdapter<Problem> problemArrayAdapter =
                new ArrayAdapter<Problem>(this, android.R.layout.simple_list_item_1, problems);
        ArrayAdapter<PatientRecord> recordArrayAdapter =
                new ArrayAdapter<PatientRecord>(this, android.R.layout.simple_list_item_1, records);
        ArrayAdapter<CareProviderComment> commentArrayAdapter =
                new ArrayAdapter<CareProviderComment>(this, android.R.layout.simple_list_item_1, comments);

        ListView problemList = findViewById(R.id.found_problems);
        ListView commentList = findViewById(R.id.found_comments);
        ListView recordList = findViewById(R.id.found_records);

        problemList.setAdapter(problemArrayAdapter);
        commentList.setAdapter(commentArrayAdapter);
        recordList.setAdapter(recordArrayAdapter);

        problemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchResultsView.this, search_results_problem.class);
                intent.putExtra("problemIndex", position);
                intent.putExtra("hits", UserDataController.serializeObjectArray(SearchResultsView.this, hits));
                startActivity(intent);
            }
        });

        recordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchResultsView.this, search_results_record.class);
                intent.putExtra("recordIndex", position);
                intent.putExtra("hits", UserDataController.serializeObjectArray(SearchResultsView.this, hits));
                startActivity(intent);
            }
        });
    }

    // Load the icon for the CareProvider view
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