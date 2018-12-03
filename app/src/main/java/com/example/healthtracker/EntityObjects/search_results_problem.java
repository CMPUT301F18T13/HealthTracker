package com.example.healthtracker.EntityObjects;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.R;

import java.util.ArrayList;

/**
 * search_results_problem is the generated object from calls to the search activity when
 * a user attempts search functionality resulting in problem hits matching the input parameters
 *
 * @author Michael Boisvert
 * @version 1.0
 * @since 2018-11-29
 */
public class search_results_problem extends Activity {

    private Object[] hits;
    private int index;

    /**
     * On creation or launch the Problem object is populated with the appropriate data to display
     * all problems associated with the user inputted search parameters
     *
     * @param savedInstanceState related to bundle in onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_problem);

        hits = UserDataController
                .unserializeObjectArray(this, getIntent().getStringExtra("hits"));
        index = getIntent().getIntExtra("problemIndex", -1);

        ArrayList<Problem> problems = (ArrayList<Problem>) hits[0];
        Problem problem = problems.get(index);

        ArrayList<PatientRecord> records = problem.getRecords();

        String title = problem.getTitle();
        String date = problem.getDate();
        String des = problem.getDescription();

        TextView titleText = findViewById(R.id.titleView);
        TextView dateText = findViewById(R.id.dateView);
        TextView desText = findViewById(R.id.desView);
        ListView recordList = findViewById(R.id.care_record_list);

        titleText.setText(title);
        dateText.setText(date);
        desText.setText(des);

        ArrayAdapter<PatientRecord> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, records);
        recordList.setAdapter(adapter);


        // Add listener to detect button click on items in listview
        // method to initiate after listener detects click
        recordList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(search_results_problem.this, search_results_record.class);
            intent.putExtra("hits", UserDataController.serializeObjectArray(search_results_problem.this, hits));
            intent.putExtra("recordIndex", position);
            startActivity(intent);
        });

        Button view_comments = findViewById(R.id.search_problem_to_comments);

        view_comments.setOnClickListener(v -> {
            Intent intent = new Intent(search_results_problem.this, search_results_comments.class);
            intent.putExtra("hits", UserDataController.serializeObjectArray(search_results_problem.this, hits));
            intent.putExtra("problemIndex", index);
            startActivity(intent);
        });
    }
}
