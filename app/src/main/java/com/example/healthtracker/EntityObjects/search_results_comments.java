package com.example.healthtracker.EntityObjects;


import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.R;

import java.util.ArrayList;

/**
 * search_results_comments is the generated object from calls to the search activity when
 * a user attempts search functionality resulting in CareProvider comments containing
 * user inputted search words. Populates the text views with the corresponding comment data.
 *
 * @author Michael Boisvert
 * @version 1.0
 * @since 2018-11-29
 */
public class search_results_comments extends Activity {

    /**
     * On creation or launch the CareProvider comment object is populated with the
     * appropriate data to display the comment contents
     *
     * @param savedInstanceState related to bundle in onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_comments);

        Object[] hits = UserDataController
                .unserializeObjectArray(this, getIntent().getStringExtra("hits"));
        int index = getIntent().getIntExtra("problemIndex", -1);

        ArrayList<Problem> problems = (ArrayList<Problem>) hits[0];
        ArrayList<CareProviderComment> comments = problems.get(index).getcaregiverRecords();

        ArrayAdapter<CareProviderComment> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, comments);

        ListView commentList = findViewById(R.id.search_comments);
        commentList.setAdapter(adapter);
    }

}
