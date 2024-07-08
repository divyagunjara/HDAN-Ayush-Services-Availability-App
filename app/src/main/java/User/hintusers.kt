package com.example.hdan_ayushservicesavailabilityapp.User

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.hdan_ayushservicesavailabilityapp.R
import com.example.hdan_ayushservicesavailabilityapp.RetrofitClient
import com.example.hdan_ayushservicesavailabilityapp.model.hintresponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class hintusers : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hintusers)

        val tvhname=findViewById<TextView>(R.id.tvhname)
        val tvhides=findViewById<TextView>(R.id.tvhides)
        val viewimg=findViewById<ImageView>(R.id.viewimg)
        val tvdocdetails=findViewById<TextView>(R.id.tvdocdetails)
        val linearuser=findViewById<LinearLayout>(R.id.linearuser)
        val tvdname=findViewById<TextView>(R.id.tvdname)
        val tvemail=findViewById<TextView>(R.id.tvemail)
        val tvnum=findViewById<TextView>(R.id.tvnum)


        val p= ProgressDialog(this)
        p.show()
        CoroutineScope(Dispatchers.IO).launch {
            intent.getIntExtra("id",345)?.let {
                RetrofitClient.instance.readbyid(it,"readbyid")
                    .enqueue(object : Callback<hintresponse> {
                        @SuppressLint("SetTextI18n")
                        override fun onResponse(call: Call<hintresponse>, response: Response<hintresponse>) {
                            response.body()!!.user.get(0).apply {
                                tvhname.text=hiname
                                tvhides.text=hides
                                tvdname.text=name
                                tvemail.text=email
                                tvnum.text=num
                                val uri= Uri.parse(path)
                                Glide.with(this@hintusers).load(uri).into(viewimg)
                                linearuser.visibility= View.GONE



                                tvdocdetails.setOnClickListener {
                                    linearuser.visibility= View.VISIBLE
                                    Handler().postDelayed({
                                        linearuser.visibility= View.GONE
                                    },3500)
                                }
                            }
                            p.dismiss()
                        }

                        override fun onFailure(call: Call<hintresponse>, t: Throwable) {
                            Toast.makeText(this@hintusers, "${t.message}", Toast.LENGTH_SHORT).show()

                        }

                    })
            }
        }
    }
}