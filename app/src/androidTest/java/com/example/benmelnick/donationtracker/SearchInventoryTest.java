package com.example.benmelnick.donationtracker;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import android.support.test.rule.ActivityTestRule;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;


@SuppressWarnings("ALL")
public class SearchInventoryTest {
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
    public void testSearch() {
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

        onView(withId(R.id.search))
                .perform(click());

        onView(withId(R.id.itemText))
                .perform(typeText("Jeans"), closeSoftKeyboard());

        onView(withId(R.id.submit))
                .perform(click());

        onView(withId(R.id.list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void testSearchWithCategory() {
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

        onView(withId(R.id.search))
                .perform(click());

        onView(withId(R.id.itemText))
                .perform(typeText("Jeans"), closeSoftKeyboard());

        onView(withId(R.id.category_spinner)).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("Clothing")))
                .perform(click());

        onView(withId(R.id.submit))
                .perform(click());

        onView(withId(R.id.list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
        Espresso.unregisterIdlingResources(espressoIdler);
    }
}
