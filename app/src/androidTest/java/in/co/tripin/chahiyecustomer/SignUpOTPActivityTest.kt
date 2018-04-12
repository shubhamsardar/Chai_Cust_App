package `in`.co.tripin.chahiyecustomer

import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)

class SignUpOTPActivityTest{

    @Rule
    @JvmField
    val activity = ActivityTestRule<SignUpOTPActivity>(SignUpOTPActivity::class.java)

    @Test
    fun ui_exists() {

        Espresso.onView(ViewMatchers.withId(R.id.backButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.pinViewOTP)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.resendotp)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.btn_submitotp)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))


    }


}