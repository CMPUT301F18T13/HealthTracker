package com.example.healthtracker;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;

import com.example.healthtracker.Activities.LoginActivity;
import com.example.healthtracker.Activities.SearchActivity;
import com.example.healthtracker.Activities.UserSettingsActivity;
import com.example.healthtracker.View.AddPatientView;
import com.example.healthtracker.View.ViewPatients;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CareProviderHomeViewIntentTest {


    @Rule
    public final ActivityTestRule<LoginActivity> activityTestRule =
            new ActivityTestRule<>(LoginActivity.class);

    private Solo solo;

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),
                activityTestRule.getActivity());

        /// login
        solo.clickOnCheckBox(0);
        EditText name = (EditText) solo.getView("userID");
        solo.enterText(name, "doctortyler");
        solo.clickOnButton(solo.getString(R.string.login));
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }

    @Test
    public void testButtons() {
        //solo.clickOnButton("add_problem");
        solo.clickOnButton(solo.getString(R.string.add_patient));
        boolean result_1 = solo.waitForActivity(AddPatientView.class, 2000);
        Assert.assertTrue(result_1);
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.view_problems));
        boolean result_2 = solo.waitForActivity(ViewPatients.class, 6000);
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
        solo.clickOnView(solo.getView(R.id.logout_button2));
        boolean result_3 = solo.waitForActivity(LoginActivity.class, 2000);
        Assert.assertTrue(result_3);
    }
}


