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
public class ViewPatientsTest {

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
    public void testViewPatients(){


        // First step: Log in
        EditText userID = (EditText) solo.getView("userID");
        EditText password = (EditText) solo.getView("login_password");
        solo.enterText(userID,"chenlin2");
        solo.enterText(password,"passwords");
        solo.clickOnView(solo.getView("CareGiverLogin"));
        solo.clickOnView(solo.getView("login_button"));


        // Second step: Check whether patients assigned to this care provider shows up
        solo.clickOnView(solo.getView(R.id.view_problems));

        if (solo.searchText("P")) {
            assertTrue("Patient info is not found!", solo.searchText("Patient:"));
        }
        else{
            assertTrue("Alert message is not displayed!",solo.searchText("No Patients Assigned!"));
        }
    }
}
