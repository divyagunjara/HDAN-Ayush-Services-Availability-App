package com.example.hdan_ayushservicesavailabilityapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.example.hdan_ayushservicesavailabilityapp.User.Ayurvedahintuser
import com.example.hdan_ayushservicesavailabilityapp.User.Ayurvedarecom
import com.example.hdan_ayushservicesavailabilityapp.User.History
import com.example.hdan_ayushservicesavailabilityapp.User.Userprofile
import com.example.hdan_ayushservicesavailabilityapp.User.Useryoga
import com.example.hdan_ayushservicesavailabilityapp.User.ViewDoctors

class Userdashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userdashboard)


       getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).apply {
           findViewById<TextView>(R.id.tvname).setText(getString("name", "").toString())
        }


        findViewById<View>(R.id.carduserhints).setOnClickListener {
            startActivity(Intent(this,Ayurvedahintuser::class.java))
        }


        findViewById<View>(R.id.cardviewyoga).setOnClickListener {
            startActivity(Intent(this,Useryoga::class.java))
        }

        findViewById<View>(R.id.carduserprofile).setOnClickListener {
            startActivity(Intent(this,Userprofile::class.java))
        }

        findViewById<View>(R.id.linearayurvedarecom).setOnClickListener {
            startActivity(Intent(this,Ayurvedarecom::class.java))
        }

        findViewById<View>(R.id.cardviewdoctors).setOnClickListener {
            startActivity(Intent(this,ViewDoctors::class.java))
        }

        findViewById<CardView>(R.id.carduserlogout).setOnClickListener {
            startActivity(Intent(this, History::class.java))

        }
    }
}