package com.example.healthtracker.View;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.CareProvider;
import com.example.healthtracker.EntityObjects.PatientRecord;
import com.example.healthtracker.R;

public class CareProviderRecordView extends Activity {
    private TextView titleText;
    private TextView desText;
    private TextView timestampText;
    private PatientRecord record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_provider_record_view);
        titleText = findViewById(R.id.care_record_title);
        desText = findViewById(R.id.care_record_comment);
        timestampText = findViewById(R.id.care_record_timestamp);
        //ActionBar bar = getActionBar();
        //assert bar != null;
        //bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));


        Bundle bd = getIntent().getExtras();
        assert bd != null;
        int patientNum = bd.getInt("patientNum");
        int problemNum = bd.getInt("problemNum");
        int recordNum = bd.getInt("recordNum");

        CareProvider careProvider = UserDataController.loadCareProviderData(this);
        record = careProvider
                .getPatient(patientNum)
                .getProblem(problemNum)
                .getPatientRecord(recordNum);
        displayRecord();
    }

    // Set the text fields
    @SuppressLint("SetTextI18n")
    private void displayRecord(){
        desText.setText("Comment: \n" + record.getComment());
        titleText.setText("Title: \n" + record.getTitle());
        timestampText.setText("Timestamp: \n" + record.getTimestamp().toString());
    }

    // Form the CareProvider logo at the top right of the actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
