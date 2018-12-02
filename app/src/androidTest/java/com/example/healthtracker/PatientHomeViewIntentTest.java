package com.example.healthtracker;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;

import com.example.healthtracker.Activities.LoginActivity;
import com.example.healthtracker.Activities.SearchActivity;
import com.example.healthtracker.Activities.UserSettingsActivity;
import com.example.healthtracker.View.AddProblemView;
import com.example.healthtracker.View.ViewMyProblems;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PatientHomeViewIntentTest {


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
    public void testButtons() {
        //solo.clickOnButton("add_problem");
        solo.clickOnButton(solo.getString(R.string.add_problem));
        boolean result_1 = solo.waitForActivity(AddProblemView.class, 2000);
        Assert.assertTrue(result_1);
        solo.goBack();
        solo.clickOnButton(solo.getString(R.string.view_myproblems));
        boolean result_2 = solo.waitForActivity(ViewMyProblems.class, 6000);
        Assert.assertTrue(result_2);
        solo.goBack();
        solo.clickOnButton(solo.getString(R.string.settings));
        boolean result_4 = solo.waitForActivity(UserSettingsActivity.class, 2000);
        Assert.assertTrue(result_4);
        solo.goBack();
        solo.clickOnButton(solo.getString(R.string.search));
        boolean result_5 = solo.waitForActivity(SearchActivity.class, 2000);
        Assert.assertTrue(result_5);
        solo.goBack();
        solo.clickOnButton(solo.getString(R.string.logout));
        boolean result_3 = solo.waitForActivity(LoginActivity.class, 2000);
        Assert.assertTrue(result_3);
    }
}



