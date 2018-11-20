package com.example.healthtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * Used to load data from server or local cache as well as save to server and local cache.
 * Server is saved to and loaded from using elastic search.
 * Objects are serialized and saved to shared preferences for local cache.
 *
 * The methods for serializing Objects and reading and writing serialized objects to shared preferences
 * are taken from the Student Picker for Android video series by Abram Hindle.
 * Student Picker for Android by Abram Hindle: https://www.youtube.com/watch?v=5PPD0ncJU1g
 *
 * @author caochenlin
 * @version 1.0
 * @since 2018/11/2
 *
 *
 */
public class UserDataController<E> {


    final private static String userKey = "Key";
    private Context context;

    UserDataController(Context context) {
        this.context = context;
    }

    // save serialized string of user object to shared preferences
    // calls method to convert user object  to serialized string

    /**
     * Retrieves all of the data of the currently logged in CareProvider in the form of
     * a single CareProvider object
     *
     * @param context input context to use to access shared preferences
     * @return returns CareProvider object corresponding to the currently logged in CareProvider
     */
    public static CareProvider loadCareProviderData(Context context) {
        if (ElasticsearchController.testConnection(context)) {
            // Download user data with elastic search
            SharedPreferences myPrefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
            String userID = myPrefs.getString("userID", "");
            ElasticsearchController.GetCareProvider getCareProvider = new ElasticsearchController.GetCareProvider();
            getCareProvider.execute(userID);
            CareProvider careProvider = null;
            try {
                careProvider = getCareProvider.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return careProvider;
        } else {
            // Load local cache of user data
            return new UserDataController<CareProvider>(context).loadUserLocally();
        }
    }

    /**
     * Retrieves all of the data of the currently logged in Patient in the form of
     * a single Patient object
     *
     * @param context input context to use to access shared preferences
     * @return returns Patient object corresponding to the currently logged in Patient
     */
    public static Patient loadPatientData(Context context) {
        if (ElasticsearchController.testConnection(context)) {

            // Download user data with elastic search
            SharedPreferences myPrefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
            String userID = myPrefs.getString("userID", "");
            ElasticsearchController.GetPatient getPatient = new ElasticsearchController.GetPatient();
            getPatient.execute(userID);
            Patient patient = null;
            try {
                patient = getPatient.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return patient;
        } else {
            // Load local cache of user data
            return new UserDataController<Patient>(context).loadUserLocally();
        }
    }

    /**
     * Retrieves a specific patient by inputting their id
     *
     * @param context input context to use to access shared preferences
     * @param ID input the ID of the patient to be returned
     * @return Patient to who the ID input belongs to
     */
    public static Patient loadPatientById(Context context, String ID) {
        if (ElasticsearchController.testConnection(context)) {

            // Download user data with elastic search
            ElasticsearchController.GetPatient getPatient = new ElasticsearchController.GetPatient();
            getPatient.execute(ID);
            Patient patient = null;
            try {
                patient = getPatient.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return patient;
        } else {
            // Load local cache of user data
            return new UserDataController<Patient>(context).loadUserLocally();
        }
    }

    /**
     * Saves patient object to server if internet is available and the server is reachable. Always
     * saves patient object locally. Creates toast message to confirm success or failure of saving
     * patient to server.
     *
     * @param context input context needed to access Shared Preferences
     * @param patient input patient to save
     */
    public static void savePatientData(Context context, Patient patient) {
        // save online if possible
        if (ElasticsearchController.testConnection(context)) {
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
            ElasticsearchController.AddPatient addPatientTask = new ElasticsearchController.AddPatient();
            addPatientTask.execute(patient);
        } else {
            Toast.makeText(context, "Could not reach server. Changes saved locally.", Toast.LENGTH_LONG).show();
            Toast.makeText(context, "Sync data when a connection is available to save changes to server.", Toast.LENGTH_LONG).show();

        }
        // save to local cache
        new UserDataController<Patient>(context).saveUserLocally(patient);
    }

    /**
     * Saves CareProvider object to server if internet is available and the server is reachable. Always
     * saves CareProvider object locally. Creates toast message to confirm success or failure of saving
     * CareProvider to server.
     *
     * @param context input context needed to access Shared Preferences
     * @param careProvider input CareProvider to save
     */
    public static void saveCareProviderData(Context context, CareProvider careProvider) {
        // save online if possible
        if (ElasticsearchController.testConnection(context)) {
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
            ElasticsearchController.AddCareProvider addCareProviderTask = new ElasticsearchController.AddCareProvider();
            addCareProviderTask.execute(careProvider);
        } else{
            Toast.makeText(context, "Could not reach server. Changes saved locally.", Toast.LENGTH_LONG).show();
            Toast.makeText(context, "Sync data when a connection is available to save changes to server.", Toast.LENGTH_LONG).show();
        }
        // save to local cache
        new UserDataController<CareProvider>(context).saveUserLocally(careProvider);
    }

    // Method taken from Abram Hindle's Student Picker for android series: https://www.youtube.com/watch?v=5PPD0ncJU1g
    // serialize and save object to shared preferences
    private void saveUserLocally(E user) {
        SharedPreferences myPrefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putString(userKey, objectToString(user));
        editor.apply();
    }

    // Method taken from Abram Hindle's Student Picker for android series: https://www.youtube.com/watch?v=5PPD0ncJU1g
    // load serialized string of user from shared preferences
    // call method to convert serialized string back to user object
    private E loadUserLocally() {
        SharedPreferences myPrefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String userString = myPrefs.getString(userKey, "");
        E user = objectFromString(userString);
        if (user == null) {
            return null;
        }
        return user;
    }

    // Method taken from Abram Hindle's Student Picker for android series: https://www.youtube.com/watch?v=5PPD0ncJU1g
    // convert object to serialized string
    private String objectToString(E data) {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(data);
            oo.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        byte[] bytes = bo.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    // Method taken from Abram Hindle's Student Picker for android series: https://www.youtube.com/watch?v=5PPD0ncJU1g
    // convert serialized string to object
    private E objectFromString(String data) {
        ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(data, Base64.DEFAULT));
        E user = null;
        try {
            ObjectInputStream oi = new ObjectInputStream(bi);
            user = (E) oi.readObject();
        } catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        return user;
    }

    /**
     * Replaces server data of current logged in patient with local cache if an internet connection is found. Otherwise simply
     * creates a toast message indicating failure to sync.
     *
     * @param context input context to use to access Shared Preferences
     */
    public static void syncPatientData(Context context) {
        if (ElasticsearchController.testConnection(context)) {
            // upload cached user data
            Patient user = new UserDataController<Patient>(context).loadUserLocally();
            UserDataController.savePatientData(context, user);
        } else {
            Toast.makeText(context, "No internet connection available. Unable to sync.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Replaces server data of current logged in CareProvider with local cache if an internet connection is found. Otherwise simply
     * creates a toast message indicating failure to sync.
     *
     * @param context input context to use to access Shared Preferences
     */
    public static void syncCareProviderData(Context context) {
        if (ElasticsearchController.testConnection(context)) {
            // upload cached user data
            CareProvider user = new UserDataController<CareProvider>(context).loadUserLocally();
            UserDataController.saveCareProviderData(context, user);
            for(Patient patient: user.getPatientList()){
                UserDataController.savePatientData(context, patient);
            }
        } else {
            Toast.makeText(context, "No internet connection available. Unable to sync.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Serialize record into a string and return that string.
     *
     * @param context input context to initialize a new UserDataController
     * @param record input record to create a serialized string of
     * @return serialized string of record
     */
    public static String serializeRecord(Context context, PatientRecord record){
      return new UserDataController<PatientRecord>(context).objectToString(record);
    }

    /**
     * Convert serialized record string back into a PatientRecord object
     * @param context input context to initialize a new UserDataController
     * @param recordString input serialized string of record to convert back into a PatientRecord object
     * @return PatientRecord object corresponding to serialized record string
     */
    public static PatientRecord unSerializeRecord(Context context, String recordString){
        return new UserDataController<PatientRecord>(context).objectFromString(recordString);
    }


}
