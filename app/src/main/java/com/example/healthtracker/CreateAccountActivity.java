package com.example.healthtracker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.searchly.jestdroid.JestDroidClient;

import java.util.concurrent.ExecutionException;

/**
 * CreateAccountActivity enables a user to create a new account for the HealthTracker app by filling in the required account details.
 * The user's data will be stored in an ElasticSearch database and partially stored locally.
 *
 * @author Tyler Watson
 * @version 1.0
 * @since 2018-10-30
 */
public class CreateAccountActivity extends AppCompatActivity {

    private static JestDroidClient client;

    private static final String TAG = "CreateAccountActivity";
    private EditText Email, Password, Phone, UserID;
    private Button Register;
    private CheckBox checkBox;
    private Context context;


    private CreateAccountActivity Context;
    private String email, password, phone, userID;
    private User user;

    /**
     * onCreate launched on activity creation
     *
     * @param savedInstanceState SIS
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Register = findViewById(R.id.create_new_account_button);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.new_password);
        Phone = findViewById(R.id.phone_number);
        UserID = findViewById(R.id.userID);
        checkBox = findViewById(R.id.caregiver_checkbox);
        context = this;
        Log.d(TAG, "Testing Internet Connection");
        if (testConnection()) {
            Toast.makeText(context, "No internet", Toast.LENGTH_SHORT).show();
        }
        init();
    }

    /**
     * Adds a new user with the specified account information filled in by the user in each specific field. The userId for the new
     * user will first be checked to see if it already exists. If so account creation will be denied. Additionally each field is
     * checked to see if it was left empty or not by calling the checkInputs method.
     */
    private void init(){
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = Email.getText().toString().toLowerCase();
                password = Password.getText().toString();
                phone = Phone.getText().toString();
                userID = UserID.getText().toString();
                if (checkInputs(email, userID, password, phone)) {
                    try {
                        if(!userExists(userID)){
                            addNewUser();
                        } else{
                            Toast.makeText(context, "User ID is taken", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                } else{
                    Toast.makeText(context, "All fields must be filled", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Checks that all the input fields are filled, displaying a toast message if the fields are not filled. The method
     * returns a boolean object based on whether or not the account info fields are filled.
     *
     * @param email input email string to be tested whether it was filled in by the user
     * @param userID input userID string to be tested whether it was filled in by the user
     * @param password input password string to be tested whether it was filled in by the user
     * @param phone input phone string to be tested whether it was filled in by the user
     * @return returns boolean object on whether an entry field is blank
     */
    private boolean checkInputs(String email, String userID, String password, String phone){
        Log.d(TAG, "checkInputs: checking inputs for null values");
        if(email.equals("") || userID.equals("") || password.equals("") || phone.equals("")){
            Toast.makeText(context, "All fields must be filled out", Toast.LENGTH_SHORT).show();
            return false;
        } else if(userID.length()<8){
            Toast.makeText(context, "User ID must be at least 8 characters long.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Checks if an account with the provided userID already exists in the system and returns a boolean value based off of the
     * result of verification testing.
     *
     * @param userID userID entered within the method call to be checked for its existence in the database before account creation
     * @return returns a boolean value determined by the existence of an existing userID
     * @throws ExecutionException   exception for .get() to catch errors during method execution
     * @throws InterruptedException exception for .get() to catch interruptions resulting in errors during execution
     */
    public boolean userExists(String userID) throws ExecutionException, InterruptedException {
        if(checkBox.isChecked()){
            CareProvider foundUser;
            ElasticsearchController.GetCareProvider getCareProvider = new ElasticsearchController.GetCareProvider();
            getCareProvider.execute(userID);
            foundUser = getCareProvider.get();
            return foundUser != null;
        } else{
            Patient foundUser;
            ElasticsearchController.GetPatient getPatient = new ElasticsearchController.GetPatient();
            getPatient.execute(userID);
            foundUser = getPatient.get();
            return foundUser != null;
        }

    }

    /**
     * The addNewUser() method is called by init() when the user attempts to add a new account. If the checkbox is clicked by
     * the user, a CareProvider account will try to be created. If the checkbox is not checked, a patient account will
     * attempt to be created. This method utilizes the ElasticsearchController, CareProvider, and Patient classes.
     */
    public void addNewUser(){
        if (ElasticsearchController.testConnection(context)) {
            // Save new user with elasticsearch
            if (checkBox.isChecked()) {
                // save new care provider
                CareProvider newCareProvider = new CareProvider(phone, email, userID, password);
                ElasticsearchController.AddCareProvider addCareProviderTask = new ElasticsearchController.AddCareProvider();
                addCareProviderTask.execute(newCareProvider);
            } else {
                // save new patient
                Patient newPatient = new Patient(phone, email, userID, password);
                ElasticsearchController.AddPatient addPatientTask = new ElasticsearchController.AddPatient();
                addPatientTask.execute(newPatient);
            }

            try {
                if (userExists(userID)) {
                    Toast.makeText(context, "Account created", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(context, "Failed to create account. Check internet connection.", Toast.LENGTH_SHORT).show();
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * testConnection() checks for online connectivity on either wifi or mobile data and returns the connectivity state
     *
     * @return returns a boolean object on whether the user is connected to wifi or cellular data for online connectivity checks
     */
    public boolean testConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        //we are connected to a network
        assert connectivityManager != null;
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() != NetworkInfo.State.CONNECTED &&
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() != NetworkInfo.State.CONNECTED;
    }
}