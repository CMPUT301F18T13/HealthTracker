package com.example.healthtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SearchResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
    }

    public void Return4(View view) {
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(SearchResults.this, SearchActivity.class);
        // Launch the browse emotions activity
        startActivity(intent);
    }
}