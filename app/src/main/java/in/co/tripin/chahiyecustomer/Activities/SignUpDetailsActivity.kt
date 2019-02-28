package `in`.co.tripin.chahiyecustomer.Activities

import `in`.co.tripin.chahiyecustomer.Model.CompanyModel
import `in`.co.tripin.chahiyecustomer.Model.responce.SignUpResponse
import `in`.co.tripin.chahiyecustomer.R
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.view.View
import kotlinx.android.synthetic.main.activity_sign_up_details.*
import `in`.co.tripin.chahiyecustomer.rest.ApiInterface
import `in`.co.tripin.chahiyecustomer.rest.ApiClient
import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import org.json.JSONException
import org.json.JSONObject
import android.graphics.Movie
import android.graphics.Typeface
import android.opengl.Visibility
import android.support.annotation.LayoutRes
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
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
        var spinner = findViewById<Spinner>(R.id.spinner)
        var tvCorporate = findViewById<TextView>(R.id.textViewCorporate);
        val btnSignUp = findViewById<Button>(R.id.btn_signup)
        spinner.visibility = View.GONE;
        val companyList = arrayListOf<String>();
        companyList.add("Company 1");
        companyList.add("Company 2");
        companyList.add("Company 3");
        companyList.add("Company 4");
        val companyModelList = arrayListOf<CompanyModel>()
        for (i in companyList) {
            //val companyModel = CompanyModel(i) as CompanyModel
            companyModelList.add(CompanyModel(i));
        }


        var name = etName.text
        var mobile = etMobile.text
        var pin = etPin.text.toString()
        var reenterpin = etPinreenter.text.toString()

        btnSignUp.setOnClickListener(View.OnClickListener {
            var checkpin = checkPin(pin, reenterpin)
            if (!checkpin) {
                etPinreenter.error = "Pin Mismatch"
            }
        })
        
        textViewCorporate.setOnClickListener{
            spinner.visibility= View.VISIBLE

        }

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

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        val customAdapter = CustomAdapter(this, android.R.layout.simple_list_item_1, companyModelList);
        spinner.adapter = customAdapter

    }

    fun checkPin(pin: String, reentpin: String): Boolean {
        return pin.equals(reentpin)
    }

    fun gotootpverification(view: View) {
        val intent = Intent(this@SignUpDetailsActivity, SignUpOTPActivity::class.java)
        startActivity(intent)
    }


    fun back(view: View) {
        finish()
    }

    public class CustomAdapter(context: Context, @LayoutRes private val layoutResource: Int, private val companyList: List<CompanyModel>) :
            ArrayAdapter<CompanyModel>(context, layoutResource, companyList) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view : View?
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater;
            view = inflater.inflate(android.R.layout.simple_list_item_1,null);

            val textViewCompanyName = view.findViewById(android.R.id.text1) as TextView;
                    textViewCompanyName.setTextColor(Color.WHITE)
                    textViewCompanyName.gravity = Gravity.CENTER
                    textViewCompanyName.typeface = Typeface.SANS_SERIF
            companyList.get(position).name
            textViewCompanyName.text = companyList.get(position).name;


            return view
        }
//        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//            val view: View?
//            view = super.getView(position, convertView, parent)
//            return view
//        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val convertView: View?
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater;
            convertView = inflater.inflate(R.layout.custom_comapany_spinner, null);

            val textViewCompanyName = convertView.findViewById(R.id.textViewCompany) as TextView;
            companyList.get(position).name
            textViewCompanyName.text = companyList.get(position).name;


            return convertView
        }
    }
}
