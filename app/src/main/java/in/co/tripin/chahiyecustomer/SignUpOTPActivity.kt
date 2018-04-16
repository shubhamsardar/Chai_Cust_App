package `in`.co.tripin.chahiyecustomer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SignUpOTPActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_otp)
    }

    fun gotomainscreen(view: View) {
        val intent = Intent(this@SignUpOTPActivity,MainLandingMapActivity::class.java)
        startActivity(intent)
    }
    fun back(view: View) {
        finish()
    }
}
