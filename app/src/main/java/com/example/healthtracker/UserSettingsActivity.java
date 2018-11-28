package com.example.healthtracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/*
 * UserSettingsActivity enables users to modify their account information. The changes to their local account are modified in the
 * ElasticSearch database as well as stored locally after adjustment.
 */
public class UserSettingsActivity extends AppCompatActivity {
    private static final String TAG = "Settings";
    String userID;
    String profileType;
    List<User> userInfo;
    EditText uemail;
    EditText phone;
    TextView code;
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
        if(profileType.equals("Patient")){
            loadCurrentPatientData();
        } else{
            loadCurrentCareProviderData();
        }
    }

    /*
     * Get and display the current contact information of the patient.
     */
    private void loadCurrentPatientData(){
        patient = UserDataController.loadPatientData(this);
        code=findViewById(R.id.code);
        uemail.setText(patient.getEmail());
        phone.setText(patient.getPhone());
        code.setText(patient.getCode());
    }

    /*
     * Get and display the current contact information of the careprovider.
     */
    private void loadCurrentCareProviderData(){
        careProvider = UserDataController.loadCareProviderData(this);
        uemail.setText(careProvider.getEmail());
        phone.setText(careProvider.getPhone());
    }


    public void editUserInfo(View view){
        String phoneString = phone.getText().toString();
        String emailString = uemail.getText().toString().toLowerCase();
        if(!isEmpty(phoneString) && !isEmpty(emailString)){
            if(profileType.equals("Patient")){
                patient.updateUserInfo(phoneString, emailString);
                UserDataController.savePatientData(this, patient);
            } else{
                careProvider.updateUserInfo(phoneString, emailString);
                UserDataController.saveCareProviderData(this, careProvider);
            }
        } else{
            Toast.makeText(this, "All fields must be filled in.", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isEmpty(String string) {
        return string.equals("");
    }


}
