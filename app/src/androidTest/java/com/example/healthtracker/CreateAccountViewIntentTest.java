package com.example.healthtracker;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.healthtracker.Activities.CreateAccountActivity;
import com.example.healthtracker.Activities.LoginActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CreateAccountViewIntentTest {

    @Rule
    public final ActivityTestRule<LoginActivity> activityTestRule =
            new ActivityTestRule<>(LoginActivity.class);

    private Solo solo;
    private EditText userID;
    private EditText email;
    private EditText phone;
    private EditText password;
    private CheckBox checkBox;
    private Button createAccount;

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),
                activityTestRule.getActivity());
        solo.clickOnButton(0);
        boolean result_2 = solo.waitForActivity(CreateAccountActivity.class, 1000);
        userID = solo.getEditText(0);
        email = solo.getEditText(1);
        phone = solo.getEditText(2);
        checkBox = (CheckBox) solo.getView("caregiver_checkbox");
        createAccount = (Button) solo.getView("create_new_account_button");
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }

    @Test
    public void textDisplayedByUserID() {
        solo.enterText(userID, "jromans1");
        String text = userID.getText().toString();

        Assert.assertTrue(text.equals("jromans1"));
    }

    @Test
    public void textDisplayedByPhone() {
        solo.enterText(phone, "7801234567");
        String text = phone.getText().toString();

        Assert.assertTrue(text.equals("7801234567"));
    }

    @Test
    public void textDisplayedByEmail() {
        solo.enterText(email, "abc@gmail.com");
        String text = email.getText().toString();

        Assert.assertTrue(text.equals("abc@gmail.com"));
    }

    @Test
    public void selectCheckBox() {
        solo.clickOnCheckBox(0);

        Assert.assertTrue(solo.isCheckBoxChecked(0));
    }

    @Test
    public void unselectCheckBox() {
        solo.clickOnCheckBox(0);
        solo.clickOnCheckBox(0);

        Assert.assertFalse(solo.isCheckBoxChecked(0));
    }

    @Test
    public void checkUnfilledFields() {
        boolean result;

        solo.enterText(userID, "jromans1");
        solo.enterText(phone, "7801234567");
        solo.enterText(email, "abc@gmail.com");

        solo.clearEditText(userID);
        solo.clickOnButton(0);
        result = solo.searchText("All fields must be filled out");
        Assert.assertEquals(result, true);
        solo.enterText(userID, "jromans1");

        solo.clearEditText(phone);
        solo.clickOnButton(0);
        result = solo.searchText("All fields must be filled out");
        Assert.assertEquals(result, true);
        solo.enterText(phone, "7801234567");

        solo.clearEditText(email);
        solo.clickOnButton(0);
        result = solo.searchText("All fields must be filled out");
        Assert.assertEquals(result, true);
        solo.enterText(email, "abc@gmail.com");
    }


    /*@Test
    public void testEditProblem() throws Exception {
        EditText name = (EditText) solo.getView("userID");
        EditText pwd = (EditText) solo.getView("login_password");
        solo.enterText(name, "");
        solo.enterText(pwd, "");
        solo.enterText(name, "foreverznb");
        solo.enterText(pwd, "ZHUNINGBO1234");
        solo.clickOnView(solo.getView("login_button"));

        solo.clickOnView(solo.getView(R.id.view_patients));
        solo.clickInList(1);
        solo.waitForDialogToOpen(1000);
        solo.clickOnButton("Edit/View");

        EditText title = (EditText) solo.getView("title_text_editscreen");
        EditText date = (EditText) solo.getView("date_started_editscreen");
        EditText des = (EditText) solo.getView("problem_description_editscreen");

        solo.enterText(title, "");
        solo.enterText(title, "Hungry");
        solo.enterText(date, "");
        solo.enterText(date, "2018-10-11");
        solo.enterText(des, "");
        solo.enterText(des, "I am hungry!!!!!!!");

        solo.clickOnView(solo.getView(R.id.edit_save));
        boolean result_1 = solo.waitForActivity(ViewMyProblems.class, 2000);
        Assert.assertEquals(true, result_1);

        solo.clickInList(0);
        solo.waitForDialogToOpen(1000);
        solo.clickOnButton("Edit/View");

        solo.clickOnView(solo.getView(R.id.add_record_from_add_problem));
        boolean result_2 = solo.waitForActivity(AddorEditRecordView.class, 2000);
        Assert.assertEquals(true, result_2);
        solo.goBack();


    }
    */
}
