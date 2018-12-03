package com.example.healthtracker.Contollers;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;


import com.example.healthtracker.EntityObjects.CareProviderComment;
import com.example.healthtracker.EntityObjects.PatientRecord;

import com.example.healthtracker.EntityObjects.CareProvider;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.Problem;


import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.mapping.PutMapping;

import static org.elasticsearch.index.query.QueryStringQueryBuilder.Operator.AND;



/* General ideas for how to implement basic elastic search features from
*CMPUT301W18T17, https://github.com/CMPUT301W18T17/TheProfessionals , 2018/04/09, viewed 2018/10/13* with apache
license identified and documented at https://github.com/CMPUT301W18T17/TheProfessionals/blob/master/LICENSE
*/


/* ElasticSearch code implementation ideas for verifySettings from the ElasticLab search code discussed in Lab 5 on Oct 4, 2018*/


/* ElasticSearch functionality licesned from
 *https://creativecommons.org/licenses/by-nc-nd/3.0/legalcode, viewed 2018/11/14*
 */

/**
 * ElasticsearchController enables a user to communicate with the ElasticSearch database for the purposes of storing and accessing users.
 *
 * @author Tyler Watson
 * @version 1.0
 * @since 2018-10-30
 */
public class ElasticsearchController {
    private static JestDroidClient client;
    private static final String Index = "cmput301f18t13";


    /**
     * Initialize Jest Droid Client if it has not already been done.
     * Client is initialized with the server that will be saved to with elastic search.
     *
     * Method taken from CMPUT301 lab tutorial: https://github.com/watts1/lonelyTwitter.git
     *
     */
    private static void verifySettings() {
        if (client == null) {
            // if the client is not yet created, build the client factory, establish connection to the DB, and finally set the client and its factory
            String server = "http://es2.softwareprocess.ca:8080/";
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

            Index index = new Index.Builder(patient)
                    .index(Index)
                    .type("Patient")
                    .id(patient.getUserID())
                    .build();

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

    public static class AddProblem extends AsyncTask<Problem, Void, Void> {
        @Override
        protected Void doInBackground(Problem... problems) {
            verifySettings();
            Problem problem = problems[0];

            Index index = new Index.Builder(problem)
                    .index(Index)
                    .type("Problem")
                    .build();

            try {
                // where is the client?
                DocumentResult result = client.execute(index);

                if (!result.isSucceeded()) {
                    Log.i("Error", "Elasticsearch was not able to add the problem");
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and add the problem");
            }
            return null;
        }
    }

    public static class AddRecord extends AsyncTask<PatientRecord, Void, Void> {
        @Override
        protected Void doInBackground(PatientRecord... records) {
            verifySettings();
            PatientRecord record = records[0];


            // Create an Index Mapping
            PutMapping putMapping = new PutMapping.Builder(
                    Index,
                    "Record",
                    "{ \"Record\" : { " +
                            "   \"properties\" : { " +
                            "       \"RecordTitle\" : { " +
                            "           \"type\" : " + "\"string\" " +
                            "         }," +
                            "        \"comment\" : { " +
                            "           \"type\" : " + "\"string\" " +
                            "         }," +
                            "        \"geoLocations\" : { "+
                            "           \"type\" : " + "\"geo_point\" " +
                            "         }," +
                            "        \"timestamp\" : { " +
                            "           \"type\" : " + " \"date\" " +
                            "         } " +
                            "     } " +
                            "   } " +
                            "}"
            ).build();

            try{
                client.execute(putMapping);

            }catch (IOException e){
                Log.i("Error", "The application failed to build the mapping");
            }

            Index index = new Index.Builder(record)
                    .index(Index)
                    .type("Record")
                    .build();


            try {
                // where is the client?
                DocumentResult result = client.execute(index);

                if (!result.isSucceeded()) {
                    Log.i("Error", "Elasticsearch was not able to add the record");
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and add the record");
            }

            return null;

        }



    }

    public static class AddComment extends AsyncTask<CareProviderComment, Void, Void> {
        @Override
        protected Void doInBackground(CareProviderComment... records) {
            verifySettings();
            CareProviderComment record = records[0];

            Index index = new Index.Builder(record)
                    .index(Index)
                    .type("CommentRecord")
                    .build();

            try {
                // where is the client?
                DocumentResult result = client.execute(index);

                if (!result.isSucceeded()) {
                    Log.i("Error", "Elasticsearch was not able to add the comment)");
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and add the comment");
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
            ArrayList<Patient> patients = new ArrayList<>() ;
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
                assert result != null;
                patients_list = result.getSourceAsObjectList(Patient.class);
                System.out.println(patients_list);
                // Convert patients_list (List) to patients (ArrayList)
                patients.addAll(patients_list);

            }catch(IOException e){
                Log.i("error","search failed");
            }

            return patients;

        }
    }

    public static class SearchForPatient extends AsyncTask<String, Void, Patient> {
        @Override
        protected Patient doInBackground(String... params) {
            verifySettings();

            String searchType = params[0];
            String keyword = params[1];

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchQuery(searchType, keyword));

            Search search = new Search.Builder(searchSourceBuilder.toString())
                    .addIndex(Index)
                    .addType("Patient")
                    .build();

            SearchResult result = null;

            try {
                result = client.execute(search);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(result == null){
                return null;
            }

            return result.getSourceAsObject(Patient.class, false);
        }
    }

    public static class SearchByKeyword extends AsyncTask<String, Void, SearchResult> {
        @Override
        protected SearchResult doInBackground(String... params) {
            verifySettings();

            String searchType = params[0];
            String keyword = params[1];

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.queryString(keyword).defaultOperator(AND));
            Search search = new Search.Builder(searchSourceBuilder.toString())
                    .addIndex(Index)
                    .addType(searchType)
                    .build();

            SearchResult result = null;

            try {
                result = client.execute(search);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return result;
        }
    }

    public static class SearchByGeoLocations extends AsyncTask<String,Void,SearchResult> {
        @Override
        protected SearchResult doInBackground(String...params) {
            verifySettings();

            String searchType = params[0];
            String keyDistance = params[1];
            String latitude = params[2];
            String longitude = params[3];
            String identifier = params[4];

            //SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            // Create a geoLocationQuery

            String geoLocationQuery = "{\n" +
                    "    \"query\" : {\n" +
                    "        \"filtered\" : {\n" +
                    "            \"query\" : {\n" +
                    "            \t\"term\" : {\"_id\" : \"" + identifier + "\" " + "}\n" +
                    "            },\n" +
                    "            \"filter\" : {\n" +
                    "                \"geo_distance\" : {\n" +
                    "                    \"distance\" : \" " + keyDistance + "km\",\n" +
                    "                    \"geoLocations\" : [" + longitude + "," + latitude+ "]\n" +
                    "                }\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";

            Search search = new Search.Builder(geoLocationQuery)
                    .addIndex(Index)
                    .addType(searchType)
                    .build();

            try {
                SearchResult result = client.execute(search);
                if (result == null) {
                    System.out.println("result is null");
                } else {
                    System.out.println("result not null");
                    System.out.println("result is "+result);
                }

                return result;

            }catch (IOException e){
                e.printStackTrace();
                }
            return null;
        }
    }

    public static class SearchByBodyLocations extends AsyncTask<String,Void,SearchResult>{
        @Override
        protected SearchResult doInBackground(String...params){
            verifySettings();

            String searchType = params[0];
            String locationText = params[1];
            String identifier = params[2];

            // Create a matchBodyLocationQuery

            String matchBodyLocationQuery = "{\t\n" +
                    "\t\"query\" : {\n" +
                    "\t\t\"bool\" : {\n" +
                    "\t\t\t\"must\" : [\n" +
                    "\t\t\t\t{ \"match\" : { \"bodyLocation.locationText\" : \"" + locationText +"\" }},\n" +
                    "\t\t\t\t{ \"match\" : {\"_id\" : \"" +identifier +"\" }}\n" +
                    "\t\t\t]\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "}";

            Search search = new Search.Builder(matchBodyLocationQuery)
                    .addIndex(Index)
                    .addType(searchType)
                    .build();

            try{
                SearchResult result = client.execute(search);
                if(result == null){
                    System.out.println("result is NULL");
                }
                else{
                    System.out.println("result is NOT null");
                    System.out.println("result is "+result);
                }
                return result;

            }catch (IOException e){
                e.printStackTrace();
            }

            return null;

        }
    }
}

