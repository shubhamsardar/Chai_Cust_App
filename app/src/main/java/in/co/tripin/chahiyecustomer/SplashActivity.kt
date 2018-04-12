package `in`.co.tripin.chahiyecustomer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //go to SignInSignUp Activity if not signed in after 3 sec

        Timer().schedule(3000){
            //if user is not signed in
            val intent = Intent(this@SplashActivity,SignUpLogInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
