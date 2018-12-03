package com.example.healthtracker.View;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthtracker.EntityObjects.CareProviderComment;
import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.CareProvider;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.Problem;
import com.example.healthtracker.R;

import java.util.ArrayList;

/*
 * AddCareProviderCommentView enables a CareProvider to create a comment record that will be added
 * to a patients problem. The patient and problem will have been selected before starting this
 * activity. The careprovider can fill in a title and comment for the record in textviews and save the
 * record by selecting the save button.
 */
public class AddCareProviderCommentView extends AppCompatActivity {
    private CareProviderComment newComment;
    private ArrayList<CareProviderComment> careProviderComments;
    private CareProvider careProvider;
    private Patient myPatient;
    private Problem pProblem;
    private int patientNum;
    private int problemNum;
    private EditText commentText;
    private EditText titleText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_care_provider_comment);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        careProvider = UserDataController.loadCareProviderData(this);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        assert bd != null;
        patientNum = bd.getInt("patientNum");
        problemNum = bd.getInt("problemNum");
        myPatient = careProvider.getPatientList().get(patientNum);
        pProblem = myPatient.getProblem(problemNum);
        careProviderComments = pProblem.getcaregiverRecords();
        if (careProviderComments == null){
            careProviderComments = new ArrayList<>();
        }
        newComment = new CareProviderComment();
        titleText = findViewById(R.id.care_comment_title);
        commentText = findViewById(R.id.care_comment);
    }

    // Handle a back button selection event after edit text fields are modified so user is alerted they may lose data
    @Override
    public void onBackPressed() {
        // Create an intent object containing the bridge to between the two activities
        if (!titleText.getText().toString().equals("") || !commentText.getText().toString().equals("")) {
            AlertDialog.Builder ab = new AlertDialog.Builder(AddCareProviderCommentView.this);
            ab.setMessage("Warning. Changes have been made to the comment." + "\n" + "Returning to the home screen will not save changes.");
            ab.setCancelable(true);
            // Set a button to return to the Home screen and don't save changes
            ab.setNeutralButton("Exit And Lose Changes", (dialog, which) -> finish());

            // set a button which will close the alert dialog
            ab.setNegativeButton("Return to Comment", (dialog, which) -> {
            });
            // show the alert dialog on the screen
            ab.show();
        } else {
            finish();
        }
    }

    // Save the comment record created by a care provider to be assigned to a patient's problem
    public void saveCareProviderComment(View view) {

        // get data
        String title = titleText.getText().toString();
        String comment = commentText.getText().toString();

        // set data
        newComment.setTitle(title);
        newComment.setComment(comment);
        careProviderComments.add(newComment);
        pProblem.setCaregiverRecords(careProviderComments);
        myPatient.setProblem(pProblem, problemNum);
        careProvider.setPatient(myPatient, patientNum);

        // notify user
        Toast.makeText(this, "New Comment Added", Toast.LENGTH_LONG).show();

        // save data
        UserDataController.saveCareProviderComments(this, myPatient, patientNum);

        // done
        finish();
    }

    // Load the icon for the CareProvider view
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
