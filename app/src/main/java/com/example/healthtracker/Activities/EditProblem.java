package com.example.healthtracker.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.Problem;
import com.example.healthtracker.EntityObjects.PatientRecord;
import com.example.healthtracker.R;
import com.example.healthtracker.View.AddorEditRecordView;
import com.example.healthtracker.View.CareProviderProblemView;
import com.example.healthtracker.View.ViewCareProviderComments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
    private Date date;
    private String initial_entry;
    private Patient user;
    private String initialTitle;
    private Problem problem;
    private int index;
    private Context context;
    private ArrayList<PatientRecord> recordList;
    private ArrayAdapter<PatientRecord> adapter;
    private ListView mListView;

    @SuppressLint("Assert")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_problem);

        titleText = findViewById(R.id.title_text_editscreen);
        initialTitle=titleText.getText().toString();
        dateText = findViewById(R.id.date_started_editscreen);
        descriptionText = findViewById(R.id.problem_description_editscreen);
        context = this;

        // get current problem data
        user = UserDataController.loadPatientData(this);
        Intent intent = getIntent();
        index = intent.getIntExtra("Index", -1);
        assert index >= 0;
        problem = user.getProblem(index);
        recordList = problem.getRecords();

        // display current data
        displayData();

        // initial entry to check if changes have been made
        initial_entry = getEntry();


    }

    @Override
    protected void onResume() {
        super.onResume();

        // Create an instance of an array adapter
        adapter = new ArrayAdapter<PatientRecord>(this, android.R.layout.simple_list_item_1, recordList);

        // Set an adapter for the list view
        mListView = findViewById(R.id.record_list_editscreen);
        mListView.setAdapter(adapter);

        // Create a context menu to permit users to select and edit a problem
        registerForContextMenu(mListView);
        mListView.setOnCreateContextMenuListener(this);

        // Add listener to detect button click on items in listview
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // method to initiate after listener detects click
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // create an alert dialog via the alert dialog builder to help build dialog to specifics
                AlertDialog.Builder ab = new AlertDialog.Builder(EditProblem.this);
                // set dialog message to edit entry to appear at grabbed position
                ab.setMessage("Record Options:" + recordList.get(position).getTitle() + "\n");
                // set the dialog to be cancelable outside of box
                ab.setCancelable(true);


                // set a negative button for deleting records
                ab.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete problem
                        recordList.remove(position);

                        // update listview
                        adapter.notifyDataSetChanged();

                        // done
                        dialog.dismiss();
                    }
                });

                ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // set a neutral button in the dialog which will open up the edit activity to modify the record
                ab.setNeutralButton("Edit/View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Create an intent object containing the bridge to between the two activities
                        Intent intent = new Intent(EditProblem.this, AddorEditRecordView.class);

                        // store record index
                        index = position;
                        PatientRecord selectedRecord = recordList.get(position);
                        intent.putExtra("Record", UserDataController
                                .serializeRecord(EditProblem.this, selectedRecord));
                        intent.putExtra("Index", position);
                        intent.putExtra("ProblemTitle",problem.getTitle());

                        // Launch the edit record activity
                        startActivityForResult(intent, 2);
                    }
                });

                // required in order for dialog object to appear on screen
                ab.show();
            }
        });
    }

    private void displayData() {
        titleText.setText(problem.getTitle());
        descriptionText.setText(problem.getDescription());
        date = problem.getDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        String dateString = format.format(date);
        dateText.setText(dateString);
    }

    public static boolean testDate(String date) {
        // establish the date format and make the format non lenient, include a parse catch and try clause
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        format.setLenient(false);
        try {
            format.parse(date.trim());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private String getEntry() {
        return titleText.getText().toString() + " -- " + dateText.getText().toString() + "\n" + descriptionText.getText().toString();
    }

    @Override
    /*
     * Override back button to warn patient that their changes will not be saved.
     */
    public void onBackPressed() {
        if (!initial_entry.equals(getEntry())) {
            AlertDialog.Builder ab = new AlertDialog.Builder(this);
            ab.setMessage("Warning. Changes have been made to the problem." + "\n" + "Returning to the home screen will not save changes.");
            ab.setCancelable(true);
            // Set a button to return to the Home screen and don't save changes
            ab.setNeutralButton("Exit And Lose Changes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            // set a button which will close the alert dialog
            ab.setNegativeButton("Return to Problem", new DialogInterface.OnClickListener() {
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

    /*
     * Clicking save button will save the edited problem as long as all the necessary fields are filled
     * and the date is in the proper date format.
     */
    public void editPatientProblem(View view) {
        if (titleText.getText().toString().equals("") || dateText.getText().toString().equals("")
                || descriptionText.getText().toString().equals("")) {
            Toast.makeText(this, "Error, all fields must be filled", Toast.LENGTH_LONG).show();
        } else if (!testDate(dateText.getText().toString())) {
            Toast.makeText(this, "Improper Date Format", Toast.LENGTH_LONG).show();
        } else {
            // get changes
            String titleString = titleText.getText().toString();
            String descriptionString = descriptionText.getText().toString();
            Date date = null;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
            try {
                date = format.parse(dateText.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // save changes
            user = UserDataController.loadPatientData(this);
            problem = user.getProblem(index);
            problem.update(titleString, date, descriptionString);
            problem.setRecords(recordList);
            user.setProblem(problem, index);
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
        if(user.getProblem(index).getcaregiverRecords().size()>0){
            // Create an intent object containing the bridge to between the two activities
            Intent intent = new Intent(EditProblem.this, ViewCareProviderComments.class);

            Bundle bd = new Bundle();
            bd.putInt("problemNum", index);
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
                recordList.set(index, record);
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



}