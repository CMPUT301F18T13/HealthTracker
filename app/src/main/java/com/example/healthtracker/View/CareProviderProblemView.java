package com.example.healthtracker.View;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.CareProvider;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.Problem;
import com.example.healthtracker.EntityObjects.PatientRecord;
import com.example.healthtracker.R;
import com.example.healthtracker.Activities.SlideShowActivity;

import java.util.ArrayList;

/*
 * CareProviderProblemView enables a careprovider to view the current details of a patient's problems
 * including title, date, description and records. From this activity a careprovider can select
 * the "Add Comment" button to initiate the AddCareProvderCommentView activity.
 */
public class CareProviderProblemView extends AppCompatActivity {

    private TextView titleText;
    private TextView dateText;
    private TextView desText;
    private ListView recordList;

    private ArrayAdapter<PatientRecord> rArrayAdapter;

    private Patient myPatient;
    private int patientNum;
    private int problemNum;
    private Bundle bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_provider_problem_view);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));


        titleText = findViewById(R.id.titleView);
        dateText = findViewById(R.id.viewDate);
        desText = findViewById(R.id.desView);
        recordList = findViewById(R.id.care_record_list);

        Intent intent = getIntent();
        bd = intent.getExtras();

        assert bd != null;
        patientNum = bd.getInt("patientNum");
        problemNum = bd.getInt("problemNum");
    }

    @Override
    public void onResume(){
        super.onResume();

        CareProvider careProvider = UserDataController.loadCareProviderData(this);
        myPatient = careProvider.getPatientList().get(patientNum);
        Problem pProblem = myPatient.getProblem(problemNum);
        ArrayList<PatientRecord> records = pProblem.getRecords();

        String title = pProblem.getTitle();
        String date = pProblem.getDate();
        String des = pProblem.getDescription();

        showProblem(title,date,des);

        ArrayAdapter<PatientRecord> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, records);
        recordList.setAdapter(adapter);


        // Add listener to detect button click on items in listview
        // method to initiate after listener detects click
        recordList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(CareProviderProblemView.this, CareProviderRecordView.class);
            bd.putInt("recordNum", position);
            intent.putExtras(bd);
            startActivity(intent);
        });
    }

    private void showProblem(String title, String date, String des) {
        titleText.setText(title);
        dateText.setText(date);
        desText.setText(des);
    }

    /*
     * Clicking the "Add Comment" button initiates a new AddCareProviderComment activity and
     * sends the patient and problem index numbers to that activity. This allows the CareProvider
     * to then add a comment record to the problem in the AddCareProviderComment activity.
     */
    public void addCareProviderComment(View view) {
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(CareProviderProblemView.this, AddCareProviderCommentView.class);
        Bundle bd = new Bundle();
        bd.putInt("patientNum", patientNum);
        bd.putInt("problemNum", problemNum);
        intent.putExtras(bd);
        // Launch the browse emotions activity
        startActivity(intent);
    }

    public void viewCareProviderComments(View view){
        // Create an intent object containing the bridge to between the two activities
        if(myPatient.getProblem(problemNum).getcaregiverRecords().size() > 0){
            Intent intent = new Intent(CareProviderProblemView.this, ViewCareProviderComments.class);
            Bundle bd = new Bundle();
            bd.putInt("patientNum", patientNum);
            bd.putInt("problemNum", problemNum);
            bd.putString("profileType", "CareProvider");
            intent.putExtras(bd);
            // Launch the browse emotions activity
            startActivity(intent);
        } else{
            Toast.makeText(this, "No comments to view!", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * Initiate a slidshow of all of the photos associated with the currently viewed problem.
     */
    public void viewProblemsPhotos(View view) {
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(CareProviderProblemView.this, SlideShowActivity.class);
        // Launch the browse emotions activity
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
