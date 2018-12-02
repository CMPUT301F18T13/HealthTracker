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

        // login
        solo.clickOnCheckBox(0);
        EditText name = (EditText) solo.getView("userID");
        solo.enterText(name, "doctortyler");
        solo.clickOnButton(solo.getString(R.string.login));
        solo.clickOnView(solo.getView("add_patient"));
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }

    @Test
    public void testAddinvalidPatient() {
        // Second step: Add a patient
        // Case 1: A care giver add a new patient with invalid id
        EditText patientId = (EditText) solo.getView(R.id.editText4);
        solo.enterText(patientId, "invalidPatient");
        solo.clickOnView(solo.getView(R.id.add_patient_button));
        assertTrue("Could not find the dialog!", solo.searchText("Patient code invalid. Please try again."));
    }

    @Test
    public void testAddAssignedPatient() {
        // Case 2: A care giver tries to add an existing patient
        EditText patientId = (EditText) solo.getView(R.id.editText4);
        solo.clearEditText(patientId);
        solo.enterText(patientId,"MURH2");
        solo.clickOnView(solo.getView(R.id.add_patient_button));
        assertTrue("Could not find the dialog!",solo.searchText("Patient already assigned to you."));
    }

    @Test
    public void testAddNewPatient() {
        // Case 3: A care giver add a new patient with valid id. WILL FAIL DUE TO INABILITY TO PREDICT NEW ACCOUNT CODE
        EditText patientId = (EditText) solo.getView(R.id.editText4);
        solo.clearEditText(patientId);
        solo.enterText(patientId,"VOXX5");
        solo.clickOnView(solo.getView(R.id.add_patient_button));
        assertTrue("Could not find the toast message",solo.searchText("Success"));
    }
}
