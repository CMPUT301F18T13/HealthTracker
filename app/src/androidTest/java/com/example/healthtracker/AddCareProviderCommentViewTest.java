package com.example.healthtracker;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class AddCareProviderCommentViewTest {

    @Rule
    public ActivityTestRule<AddCareProviderCommentView> activityTestRule =
            new ActivityTestRule<>(AddCareProviderCommentView.class);

    @Test
    public void saveCareProviderComment() {
        // Arrange
        onView(withId(R.id.titleText)).perform(typeText("New Comment"));
        // Act
        onView(withId(R.id.titleText)).perform(pressImeActionButton());
        // Assert
        onView(withId(R.id.titleText)).check(matches(withText("New Comment")));
    }
}
