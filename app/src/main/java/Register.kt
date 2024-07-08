package com.example.hdan_ayushservicesavailabilityapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.gsm.SmsManager
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.chaos.view.PinView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.ymts0579.model.model.DefaultResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {
    lateinit var etname:EditText
    lateinit var etunum:EditText
    lateinit var etemail:EditText
    lateinit var etuaddress:EditText
    lateinit var etucity:EditText
    lateinit var etupass:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


         etname=findViewById<EditText>(R.id.etname)
        etunum=findViewById<EditText>(R.id.etunum)
        etemail=findViewById<EditText>(R.id.etemail)
        etuaddress=findViewById<EditText>(R.id.etuaddress)
        etucity=findViewById<EditText>(R.id.etucity)
        etupass=findViewById<EditText>(R.id.etupass)
        val btnsubmit=findViewById<Button>(R.id.btnsubmit)

        btnsubmit.setOnClickListener {
            val name=etname.text.toString().trim()
            val num=etunum.text.toString().trim()
            val email=etemail.text.toString().trim()
            val add=etuaddress.text.toString().trim()
            val city=etucity.text.toString().trim()
            val pass=etupass.text.toString().trim()
            if (name.isEmpty()) {
                showToast("Enter your Name")
                etname.error = "Enter your Name"
            } else if (num.isEmpty()) {
                showToast( "Enter your Number")
                etunum.error = "Enter your Number"
            } else if (email.isEmpty()) {
               showToast( "Enter your Email")
                etemail.error = "Enter your Mail"
            } else if (add.isEmpty()) {
                showToast( "Enter your Address")
                etuaddress.error = "Enter your Address"
            } else if (pass.isEmpty()) {
                showToast( "Enter your password")
                etupass.error = "Enter your Password"
            } else if (city.isEmpty()) {
                showToast( "Enter your City")
                etucity.error = "Enter your City"
            } else if (!email.contains("@gmail.com")) {
               showToast( "Enter your Email Properly")
                etemail.error = "Enter your Email Properly"
            }else{
                if (num.count() == 10) {
                    val ii="${(1000..9999).shuffled().last()}"
                    if (TextUtils.isDigitsOnly(num)) {
                        val smsManager: SmsManager = SmsManager.getDefault()
                        smsManager.sendTextMessage(num, null, "your verification OTP  is ${ii}", null, null)
                    }
                    val dd=BottomSheetDialog(this)
                    dd.setContentView(R.layout.cardopt)
                    val btnoptsubmit=dd.findViewById<Button>(R.id.btnoptsubmit)!!
                    val pinView=dd.findViewById<PinView>(R.id.pinview);


                    btnoptsubmit.setOnClickListener {
                        val kkk = pinView!!.text.toString()
                        if(ii==kkk){
                            funregister(name,
                                num,
                                email,
                                add,
                                city,
                                pass)
                        }else{
                            showToast("your Opt is not matched")
                        }
                        dd.dismiss()
                    }


                     dd.show()

                } else {
                    showToast("Enter your Number")
                    etunum.error = "Enter your Number"
                }

            }
        }
    }

    private fun funregister(name: String, num: String, email: String, add: String, city: String, pass: String) {
        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.instance.register(name,num,email,city,pass,add,"User","","","register")
                .enqueue(object: Callback<DefaultResponse> {
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                      showToast(t.message)
                    }
                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                        Toast.makeText(this@Register,"Your Registration Successful ", Toast.LENGTH_SHORT).show()
                         startActivity(Intent(this@Register,login::class.java))

                        etname.text.clear()
                        etunum.text.clear()
                        etemail.text.clear()
                        etuaddress.text.clear()
                        etucity.text.clear()
                        etupass.text.clear()

                    }
                })
        }
    }


}