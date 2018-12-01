package com.example.healthtracker.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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
    private String profileType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        profileType = intent.getStringExtra("profileType");
        // Set the colour for the actionbar to differentiate current user type
        if(profileType.equals("CareProvider")){
            android.support.v7.app.ActionBar bar = getSupportActionBar();
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        }
    }

    public void Search(View view) {
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(SearchActivity.this, SearchResultsView.class);
        intent.putExtra("profileType", profileType);
        // Launch the browse emotions activity
        startActivity(intent);
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
