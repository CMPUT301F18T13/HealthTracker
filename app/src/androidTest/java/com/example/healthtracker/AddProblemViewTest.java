package com.example.healthtracker;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;

import com.example.healthtracker.Activities.EditProblem;
import com.example.healthtracker.Activities.LoginActivity;
import com.example.healthtracker.View.PatientHomeView;
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
    public void testAddProblem() {
        solo.assertCurrentActivity("wrong activity", PatientHomeView.class);
        solo.clickOnButton(solo.getString(R.string.add_problem));

        EditText title = (EditText) solo.getView(R.id.title_text);
        EditText problemDescription = (EditText) solo.getView(R.id.problem_description_edit);
        solo.clickOnButton(solo.getString(R.string.pick_date2));
        solo.setDatePicker(0, 2012, 2, 16);
        solo.clickOnText("OK");
        solo.enterText(title, "Rashes");
        solo.enterText(problemDescription, "Red spots on my left ear");

        solo.clickOnView(solo.getView(R.id.add_problem_button));
        solo.waitForActivity(PatientHomeView.class, 6000);
        solo.assertCurrentActivity("Should be edit problem activity.", PatientHomeView.class);
    }
}
