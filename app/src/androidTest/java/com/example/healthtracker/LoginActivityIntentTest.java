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

import static junit.framework.TestCase.assertTrue;

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
        boolean result_2 = solo.waitForActivity(CreateAccountActivity.class, 5000);
        Assert.assertTrue(result_2);
        solo.goBack();

        // test login with empty fields
        EditText name = (EditText) solo.getView("userID");
        solo.clickOnButton(solo.getString(R.string.login));
        assertTrue("Login not successful", solo.searchText("Either field not filled"));
        solo.clearEditText(name);

        // test login with correct userID
        solo.enterText(name, "testingcode12");
        solo.clickOnButton(solo.getString(R.string.login));
        boolean result = solo.waitForActivity(PatientHomeView.class, 5000);
        Assert.assertTrue(result);
        solo.clickOnButton(solo.getString(R.string.logout));
        boolean result2 = solo.waitForActivity(LoginActivity.class, 5000);
        Assert.assertTrue(result2);

        // test login with correct code
        solo.clearEditText(name);
        EditText code = (EditText) solo.getView("codeLogin");
        solo.enterText(code,"MURH2");
        solo.clickOnButton(solo.getString(R.string.login));
        boolean result3 = solo.waitForActivity(PatientHomeView.class, 5000);
        Assert.assertTrue(result3);
        solo.goBack();

        // test login with incorrect userID
        solo.clearEditText(name);
        solo.clearEditText(code);
        solo.enterText(name, "fdasfjasdkls");
        solo.clickOnButton(solo.getString(R.string.login));
        assertTrue("Login not successful", solo.searchText("Unknown Account"));


        // test login with incorrect code
        solo.clearEditText(name);
        solo.clearEditText(code);
        solo.enterText(code,"fasfas");
        solo.clickOnButton(solo.getString(R.string.login));
        assertTrue("Login not successful", solo.searchText( "Account code not found"));

        // test id and code both filled
        solo.enterText(name,"fasfasadfass");
        solo.clickOnButton(solo.getString(R.string.login));
        assertTrue("Login not successful", solo.searchText( "Please fill only 1 field"));
    }

    @Test
    public void testCareLogin() {
        // login
        solo.clickOnCheckBox(0);

        // test login with incorrect userID
        EditText name = (EditText) solo.getView("userID");
        solo.clearEditText(name);
        solo.enterText(name, "fdasfjasdkls");
        solo.clickOnButton(solo.getString(R.string.login));
        assertTrue("Login not successful", solo.searchText("Unknown Account"));

        solo.clearEditText(name);
        solo.enterText(name, "doctortyler");
        solo.clickOnButton(solo.getString(R.string.login));
        boolean result = solo.waitForActivity(CareProviderHomeView.class, 2000);
        Assert.assertTrue(result);
    }
}
