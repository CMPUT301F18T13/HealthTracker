package com.example.healthtracker.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.CareProvider;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.User;
import com.example.healthtracker.R;

import java.util.List;


/*
 * UserSettingsActivity enables users to modify their account information. The changes to their local account are modified in the
 * ElasticSearch database as well as stored locally after adjustment.
 */
public class UserSettingsActivity extends AppCompatActivity {
    private static final String TAG = "Settings";
    String userID;
    private String profileType;
    List<User> userInfo;
    private EditText uemail;
    private EditText phone;
    private Patient patient;
    private CareProvider careProvider;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);// update problems if they are ever changed
        uemail = findViewById(R.id.edit_email);
        phone = findViewById(R.id.edit_phone);
        Intent intent = getIntent();
        profileType = intent.getStringExtra("profileType");
        // Set the colour for the actionbar to differentiate current user type
        if(profileType.equals("CareProvider")){
            android.support.v7.app.ActionBar bar = getSupportActionBar();
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
            loadCurrentCareProviderData();
        }
        else{
            loadCurrentPatientData();
        }
    }

    // Get and display the current contact information of the patient.
    private void loadCurrentPatientData(){
        patient = UserDataController.loadPatientData(this);
        TextView code = findViewById(R.id.code);
        uemail.setText(patient.getEmail());
        phone.setText(patient.getPhone());
        code.setText(patient.getCode());
    }

    // Get and display the current contact information of the careprovider.
    private void loadCurrentCareProviderData(){
        careProvider = UserDataController.loadCareProviderData(this);
        uemail.setText(careProvider.getEmail());
        phone.setText(careProvider.getPhone());
    }

    // After the user information is edited the new contact information will be saved for the user
    public void editUserInfo(View view){
        // Get the text in the edit fields
        String phoneString = phone.getText().toString();
        String emailString = uemail.getText().toString().toLowerCase();
        // Check if the fields are empty
        if(isEmpty(phoneString) && isEmpty(emailString)){
            // Validate the fields
            if(validateEmail(emailString)) {
                if(validatePhone(phoneString)) {
                    // Determine the profile type and make edited saves accordingly
                    if (profileType.equals("Patient")) {
                        patient.updateUserInfo(phoneString, emailString);
                        UserDataController.savePatientData(this, patient);
                        Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show();
                    } else {
                        careProvider.updateUserInfo(phoneString, emailString);
                        UserDataController.saveCareProviderData(this, careProvider);
                        Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            } 
        } else {
            Toast.makeText(this, "All fields must be filled in.", Toast.LENGTH_SHORT).show();
        }
    }

    // Validate the user provided email
    public static boolean validateEmail(CharSequence target) {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    // Validate the user provided phone number
    public boolean validatePhone(String number) {
        return Patterns.PHONE.matcher(number).matches();
    }

    // Check if the input string is empty
    private boolean isEmpty(String string) {
        return !string.equals("");
    }

    // Load the icon for the CareProvider view
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Intent intent = getIntent();
        profileType = intent.getStringExtra("profileType");
        if (profileType.equals("CareProvider")) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.actionbar, menu);
            return super.onCreateOptionsMenu(menu);
        } else {
            return false;
        }
    }
}
