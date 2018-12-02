package com.example.healthtracker;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.example.healthtracker.Activities.EditProblem;
import com.example.healthtracker.Activities.LoginActivity;
import com.example.healthtracker.View.ViewMyProblems;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PatientEditProblemViewIntentTest {

    @Rule
    public final ActivityTestRule<LoginActivity> activityTestRule =
            new ActivityTestRule<>(LoginActivity.class);

    private Solo solo;

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),
                activityTestRule.getActivity());

        // login
        EditText name = (EditText) solo.getView("userID");
        solo.enterText(name, "testingcode12");
        solo.clickOnButton(solo.getString(R.string.login));
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }

    @Test
    public void testEditProblem() {
        solo.clickOnButton(solo.getString(R.string.view_myproblems));
        solo.clickInList(1);
        solo.waitForDialogToOpen(1000);
        solo.clickOnButton("Edit/View");
        boolean result_1 = solo.waitForActivity(EditProblem.class, 2000);
        Assert.assertTrue(result_1);
        solo.goBack();
        solo.clickInList(1);
        solo.waitForDialogToOpen(1000);
        solo.clickOnButton("Cancel");
        boolean result_2 = solo.waitForActivity(ViewMyProblems.class, 2000);
        Assert.assertTrue(result_2);
        solo.clickInList(1);
        solo.waitForDialogToOpen(1000);
        solo.clickOnButton("Delete");
        boolean result_3 = solo.waitForActivity(ViewMyProblems.class, 2000);
        Assert.assertTrue(result_3);
        solo.goBack();

    }
}