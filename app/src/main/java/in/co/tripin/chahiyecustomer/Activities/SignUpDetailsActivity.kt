package `in`.co.tripin.chahiyecustomer.Activities

import `in`.co.tripin.chahiyecustomer.Model.responce.SignUpResponse
import `in`.co.tripin.chahiyecustomer.R
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_sign_up_details.*
import `in`.co.tripin.chahiyecustomer.rest.ApiInterface
import `in`.co.tripin.chahiyecustomer.rest.ApiClient
import org.json.JSONException
import org.json.JSONObject
import android.graphics.Movie
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_details)

        var etName = findViewById<TextInputEditText>(R.id.name)
        var etMobile = findViewById<TextInputEditText>(R.id.mobile)
        var etPin = findViewById<TextInputEditText>(R.id.pin)
        var etPinreenter = findViewById<TextInputEditText>(R.id.pin_reenter)
        val btnSignUp = findViewById<Button>(R.id.btn_signup)


        var name = etName.text
        var mobile = etMobile.text
        var pin = etPin.text.toString()
        var reenterpin = etPinreenter.text.toString()

        btnSignUp.setOnClickListener( View.OnClickListener {
            var checkpin = checkPin(pin, reenterpin)
            if(!checkpin) {
                etPinreenter.error = "Pin Mismatch"
            }
        })

        // prepare call in Retrofit 2.0
        val paramObject = JSONObject()

        try {
            paramObject.put("name", "Siddharth Srivastava")
            paramObject.put("mobile", "7039241820")
            paramObject.put("pin", "1234")
            paramObject.put("fcm", "fcm sid")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun checkPin(pin : String, reentpin : String) : Boolean {
        return pin.equals(reentpin)
    }

    fun gotootpverification(view: View) {
        val intent = Intent(this@SignUpDetailsActivity, SignUpOTPActivity::class.java)
        startActivity(intent)
    }


    fun back(view: View) {
        finish()
    }
}
