package com.example.hdan_ayushservicesavailabilityapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.hdan_ayushservicesavailabilityapp.Doctor.doctorhome
import com.example.hdan_ayushservicesavailabilityapp.Doctor.profile
import com.google.android.material.bottomnavigation.BottomNavigationView

class DoctorDashboard : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_dashboard)
        bottomNav = findViewById(R.id.bottomNav)
        bottom()
        callingFragment(doctorhome())
    }
    private fun bottom() {
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home ->{
                    callingFragment(doctorhome())
                    true
                }
                R.id.profile ->{
                    callingFragment(profile())
                    true
                }
                R.id.hlogout->{
                    logout()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun callingFragment(fragment: Fragment) {
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fcontainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun logout() {
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