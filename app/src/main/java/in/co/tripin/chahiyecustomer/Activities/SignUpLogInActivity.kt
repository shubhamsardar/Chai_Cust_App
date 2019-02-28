package `in`.co.tripin.chahiyecustomer.Activities

import `in`.co.tripin.chahiyecustomer.R
import `in`.co.tripin.chahiyecustomer.javacode.activity.LoginActivity
import `in`.co.tripin.chahiyecustomer.javacode.activity.SignUpActivity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SignUpLogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_log_in)
    }

    fun gotosignup(view: View) {
        val intent = Intent(this@SignUpLogInActivity, SignUpDetailsActivity::class.java)
        startActivity(intent)
    }

    fun gotologin(view: View) {
        val intent = Intent(this@SignUpLogInActivity, LogInDetailsActivity::class.java)
        startActivity(intent)
    }
}
