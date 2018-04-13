package `in`.co.tripin.chahiyecustomer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SignUpDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_details)
    }

    fun gotootpverification(view: View) {
        val intent = Intent(this@SignUpDetailsActivity,SignUpOTPActivity::class.java)
        startActivity(intent)
    }


    fun back(view: View) {
        finish()
    }
}
