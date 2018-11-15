package com.example.benmelnick.donationtracker;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.idling.CountingIdlingResource;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import android.support.test.rule.ActivityTestRule;
import static org.hamcrest.Matchers.not;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;


@SuppressWarnings("ALL")
public class LoginActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> loginActivityTestRule = new ActivityTestRule<>(LoginActivity.class);
    private CountingIdlingResource espressoIdler;

    @Before
    public void setUp() throws Exception {
        Intents.init();
        espressoIdler = loginActivityTestRule.getActivity().getEspressoIdler();
        Espresso.registerIdlingResources(espressoIdler);
    }

    @Test
    public void testValidLogin() {
        onView(withId(R.id.email))
                .perform(typeText("bmelnick3@gatech.edu"), closeSoftKeyboard());

        onView(withId(R.id.email))
                .check(matches(withText("bmelnick3@gatech.edu")));

        onView(withId(R.id.password))
                .perform(typeText("benmelnick"), closeSoftKeyboard());

        onView(withId(R.id.password))
                .check(matches(withText("benmelnick")));

        onView(withId(R.id.email_sign_in_button))
                .perform(click());

        intended(hasComponent(MainContentActivity.class.getName()));
    }

    @Test
    public void testInvalidLogin() {
        onView(withId(R.id.email))
                .perform(typeText("bmelnick3@gatech.edu"), closeSoftKeyboard());

        onView(withId(R.id.email))
                .check(matches(withText("bmelnick3@gatech.edu")));

        onView(withId(R.id.password))
                .perform(typeText("invalidpassword"), closeSoftKeyboard());

        onView(withId(R.id.password))
                .check(matches(withText("invalidpassword")));

        onView(withId(R.id.email_sign_in_button))
                .perform(click());

        onView(withText("Sign In Failed"))
                .inRoot(withDecorView(not(loginActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
        Espresso.unregisterIdlingResources(espressoIdler);
    }
}
