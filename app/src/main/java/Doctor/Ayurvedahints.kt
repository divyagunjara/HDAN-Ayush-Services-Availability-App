package com.example.hdan_ayushservicesavailabilityapp.Doctor

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.example.hdan_ayushservicesavailabilityapp.Admin.Adminusers
import com.example.hdan_ayushservicesavailabilityapp.R
import com.example.hdan_ayushservicesavailabilityapp.RetrofitClient
import com.example.hdan_ayushservicesavailabilityapp.model.hintresponse
import com.example.hdan_ayushservicesavailabilityapp.model.hints
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ymts0579.fooddonationapp.model.Userresponse
import com.ymts0579.model.model.DefaultResponse
import com.ymts0579.model.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Ayurvedahints : AppCompatActivity() {
    lateinit var listhints:RecyclerView
    var umail=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ayurvedahints)
        listhints=findViewById(R.id.listhints)
        listhints.layoutManager = LinearLayoutManager(this)
        listhints.setHasFixedSize(true)

        findViewById<LinearLayout>(R.id.btnaddhints).setOnClickListener{
              startActivity(Intent(this,Addhints::class.java))
        }
        getSharedPreferences("user", MODE_PRIVATE).apply {
            umail=getString("email","").toString()
        }

        val p= ProgressDialog(this)
        p.show()
        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.instance.viewhints("$umail","Viewemailhint")
                .enqueue(object : Callback<hintresponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call<hintresponse>, response: Response<hintresponse>) {


                        listhints.adapter=dochintadapter(this@Ayurvedahints,response.body()!!.user)

                        p.dismiss()
                    }

                    override fun onFailure(call: Call<hintresponse>, t: Throwable) {
                        Toast.makeText(this@Ayurvedahints, "${t.message}", Toast.LENGTH_SHORT).show()

                    }

                })
        }
    }


    class dochintadapter(var context: Context, var listdata: ArrayList<hints>):
        RecyclerView.Adapter<dochintadapter.DataViewHolder>(){
        var id=0
        class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            val tvhname=view.findViewById<TextView>(R.id.tvhname)
            val tvhides=view.findViewById<TextView>(R.id.tvhides)
            val viewimg=view.findViewById<ImageView>(R.id.viewimg)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.cardviewhints, parent, false)
            return DataViewHolder(view)
        }

        override fun onBindViewHolder(holder: DataViewHolder, @SuppressLint("RecyclerView") position:Int) {
            holder.apply {
                listdata.get(position).apply {
                    tvhname.text=hiname
                    tvhides.text=hides
                    val uri= Uri.parse(path)
                    Glide.with(context).load(uri).into(holder.viewimg)



                    itemView.setOnClickListener {
                        val alertdialog= AlertDialog.Builder(context)
                        alertdialog.setIcon(R.drawable.ic_launcher_foreground)
                        alertdialog.setTitle("Delete")
                        alertdialog.setIcon(R.drawable.log)

                        alertdialog.setMessage("Do you Want to Delete ?")
                        alertdialog.setPositiveButton("Yes"){ alertdialog, which->
                            deletehint(id)
                        }
                        alertdialog.setNegativeButton("No"){alertdialog,which->
                            Toast.makeText(context,"thank you", Toast.LENGTH_SHORT).show()
                            alertdialog.dismiss()
                        }
                        alertdialog.show()
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