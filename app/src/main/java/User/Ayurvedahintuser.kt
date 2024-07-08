package com.example.hdan_ayushservicesavailabilityapp.User

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hdan_ayushservicesavailabilityapp.Doctor.Ayurvedahints
import com.example.hdan_ayushservicesavailabilityapp.R
import com.example.hdan_ayushservicesavailabilityapp.RetrofitClient
import com.example.hdan_ayushservicesavailabilityapp.login
import com.example.hdan_ayushservicesavailabilityapp.model.hintresponse
import com.example.hdan_ayushservicesavailabilityapp.model.hints
import com.ymts0579.model.model.DefaultResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Ayurvedahintuser : AppCompatActivity() {
    lateinit var listhints: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ayurvedahintuser)
        listhints=findViewById(R.id.listhints)
        listhints.layoutManager = LinearLayoutManager(this)
        listhints.setHasFixedSize(true)


        val p= ProgressDialog(this)
        p.show()
        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.instance.gethints()
                .enqueue(object : Callback<hintresponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call<hintresponse>, response: Response<hintresponse>) {
                        listhints.adapter=
                          userhintadapter(this@Ayurvedahintuser, response.body()!!.user)
                        p.dismiss()
                    }

                    override fun onFailure(call: Call<hintresponse>, t: Throwable) {
                        Toast.makeText(this@Ayurvedahintuser, "${t.message}", Toast.LENGTH_SHORT).show()

                    }

                })
        }
    }

    class userhintadapter(var context: Context, var listdata: ArrayList<hints>):
        RecyclerView.Adapter<userhintadapter.DataViewHolder>(){
        var id=0
        class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            val tvhname=view.findViewById<TextView>(R.id.tvhname)
            val tvhides=view.findViewById<TextView>(R.id.tvhides)
            val viewimg=view.findViewById<ImageView>(R.id.viewimg)
            val tvdocdetails=view.findViewById<TextView>(R.id.tvdocdetails)
            val linearuser=view.findViewById<LinearLayout>(R.id.linearuser)
            val tvdname=view.findViewById<TextView>(R.id.tvdname)
            val tvemail=view.findViewById<TextView>(R.id.tvemail)
            val tvnum=view.findViewById<TextView>(R.id.tvnum)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.carduserhints, parent, false)
            return DataViewHolder(view)
        }

        override fun onBindViewHolder(holder: DataViewHolder, @SuppressLint("RecyclerView") position:Int) {
            holder.apply {
                listdata.get(position).apply {
                    tvhname.text=hiname
                    tvhides.text=hides
                    tvdname.text=name
                    tvemail.text=email
                    tvnum.text=num
                    val uri= Uri.parse(path)
                    Glide.with(context).load(uri).into(holder.viewimg)
                    linearuser.visibility=View.GONE



                   tvdocdetails.setOnClickListener {
                       linearuser.visibility=View.VISIBLE
                       Handler().postDelayed({
                           linearuser.visibility=View.GONE
                       },3500)
                   }


                    itemView.setOnClickListener {
                        val ii=Intent(context,hintusers::class.java)
                        ii.putExtra("id",id)
                        context.startActivity(ii)
                    }
                }

            }




        }

        private fun deletehint(id: Int) {
            CoroutineScope(Dispatchers.IO).launch {
                RetrofitClient.instance.deletehint(id,"deletetable")
                    .enqueue(object: Callback<DefaultResponse> {
                        override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                            Toast.makeText(context, ""+t.message, Toast.LENGTH_SHORT).show()
                        }
                        override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                            Toast.makeText(context, ""+response.body()!!.message, Toast.LENGTH_SHORT).show()

                        }
                    })
            }
        }


        override fun getItemCount() = listdata.size
    }
}