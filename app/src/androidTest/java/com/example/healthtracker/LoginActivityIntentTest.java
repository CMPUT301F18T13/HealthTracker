package com.example.healthtracker;


import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.example.healthtracker.Activities.CreateAccountActivity;
import com.example.healthtracker.Activities.LoginActivity;
import com.example.healthtracker.View.CareProviderHomeView;
import com.example.healthtracker.View.PatientHomeView;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityIntentTest {
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
    public void testPatientLogin() {
        solo.clickOnButton(solo.getString(R.string.create_account));
        boolean result_2 = solo.waitForActivity(CreateAccountActivity.class, 1000);
        Assert.assertTrue(result_2);
        solo.goBack();
        EditText name = (EditText) solo.getView("userID");
        solo.enterText(name, "testingcode12");
        solo.clickOnButton(solo.getString(R.string.login));
        boolean result = solo.waitForActivity(PatientHomeView.class, 2000);
        Assert.assertTrue(result);
    }

    @Test
    public void testCareLogin() {
        // login
        solo.clickOnCheckBox(0);
        EditText name = (EditText) solo.getView("userID");
        solo.enterText(name, "doctortyler");
        solo.clickOnButton(solo.getString(R.string.login));
        boolean result = solo.waitForActivity(CareProviderHomeView.class, 2000);
        Assert.assertTrue(result);
    }
}
