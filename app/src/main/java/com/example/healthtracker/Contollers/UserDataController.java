package com.example.healthtracker.Contollers;

import android.content.Context;
import android.content.SharedPreferences;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AlertDialog;

import android.util.Base64;
import android.widget.Toast;

import org.elasticsearch.common.geo.GeoPoint;

import com.example.healthtracker.EntityObjects.CareProvider;
import com.example.healthtracker.EntityObjects.CareProviderComment;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.PatientRecord;
import com.example.healthtracker.EntityObjects.Problem;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final Context context;

    private UserDataController(Context context) {
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
            ArrayList<Patient> patients = careProvider.getPatientList();
            for(int i = 0; i<patients.size(); i++){
                Patient patient = loadPatientById(context, patients.get(i).getUserID());
                patients.set(i, patient);
            }
            careProvider.setPatientList(patients);
            return careProvider;
        } else {
            // Load local cache of user data
            return new UserDataController<CareProvider>(context).loadUserLocally();
        }
    }

    /**
     * Retrieves the CareProvider object to who the ID input belongs to. Retrieves from server
     * if an internet connection is available or from the local cache otherwise.
     * @param context The context in which to access local cache if necessary.
     * @param ID The ID of the desired CareProvider.
     * @return The CareProvider to whom the ID input belongs to.
     */
    public static CareProvider loadCareProviderByID(Context context, String ID) {
        if (ElasticsearchController.testConnection(context)) {
            // Download user data with elastic search
            SharedPreferences myPrefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
            ElasticsearchController.GetCareProvider getCareProvider = new ElasticsearchController.GetCareProvider();
            getCareProvider.execute(ID);
            CareProvider careProvider = null;
            try {
                careProvider = getCareProvider.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if(careProvider != null){
                ArrayList<Patient> patients = careProvider.getPatientList();
                for(int i = 0; i<patients.size(); i++){
                    Patient patient = loadPatientById(context, patients.get(i).getUserID());
                    patients.set(i, patient);
                }
                careProvider.setPatientList(patients);
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
    private static Patient loadPatientById(Context context, String ID) {
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
            for(Problem problem: patient.getProblemList()){
                saveProblemData(problem, context);
            }
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
            // update patient data
            for(Patient patient: careProvider.getPatientList()){
                savePatientData(context, patient);
            }
        } else{
            Toast.makeText(context, "Could not reach server. Changes saved locally.", Toast.LENGTH_LONG).show();
            Toast.makeText(context, "Sync data when a connection is available to save changes to server.", Toast.LENGTH_LONG).show();
        }
        // save to local cache
        new UserDataController<CareProvider>(context).saveUserLocally(careProvider);
    }

    /**
     * Get the most up to date CareProvider data then add any new CareProvider comments to it. Save
     * the data to server if possible and to cache always.
     *
     * @param context The context in which to access shared preferences.
     * @param patient The patient to whom new CareProviderComments were added.
     * @param patientNum The index of the updated patient in the CareProvider's patientList.
     */
    public static void saveCareProviderComments(Context context, Patient patient, int patientNum){
        CareProvider careProvider = loadCareProviderData(context);
        careProvider.setPatient(patient, patientNum);
        UserDataController.saveCareProviderData(context, careProvider);
        UserDataController.savePatientData(context, patient);
        for(Problem problem: patient.getProblemList()){
            for(CareProviderComment comment: problem.getcaregiverRecords()){
                ElasticsearchController.AddComment addCommentTask = new ElasticsearchController.AddComment();
                addCommentTask.execute(comment);
            }
        }
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
            for(Problem problem: user.getProblemList()){
                UserDataController.saveProblemData(problem, context);
            }
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
                for(Problem problem: patient.getProblemList()){
                    for(CareProviderComment comment: problem.getcaregiverRecords()){
                        ElasticsearchController.AddComment addCommentTask = new ElasticsearchController.AddComment();
                        addCommentTask.execute(comment);
                    }
                }
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

    public static String serializeObjectArray(Context context, Object[] objects){
        return new UserDataController<Object[]>(context).objectToString(objects);
    }

    public static Object[] unserializeObjectArray(Context context, String data){
        return new UserDataController<Object[]>(context).objectFromString(data);
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

    public static Patient searchForPatient(String searchType, String searchTerm){
        String[] searchInfo = new String[]{searchType, searchTerm};
        ElasticsearchController.SearchForPatient searchTask = new ElasticsearchController.SearchForPatient();
        searchTask.execute(searchInfo);
        Patient patient = null;
        try {
            patient = searchTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return patient;
    }

    public static Object[] searchForKeywords(String searchTerm){

        Object[] hits = new Object[3];

        // search for problems
        String[] searchInfo = new String[]{"Problem", searchTerm};
        ElasticsearchController.SearchByKeyword searchProblemsTask = new ElasticsearchController.SearchByKeyword();
        searchProblemsTask.execute(searchInfo);
        try {
            hits[0] = searchProblemsTask.get().getSourceAsObjectList(Problem.class, false);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // search for records
        searchInfo[0] = "Record";
        ElasticsearchController.SearchByKeyword searchRecordsTask = new ElasticsearchController.SearchByKeyword();
        searchRecordsTask.execute(searchInfo);
        try {
            hits[1] = searchRecordsTask.get().getSourceAsObjectList(PatientRecord.class, false);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        searchInfo[0] = "CommentRecord";
        ElasticsearchController.SearchByKeyword searchCommentsTask = new ElasticsearchController.SearchByKeyword();
        searchCommentsTask.execute(searchInfo);
        try {
            hits[2] = searchCommentsTask.get().getSourceAsObjectList(CareProviderComment.class, false);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return hits;
    }

    // Search By Geo-Locations
    public static Object[] searchForGeoLocations(String distance,Double latitude,Double longitude,String identifier){

        // Create an Object array which can hold 3 items
        Object[] hits = new Object[3];

        // Search for problem
        hits[0] = new ArrayList<Problem>();

        // Search for records: Initialize a String Array
        String searchInfo[] = new String[]{"Record",distance,latitude.toString(),longitude.toString(),identifier};
        ElasticsearchController.SearchByGeoLocations searchRecordsTask = new ElasticsearchController.SearchByGeoLocations();
        searchRecordsTask.execute(searchInfo);

        try {
            hits[1] = searchRecordsTask.get().getSourceAsObjectList(PatientRecord.class,false);

        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        // Search for commentRecords
        hits[2] = new ArrayList<CareProviderComment>();

        return hits;

    }

    public static void saveProblemData(Problem problem, Context context){
        ElasticsearchController.AddProblem addProblem = new ElasticsearchController.AddProblem();
        addProblem.execute(problem);
        for(PatientRecord record: problem.getRecords()){
            ElasticsearchController.AddRecord addRecord = new ElasticsearchController.AddRecord();
            addRecord.execute(record);
        }
    }




}
