package com.example.healthtracker.View;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.healthtracker.Activities.EditProblem;
import com.example.healthtracker.Contollers.PhotoController;
import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.Problem;
import com.example.healthtracker.R;

import java.util.ArrayList;

/*
 * Enables patients to view a list of their own problems. Selecting a problem from the list will give
 * the patient the choice to edit/view or delete the problem. Selecting edit/view will initiate the
 * EditProblem activity.
 */
public class ViewMyProblems extends AppCompatActivity {

    private ArrayList<Problem> mProblems;
    private ListView mListView;
    private Context context;
    private ArrayAdapter<Problem> mArrayAdapter;
    private Patient user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_problems);

        // get context
        context = this;

    }

    @Override
    public void onResume(){
        super.onResume();

        // Load patient data
        user = UserDataController.loadPatientData(this);
        mProblems = user.getProblemList();

        // Create an instance of an array adapter
        mArrayAdapter = new ArrayAdapter<Problem>(this, android.R.layout.simple_list_item_1, mProblems);

        // Set an adapter for the list view
        mListView = findViewById(R.id.problem_list_view);
        mListView.setAdapter(mArrayAdapter);

        // Create a context menu to permit users to select and edit a problem
        registerForContextMenu(mListView);
        mListView.setOnCreateContextMenuListener(this);

        // Add listener to detect button click on items in listview
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // method to initiate after listener detects click
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // create an alert dialog via the alert dialog builder to help build dialog to specifics
                AlertDialog.Builder ab = new AlertDialog.Builder(ViewMyProblems.this);
                // set dialog message to edit entry to appear at grabbed position
                ab.setMessage("Problem Options:" + mProblems.get(position).toString() + "\n");
                // set the dialog to be cancelable outside of box
                ab.setCancelable(true);


                // set a negative button for deleting problem
                ab.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete problem
                        ContextWrapper cw = new ContextWrapper(getApplicationContext());
                        PhotoController.removePhotosByProblem(cw, mProblems.get(position).getTitle());
                        mProblems.remove(position);
                        user.setProblems(mProblems);
                        UserDataController.savePatientData(context, user);

                        // update listview
                        mArrayAdapter.notifyDataSetChanged();

                        // done
                        dialog.dismiss();

                    }
                });

                ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                // set a neutral button in the dialog which will open up the edit activity to edit problem
                ab.setNeutralButton("Edit/View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Create an intent object containing the bridge to between the two activities
                        Intent intent = new Intent(ViewMyProblems.this, EditProblem.class);

                        // store problem index
                        intent.putExtra("Index", position);

                        // Launch the browse emotions activity
                        startActivity(intent);
                    }
                });

                // required in order for dialog object to appear on screen
                ab.show();
            }
        });

    }
}
