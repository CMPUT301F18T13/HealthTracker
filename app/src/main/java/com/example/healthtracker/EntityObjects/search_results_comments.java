package com.example.healthtracker.EntityObjects;


import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.R;

import java.util.ArrayList;

public class search_results_comments extends Activity {

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
                new ArrayAdapter<CareProviderComment>(this, android.R.layout.simple_list_item_1, comments);

        ListView commentList = findViewById(R.id.search_comments);
        commentList.setAdapter(adapter);
    }

}
