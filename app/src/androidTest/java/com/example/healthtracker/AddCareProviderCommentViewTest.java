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
        // First step: Log in
        EditText userID = (EditText) solo.getView("userID");
        EditText password = (EditText) solo.getView("login_password");
        solo.enterText(userID, "chenlin2");
        solo.enterText(password, "passwords");
        solo.clickOnView(solo.getView("CareGiverLogin"));
        solo.clickOnView(solo.getView("login_button"));

        // Second step: View patients
        solo.clickOnView(solo.getView(R.id.view_problems));

        solo.waitForActivity(ViewPatients.class, 2000);
        solo.assertCurrentActivity("Should be in ViewPatient activity", ViewPatients.class);

        // Third step: Click on the first patient shown in the patient list if at least a patient exists
        solo.clickInList(0, 0, R.id.patients_list_view);


        solo.waitForActivity(ViewPatientsProblems.class, 2000);
        solo.assertCurrentActivity("Should be in ViewPatientsProblems activity", ViewPatientsProblems.class);

        solo.clickInList(0, 0, R.id.pProblem_list_view);

        solo.waitForActivity(CareProviderProblemView.class, 2000);
        solo.assertCurrentActivity("Should be in CareProviderProblemView activity", CareProviderProblemView.class);

        solo.clickOnView(solo.getView(R.id.add_comment_button));

        solo.waitForActivity(AddCareProviderCommentView.class, 2000);
        solo.assertCurrentActivity("Should be in AddCareProviderCommentView activity", AddCareProviderCommentView.class);

        solo.enterText(solo.getEditText("care_comment_title"), "Comment title");
        solo.clickOnView(solo.getView(R.id.add_comment_button));

        solo.waitForActivity(CareProviderProblemView.class, 2000);
        solo.assertCurrentActivity("Should be in CareProviderProblemView activity", CareProviderProblemView.class);

        solo.clickOnView(solo.getView(R.id.view_comments));

        solo.waitForActivity(ViewCareProviderComments.class, 2000);
        solo.assertCurrentActivity("Should be in ViewCareProviderComments activity", ViewCareProviderComments.class);
    }
}
