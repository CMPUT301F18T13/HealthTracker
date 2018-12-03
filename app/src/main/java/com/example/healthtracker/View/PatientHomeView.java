package com.example.healthtracker.View;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.R;
import com.example.healthtracker.Activities.SearchActivity;
import com.example.healthtracker.Activities.UserSettingsActivity;

/* Idea and implemented code for learning how to make a textview clickable to bring to an
external website from *Bebin T.N (user:1485235),  
https://stackoverflow.com/questions/2734270/how-do-i-make-links-in-a-textview-clickable, 2013/06/18, viewed 2018/11/03*
*/


/*
 * PatientHomeView acts as a home screen interface for Patient users to navigate to different app activities.
 */
public class PatientHomeView extends AppCompatActivity {

    /*
     * onCreate launched on activity creation. Contains the code for generating links to external medical resources allowing the patient
     * to help identify and better document their medical issues.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        TextView webMDTV = findViewById(R.id.webMDLink);
        String MDtext = "<a href='https://www.webmd.com/'> WebMD </a>";
        webMDTV.setMovementMethod(LinkMovementMethod.getInstance());
        webMDTV.setText(Html.fromHtml(MDtext));

        TextView mayoTV = findViewById(R.id.mayoLink);
        mayoTV.setMovementMethod(LinkMovementMethod.getInstance());
        String mayoText = "<a href='https://www.mayoclinic.org/symptom-checker/select-symptom/itt-20009075'> Mayo Clinic </a>";
        //String mayoText = "<a href='https://www.mayoclinic.org'> Mayo Clinic </a>";
        mayoTV.setText(Html.fromHtml(mayoText));

        TextView checkTV = findViewById(R.id.checkLink);
        checkTV.setMovementMethod(LinkMovementMethod.getInstance());
        String checkText = "<a href='https://symptomchecker.isabelhealthcare.com/suggest_diagnoses_advanced/landing_page'> Symptom Checker </a>";
        //String checkText = "<a href='https://www.symptomchecker.isabelhealthcare.com'> Symptom Checker </a>";
        checkTV.setText(Html.fromHtml(checkText, Html.FROM_HTML_MODE_COMPACT));
    }

    /*
     * Method containing the new intent which will bring user to the search activity layout
     */
    public void Search(View view) {
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(PatientHomeView.this, SearchActivity.class);
        intent.putExtra("profile","Patient");
        // Launch the browse emotions activity
        startActivity(intent);
    }

    /*
     * finish's the current activity and returns the user the main login screen, logging out the user
     */
    public void Sync(View view) {
        UserDataController.syncPatientData(this);
    }

    public void Logout(View view){
        finish();
    }

    /*
     * Method containing the new intent which will bring the patient to the add problem activity layout
     */
    public void addProblem(View view) {
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(PatientHomeView.this, AddProblemView.class);
        // Launch the browse emotions activity
        startActivity(intent);
    }

    /**
     * Method containing the new intent which will bring the patient to the view problems activity layout
     *
     * @param view the view for the Patient home screen layout included for onClick methods in XML
     */
    public void viewMyProblems(View view) {

        // load current user
        Patient user = UserDataController.loadPatientData(this);

        // check if there are any problems
        if(user.noProblemsExist()){
            // no problems to view
            Toast.makeText(this, "No problems to view.", Toast.LENGTH_SHORT).show();
        } else {
            // Go to problem view activity
            // Create an intent object containing the bridge to between the two activities
            Intent intent = new Intent(PatientHomeView.this, ViewMyProblems.class);
            // Launch the ViewMyProblems activity
            startActivity(intent);
        }

    }

    /*
     * Method containing the new intent which will bring user to the user settings activity layout
     */
    public void Settings(View view) {
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(PatientHomeView.this, UserSettingsActivity.class);
        intent.putExtra("profileType", "Patient");
        // Launch the browse emotions activity
        startActivity(intent);
    }

    /*
     * Method containing the new intent which will bring user to the geo map activity layout
     */
    public void viewMap(View view) {
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(PatientHomeView.this, MapView.class);
        // Launch the browse emotions activity
        startActivity(intent);
    }
}
