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
public class AddPatientViewTest  {

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
    public void testAddPatient(){
        // First step: Log in
        EditText userID = (EditText) solo.getView("userID");
        EditText password = (EditText) solo.getView("login_password");
        solo.enterText(userID,"chenlin2");
        solo.enterText(password,"passwords");
        solo.clickOnView(solo.getView("CareGiverLogin"));
        solo.clickOnView(solo.getView("login_button"));

        // Second step: Add a patient
        // Case 1: A care giver add a new patient with invalid id
        solo.clickOnView(solo.getView(R.id.add_patient));
        EditText patientId = (EditText) solo.getView(R.id.editText4);
        solo.enterText(patientId,"invalidPatient");
        solo.clickOnView(solo.getView(R.id.add_patient_button));
        assertTrue("Could not find the dialog!", solo.searchText("The patient ID is not valid. Please try again."));

        // Case 2: A care giver add a new patient with valid id
        solo.clearEditText(patientId);
        solo.enterText(patientId,"chenlin");
        solo.clickOnView(solo.getView(R.id.add_patient_button));
        assertTrue("Could not find the toast message",solo.searchText("Patient Added"));

        // Case 3: A care giver tries to add an existing patient
        solo.clickOnView(solo.getView(R.id.add_patient));
        solo.enterText(patientId,"chenlin");
        solo.clickOnView(solo.getView(R.id.add_patient_button));
        assertTrue("Could not find the dialog!",solo.searchText("This patient has already been assigned you."));



    }
}
