package org.openintents.keyboardcts;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class DecimalInputTest {
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void enterDecimalsWithDot() throws Exception {
        onView(withId(R.id.input_1)).perform(replaceText("12.34"));
        onView(withId(R.id.input_2)).perform(replaceText("12.34"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.fab)).perform(click());
    }

    @Test
    public void enterDecimalsWithComma() throws Exception {
        onView(withId(R.id.input_1)).perform(typeText("12,34"));
        onView(withId(R.id.input_2)).perform(typeText("12,34"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.fab)).perform(click());
    }

    @Test
    public void enterDecimalsWithDotAndComma() throws Exception {
        onView(withId(R.id.input_1)).perform(typeText("12.3,4"));
        onView(withId(R.id.input_2)).perform(typeText("12.3,4"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.fab)).perform(click());
    }

    @Test
    public void enterDecimalsWithCommaAndDot() throws Exception {
        onView(withId(R.id.input_1)).perform(typeText("1,2.34"));
        onView(withId(R.id.input_2)).perform(typeText("1,2.34"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.fab)).perform(click());
    }
}
