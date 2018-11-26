package com.example.healthtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

/**
 * SearchActivity will enable patients and careproviders to search for problems and records.
 */
public class SearchActivity extends AppCompatActivity {

    private Spinner spinner;
    private TextView keywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        keywords = findViewById(R.id.keywords);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void Search(View view) {
        List<Problem> problems = UserDataController.searchForProblem("ProblemTitle", keywords.getText().toString());
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(SearchActivity.this, SearchResultsView.class);
        // Launch the browse emotions activity
        startActivity(intent);
    }
}
