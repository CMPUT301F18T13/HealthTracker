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
public class PatientProblemViewIntentTest {

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
    public void testEditProblem() {
        EditText name = (EditText) solo.getView("userID");
        EditText pwd = (EditText) solo.getView("login_password");
        solo.enterText(name, "");
        solo.enterText(pwd, "");
        solo.enterText(name, "foreverznb");
        solo.enterText(pwd, "ZHUNINGBO1234");
        solo.clickOnView(solo.getView("login_button"));
        solo.clickOnView(solo.getView(R.id.view_problems));
        solo.clickInList(1);
        solo.waitForDialogToOpen(1000);
        solo.clickOnButton("Edit/View");
        boolean result_1 = solo.waitForActivity(EditProblem.class, 2000);
        Assert.assertEquals(true, result_1);
        solo.goBack();
        solo.clickInList(1);
        solo.waitForDialogToOpen(1000);
        solo.clickOnButton("Cancel");
        boolean result_2 = solo.waitForActivity(ViewMyProblems.class, 2000);
        Assert.assertEquals(true, result_2);
        solo.clickInList(1);
        solo.waitForDialogToOpen(1000);
        solo.clickOnButton("Delete");
        boolean result_3 = solo.waitForActivity(ViewMyProblems.class, 2000);
        Assert.assertEquals(true, result_3);
        solo.goBack();

    }
}