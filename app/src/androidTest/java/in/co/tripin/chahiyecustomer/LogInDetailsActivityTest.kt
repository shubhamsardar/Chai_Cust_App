package `in`.co.tripin.chahiyecustomer

import `in`.co.tripin.chahiyecustomer.Activities.LogInDetailsActivity
import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LogInDetailsActivityTest{
    @Rule
    @JvmField
    val activity = ActivityTestRule<LogInDetailsActivity>(LogInDetailsActivity::class.java)

    @Test
    fun ui_exists() {

        Espresso.onView(ViewMatchers.withId(R.id.backButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.mobile)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.pin)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
}