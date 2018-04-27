package `in`.co.tripin.chahiyecustomer.Activities

import `in`.co.tripin.chahiyecustomer.R
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class TapriDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tapri_details)
        supportActionBar!!.setTitle("Dubey Tapriwala")
        supportActionBar!!.setSubtitle("Some Subtitle")
        supportActionBar!!.setLogo(R.drawable.ic_local_drink_black_24dp)
    }


}
