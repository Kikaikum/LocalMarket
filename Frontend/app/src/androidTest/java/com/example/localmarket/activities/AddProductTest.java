package com.example.localmarket.activities;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.localmarket.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddProductTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void addProductTest() {
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.editTextTextEmailAddress),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayoutEmail),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("testVendorTest"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.editTextTextPassword),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.editTextLayoutTextPassword),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("Testvendor!!"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.buttonLogin), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                0)));
        materialButton.perform(scrollTo(), click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.addProduct),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container2),
                                        0),
                                0),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.spinnerProduct),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container2),
                                        0),
                                1),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction cardView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        cardView.perform(click());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.itPrice),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.itPriceLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("12"), closeSoftKeyboard());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.itStock),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.itStockLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("12"), closeSoftKeyboard());

        ViewInteraction switch_ = onView(
                allOf(withId(R.id.switchPesoUnidad),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container2),
                                        0),
                                2),
                        isDisplayed()));
        switch_.perform(click());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.etDescription),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout3),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText5.perform(replaceText("test para espresso"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btnAcept), withText("Listo"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container2),
                                        0),
                                4),
                        isDisplayed()));
        materialButton3.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
