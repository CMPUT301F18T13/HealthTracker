package com.example.healthtracker;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.example.healthtracker.Activities.LoginActivity;
import com.example.healthtracker.View.AddorEditRecordView;
import com.example.healthtracker.View.ViewMyProblems;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class EditProblemIntentTest {


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
        EditText name = (EditText) solo.getView("userID");
        solo.enterText(name, "testingcode12");
        solo.clickOnView(solo.getView("login_button"));

        solo.clickOnView(solo.getView("view_problems"));
        solo.clickInList(1);
        solo.waitForDialogToOpen(1000);
        solo.clickOnButton("Edit/View");

        EditText title = (EditText) solo.getView("title_text_editscreen");
        EditText des = (EditText) solo.getView("problem_description_editscreen");

        solo.clearEditText(title);
        solo.enterText(title, "Hungry");
        solo.clickOnButton(solo.getString(R.string.pick_date2));
        solo.setDatePicker(0, 2012, 2, 16);
        solo.clickOnText("OK");
        solo.clearEditText(des);
        solo.enterText(des, "I am hungry!!!!!!!");

        solo.clickOnView(solo.getView(R.id.edit_save));
        boolean result_1 = solo.waitForActivity(ViewMyProblems.class, 2000);
        Assert.assertTrue(result_1);

        solo.clickInList(0);
        solo.waitForDialogToOpen(1000);
        solo.clickOnButton("Edit/View");

        solo.clickOnButton(solo.getString(R.string.add_record_from_edit));
        boolean result_2 = solo.waitForActivity(AddorEditRecordView.class, 2000);
        Assert.assertTrue(result_2);
        solo.goBack();


    }
}



