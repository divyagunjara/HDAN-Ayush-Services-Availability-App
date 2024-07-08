package com.example.hdan_ayushservicesavailabilityapp.Admin

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hdan_ayushservicesavailabilityapp.R
import com.example.hdan_ayushservicesavailabilityapp.RetrofitClient
import com.example.hdan_ayushservicesavailabilityapp.showToast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.ymts0579.fooddonationapp.model.Userresponse
import com.ymts0579.model.model.DefaultResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminYoga : AppCompatActivity() {
    lateinit var listyoga:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_yoga)
        listyoga=findViewById(R.id.listyoga)
        listyoga.layoutManager = LinearLayoutManager(this)
        listyoga.setHasFixedSize(true)


        readyoga()


        findViewById<FloatingActionButton>(R.id.btnadddoctor).setOnClickListener{
            val dd= BottomSheetDialog(this)
            dd.setContentView(R.layout.cardaddyoga)
            val  etname=dd.findViewById<EditText>(R.id.etname)!!
            val etunum=dd.findViewById<EditText>(R.id.etunum)!!
            val etemail=dd.findViewById<EditText>(R.id.etemail)!!
            val etuaddress=dd.findViewById<EditText>(R.id.etuaddress)!!
            val etucity=dd.findViewById<EditText>(R.id.etucity)!!
            val etspecific=dd.findViewById<EditText>(R.id.etspecific)!!
            val btnsubmit=dd.findViewById<Button>(R.id.btnsubmit)!!

            btnsubmit.setOnClickListener {
                val name=etname.text.toString().trim()
                val num=etunum.text.toString().trim()
                val email=etemail.text.toString().trim()
                val add=etuaddress.text.toString().trim()
                val city=etucity.text.toString().trim()
                val spec=etspecific.text.toString().trim()
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
                }  else if (city.isEmpty()) {
                    showToast( "Enter your City")
                    etucity.error = "Enter your City"
                } else if (!email.contains("@gmail.com")) {
                    showToast( "Enter your Email Properly")
                    etemail.error = "Enter your Email Properly"
                }
                else{
                    if (num.count() == 10) {
                        CoroutineScope(Dispatchers.IO).launch {
                            RetrofitClient.instance.register(name,num,email,city,"",add,"Yoga","Available",spec,"register")
                                .enqueue(object: Callback<DefaultResponse> {
                                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                                        t.message?.let { it1 -> Snackbar.make(it, it1, Snackbar.LENGTH_SHORT).show() }
                                    }
                                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                                        Toast.makeText(this@AdminYoga,"Successful", Toast.LENGTH_SHORT).show()
                                        etspecific.text!!.clear()
                                        etname.text!!.clear()
                                        etunum.text!!.clear()
                                        etemail.text!!.clear()
                                        etuaddress.text!!.clear()
                                        etucity.text!!.clear()
                                        readyoga()
                                        dd.dismiss()

                                    }
                                })
                        }
                    } else {
                        showToast("Enter your Number")
                        etunum.error = "Enter your Number"
                    }

                }
            }

            dd.show()
        }
    }

    private fun readyoga() {
        val p= ProgressDialog(this)
        p.show()
        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.instance.getyoga()
                .enqueue(object : Callback<Userresponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call<Userresponse>, response: Response<Userresponse>) {


                        listyoga.adapter = Admindoctor.doctoradminadapter(
                            this@AdminYoga,
                            response.body()!!.user
                        )

                        p.dismiss()
                    }

                    override fun onFailure(call: Call<Userresponse>, t: Throwable) {
                        Toast.makeText(this@AdminYoga, "${t.message}", Toast.LENGTH_SHORT).show()

                    }

                })
        }
    }
}