package com.example.hdan_ayushservicesavailabilityapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val type=getSharedPreferences("user", MODE_PRIVATE).getString("type", "")!!

        when (type) {
            "admin" -> {
                startActivity(Intent(this,AdminDashboard::class.java))
                finish()
            }
            "User"->{
                startActivity(Intent(this,Userdashboard::class.java))
                finish()
            }
            "Doctor"->{
                startActivity(Intent(this,DoctorDashboard::class.java))
                finish()
            }
            else -> {
                Handler().postDelayed({
                    startActivity(Intent(this,login::class.java))
                    finish()
                },3500)
            }
        }

    }
}