package com.example.healthtracker;


import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;

import com.example.healthtracker.Activities.LoginActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class AddProblemViewTest {
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
    public void testAddProblem(){
        // First step: Log in
        EditText userID = (EditText) solo.getView("userID");
        EditText password = (EditText) solo.getView("login_password");
        solo.enterText(userID,"chenlinPatient");
        solo.enterText(password,"passwords");
        solo.clickOnView(solo.getView("login_button"));

        // Second step: Add a problem
        solo.clickOnView(solo.getView(R.id.add_problem));

        EditText title = (EditText) solo.getView(R.id.title_text);
        EditText dateStarted = (EditText) solo.getView(R.id.date_started_editable);
        EditText problemDescription = (EditText) solo.getView(R.id.problem_description_edit);
        solo.enterText(title,"Rashes");
        solo.enterText(dateStarted,"2018-07-10");
        solo.enterText(problemDescription,"Red spots on my left ear");

        solo.clickOnView(solo.getView(R.id.add_problem_button));
        assertTrue("Success message is not displayed",solo.searchText("Success"));
    }
}
