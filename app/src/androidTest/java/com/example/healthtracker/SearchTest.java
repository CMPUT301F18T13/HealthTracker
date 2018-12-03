package com.example.healthtracker;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.example.healthtracker.Activities.LoginActivity;
import com.example.healthtracker.Activities.SearchActivity;
import com.example.healthtracker.View.SearchResultsView;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SearchTest {
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
    public void testPatientCorrectKeywordSearchActivity() {
        // login
        EditText name = (EditText) solo.getView("userID");
        solo.enterText(name, "testingcode12");
        solo.clickOnButton(solo.getString(R.string.login));

        solo.clickOnButton(solo.getString(R.string.search));
        boolean result_1 = solo.waitForActivity(SearchActivity.class, 2000);
        Assert.assertTrue(result_1);

        // Test Existing Search terms
        EditText keyword = (EditText) solo.getView("search_terms");
        solo.enterText(keyword, "test");
        solo.pressSpinnerItem(0, 0);
        Boolean actual = solo.isSpinnerTextSelected(0, "Keywords");
        Assert.assertEquals("spinner item selected", true, actual);
        solo.clickOnButton(solo.getString(R.string.search));
        boolean result_2 = solo.waitForActivity(SearchResultsView.class, 2000);
        Assert.assertTrue(result_2);
    }

    @Test
    public void testPatientIncorrectKeywordSearchActivity() {
        // login
        EditText name = (EditText) solo.getView("userID");
        solo.enterText(name, "testingcode12");
        solo.clickOnButton(solo.getString(R.string.login));

        solo.clickOnButton(solo.getString(R.string.search));
        boolean result_1 = solo.waitForActivity(SearchActivity.class, 2000);
        Assert.assertTrue(result_1);

        // Test Existing Search terms
        EditText keyword = (EditText) solo.getView("search_terms");
        solo.enterText(keyword, "fasfasdfasdf");
        solo.pressSpinnerItem(0, 0);
        Boolean actual = solo.isSpinnerTextSelected(0, "Keywords");
        Assert.assertEquals("spinner item selected", true, actual);
        solo.clickOnButton(solo.getString(R.string.search));
        boolean result_2 = solo.waitForActivity(SearchResultsView.class, 2000);
        Assert.assertTrue(result_2);
    }

        // Test Searching Geo location

        // Test Searching Body Location

    @Test
    public void testCareProviderCorrectKeywordSearchActivity() {
        // Login
        solo.clickOnCheckBox(0);
        EditText name = (EditText) solo.getView("userID");
        solo.enterText(name, "doctortyler");
        solo.clickOnButton(solo.getString(R.string.login));

        solo.clickOnButton(solo.getString(R.string.search));
        boolean result_1 = solo.waitForActivity(SearchActivity.class, 2000);
        Assert.assertTrue(result_1);

        // Test Existing Search terms
        EditText keyword = (EditText) solo.getView("search_terms");
        solo.enterText(keyword, "test");
        solo.pressSpinnerItem(0, 0);
        Boolean actual = solo.isSpinnerTextSelected(0, "Keywords");
        Assert.assertEquals("spinner item selected", true, actual);
        solo.clickOnButton(solo.getString(R.string.search));
        boolean result_2 = solo.waitForActivity(SearchResultsView.class, 2000);
        Assert.assertTrue(result_2);
    }

    @Test
    public void testCareProviderIncorrectKeywordSearchActivity() {
        // Login
        solo.clickOnCheckBox(0);
        EditText name = (EditText) solo.getView("userID");
        solo.enterText(name, "doctortyler");
        solo.clickOnButton(solo.getString(R.string.login));

        solo.clickOnButton(solo.getString(R.string.search));
        boolean result_1 = solo.waitForActivity(SearchActivity.class, 2000);
        Assert.assertTrue(result_1);

        // Test Existing Search terms
        EditText keyword = (EditText) solo.getView("search_terms");
        solo.enterText(keyword, "fasfasdfasdf");
        solo.pressSpinnerItem(0, 0);
        Boolean actual = solo.isSpinnerTextSelected(0, "Keywords");
        Assert.assertEquals("spinner item selected", true, actual);
        solo.clickOnButton(solo.getString(R.string.search));
        boolean result_2 = solo.waitForActivity(SearchResultsView.class, 2000);
        Assert.assertTrue(result_2);
    }
}
