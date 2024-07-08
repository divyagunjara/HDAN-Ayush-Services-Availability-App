package com.example.hdan_ayushservicesavailabilityapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.hdan_ayushservicesavailabilityapp.Admin.AdminYoga
import com.example.hdan_ayushservicesavailabilityapp.Admin.Admindoctor
import com.example.hdan_ayushservicesavailabilityapp.Admin.Adminfeedbacks
import com.example.hdan_ayushservicesavailabilityapp.Admin.Adminusers

class AdminDashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)


        findViewById<Button>(R.id.btnviewuser).setOnClickListener {
            startActivity(Intent(this,Adminusers::class.java))
        }

        findViewById<Button>(R.id.btnyoga).setOnClickListener {
            startActivity(Intent(this,AdminYoga::class.java))
        }


        findViewById<Button>(R.id.btnadddoctor).setOnClickListener {
            startActivity(Intent(this,Admindoctor::class.java))
        }

        findViewById<Button>(R.id.btnviewfeedback).setOnClickListener {
            startActivity(Intent(this,Adminfeedbacks::class.java))
        }

        findViewById<Button>(R.id.btnlogout).setOnClickListener {
            val alertdialog= AlertDialog.Builder(this)
            alertdialog.setTitle("LOGOUT")
            alertdialog.setIcon(R.drawable.log)
            alertdialog.setCancelable(false)
            alertdialog.setMessage("Do you Want to Logout?")
            alertdialog.setPositiveButton("Yes"){ alertdialog, which->
                startActivity(Intent(this, login::class.java))
                finish()
                val  shared=getSharedPreferences("user", MODE_PRIVATE)
                shared.edit().clear().apply()
                alertdialog.dismiss()
            }
            alertdialog.setNegativeButton("No"){alertdialog,which->
                Toast.makeText(this,"thank you", Toast.LENGTH_SHORT).show()
                alertdialog.dismiss()
            }
            alertdialog.show()
        }
    }
}