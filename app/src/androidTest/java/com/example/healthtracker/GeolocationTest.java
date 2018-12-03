package com.example.healthtracker;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.example.healthtracker.Activities.EditProblem;
import com.example.healthtracker.Activities.LoginActivity;
import com.example.healthtracker.View.AddProblemView;
import com.example.healthtracker.View.AddorEditRecordView;
import com.example.healthtracker.View.PatientHomeView;
import com.example.healthtracker.View.ViewMyProblems;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class GeolocationTest {
    @Rule
    public final ActivityTestRule<LoginActivity> activityTestRule =
            new ActivityTestRule<>(LoginActivity.class);
    private Solo solo;

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),
                activityTestRule.getActivity());
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }

    @Test
    public void testAddGeoLocation() {
        // login
        EditText name = (EditText) solo.getView("userID");
        solo.enterText(name, "testingcode12");
        solo.clickOnButton(solo.getString(R.string.login));

        solo.clickOnButton(solo.getString(R.string.add_problem));

        solo.waitForActivity(AddProblemView.class, 6000);
        solo.assertCurrentActivity("Problem activity", AddProblemView.class);
        EditText title = (EditText) solo.getView(R.id.title_text);
        solo.enterText(title, "Black Plague");
        solo.clickOnButton(solo.getString(R.string.add_record_from_add));
        solo.waitForActivity(AddorEditRecordView.class, 6000);

        // add the geolocation bit

        solo.clickOnButton(solo.getString(R.string.save_record_button));

    }

    @Test
    public void testAddGeoLocationFromEdit() {
        /// login
        EditText name = (EditText) solo.getView("userID");
        solo.enterText(name, "testingcode12");
        solo.clickOnButton(solo.getString(R.string.login));


        solo.assertCurrentActivity("wrong activity", PatientHomeView.class);
        solo.clickOnButton(solo.getString(R.string.view_myproblems));

        solo.waitForActivity(ViewMyProblems.class, 6000);
        solo.assertCurrentActivity("Should be problem list.", ViewMyProblems.class);

        solo.clickInList(0);
        solo.clickOnButton("Edit/View");

        solo.waitForActivity(EditProblem.class, 6000);
        solo.assertCurrentActivity("Should be edit problem activity.", EditProblem.class);

        solo.clickOnButton(solo.getString(R.string.add_record_from_edit));

        solo.waitForActivity(AddorEditRecordView.class, 6000);
        solo.assertCurrentActivity("Should be Add record activity.", AddorEditRecordView.class);

        // add the geo bit

        solo.clickOnButton(solo.getString(R.string.save_record_button));

        solo.waitForActivity(EditProblem.class, 6000);
        solo.assertCurrentActivity("Problem", EditProblem.class);

        solo.clickInList(0);
        solo.clickOnButton("Edit/View");

        solo.waitForActivity(AddorEditRecordView.class, 6000);
        solo.assertCurrentActivity("Record activity", AddorEditRecordView.class);
    }
}