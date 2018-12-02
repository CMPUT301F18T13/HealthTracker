package com.example.healthtracker;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.PatientRecord;
import com.example.healthtracker.EntityObjects.Problem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class search_results_problem extends Activity {

    private Object[] hits;
    private int index;

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
            Intent intent = new Intent(search_results_problem.this, search_result_comments.class);
            intent.putExtra("hits", UserDataController.serializeObjectArray(search_results_problem.this, hits));
            intent.putExtra("problemIndex", index);
            startActivity(intent);
        });

    }

    private String dateToString(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        return format.format(date);
    }

}
