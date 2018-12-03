package com.example.healthtracker;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.example.healthtracker.Activities.EditProblem;
import com.example.healthtracker.Activities.LoginActivity;
import com.example.healthtracker.View.AddProblemView;
import com.example.healthtracker.View.AddorEditRecordView;
import com.example.healthtracker.View.PatientHomeView;
import com.example.healthtracker.View.ViewMyProblems;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class AddOrEditRecordView {

    private EditText description;

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
    public void testAddAndEditRecordWhileAddingProblem() {
        solo.clickOnButton(solo.getString(R.string.add_problem));

        solo.waitForActivity(AddProblemView.class, 6000);
        solo.assertCurrentActivity("Problem activity", AddProblemView.class);
        EditText title = (EditText) solo.getView(R.id.title_text);
        solo.enterText(title, "Rashes");
        solo.clickOnButton(solo.getString(R.string.add_record_from_add));
        solo.waitForActivity(AddorEditRecordView.class, 6000);
        solo.assertCurrentActivity("Record activity", AddorEditRecordView.class);


        EditText title1 = (EditText) solo.getView(R.id.title_edit_text);
        EditText comment2 = (EditText) solo.getView(R.id.description_edit_text);
        solo.enterText(title1,"Day after injury.");
        solo.enterText(comment2,"Ankle has become more swollen.");
        Assert.assertEquals("Day after injury.", title1.getText().toString());
        Assert.assertEquals("Ankle has become more swollen.",comment2.getText().toString());

        solo.clickOnButton(solo.getString(R.string.save_record_button));

        solo.waitForActivity(AddProblemView.class, 6000);
        solo.assertCurrentActivity("Problem", AddProblemView.class);

        solo.clickInList(0);
        solo.clickOnButton("Edit/View");

        solo.waitForActivity(AddorEditRecordView.class, 6000);
        solo.assertCurrentActivity("Record activity", AddorEditRecordView.class);

    }

    @Test
    public void testAddAndEditRecordWhileEditingProblem() {
        solo.assertCurrentActivity("wrong activity", PatientHomeView.class);
        solo.clickOnButton(solo.getString(R.string.view_myproblems));

        solo.waitForActivity(ViewMyProblems.class, 6000);
        solo.assertCurrentActivity("Should be problem list.", ViewMyProblems.class);

        solo.clickInList(0);
        solo.clickOnButton("Edit/View");

        solo.waitForActivity(EditProblem.class, 6000);
        solo.assertCurrentActivity("Should be edit problem activity.", EditProblem.class);

        solo.clickOnButton(solo.getString(R.string.add_record_from_edit));

        solo.waitForActivity(AddorEditRecordView.class, 6000);
        solo.assertCurrentActivity("Should be Add record activity.", AddorEditRecordView.class);

        EditText title1 = (EditText) solo.getView(R.id.title_edit_text);
        EditText comment2 = (EditText) solo.getView(R.id.description_edit_text);
        solo.enterText(title1,"Day after injury.");
        solo.enterText(comment2,"Ankle has become more swollen.");
        Assert.assertEquals("Day after injury.", title1.getText().toString());
        Assert.assertEquals("Ankle has become more swollen.",comment2.getText().toString());
        solo.clickOnButton(solo.getString(R.string.save_record_button));

        solo.waitForActivity(EditProblem.class, 6000);
        solo.assertCurrentActivity("Problem", EditProblem.class);

        solo.clickInList(0);
        solo.clickOnButton("Edit/View");

        solo.waitForActivity(AddorEditRecordView.class, 6000);
        solo.assertCurrentActivity("Record activity", AddorEditRecordView.class);
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
        solo.assertCurrentActivity("wrong activity", PatientHomeView.class);
    }
}
