package com.example.healthtracker.Activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthtracker.Contollers.PhotoController;
import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.Problem;
import com.example.healthtracker.EntityObjects.PatientRecord;
import com.example.healthtracker.R;
import com.example.healthtracker.View.AddorEditRecordView;
import com.example.healthtracker.View.ViewCareProviderComments;

import java.util.ArrayList;
import java.util.Calendar;

/* Idea and code implementation knowledge for the date picker from the tutorial at,
url: https://www.youtube.com/watch?v=-mJmScTAWyQ 
Author: Atif Pervaiz 
posted: 2018/01/05 
viewed: 2018/11/29
*/

/*
 * EditProblem enables a patient to alter the details of one of their problems and select any PatientRecord
 * associated with it to edit. The altered problem is saved by selecting the "save" button.
 */
public class EditProblem extends AppCompatActivity {

    private EditText titleText;
    private EditText dateText;
    private EditText descriptionText;
    private String title;
    private String description;
    private String initial_entry;
    private Patient user;
    private Problem problem;
    private String initialTitle;
    private int probIndex;
    private int recordIndex;
    private ArrayList<PatientRecord> recordList;
    private ArrayAdapter<PatientRecord> adapter;
    private TextView pickedDate;

    @SuppressLint("Assert")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_problem);

        titleText = findViewById(R.id.title_text_editscreen);
        String initialTitle = titleText.getText().toString();
        //dateText = findViewById(R.id.date_started_editscreen);
        descriptionText = findViewById(R.id.problem_description_editscreen);
        Context context = this;

        // get current problem data
        user = UserDataController.loadPatientData(this);
        Intent intent = getIntent();
        probIndex = intent.getIntExtra("Index", -1);
        assert probIndex >= 0;
        problem = user.getProblem(probIndex);
        recordList = problem.getRecords();
        Button datePickButton = findViewById(R.id.pickDateButton);
        pickedDate = findViewById(R.id.pickedDate);

        // display current data
        displayData();

        initialTitle = problem.getTitle();

        // initial entry to check if changes have been made
        initial_entry = getEntry();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Create an instance of an array adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recordList);

        // Set an adapter for the list view
        ListView mListView = findViewById(R.id.record_list_editscreen);
        mListView.setAdapter(adapter);

        // Create a context menu to permit users to select and edit a problem
        registerForContextMenu(mListView);
        mListView.setOnCreateContextMenuListener(this);

        // Add listener to detect button click on items in listview
        // method to initiate after listener detects click
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            // create an alert dialog via the alert dialog builder to help build dialog to specifics
            AlertDialog.Builder ab = new AlertDialog.Builder(EditProblem.this);
            // set dialog message to edit entry to appear at grabbed position
            ab.setMessage("Record Options:" + recordList.get(position).getTitle() + "\n");
            // set the dialog to be cancelable outside of box
            ab.setCancelable(true);


            // set a negative button for deleting records
            ab.setPositiveButton("Delete", (dialog, which) -> {
                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                PhotoController.removePhotosFromInternalStorage(cw, problem.getTitle(), recordList.get(position).getTitle());
                // delete record
                recordList.remove(position);

                // update listview
                adapter.notifyDataSetChanged();

                // done
                dialog.dismiss();
            });

            ab.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            // set a neutral button in the dialog which will open up the edit activity to modify the record
            ab.setNeutralButton("Edit/View", (dialog, which) -> {
                // Create an intent object containing the bridge to between the two activities
                Intent intent = new Intent(EditProblem.this, AddorEditRecordView.class);

                // store record index
                recordIndex = position;
                PatientRecord selectedRecord = recordList.get(position);
                intent.putExtra("Record", UserDataController
                        .serializeRecord(EditProblem.this, selectedRecord));
                intent.putExtra("Index", recordIndex);
                intent.putExtra("ProblemTitle",problem.getTitle());

                // Launch the edit record activity
                startActivityForResult(intent, 2);
            });

            // required in order for dialog object to appear on screen
            ab.show();
        });
    }

    // Fills the text fields with the original problems string values
    private void displayData() {
        titleText.setText(problem.getTitle());
        descriptionText.setText(problem.getDescription());
        String date = problem.getDate();
        pickedDate.setText(problem.getDate());
    }

    // Gets a concatened string of the edited problem
    private String getEntry() {
        return titleText.getText().toString() + " -- " + pickedDate.getText().toString() + "\n" + descriptionText.getText().toString();
    }

    // Override back button to warn patient that their changes will not be saved.
    @Override
    public void onBackPressed() {
        if (!initial_entry.equals(getEntry())) {
            AlertDialog.Builder ab = new AlertDialog.Builder(this);
            ab.setMessage("Warning. Changes have been made to the problem." + "\n" + "Returning to the home screen will not save changes.");
            ab.setCancelable(true);
            // Set a button to return to the Home screen and don't save changes
            ab.setNeutralButton("Exit And Lose Changes", (dialog, which) -> finish());
            // set a button which will close the alert dialog
            ab.setNegativeButton("Return to Problem", (dialog, which) -> {
            });
            // show the alert dialog on the screen
            ab.show();
        } else {
            finish();
        }
    }

    /*
     * Clicking save button will save the edited problem as long as all the necessary fields are filled
     * and the date is in the proper date format.
     */
    public void editPatientProblem(View view) {
        if (titleText.getText().toString().equals("") || pickedDate.getText().toString().equals("")
                || descriptionText.getText().toString().equals("")) {
            Toast.makeText(this, "Error, all fields must be filled", Toast.LENGTH_LONG).show();
        } else {
            // get changes
            String titleString = titleText.getText().toString();
            String descriptionString = descriptionText.getText().toString();
            String date = pickedDate.getText().toString();


            // If title has been changed, photos must be too...
            if (!titleString.equals(initialTitle)) {
                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                PhotoController.renamePhotosByProblem(cw, initialTitle, titleString);

            }


            // save changes
            user = UserDataController.loadPatientData(this);
            problem = user.getProblem(probIndex);
            problem.update(titleString, date, descriptionString);
            problem.setRecords(recordList);
            user.setProblem(problem, probIndex);
            UserDataController.savePatientData(this, user);

            // done
            finish();
        }
    }

    /*
     * Selecting the "add record" button begins the AddorEditRecord activity with request code 1 which
     * indicates a new problem is being added.
     */
    public void addRecordFromAdd(View view) {
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(EditProblem.this, AddorEditRecordView.class);
        intent.putExtra("ProblemTitle",problem.getTitle());
        // Launch the activity
        startActivityForResult(intent, 1);
    }

    /*
    * Select "View Care Provider Comments" to be taken to a page listing all comments left by the
    * user's Care Providers.
     */
    public void viewCareProviderComments(View view){
        if(user.getProblem(probIndex).getcaregiverRecords().size()>0){
            // Create an intent object containing the bridge to between the two activities
            Intent intent = new Intent(EditProblem.this, ViewCareProviderComments.class);

            Bundle bd = new Bundle();
            bd.putInt("problemNum", probIndex);
            bd.putString("profileType", "Patient");
            intent.putExtras(bd);

            // Launch the activity
            startActivity(intent);
        } else{
            Toast.makeText(this, "No comments to view!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    /*
     * Deal with result of AddorEditRecord. If no result is found (patient backed out of page) do
     * nothing. If a result is found add or set the record based on the request code. 1 indicates
     * a record should be added. 2 indicates a record was edited so the record should be set.
     */
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            // get record
            String recordString = data.getStringExtra("Record");
            PatientRecord record = UserDataController
                    .unSerializeRecord(EditProblem.this, recordString);

            // Check which request we're responding to
            if (requestCode == 1) {
                // Add Record Request
                recordList.add(record);
            } else if(requestCode == 2){
                // Edit Record Request
                recordList.set(recordIndex, record);
            }
        }
    }

    public void viewProblemsPhotos(View view) {
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(EditProblem.this, SlideShowActivity.class);
        intent.putExtra("ProblemTitle",problem.getTitle());
        intent.putExtra("isProblem", "Problem");
        // Launch the browse emotions activity
        startActivity(intent);
    }

    // Creates and opens a datePicker widget to allow the Patient to more easily choose a date
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void datePicker(View view){
        Calendar calender = Calendar.getInstance();
        int day = calender.get(Calendar.DAY_OF_MONTH);
        int month = calender.get(Calendar.MONTH);
        int year = calender.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view1, myear, mmonth, mday) -> {
            mmonth += 1;
            pickedDate.setText(myear + "-" + mmonth + "-" + mday);
        }, year, month, day);
        datePickerDialog.show();
    }
}
