package com.example.hdan_ayushservicesavailabilityapp.model

data class Appointment(
    val id:Int,
    val date:String,
    val reason:String,
    val hours:String,
    val dname:String,
    val dnum:String,
   val  demail:String,
   val  appointid:String,
    val uname:String,
    val unum:String,
    val uemail:String,
    val cost:String,
   val  status:String,
   val  pstatus:String,
   val  rating:String,
   val  feedback:String,
) {
}