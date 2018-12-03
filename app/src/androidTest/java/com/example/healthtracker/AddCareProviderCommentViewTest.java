package com.example.healthtracker;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;

import com.example.healthtracker.Activities.LoginActivity;
import com.example.healthtracker.View.AddCareProviderCommentView;
import com.example.healthtracker.View.CareProviderProblemView;
import com.example.healthtracker.View.ViewCareProviderComments;
import com.example.healthtracker.View.ViewPatients;
import com.example.healthtracker.View.ViewPatientsProblems;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


public class AddCareProviderCommentViewTest {
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
    public void testAddCareProviderCommentView() {
        /// login
        solo.clickOnCheckBox(0);
        EditText name = (EditText) solo.getView("userID");
        solo.enterText(name, "doctortyler");
        solo.clickOnButton(solo.getString(R.string.login));

        // Second step: View patients
        solo.clickOnView(solo.getView(R.id.view_problems));

        solo.waitForActivity(ViewPatients.class, 6000);
        solo.assertCurrentActivity("Should be in ViewPatient activity", ViewPatients.class);

        // Third step: Click on the first patient shown in the patient list if at least a patient exists
        solo.clickInList(0, 0);

        solo.waitForActivity(ViewPatientsProblems.class, 6000);
        solo.assertCurrentActivity("Should be in ViewPatientsProblems activity", ViewPatientsProblems.class);

        solo.clickInList(0, 0);

        solo.waitForActivity(CareProviderProblemView.class, 6000);
        solo.assertCurrentActivity("Should be in CareProviderProblemView activity", CareProviderProblemView.class);

        solo.clickOnView(solo.getView(R.id.add_comment_button));

        solo.waitForActivity(AddCareProviderCommentView.class, 2000);
        solo.assertCurrentActivity("Should be in AddCareProviderCommentView activity", AddCareProviderCommentView.class);

        EditText title = (EditText) solo.getView(R.id.care_comment_title);
        solo.enterText(title,"IntentTestTitle");
        EditText comment = (EditText) solo.getView(R.id.care_comment);
        solo.enterText(comment,"IntentTestComment");
        solo.clickOnButton(solo.getString(R.string.save_care_comment));

        solo.waitForActivity(CareProviderProblemView.class, 6000);
        solo.assertCurrentActivity("Should be in CareProviderProblemView activity", CareProviderProblemView.class);

        solo.clickOnView(solo.getView(R.id.view_comments));

        solo.waitForActivity(ViewCareProviderComments.class, 6000);
        solo.assertCurrentActivity("Should be in ViewCareProviderComments activity", ViewCareProviderComments.class);
    }
}
