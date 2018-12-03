package com.example.healthtracker;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.example.healthtracker.Activities.LoginActivity;
import com.example.healthtracker.Activities.SearchActivity;
import com.example.healthtracker.SearchHelpers.search_results_problem;
import com.example.healthtracker.View.SearchResultsView;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertTrue;

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
        solo.enterText(keyword, "Rashes");
        solo.pressSpinnerItem(0, 0);
        Boolean actual = solo.isSpinnerTextSelected(0, "Keywords");
        Assert.assertEquals("spinner item selected", true, actual);
        solo.clickOnButton(solo.getString(R.string.search));
        boolean result_2 = solo.waitForActivity(SearchResultsView.class, 4000);
        Assert.assertTrue(result_2);

        solo.clickInList(0,0);
        solo.waitForActivity(search_results_problem.class, 2000);
        solo.assertCurrentActivity("Should be edit problem activity.", search_results_problem.class);
        assertTrue("Patient info is not found!", solo.searchText("Rashes"));
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

    @Test
    public void testPatientCorrectGeoSearchActivity() {
        // Login
        EditText name = (EditText) solo.getView("userID");
        solo.enterText(name, "testingcode12");
        solo.clickOnButton(solo.getString(R.string.login));

        solo.clickOnButton(solo.getString(R.string.search));
        boolean result_1 = solo.waitForActivity(SearchActivity.class, 2000);
        Assert.assertTrue(result_1);

        // Test Existing Search terms
        EditText keyword = (EditText) solo.getView("search_terms");
        solo.enterText(keyword, "Rashes");
        solo.pressSpinnerItem(0, 1);
        Boolean actual = solo.isSpinnerTextSelected(0, "Geo-location");
        Assert.assertEquals("spinner item selected", true, actual);
        solo.clickOnButton(solo.getString(R.string.search));
        /*
        boolean result_2 = solo.waitForActivity(SearchResultsView.class, 4000);
        Assert.assertTrue(result_2);

        solo.clickInList(0,0);
        solo.waitForActivity(search_results_problem.class, 2000);
        solo.assertCurrentActivity("Should be edit problem activity.", search_results_problem.class);

        solo.clickInList(0,0);
        solo.waitForActivity(search_results_problem.class, 2000);
        solo.assertCurrentActivity("Should be edit problem activity.", search_results_problem.class);
        assertTrue("Patient info is not found!", solo.searchText("Rashes"));*/
    }

    @Test
    public void testPatientCorrectBodySearchActivity() {
        // Login
        EditText name = (EditText) solo.getView("userID");
        solo.enterText(name, "testingcode12");
        solo.clickOnButton(solo.getString(R.string.login));

        solo.clickOnButton(solo.getString(R.string.search));
        boolean result_1 = solo.waitForActivity(SearchActivity.class, 2000);
        Assert.assertTrue(result_1);

        // Test Existing Search terms
        EditText keyword = (EditText) solo.getView("search_terms");
        solo.enterText(keyword, "Rashes");
        solo.pressSpinnerItem(0, 2);
        Boolean actual = solo.isSpinnerTextSelected(0, "Body-location");
        Assert.assertEquals("spinner item selected", true, actual);
        solo.clickOnButton(solo.getString(R.string.search));
        /*
        boolean result_2 = solo.waitForActivity(SearchResultsView.class, 4000);
        Assert.assertTrue(result_2);

        solo.clickInList(0,0);
        solo.waitForActivity(search_results_problem.class, 2000);
        solo.assertCurrentActivity("Should be edit problem activity.", search_results_problem.class);

        solo.clickInList(0,0);
        solo.waitForActivity(search_results_problem.class, 2000);
        solo.assertCurrentActivity("Should be edit problem activity.", search_results_problem.class);
        assertTrue("Patient info is not found!", solo.searchText("Rashes"));*/
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
        solo.enterText(keyword, "Rashes");
        solo.pressSpinnerItem(0, 0);
        Boolean actual = solo.isSpinnerTextSelected(0, "Keywords");
        Assert.assertEquals("spinner item selected", true, actual);
        solo.clickOnButton(solo.getString(R.string.search));
        boolean result_2 = solo.waitForActivity(SearchResultsView.class, 4000);
        Assert.assertTrue(result_2);

        solo.clickInList(0,0);
        solo.waitForActivity(search_results_problem.class, 2000);
        solo.assertCurrentActivity("Should be search results problem.", search_results_problem.class);
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

    @Test
    public void testCareProviderCorrectGeoSearchActivity() {
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
        solo.enterText(keyword, "Rashes");
        solo.pressSpinnerItem(0, 1);
        Boolean actual = solo.isSpinnerTextSelected(0, "Geo-location");
        Assert.assertEquals("spinner item selected", true, actual);
        solo.clickOnButton(solo.getString(R.string.search));
        /*
        boolean result_2 = solo.waitForActivity(SearchResultsView.class, 4000);
        Assert.assertTrue(result_2);

        solo.clickInList(0,0);
        solo.waitForActivity(search_results_problem.class, 2000);
        solo.assertCurrentActivity("Should be edit problem activity.", search_results_problem.class);

        solo.clickInList(0,0);
        solo.waitForActivity(search_results_problem.class, 2000);
        solo.assertCurrentActivity("Should be edit problem activity.", search_results_problem.class);
        assertTrue("Patient info is not found!", solo.searchText("Rashes"));*/
    }

    @Test
    public void testCareProviderCorrectBodySearchActivity() {
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
        solo.enterText(keyword, "Rashes");
        solo.pressSpinnerItem(0, 2);
        Boolean actual = solo.isSpinnerTextSelected(0, "Body-location");
        Assert.assertEquals("spinner item selected", true, actual);
        solo.clickOnButton(solo.getString(R.string.search));
        /*
        boolean result_2 = solo.waitForActivity(SearchResultsView.class, 4000);
        Assert.assertTrue(result_2);

        solo.clickInList(0,0);
        solo.waitForActivity(search_results_problem.class, 2000);
        solo.assertCurrentActivity("Should be edit problem activity.", search_results_problem.class);

        solo.clickInList(0,0);
        solo.waitForActivity(search_results_problem.class, 2000);
        solo.assertCurrentActivity("Should be edit problem activity.", search_results_problem.class);
        assertTrue("Patient info is not found!", solo.searchText("Rashes"));*/
    }
}
