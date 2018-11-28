package com.example.healthtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

/**
 * SearchActivity will enable patients and careproviders to search for problems and records.
 */
public class SearchActivity extends AppCompatActivity {

    private String searchType;
    private Spinner spinner;
    private EditText keywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        spinner = findViewById(R.id.search_type_dropdown);
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
        if(searchType.equals("keyword")){
            hits = UserDataController.searchForKeywords(keywords.getText().toString());
        } else if(searchType.equals("geoLocation")){

        } else if(searchType.equals("bodyLocation")){

        }
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(SearchActivity.this, SearchResultsView.class);
        intent.putExtra("hits", UserDataController.serializeObjectArray(this, hits));

        // Launch the browse activity
        startActivity(intent);
    }
}