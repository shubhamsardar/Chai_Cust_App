package `in`.co.tripin.chahiyecustomer.Activities

import `in`.co.tripin.chahiyecustomer.R
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LogInForgotPINActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in_forgot_pin)
    }

    fun back(view: View) {
        finish()
    }
}
