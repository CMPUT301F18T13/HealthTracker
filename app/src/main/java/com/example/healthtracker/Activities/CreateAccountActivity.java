package com.example.healthtracker.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthtracker.Contollers.ElasticsearchController;
import com.example.healthtracker.EntityObjects.CareProvider;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.User;
import com.example.healthtracker.R;
import com.searchly.jestdroid.JestDroidClient;

import java.util.Random;
import java.util.concurrent.ExecutionException;

/* Idea and implemented code for testing interent connection from *binnyb(user:416412),
https://stackoverflow.com/questions/5474089/how-to-check-currently-internet-connection-is-available-or-not-in-android,
2011/03/29, viewed 2018/11/16*
*/

/* Idea and code implementation knowledge for random string generation for account code from
url: https://stackoverflow.com/questions/20536566/creating-a-random-string-with-a-z-and-0-9-in-java 
Author: SURESH ATTA(user:1927832) 
posted: 2013/12/12 
viewed: 2018/11/23
*/

/*
 * CreateAccountActivity enables a user to create a new account for the HealthTracker app by filling in the required account details.
 * The user's data will be stored in an ElasticSearch database and partially stored locally.
 */
public class CreateAccountActivity extends AppCompatActivity {

    private static JestDroidClient client;

    private static final String TAG = "CreateAccountActivity";
    private EditText Email, Phone, UserID;
    private Button Register;
    private CheckBox checkBox;
    private Context context;


    private CreateAccountActivity Context;
    private String email, phone, userID;
    private User user;

    /*
     * onCreate launched on activity creation
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Register = findViewById(R.id.create_new_account_button);
        Email = findViewById(R.id.email);
        Phone = findViewById(R.id.phone_number);
        UserID = findViewById(R.id.userID);
        checkBox = findViewById(R.id.caregiver_checkbox);
        context = this;
        Log.d(TAG, "Testing Internet Connection");
        if (!ElasticsearchController.testConnection(this)) {
            Toast.makeText(context, "No internet", Toast.LENGTH_SHORT).show();
        }
        init();
    }

    /*
     * Adds a new user with the specified account information filled in by the user in each specific field. The userId for the new
     * user will first be checked to see if it already exists. If so account creation will be denied. Additionally each field is
     * checked to see if it was left empty or not by calling the checkInputs method.
     */
    private void init(){
        Register.setOnClickListener(view -> {
            email = Email.getText().toString().toLowerCase();
            phone = Phone.getText().toString();
            userID = UserID.getText().toString();
            if (checkInputs(email, userID, phone)) {
                if(validateEmail(email)) {
                    if (validatePhone(phone)) {
                        try {
                            if (!userExists(userID)) {
                                addNewUser();
                            } else {
                                Toast.makeText(context, "User ID is taken", Toast.LENGTH_SHORT).show();
                            }
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(context, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(context, "Invalid Email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*
     * Checks that all the input fields are filled, displaying a toast message if the fields are not filled. The method
     * returns a boolean object based on whether or not the account info fields are filled.
     */
    private boolean checkInputs(String email, String userID, String phone){
        Log.d(TAG, "checkInputs: checking inputs for null values");
        if(email.equals("") || userID.equals("") || phone.equals("")){
            Toast.makeText(context, "All fields must be filled out", Toast.LENGTH_SHORT).show();
            return false;
        } else if(userID.length()<8){
            Toast.makeText(context, "User ID must be at least 8 characters long.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /*
     * Checks if an account with the provided userID already exists in the system and returns a boolean value based off of the
     * result of verification testing.
     */
    private boolean userExists(String userID) throws ExecutionException, InterruptedException {
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

    /*
     * The addNewUser() method is called by init() when the user attempts to add a new account. If the checkbox is clicked by
     * the user, a CareProvider account will try to be created. If the checkbox is not checked, a patient account will
     * attempt to be created. This method utilizes the ElasticsearchController, CareProvider, and Patient classes.
     */
    private void addNewUser(){
        if (ElasticsearchController.testConnection(context)) {
            // Save new user with elasticsearch
            if (checkBox.isChecked()) {
                // save new care provider
                CareProvider newCareProvider = new CareProvider(phone, email, userID, createCode());
                ElasticsearchController.AddCareProvider addCareProviderTask = new ElasticsearchController.AddCareProvider();
                addCareProviderTask.execute(newCareProvider);
            } else {
                // save new patient
                Patient newPatient = new Patient(phone, email, userID, createCode());
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

    /* Code for random string generation from * SURESH ATTA(user:1927832),
    https://stackoverflow.com/questions/20536566/creating-a-random-string-with-a-z-and-0-9-in-java, 2013/12/12,
    viewed 2018/11/23* */

    // Generates a 5 character long random string containing alphanumeric characters to serve as an account code
    private String createCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * chars.length());
            salt.append(chars.charAt(index));
        }
        return salt.toString();
    }

    // Validate's the entered email address
    private static boolean validateEmail(CharSequence target) {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    // Validate's the entered phone number
    private boolean validatePhone(String number) {
        return Patterns.PHONE.matcher(number).matches();
    }
}
