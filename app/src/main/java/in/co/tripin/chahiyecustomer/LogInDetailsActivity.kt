package `in`.co.tripin.chahiyecustomer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LogInDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in_details)
    }

    fun gotomainscreen(view: View) {
        val intent = Intent(this@LogInDetailsActivity,MainActivity::class.java)
        startActivity(intent)
    }

    fun gotoforgotpinscreen(view: View) {
        val intent = Intent(this@LogInDetailsActivity,LogInForgotPINActivity::class.java)
        startActivity(intent)
    }

    fun back(view: View) {
       finish()
    }
}
