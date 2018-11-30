package com.example.healthtracker.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthtracker.Contollers.ElasticsearchController;
import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.CareProvider;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.View.AddPatientView;
import com.example.healthtracker.View.PatientHomeView;
import com.example.healthtracker.R;
import com.example.healthtracker.View.CareProviderHomeView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/* Idea and implemented code for testing interent connection from *binnyb(user:416412),   
https://stackoverflow.com/questions/5474089/how-to-check-currently-internet-connection-is-available-or-not-in-android, 
2011/03/29, viewed 2018/11/16*
*/

/*
 * LoginActivity enables a user to login to their created account. In the case that an unregistered user attempts login, they
 * will be told that their account is not valid. The user can click the checkbox if they are a CareProvider inorder for them
 * to be taken to the appropriate home screen otherwise they are brought to the PatientHomeView. Users can navigate to the
 * CreateAccountActivity from this screen.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText UserID;
    private CheckBox checkBox;
    private Context context;
    private EditText accountCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set screen to layout specified in activity_main
        setContentView(R.layout.activity_login);
        //mRegister = (TextView) findViewById(R.id.link_register);
        UserID = findViewById(R.id.userID);
        checkBox = findViewById(R.id.CareGiverLogin);
        accountCode = findViewById(R.id.codeLogin);
        context = this;
    }


    /*
     * UserLogin() is initiated by the user clicking on the login button. If the user leaves a field empty when trying to log in
     * they will be shown a toast message indicating a field has been left empty. If the user tries to login with an unregistered
     * account they will be shown a toast message telling them that this account is not valid.
     * <p>
     * Upon correct login credentials the user will be brought to the appropriate login screen for patient or care provider
     * based on if the checkbox indicating which type of user is attempting to login is checked. The app checks elastic search
     * database for the entered username to see if it has been entered. Additionally when then this method is called it calls
     * test connection to see if their is an internet connection.
     */
    public void UserLogin(View view) throws ExecutionException, InterruptedException {
        if (ElasticsearchController.testConnection(context)) {
            String userID = UserID.getText().toString();
            String emptyTest = accountCode.getText().toString();
            if (checkBox.isChecked()) {
                if (!isEmpty(UserID.getText().toString())) {
                    CareProvider careProvider = UserDataController.loadCareProviderByID(this, userID);
                    if (careProvider != null) {
                        SharedPreferences myPrefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = myPrefs.edit();
                        editor.putString("userID", userID);
                        editor.apply();
                        UserDataController.saveCareProviderData(this, careProvider);
                        Intent intent = new Intent(LoginActivity.this, CareProviderHomeView.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Unknown Account", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(LoginActivity.this, "User ID Field not filled", Toast.LENGTH_SHORT).show();
                }
            } else{
                if(!isEmpty(UserID.getText().toString()) || !emptyTest.equals("")){
                    if(!emptyTest.equals("")){
                        if(!isEmpty(UserID.getText().toString())){
                            Toast.makeText(LoginActivity.this, "Please fill only 1 field", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String userIdHolder = codeLogin();
                            patientLogin(userIdHolder);
                        }
                    }
                    else{
                        patientLogin(userID);
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "Either field not filled", Toast.LENGTH_SHORT).show();
                }
            }
        } else{
            Toast.makeText(context, "No internet connection available.", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    * * isEmpty() tests if one of the login fields is not filled in
     * */
    private boolean isEmpty(String string) {
        return string.equals("");
    }

    /*
     * Brings the user to the create account screen upon clicking the create
     */
    public void CreateAccount(View view) {
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);// Launch the browse emotions activity
        startActivity(intent);
    }

    /*
     * testConnection() checks for online connectivity on either wifi or mobile data and returns the connectivity state
     */
    public boolean testConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        //check for network connection
        assert connectivityManager != null;
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() != NetworkInfo.State.CONNECTED &&
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() != NetworkInfo.State.CONNECTED;
    }

    private String codeLogin(){
        // Read user input
        String patientCode = accountCode.getText().toString();
        String patientID = "";

        boolean validID = false;
        // Read from the elastic search database, obtain a list of registered patient IDs
        ElasticsearchController.getAllPatients getAllMyPatients  = new ElasticsearchController.getAllPatients();
        getAllMyPatients.execute();
        try {
            ArrayList<Patient> patients = getAllMyPatients.get();
            System.out.println(patients);

            // Check whether the patient ID exists
            for(int i = 0; i< patients.size(); i++){
                if(patientCode.equals(patients.get(i).getCode())){
                    patientID = patients.get(i).getUserID() ;
                    validID = true;
                    Patient mPatient = patients.get(i);
                }
            }

            if (!ElasticsearchController.testConnection(this)) {
                Toast.makeText(LoginActivity.this, "No Interent Connection", Toast.LENGTH_SHORT).show();
                validID = false;
            }
            if (!validID) {
                Toast.makeText(LoginActivity.this, "Account code not found", Toast.LENGTH_SHORT).show();
            }
        }catch (ExecutionException e1) {
            Log.i("error", "execution exception");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (validID){
            return patientID;
        }
        else{
            return null;
        }
    }

    public void patientLogin(String userID) throws ExecutionException, InterruptedException {
        Patient patient;
        ElasticsearchController.GetPatient getPatient = new ElasticsearchController.GetPatient();
        getPatient.execute(userID);
        patient = getPatient.get();
        if (patient != null) {
            SharedPreferences myPrefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = myPrefs.edit();
            editor.putString("userID", userID);
            editor.apply();
            UserDataController.savePatientData(this, patient);
            Intent intent = new Intent(LoginActivity.this, PatientHomeView.class);
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "Unknown Account", Toast.LENGTH_SHORT).show();
        }
    }
}
