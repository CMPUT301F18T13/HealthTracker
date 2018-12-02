package com.example.healthtracker;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.PatientRecord;

import java.util.ArrayList;

public class search_results_record extends Activity {

    private Object[] hits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_record_view);

        hits = UserDataController
                .unserializeObjectArray(this, getIntent().getStringExtra("hits"));

        ArrayList<PatientRecord> records = (ArrayList<PatientRecord>) hits[1];
        PatientRecord record = records.get(getIntent().getIntExtra("recordIndex", -1));

        TextView titleText = findViewById(R.id.care_record_title);
        TextView desText = findViewById(R.id.care_record_comment);
        TextView timestampText = findViewById(R.id.care_record_timestamp);

        desText.setText("Comment: \n" + record.getComment());
        titleText.setText("Title: \n" + record.getTitle());
        timestampText.setText("Timestamp: \n" + record.getTimestamp().toString());

    }

}
