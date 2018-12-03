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

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),
                activityTestRule.getActivity());
        solo.clickOnButton(0);
        boolean result_2 = solo.waitForActivity(CreateAccountActivity.class, 1000);
        userID = solo.getEditText(0);
        email = solo.getEditText(1);
        phone = solo.getEditText(2);
        CheckBox checkBox = (CheckBox) solo.getView("caregiver_checkbox");
        Button createAccount = (Button) solo.getView("create_new_account_button");
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }

    @Test
    public void textDisplayedByUserID() {
        solo.enterText(userID, "jromans1");
        String text = userID.getText().toString();

        Assert.assertEquals("jromans1", text);
    }

    @Test
    public void textDisplayedByPhone() {
        solo.enterText(phone, "7801234567");
        String text = phone.getText().toString();

        Assert.assertEquals("7801234567", text);
    }

    @Test
    public void textDisplayedByEmail() {
        solo.enterText(email, "abc@gmail.com");
        String text = email.getText().toString();

        Assert.assertEquals("abc@gmail.com", text);
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
        Assert.assertTrue(result);
        solo.enterText(userID, "jromans1");

        solo.clearEditText(phone);
        solo.clickOnButton(0);
        result = solo.searchText("All fields must be filled out");
        Assert.assertTrue(result);
        solo.enterText(phone, "7801234567");

        solo.clearEditText(email);
        solo.clickOnButton(0);
        result = solo.searchText("All fields must be filled out");
        Assert.assertTrue(result);
        solo.enterText(email, "abc@gmail.com");
    }
}
