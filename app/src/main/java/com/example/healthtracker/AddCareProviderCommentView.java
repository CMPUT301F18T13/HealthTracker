package com.example.healthtracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
    private String title;
    private String comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_care_provider_comment);

        careProvider = UserDataController.loadCareProviderData(this);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        patientNum = bd.getInt("patientNum");
        problemNum = bd.getInt("problemNum");
        myPatient = careProvider.getPatientList().get(patientNum);
        pProblem = myPatient.getProblem(problemNum);


        careProviderComments = pProblem.getcaregiverRecords();
        if (careProviderComments == null){
            careProviderComments = new ArrayList<CareProviderComment>();
        }

        newComment = new CareProviderComment();


        titleText = findViewById(R.id.care_comment_title);
        commentText = findViewById(R.id.care_comment);


    }

    @Override
    public void onBackPressed() {
        // Create an intent object containing the bridge to between the two activities
        if (!titleText.getText().toString().equals("") || !commentText.getText().toString().equals("")) {
            AlertDialog.Builder ab = new AlertDialog.Builder(AddCareProviderCommentView.this);
            ab.setMessage("Warning. Changes have been made to the comment." + "\n" + "Returning to the home screen will not save changes.");
            ab.setCancelable(true);
            // Set a button to return to the Home screen and don't save changes
            ab.setNeutralButton("Exit And Lose Changes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            // set a button which will close the alert dialog
            ab.setNegativeButton("Return to Comment", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            // show the alert dialog on the screen
            ab.show();
        } else {
            finish();
        }
    }

    public void saveCareProviderComment(View view) {

        // get data
        title = titleText.getText().toString();
        comment = commentText.getText().toString();

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
}
