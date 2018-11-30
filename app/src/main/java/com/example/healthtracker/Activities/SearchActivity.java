package com.example.healthtracker.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.example.healthtracker.R;
import com.example.healthtracker.View.SearchResultsView;
import com.example.healthtracker.View.CareProviderHomeView;

/**
 * SearchActivity will enable patients and careproviders to search for problems and records.
 */
public class SearchActivity extends AppCompatActivity {

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void Return3(View view) {
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(SearchActivity.this, CareProviderHomeView.class);
        // Launch the browse emotions activity
        startActivity(intent);
    }

    public void Search(View view) {
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(SearchActivity.this, SearchResultsView.class);
        // Launch the browse emotions activity
        startActivity(intent);
    }
}
