package com.example.hdan_ayushservicesavailabilityapp

import android.app.Activity
import android.widget.Toast

fun Activity.showToast(message:Any?){
    Toast.makeText(this, "$message", Toast.LENGTH_SHORT).show()
}