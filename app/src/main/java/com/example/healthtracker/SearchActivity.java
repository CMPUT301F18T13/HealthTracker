package com.example.healthtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.healthtracker.Contollers.UserDataController;

/**
 * SearchActivity will enable patients and careproviders to search for problems and records.
 */
public class SearchActivity extends AppCompatActivity {

    private String searchType;
    private EditText keywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Spinner spinner = findViewById(R.id.search_type_dropdown);
        keywords = findViewById(R.id.search_terms);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position == 0){
                    searchType = "keyword";
                } else if(position == 1){
                    searchType = "geoLoaction";
                } else{
                    searchType = "bodyLocation";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                searchType = "keyword";

            }

        });
    }

    public void Search(View view) {
        Object[] hits = null;
        switch (searchType) {
            case "keyword":
                hits = UserDataController.searchForKeywords(keywords.getText().toString());
                break;
            case "geoLocation":

                break;
            case "bodyLocation":

                break;
        }
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(SearchActivity.this, SearchResultsView.class);
        intent.putExtra("hits", UserDataController.serializeObjectArray(this, hits));

        // Launch the browse activity
        startActivity(intent);
    }
}