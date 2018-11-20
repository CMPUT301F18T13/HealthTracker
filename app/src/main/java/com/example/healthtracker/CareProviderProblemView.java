package com.example.healthtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * CareProviderProblemView enables a careprovider to view the current details of a patient's problems
 * including title, date, description and records. From this activity a careprovider can select
 * the "Add Comment" button to initiate the AddCareProvderCommentView activity.
 */
public class CareProviderProblemView extends AppCompatActivity {

    private TextView titleText;
    private TextView dateText;
    private TextView desText;
    private TextView recordText;

    private String title;
    private Date date;
    private String des;
    private ArrayList<PatientRecord> records;

    private ArrayAdapter<PatientRecord> rArrayAdapter;

    private Patient myPatient;
    private Problem pProblem;
    private int patientNum;
    private int problemNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_provider_problem_view);

        titleText = findViewById(R.id.viewTitle);
        dateText = findViewById(R.id.viewDate);
        desText = findViewById(R.id.viewDes);
        recordText = findViewById(R.id.viewRecord);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        CareProvider careProvider = UserDataController.loadCareProviderData(this);

        patientNum = bd.getInt("patientNum");
        problemNum = bd.getInt("problemNum");
        myPatient = careProvider.getPatientList().get(patientNum);
        pProblem = myPatient.getProblem(problemNum);

        title = pProblem.getTitle();
        date = pProblem.getDate();
        des = pProblem.getDescription();
        records = pProblem.getRecords();

        showProblem(title,date,des);
    }


    private String dateToString(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        String dateString = format.format(date);
        return dateString;
    }

    private void showProblem(String title, Date date, String des) {
        titleText.setText(title);
        dateText.setText(dateToString(date));
        desText.setText(des);

        if(pProblem.getcaregiverRecords().size() > 0){
            recordText.setText(pProblem.getcaregiverRecords().get(0).getComment());
        }
    }

    /**
     * Clicking the "Add Comment" button initiates a new AddCareProviderComment activity and
     * sends the patient and problem index numbers to that activity. This allows the CareProvider
     * to then add a comment record to the problem in the AddCareProviderComment activity.
     *
     * @param view the view for the layout included for onClick methods in XML
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

    /**
     * Initiate a slidshow of all of the photos associated with the currently viewed problem.
     *
     * @param view the view for the layout included for onClick methods in XML
     *
     */
    public void viewProblemsPhotos(View view) {
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(CareProviderProblemView.this, SlideShowActivity.class);
        // Launch the browse emotions activity
        startActivity(intent);
    }

}
