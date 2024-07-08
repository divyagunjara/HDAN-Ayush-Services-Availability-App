package com.example.hdan_ayushservicesavailabilityapp.User

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.hdan_ayushservicesavailabilityapp.R
import com.example.hdan_ayushservicesavailabilityapp.RetrofitClient
import com.example.hdan_ayushservicesavailabilityapp.login
import com.example.hdan_ayushservicesavailabilityapp.showToast
import com.google.android.material.snackbar.Snackbar
import com.ymts0579.model.model.DefaultResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Userprofile : AppCompatActivity() {
    lateinit var ethemail1: EditText
    lateinit var ethname1: EditText
    lateinit var ethnum1: EditText
    lateinit var ethaddress1: EditText
    lateinit var ethcity1: EditText
    lateinit var ethpass1: EditText
    lateinit var btnupdate: Button
    lateinit var btnhistory:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userprofile)

        ethemail1=findViewById(R.id.ethemail1)
        ethname1=findViewById(R.id.ethname1)
        ethnum1=findViewById(R.id.ethnum1)
        ethaddress1=findViewById(R.id.ethaddress1)
        ethcity1=findViewById(R.id.ethcity1)
        ethpass1=findViewById(R.id.ethpass1)
        btnupdate=findViewById(R.id.btnupdate)
        btnhistory=findViewById(R.id.btnhistory)


        var id = 0;
        var type=""
        var status=""

        getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).apply {
            id = getInt("id", 0)
            ethname1.setText(getString("name", "").toString())
            ethnum1.setText(getString("mob", "").toString())
            ethpass1.setText(getString("pass", "").toString())
            ethemail1.setText(getString("email", "").toString())
            ethcity1.setText(getString("city", "").toString())
            ethaddress1.setText(getString("address", "").toString())
            type=getString("type","").toString()
            status=getString("status","").toString()
        }

        btnhistory.setOnClickListener {
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


        btnupdate.setOnClickListener {
            val name = ethname1.text.toString()
            val num = ethnum1.text.toString()
            val add = ethaddress1.text.toString()
            val pass = ethpass1.text.toString()
            val city = ethcity1.text.toString()

            if (num.count() == 10) {

                CoroutineScope(Dispatchers.IO).launch {
                    RetrofitClient.instance.updateusers(
                        "$name", "$num", "$pass",
                        "$add", "$city", id, "update"
                    )
                        .enqueue(object : Callback<DefaultResponse> {
                            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                                t.message?.let { it1 ->
                                    Snackbar.make(it, it1, Snackbar.LENGTH_SHORT).show()
                                }
                            }

                            override fun onResponse(
                                call: Call<DefaultResponse>,
                                response: Response<DefaultResponse>
                            ) {

                                  showToast(response.body()!!.message)


                                getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).edit().apply {
                                    putString("mob",num)
                                    putString("pass",pass)
                                    putString("email",ethemail1.text.toString().trim())
                                    putString("name",name)
                                    putString("address",add)
                                    putString("city",city)
                                    putString("type",type)
                                    putString("status",status)
                                    putInt("id",id)
                                    apply()
                                }

                            }
                        })
                }

                //Toast.makeText(activity, "$name,$num,$add,$city", Toast.LENGTH_SHORT).show()
            } else {
                ethnum1.setError("Enter number properly")
                Toast.makeText(this, "Enter number properly", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}