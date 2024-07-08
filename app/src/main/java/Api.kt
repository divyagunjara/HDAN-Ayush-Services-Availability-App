package com.example.hdan_ayushservicesavailabilityapp



import com.example.hdan_ayushservicesavailabilityapp.model.Appointmentresponse
import com.example.hdan_ayushservicesavailabilityapp.model.hintresponse
import com.ymts0579.fooddonationapp.model.Userresponse
import com.ymts0579.model.model.DefaultResponse
import com.ymts0579.model.model.LoginResponse

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @FormUrlEncoded
    @POST("users.php")
    fun register(
        @Field("name") name:String,
        @Field("mobile")mobile:String,
        @Field("email")email :String,
        @Field("city") city:String,
        @Field("password") password:String,
        @Field("address")address :String,
        @Field("type") type:String,
        @Field("status") status:String,
        @Field("specific") specifics:String,
        @Field("condition") condition:String,
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("users.php")
    fun login(@Field("email") email:String, @Field("password") password:String,
              @Field("condition") condition:String): Call<LoginResponse>

    @GET("getusers.php")
    fun getusers(): Call<Userresponse>


    @GET("getdoctor.php")
    fun getdoctor():Call<Userresponse>


    @GET("getyoga.php")
    fun getyoga():Call<Userresponse>

    @FormUrlEncoded
    @POST("users.php")
    fun updateusers(
        @Field("name") name:String, @Field("mobile")moblie:String, @Field("password") password:String,
        @Field("address")address :String, @Field("city") city:String,
        @Field("id")id:Int, @Field("condition")condition:String): Call<DefaultResponse>


    @FormUrlEncoded
    @POST("users.php")
    fun updatestatus(
        @Field("status") status:String,
        @Field("id")id:Int, @Field("condition")condition:String): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("users.php")
    fun usersemail(@Field("email") email:String,@Field("condition") condition:String): Call<Userresponse>


    @FormUrlEncoded
    @POST("users.php")
    fun usersstatusview(  @Field("type") type:String, @Field("city") city:String,  @Field("status") status:String,@Field("condition") condition:String): Call<Userresponse>


    @FormUrlEncoded
    @POST("users.php")
    fun admintypestatus(  @Field("type") type:String,  @Field("status") status:String,@Field("condition") condition:String): Call<Userresponse>

    @FormUrlEncoded
    @POST("users.php")
    fun deleteperson(
        @Field("id") id:Int,
        @Field("condition")condition:String
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("hints.php")
    fun Addhints(
        @Field("name") name:String,
        @Field("num") num:String,
        @Field("email")email :String,
        @Field("hiname") hiname:String,
        @Field("hides") hides:String,
        @Field("path")   path :String,
        @Field("condition") condition:String,
    ): Call<DefaultResponse>


    @FormUrlEncoded
    @POST("hints.php")
    fun readbyid(
        @Field("id") id:Int,
        @Field("condition") condition:String,
    ): Call<hintresponse>


    @GET("gethints.php")
    fun gethints():Call<hintresponse>


    @FormUrlEncoded
    @POST("hints.php")
    fun deletehint(
        @Field("id") id:Int,
        @Field("condition")condition:String
    ): Call<DefaultResponse>


    @FormUrlEncoded
    @POST("hints.php")
    fun viewhints(
        @Field("email") email:String,
        @Field("condition")condition:String
    ): Call<hintresponse>



    @FormUrlEncoded
    @POST("Appointment.php")
    fun addappointment(
        @Field("date") date:String,
        @Field("reason")reason:String,
        @Field("hours")hours :String,
        @Field("dname") dname:String,
        @Field("dnum") dnum:String,
        @Field("demail")demail :String,
        @Field("appointid") appointid :String,
        @Field("uname") uname:String,
        @Field("unum") unum:String,
        @Field("uemail")uemail:String,
        @Field("cost") cost:String,
        @Field("status") status:String,
        @Field("pstatus") pstatus:String,
        @Field("rating")rating:String,
        @Field("feedback")feedback:String,
        @Field("condition") condition:String,
    ): Call<DefaultResponse>


    @FormUrlEncoded
    @POST("Appointment.php")
    fun hospital(
        @Field("demail")demail :String,
        @Field("condition") condition:String,
    ): Call<Appointmentresponse>

    @FormUrlEncoded
    @POST("Appointment.php")
    fun docstatus(

        @Field("cost") cost:String,
        @Field("status") status:String,
        @Field("id") id: Int,
        @Field("condition") condition:String,
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("Appointment.php")
    fun userappointment(
        @Field("uemail")uemail:String,
        @Field("condition") condition:String,
    ): Call<Appointmentresponse>


    @FormUrlEncoded
    @POST("Appointment.php")
    fun userstatus(
        @Field("pstatus") pstatus:String,
        @Field("id") id: Int,
        @Field("condition") condition:String,
    ): Call<DefaultResponse>


    @FormUrlEncoded
    @POST("Appointment.php")
    fun doccompletedstatus(
        @Field("status") status:String,
        @Field("pstatus") pstatus:String,
        @Field("id") id: Int,
        @Field("condition") condition:String,
    ): Call<DefaultResponse>



    @FormUrlEncoded
    @POST("Appointment.php")
    fun userfeedback(
        @Field("rating")rating:String,
        @Field("feedback")feedback:String,
        @Field("id") id: Int,
        @Field("condition") condition:String,
    ): Call<DefaultResponse>


    @GET("getappointments.php")
    fun getappointments():Call<Appointmentresponse>
}