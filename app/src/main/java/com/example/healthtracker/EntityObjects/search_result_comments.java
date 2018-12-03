package com.example.healthtracker.EntityObjects;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.CareProviderComment;
import com.example.healthtracker.EntityObjects.Problem;
import com.example.healthtracker.R;

import java.util.ArrayList;

public class search_result_comments extends Activity {

    private Object[] hits;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_comments);

        hits = UserDataController
                .unserializeObjectArray(this, getIntent().getStringExtra("hits"));
        index = getIntent().getIntExtra("problemIndex", -1);

        ArrayList<Problem> problems = (ArrayList<Problem>)hits[0];
        ArrayList<CareProviderComment> comments = problems.get(index).getcaregiverRecords();

        ArrayAdapter<CareProviderComment> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, comments);

        ListView commentList = findViewById(R.id.search_comments);
        commentList.setAdapter(adapter);
    }

}
