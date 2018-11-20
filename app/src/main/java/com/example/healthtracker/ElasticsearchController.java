package com.example.healthtracker;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * ElasticsearchController enables a user to communicate with the ElasticSearch database for the purposes of storing and accessing users.
 *
 * @author Tyler Watson
 * @version 1.0
 * @since 2018-10-30
 */
class ElasticsearchController {
    private static JestDroidClient client;
    private static String server = "http://cmput301.softwareprocess.es:8080";
    private static String Index = "cmput301f18t13";


    /**
     * Initialize Jest Droid Client if it has not already been done.
     * Client is initialized with the server that will be saved to with elastic search.
     *
     * Method taken from CMPUT301 lab tutorial: https://github.com/watts1/lonelyTwitter.git
     *
     */
    public static void verifySettings() {
        if (client == null) {
            // if the client is not yet created, build the client factory, establish connection to the DB, and finally set the client and its factory
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(server);
            DroidClientConfig config = builder.build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

    /**
     * Add a patient to server using elastic search. Can be used to create patient account or
     * update patient data.
     *
     * Executed using patient ID.
     */
    public static class AddPatient extends AsyncTask<Patient, Void, Void> {
        @Override
        protected Void doInBackground(Patient... patients) {
            verifySettings();
            Patient patient = patients[0];

            //TODO change when finished testing
            Index index = new Index.Builder(patient)
                    .index(Index)
                    .type("Patient")
                    .id(patient.getUserID()).build();

            try {
                // where is the client?
                DocumentResult result = client.execute(index);

                if (!result.isSucceeded()) {
                    Log.i("Error", "Elasticsearch was not able to add the user(patient)");
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and add the patient");
            }
            return null;
        }
    }

    /**
     * Add a CareProvider to server using elastic search. Can be used to create CareProvider account or
     * update CareProvider data.
     * Executed using CareProvider ID.
     */
    public static class AddCareProvider extends AsyncTask<CareProvider, Void, Void> {

        @Override
        protected Void doInBackground(CareProvider... careProviders) {
            verifySettings();
            CareProvider careProvider = careProviders[0];

            Index index = new Index.Builder(careProvider)
                    .index(Index)
                    .type("CareProvider")
                    .id(careProvider.getUserID()).build();

            try {
                // where is the client?
                DocumentResult result = client.execute(index);
                if (!result.isSucceeded()) {
                    Log.i("Error", "Elasticsearch was not able to add the user(care provider)");
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and add the CareProvider");
            }

            return null;
        }
    }

    /**
     * Get Patient object from server using elastic search.
     * Executed using patient ID.
     *
     * @return Returns Patient object to who the ID belongs.
     */
    public static class GetPatient extends AsyncTask<String, Void, Patient> {
        @Override
        protected Patient doInBackground(String... id) {
            verifySettings();
            Patient patient = null;
            Get get = new Get.Builder(Index, id[0])
                    .type("Patient")
                    .build();
            try {
                JestResult result = client.execute(get);
                if (result.isSucceeded()) {
                    patient = result.getSourceAsObject(Patient.class);
                } else {
                    Log.i("error", "Search query failed to find any thing =/");
                }
            } catch (Exception e) {
                Log.i("Error", "Could not access the server to get the patient");
            }
            return patient;
        }
    }

    /**
     * Get CareProvider object from server using elastic search.
     * Executed using CareProvider ID.
     *
     * @return Returns CareProvider object to who the ID belongs.
     */
    public static class GetCareProvider extends AsyncTask<String, Void, CareProvider> {
        @Override
        protected CareProvider doInBackground(String... id) {
            verifySettings();
            CareProvider careProvider = null;
            Get get = new Get.Builder(Index, id[0])
                    .type("CareProvider")
                    .build();
            try {
                JestResult result = client.execute(get);
                if (result.isSucceeded()) {
                    careProvider = result.getSourceAsObject(CareProvider.class);
                } else {
                    Log.i("error", "Search query failed to find any thing =/");
                }
            } catch (Exception e) {
                Log.i("Error", "Could not access the server to get the CareProvider");
            }
            return careProvider;
        }
    }

    /**
     * testConnection() checks for online connectivity on either wifi or mobile data and returns the connectivity state
     *
     * @return returns a boolean object on whether the user is connected to wifi or cellular data for online connectivity checks
     */
    public static boolean testConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            // connected to wifi
            return true;
        } else
            return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED;
        // not connected
    }

    /**
     * Get all of the patients on the server.
     *
     * @return returns an ArrayList of Patient objects containing all patients saved on the server.
     */
    public static class getAllPatients extends AsyncTask<Void,Void,ArrayList<Patient>> {
        @Override
        protected ArrayList<Patient> doInBackground(Void...params){
            ArrayList<Patient> patients = new ArrayList<Patient>() ;
            List<Patient> patients_list;
            String query = "{\n"+
                    "           \"size\": 10000,"+
                    "           \"query\": {\n" +
                    "               \"match_all\": {}\n" +
                    "             }\n" +
                    "         }";
            Log.d("query_string",query);
            Search search = new Search.Builder(query)
                            .addIndex(Index)
                            .addType("Patient")
                            .build();
            try {
                SearchResult result = client.execute(search);
                if (result == null){
                    System.out.println("result is null");
                }
                else{
                    System.out.println("result not null");
                }
                patients_list = result.getSourceAsObjectList(Patient.class);
                System.out.println(patients_list);
                // Convert patients_list (List) to patients (ArrayList)
                for(int i=0;i<patients_list.size();i++){
                    patients.add(patients_list.get(i));
                }

            }catch(IOException e){
                Log.i("error","search failed");
            }

            return patients;

        }
    }


}
