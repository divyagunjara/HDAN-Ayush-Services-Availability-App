package com.example.hdan_ayushservicesavailabilityapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.ymts0579.model.model.LoginResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class login : AppCompatActivity() {
    lateinit var etemail: EditText
    lateinit var etpassword: EditText
    lateinit var btnlogin: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etemail=findViewById(R.id.etemail)
        etpassword=findViewById(R.id.etpassword)
        btnlogin=findViewById(R.id.btnlogin)

        findViewById<LinearLayout>(R.id.linearregister).setOnClickListener {
            startActivity(Intent(this,Register::class.java))
        }


        btnlogin.setOnClickListener {
            val email =etemail.text.toString().trim()
            val pass=etpassword.text.toString().trim()


            if(email.isEmpty()){
                showToast("Enter your Email")
                etemail.error = "Enter your Email"
            }else if(pass.isEmpty()){
                showToast("Enter Your Password")
                etpassword.error="Enter Your Password"
            }else{
                if(email.contains("admin")&&pass.contains("admin")){
                    startActivity(Intent(this,AdminDashboard::class.java))
                    getSharedPreferences("user", MODE_PRIVATE).edit().putString("type","admin").apply()
                    finish()
                }else{
                    CoroutineScope(Dispatchers.IO).launch {
                        RetrofitClient.instance.login(email,pass,"login")
                            .enqueue(object: Callback<LoginResponse> {
                                override fun onResponse(
                                    call: Call<LoginResponse>, response: Response<LoginResponse>
                                ) {
                                    if(!response.body()?.error!!){
                                        val type=response.body()?.user
                                        if (type!=null) {
                                            getSharedPreferences("user", MODE_PRIVATE).edit().apply {
                                                putString("mob",type.moblie)
                                                putString("pass",type.password)
                                                putString("email",type.email)
                                                putString("name",type.name)
                                                putString("address",type.address)
                                                putString("city",type.city)
                                                putString("type",type.type)
                                                putString("status",type.status)
                                                putInt("id",type.id)
                                                apply()
                                            }



                                            when (type.type) {
                                                "Doctor" -> {
                                                    startActivity(Intent(this@login,DoctorDashboard::class.java))
                                                    finish()
                                                }
                                                "User" -> {
                                                    Toast.makeText(this@login, "User", Toast.LENGTH_SHORT).show()
                                                    startActivity(Intent(this@login,Userdashboard::class.java))
                                                    finish()
                                                }

                                            }



                                        }
                                    }else{
                                        Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_SHORT).show()
                                    }

                                }

                                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()


                                }

                            })
                    }
                }

            }

        }
    }
}