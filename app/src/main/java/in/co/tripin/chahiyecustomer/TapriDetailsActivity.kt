package `in`.co.tripin.chahiyecustomer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu

class TapriDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tapri_details)
        supportActionBar!!.setTitle("Dubey Tapriwala")
        supportActionBar!!.setSubtitle("Some Subtitle")
        supportActionBar!!.setLogo(R.drawable.ic_local_drink_black_24dp)
    }


}
