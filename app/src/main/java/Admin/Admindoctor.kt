package com.example.hdan_ayushservicesavailabilityapp.Admin

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
import com.ymts0579.model.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Admindoctor : AppCompatActivity() {
    lateinit var listdoctor:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admindoctor)
        listdoctor=findViewById(R.id.listdoctor)
        listdoctor.layoutManager = LinearLayoutManager(this)
        listdoctor.setHasFixedSize(true)

        readdoctor()



        findViewById<FloatingActionButton>(R.id.btnadddoctor).setOnClickListener{
            val dd=BottomSheetDialog(this)
            dd.setContentView(R.layout.cardadddoctor)
            val  etname=dd.findViewById<EditText>(R.id.etname)!!
            val etunum=dd.findViewById<EditText>(R.id.etunum)!!
            val etemail=dd.findViewById<EditText>(R.id.etemail)!!
            val etuaddress=dd.findViewById<EditText>(R.id.etuaddress)!!
            val etucity=dd.findViewById<EditText>(R.id.etucity)!!
            val etupass=dd.findViewById<EditText>(R.id.etupass)!!
            val etspecific=dd.findViewById<EditText>(R.id.etspecific)!!
            val btnsubmit=dd.findViewById<Button>(R.id.btnsubmit)!!

            btnsubmit.setOnClickListener {
                val name=etname.text.toString().trim()
                val num=etunum.text.toString().trim()
                val email=etemail.text.toString().trim()
                val add=etuaddress.text.toString().trim()
                val city=etucity.text.toString().trim()
                val pass=etupass.text.toString().trim()
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
                } else if (pass.isEmpty()) {
                    showToast( "Enter your password")
                    etupass.error = "Enter your Password"
                } else if (city.isEmpty()) {
                    showToast( "Enter your City")
                    etucity.error = "Enter your City"
                } else if (!email.contains("@gmail.com")) {
                    showToast( "Enter your Email Properly")
                    etemail.error = "Enter your Email Properly"
                }else if (spec.isEmpty()) {
                    showToast( "Enter your Description")
                    etupass.error = "Enter your Description"
                }
                else{
                    if (num.count() == 10) {
                        CoroutineScope(Dispatchers.IO).launch {
                            RetrofitClient.instance.register(name,num,email,city,pass,add,"Doctor","Available",spec,"register")
                                .enqueue(object: Callback<DefaultResponse> {
                                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                                        t.message?.let { it1 -> Snackbar.make(it, it1, Snackbar.LENGTH_SHORT).show() }
                                    }
                                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                                        Toast.makeText(this@Admindoctor,"Your Registration Successful ", Toast.LENGTH_SHORT).show()
                                        etspecific.text!!.clear()
                                        etname.text!!.clear()
                                        etunum.text!!.clear()
                                        etemail.text!!.clear()
                                        etuaddress.text!!.clear()
                                        etucity.text!!.clear()
                                        etupass.text!!.clear()
                                        dd.dismiss()
                                        readdoctor()
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

    private fun readdoctor() {
        val p= ProgressDialog(this)
        p.show()
        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.instance.getdoctor()
                .enqueue(object : Callback<Userresponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call<Userresponse>, response: Response<Userresponse>) {


                        listdoctor.adapter = doctoradminadapter(
                            this@Admindoctor,
                            response.body()!!.user
                        )

                        p.dismiss()
                    }

                    override fun onFailure(call: Call<Userresponse>, t: Throwable) {
                        Toast.makeText(this@Admindoctor, "${t.message}", Toast.LENGTH_SHORT).show()

                    }

                })
        }
    }


    class doctoradminadapter(var context: Context, var listdata: ArrayList<User>):
        RecyclerView.Adapter<doctoradminadapter.DataViewHolder>(){
        var id=0
        class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            val tvfname: TextView =view.findViewById(R.id.tvfname);
            val tvfemail: TextView =view.findViewById(R.id.tvfemail);
            val tvfnum: TextView =view.findViewById(R.id.tvfnum);
            val tvfcity: TextView =view.findViewById(R.id.tvfcity);
            val tvdesc:TextView=view.findViewById(R.id.tvdesc)
            val btndelete:Button=view.findViewById(R.id.btndelete)


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.carduseradmin, parent, false)
            return DataViewHolder(view)
        }

        override fun onBindViewHolder(holder: DataViewHolder, @SuppressLint("RecyclerView") position:Int) {
           holder.apply {
               listdata.get(position).apply {
                   tvfname.text=name
                   tvfemail.text=email
                   tvfnum.text=moblie
                   tvfcity.text=city
                   tvdesc.text=specifics

                   btndelete.setOnClickListener {
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
                RetrofitClient.instance.deleteperson(id,"deletetable")
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