package com.one.assignement;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.one.assignment.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by sunny on 27/5/16.
 */
@RunWith(AndroidJUnit4.class)
public class MainScreenContentTest {


    @Rule
    public ActivityTestRule<MainActivity> mMainActivityActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);


    @Test
    public void clickFabBUtton_showMovieList() throws Exception
    {
        Espresso.onView(ViewMatchers.withId(R.id.fab_refresh))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.rv_feed))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
