package com.example.healthtracker.View;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.Problem;
import com.example.healthtracker.EntityObjects.PatientRecord;
import com.example.healthtracker.R;

import java.util.ArrayList;
import java.util.Calendar;

/*
 * AddProblemView enables a patient to add a new problem to their account. The patient must fill in
 * the title, description, and date started fields then click the save button to add the problem.
 * The date started field must be in the proper date format or the problem cannot be added. The
 * patient may optionally add any number of records to the problem. An individual record can be added
 * by clicking the add record button. An already added record can be edited or deleted by selecting it.
 *
 */
public class AddProblemView extends AppCompatActivity {

    private EditText titleText;
    private EditText descriptionText;
    private Context context;
    private ArrayList<PatientRecord> recordList;
    private ArrayAdapter<PatientRecord> adapter;
    private int index;
    private Calendar calender;
    private DatePickerDialog datePickerDialog;
    private TextView pickedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);
        titleText = findViewById(R.id.title_text);
        descriptionText = findViewById(R.id.problem_description_edit);
        pickedDate = findViewById(R.id.pickedDate2);
        context = this;
        recordList = new ArrayList<PatientRecord>();
    }

    @Override
    protected void onResume(){
        super.onResume();

        // Create an instance of an array adapter
        adapter = new ArrayAdapter<PatientRecord>(this, android.R.layout.simple_list_item_1, recordList);

        // Set an adapter for the list view
        ListView mListView = findViewById(R.id.record_list_addscreen);
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
                AlertDialog.Builder ab = new AlertDialog.Builder(AddProblemView.this);
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
                        Intent intent = new Intent(AddProblemView.this, AddorEditRecordView.class);

                        // store record index
                        index = position;
                        PatientRecord selectedRecord = recordList.get(position);
                        intent.putExtra("Record", UserDataController
                                .serializeRecord(AddProblemView.this, selectedRecord));
                        intent.putExtra("Index", position);

                        // Launch the edit record activity
                        startActivityForResult(intent, 2);
                    }
                });

                // required in order for dialog object to appear on screen
                ab.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!titleText.getText().toString().equals("") || !pickedDate.getText().toString().equals("")
                || !descriptionText.getText().toString().equals("")) {
            AlertDialog.Builder ab = new AlertDialog.Builder(AddProblemView.this);
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
     * When the save button is clicked the new problem is added to the patient's account. A toast
     * message will indicate if the problem was added or if it could not be added due to an improper
     * date format or not every field being filled.
     */
    public void addPatientProblem(View view) {
        if (titleText.getText().toString().equals("") || pickedDate.getText().toString().equals("")
                || descriptionText.getText().toString().equals("")) {
            Toast.makeText(this, "Error, all field must be filled", Toast.LENGTH_LONG).show();
        } else {
            saveProblem();
        }
    }

    // Save's the patient's problem both locally and remotely
    private void saveProblem(){
        // get Problem info
        String title = titleText.getText().toString();
        String date =pickedDate.getText().toString();
        String description = descriptionText.getText().toString();


        // fetch user data
        Patient patient = UserDataController.loadPatientData(context);

        // create problem
        Problem problem = new Problem(title, date, description);
        problem.setRecords(recordList);
        patient.addProblem(problem);

        // save problem
        UserDataController.savePatientData(context, patient);

        // done
        finish();

    }


    /*
     * When the add record button is clicked the AddorEditRecordView activity is started with
     * request code 1 which indicates that a record is being added and not edited.
     *
     */
    public void addRecordFromAdd(View view) {
        // Create an intent object containing the bridge to between the two activities
        if (titleText.getText().toString().equals("")){
            Toast.makeText(this, "Please fill in the Problem Title", Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent = new Intent(AddProblemView.this, AddorEditRecordView.class);
            intent.putExtra("ProblemTitle", titleText.getText().toString());
            // Launch the browse emotions activity
            startActivityForResult(intent, 1);
        }
    }

    @Override
    /*
     * Override onActivityResult to specify what should be done when the AddorEditRecordView
     * Activity finishes. If no result was acquired do nothing. Otherwise add the record to the record
     * list if a new record was added or change an existing record in the list if a record was edited.
     */
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            // get record
            String recordString = data.getStringExtra("Record");
            PatientRecord record = UserDataController
                    .unSerializeRecord(AddProblemView.this, recordString);

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

    // Creates and opens a datePicker widget to allow the Patient to more easily choose a date
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void datePicker(View view){
        calender=Calendar.getInstance();
        int day = calender.get(Calendar.DAY_OF_MONTH);
        int month = calender.get(Calendar.MONTH);
        int year = calender.get(Calendar.YEAR);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int myear, int mmonth, int mday) {
                mmonth+=1;
                pickedDate.setText(myear+"-"+mmonth+"-"+mday);
            }
        }, year,month,day);
        datePickerDialog.show();
    }

}