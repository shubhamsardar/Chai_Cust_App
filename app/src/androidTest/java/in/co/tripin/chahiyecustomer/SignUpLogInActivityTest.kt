package `in`.co.tripin.chahiyecustomer

import `in`.co.tripin.chahiyecustomer.Activities.SignUpLogInActivity
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SignUpLogInActivityTest{
    @Rule
    @JvmField
    val activity = ActivityTestRule<SignUpLogInActivity>(SignUpLogInActivity::class.java)

    @Test
    fun buttons_exists_test() {

        onView(withId(R.id.btn_signup)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()))

    }
}