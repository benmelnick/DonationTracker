package com.example.benmelnick.donationtracker;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import android.support.test.rule.ActivityTestRule;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;


@SuppressWarnings("ALL")
public class LocationDetailTest {
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
    public void testPathToItem() {
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

        onView(withId(R.id.viewLocationList))
                .perform(click());

        onView(withId(R.id.list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void testEmployeeAbilities() {
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

        onView(withId(R.id.viewLocationList))
                .perform(click());

        onView(withId(R.id.list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(allOf(withId(R.id.add_item), isDisplayed()));
    }

    @Test
    public void testUserAbilities() {
        onView(withId(R.id.email))
                .perform(typeText("jmcfarlin6@gatech.edu"), closeSoftKeyboard());

        onView(withId(R.id.email))
                .check(matches(withText("jmcfarlin6@gatech.edu")));

        onView(withId(R.id.password))
                .perform(typeText("joshmcfarlin"), closeSoftKeyboard());

        onView(withId(R.id.password))
                .check(matches(withText("joshmcfarlin")));

        onView(withId(R.id.email_sign_in_button))
                .perform(click());

        onView(withId(R.id.viewLocationList))
                .perform(click());

        onView(withId(R.id.list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(allOf(withId(R.id.add_item), not(isDisplayed())));
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
        Espresso.unregisterIdlingResources(espressoIdler);
    }
}
