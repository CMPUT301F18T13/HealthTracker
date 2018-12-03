package com.example.healthtracker;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.example.healthtracker.Activities.LoginActivity;
import com.example.healthtracker.Activities.UserSettingsActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class UserSettingsViewTest {
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
    public void testPatientInfo() {
        // login
        EditText name = (EditText) solo.getView("userID");
        solo.enterText(name, "testingcode12");
        solo.clickOnButton(solo.getString(R.string.login));

        // Second step: edit info
        solo.clickOnButton(solo.getString(R.string.settings));
        boolean result_4 = solo.waitForActivity(UserSettingsActivity.class, 2000);
        Assert.assertTrue(result_4);
        EditText email = (EditText) solo.getView(R.id.edit_email);
        EditText phone = (EditText) solo.getView(R.id.edit_phone);
        solo.clearEditText(email);
        solo.clearEditText(phone);
        solo.enterText(email, "patienttest@gmail.com");
        solo.enterText(phone, "780-268-1234");

        // Third step: Save Changes with correct info
        solo.clickOnView(solo.getView(R.id.editSaveButton));
        assertTrue("Changes Saved message is not displayed", solo.searchText("Changes Saved"));

        // 4th case: Test empty email
        solo.clearEditText(email);
        solo.clickOnView(solo.getView(R.id.editSaveButton));
        assertTrue("Empty message is not displayed", solo.searchText("All fields must be filled in."));

        // 5th case: Test empty phone
        solo.clearEditText(phone);
        solo.clickOnView(solo.getView(R.id.editSaveButton));
        assertTrue("Empty message is not displayed", solo.searchText("All fields must be filled in."));

        // 6th case: Test both empty
        solo.clickOnView(solo.getView(R.id.editSaveButton));
        assertTrue("Empty message is not displayed", solo.searchText("All fields must be filled in."));

        // 7th case: Test invalid emails
        solo.enterText(phone, "780-268-1234");
        solo.enterText(email, "test@gmail");
        solo.clickOnView(solo.getView(R.id.editSaveButton));
        assertTrue("Invalid email message is not displayed", solo.searchText("Invalid Email"));
        solo.clearEditText(email);
        solo.enterText(email, "testgmail");
        solo.clickOnView(solo.getView(R.id.editSaveButton));
        assertTrue("Invalid email message is not displayed", solo.searchText("Invalid Email"));

        // 8th case: Test invalid phone numbers
        solo.clearEditText(phone);
        solo.clearEditText(email);
        solo.enterText(phone, "1");
        solo.enterText(email, "test@gmail.com");
        solo.clickOnView(solo.getView(R.id.editSaveButton));
        assertTrue("Changes Saved message is not displayed", solo.searchText("Invalid Phone Number"));
        solo.clearEditText(phone);
        solo.enterText(phone, "testseata");
        solo.clickOnView(solo.getView(R.id.editSaveButton));
        assertTrue("Changes Saved message is not displayed", solo.searchText("Invalid Phone Number"));
    }

    @Test
    public void testCareProviderInfo() {
        /// login
        solo.clickOnCheckBox(0);
        EditText name = (EditText) solo.getView("userID");
        solo.enterText(name, "doctortyler");
        solo.clickOnButton(solo.getString(R.string.login));

        // Second step: edit info
        solo.clickOnButton(solo.getString(R.string.settings));
        boolean result_4 = solo.waitForActivity(UserSettingsActivity.class, 2000);
        Assert.assertTrue(result_4);
        EditText email = (EditText) solo.getView(R.id.edit_email);
        EditText phone = (EditText) solo.getView(R.id.edit_phone);
        solo.clearEditText(phone);
        solo.clearEditText(email);
        solo.enterText(email, "caretest@gmail.com");
        solo.enterText(phone, "780-268-1235");

        // Third step: Save Changes with correct info
        solo.clickOnView(solo.getView(R.id.editSaveButton));
        assertTrue("Changes Saved message is not displayed", solo.searchText("Changes Saved"));

        // 4th case: Test empty email
        solo.clearEditText(email);
        solo.clickOnView(solo.getView(R.id.editSaveButton));
        assertTrue("Changes Saved message is not displayed", solo.searchText("All fields must be filled in."));

        // 5th case: Test empty phone
        solo.clearEditText(phone);
        solo.clickOnView(solo.getView(R.id.editSaveButton));
        assertTrue("Changes Saved message is not displayed", solo.searchText("All fields must be filled in."));

        // 6th case: Test both empty
        solo.clickOnView(solo.getView(R.id.editSaveButton));
        assertTrue("Changes Saved message is not displayed", solo.searchText("All fields must be filled in."));

        // 7th case: Test invalid emails
        solo.enterText(phone, "780-268-1234");
        solo.enterText(email, "test@gmail");
        solo.clickOnView(solo.getView(R.id.editSaveButton));
        assertTrue("Invalid email message is not displayed", solo.searchText("Invalid Email"));
        solo.clearEditText(email);
        solo.enterText(email, "testgmail");
        solo.clickOnView(solo.getView(R.id.editSaveButton));
        assertTrue("Invalid email message is not displayed", solo.searchText("Invalid Email"));

        // 8th case: Test invalid phone numbers
        solo.clearEditText(phone);
        solo.clearEditText(email);
        solo.enterText(phone, "1");
        solo.enterText(email, "test@gmail.com");
        solo.clickOnView(solo.getView(R.id.editSaveButton));
        assertTrue("Invalid phone is not displayed", solo.searchText("Invalid Phone Number"));
        solo.clearEditText(phone);
        solo.enterText(phone, "testseata");
        solo.clickOnView(solo.getView(R.id.editSaveButton));
        assertTrue("Invalid phone message is not displayed", solo.searchText("Invalid Phone Number"));
    }
}
