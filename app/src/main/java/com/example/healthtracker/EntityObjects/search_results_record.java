package com.example.healthtracker.EntityObjects;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthtracker.Contollers.UserDataController;

import com.example.healthtracker.R;

import java.util.ArrayList;

/**
 * search_results_record is the generated object from calls to the search activity when
 * a user attempts search functionality resulting in record hits matching the input parameters
 *
 * @author Michael Boisvert
 * @version 1.0
 * @since 2018-11-29
 */
public class search_results_record extends Activity {
    private PatientRecord record;

    /**
     * On creation or launch the record object is populated with the appropriate data to display
     * a records contents
     *
     * @param savedInstanceState related to bundle in onCreate
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_record_view);

        Object[] hits = UserDataController
                .unserializeObjectArray(this, getIntent().getStringExtra("hits"));

        ArrayList<PatientRecord> records = (ArrayList<PatientRecord>) hits[1];
        TextView titleText = findViewById(R.id.care_record_title);
        TextView desText = findViewById(R.id.care_record_comment);
        TextView timestampText = findViewById(R.id.care_record_timestamp);
        Bundle bd = getIntent().getExtras();
        assert bd != null;
        int patientNum = bd.getInt("patientNum");
        int problemNum = bd.getInt("problemNum");
        int recordNum = bd.getInt("recordNum");


        try {
            PatientRecord record = records.get(getIntent().getIntExtra("recordIndex", -1));

            desText.setText("Comment: \n" + record.getComment());
            titleText.setText("Title: \n" + record.getTitle());
            timestampText.setText("Timestamp: \n" + record.getTimestamp().toString());
        }
        catch (IndexOutOfBoundsException e){
            Toast.makeText(this, "This feature requires additional purchases.", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Sponsored by EA.", Toast.LENGTH_LONG).show();

            /*
            CareProvider careProvider = UserDataController.loadCareProviderData(this);
            record = careProvider
                    .getPatient(patientNum)
                    .getProblem(problemNum)
                    .getPatientRecord(recordNum);
            desText.setText("Comment: \n" + record.getComment());
            titleText.setText("Title: \n" + record.getTitle());
            timestampText.setText("Timestamp: \n" + record.getTimestamp().toString());*/
        }

    }
}