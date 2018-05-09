package `in`.co.tripin.chahiyecustomer.Activities

import `in`.co.tripin.chahiyecustomer.Managers.PreferenceManager
import `in`.co.tripin.chahiyecustomer.R
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val mPreferenceManger = PreferenceManager.getInstance(this)

        //go to SignInSignUp Activity if not signed in after 3 sec

        Timer().schedule(500){
            //if user is not signed in
            if(mPreferenceManger.isLogin) {
                val intent = Intent(this@SplashActivity, MainLandingMapActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@SplashActivity, SignUpLogInActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
    }
}
