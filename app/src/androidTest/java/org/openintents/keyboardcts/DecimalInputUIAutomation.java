package org.openintents.keyboardcts;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class DecimalInputUIAutomation {
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void enterDecimalsWithUIAutomation() {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        onView(withId(R.id.input_1)).perform(typeText("56"));

        device.click(1890, 3840 - 384 - 192);

        onView(withId(R.id.input_1)).perform(typeText("78"));

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.fab)).perform(click());
    }
}
