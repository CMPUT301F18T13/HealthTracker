package com.example.healthtracker.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.PatientRecord;
import com.example.healthtracker.R;
import com.example.healthtracker.Activities.TakePhotoActivity;

import java.io.File;

/*
 * AddorEditRecordView enables a patient to add a new record to one of their problems or edit an
 * existing record. This activity will finish with a positive result code if a record was successfully
 * saved. The current Record data will be displayed if a record is being edited. The new
 * or changed record can be saved by selecting the save button.
 *
 */
public class AddorEditRecordView extends AppCompatActivity {

    private EditText titleText, descriptionText;
    private TextView timestampText;
    private String title, comment;
    private Context context;
    private Patient patient;
    String problemTitle;
    File capturedImages;
    private PatientRecord record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addor_edit_record);
        titleText = findViewById(R.id.title_edit_text);
        descriptionText = findViewById(R.id.description_edit_text);
        timestampText = findViewById(R.id.patient_record_timestamp);
        context = this;
        record = new PatientRecord();
        getExtraString();

        // if editing a record show its current details
        Intent intent = getIntent();
        int index = intent.getIntExtra("Index", -1);
        if (index != -1) {
            String recordString = intent.getStringExtra("Record");
            record = UserDataController.unSerializeRecord(this, recordString);
            showRecord();
        } else{
            record.setTimestamp();
            timestampText.setText(record.getTimestamp().toString());
        }
    }

    @Override
    /*
     * Overrides the android back button in order to warn user that their record will not be saved.
     * Also sets the result state to RESULT_CANCELED so that the previous activity can determine no
     * record has been added or edited.
     */
    public void onBackPressed() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setMessage("Warning. You are about to go back without saving the record.");
        ab.setCancelable(true);
        // Set a button to return to the Home screen and don't save changes
        ab.setNeutralButton("Exit And Lose Record", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = getIntent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        // set a button which will close the alert dialog
        ab.setNegativeButton("Return to Record", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        // show the alert dialog on the screen
        ab.show();
    }

    /*
     * When clicked the save button will save or update the record as long as the record at least
     * has a title.
     */
    public void saveButton(View view) {
        if (titleText.getText().toString().equals("")) {
            Toast.makeText(this, "Error, a title is required.", Toast.LENGTH_LONG).show();
        } else {
            saveRecord();
        }
    }

    /*
     * If a record is being edited this method is called to display its current data.
     */
    private void showRecord(){
        titleText.setText(record.getTitle());
        descriptionText.setText(record.getComment());
        timestampText.setText(record.getTimestamp().toString());
        //TODO show geomap, photos, bodlocation
    }

    /*
     * Update record with user entered data.
     * Save record by serializing it and setting it as the result of this activity.
     */
    private void saveRecord(){
        // get Record info
        title = titleText.getText().toString();
        comment = descriptionText.getText().toString();

        // fetch user data
        patient = UserDataController.loadPatientData(context);

        // add record
        record.setComment(comment);
        record.setTitle(title);

        // TODO set photos, geomap, bodylocation once they are implemented

        // return to add problem with record result
        Intent intent = getIntent();
        intent.putExtra("Record", UserDataController
                .serializeRecord(AddorEditRecordView.this, record));
        setResult(RESULT_OK, intent);
        finish();
    }

    public void addPhoto(View view) {
        Intent intent = new Intent(AddorEditRecordView.this, TakePhotoActivity.class);
        intent.putExtra(getExtraString(),"Problem Title");
        startActivity(intent);
    }

    public String getExtraString(){
        problemTitle= getIntent().getStringExtra("ProblemTitle");
        return problemTitle;
    }
}
