package com.example.hdan_ayushservicesavailabilityapp.Doctor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hdan_ayushservicesavailabilityapp.R
import com.example.hdan_ayushservicesavailabilityapp.RetrofitClient
import com.example.hdan_ayushservicesavailabilityapp.User.Addappointment
import com.example.hdan_ayushservicesavailabilityapp.User.viewlocation
import com.example.hdan_ayushservicesavailabilityapp.login
import com.example.hdan_ayushservicesavailabilityapp.model.Appointment
import com.example.hdan_ayushservicesavailabilityapp.model.Appointmentresponse
import com.example.hdan_ayushservicesavailabilityapp.showToast
import com.ymts0579.model.model.DefaultResponse
import com.ymts0579.model.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ViewAppointments : AppCompatActivity() {
    lateinit var listdocappointment:RecyclerView
    var demail=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_appointments)
        listdocappointment=findViewById(R.id.listdocappointment)
        listdocappointment.layoutManager = LinearLayoutManager(this)
        listdocappointment.setHasFixedSize(true)


      getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).apply {

          demail=(getString("email", "").toString())

        }



        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.instance.hospital(demail,"hospital")
                .enqueue(object :retrofit2.Callback<Appointmentresponse>{
                    override fun onResponse(call: Call<Appointmentresponse>, response: Response<Appointmentresponse>) {
                            listdocappointment.adapter=docsappointadapter(this@ViewAppointments,response.body()!!.user)
                    }

                    override fun onFailure(call: Call<Appointmentresponse>, t: Throwable) {

                    }
                })

        }
    }


    class docsappointadapter(var context: Context, var listdata: ArrayList<Appointment>):
        RecyclerView.Adapter<docsappointadapter.DataViewHolder>(){
        var id=0
        class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

           val  tvappointid:TextView=view.findViewById(R.id.tvappointid)
            val tvdate:TextView=view.findViewById(R.id.tvdate)
           val  tvhours:TextView=view.findViewById(R.id.tvhours)
           val  tvsatuts:TextView=view.findViewById(R.id.tvsatuts)
            val tvreason:TextView=view.findViewById(R.id.tvreason)
            val tvuserdetails:TextView=view.findViewById(R.id.tvuserdetails)
            val tvdname:TextView=view.findViewById(R.id.tvdname)
            val tvemail:TextView=view.findViewById(R.id.tvemail)
            val tvnum:TextView=view.findViewById(R.id.tvnum)
            val linearuser:LinearLayout=view.findViewById(R.id.linearuser)
            val linearaccepted:LinearLayout=view.findViewById(R.id.linearaccepted)
            val tvsatuts1:TextView=view.findViewById(R.id.tvsatuts1)
           val  tvcost:TextView=view.findViewById(R.id.tvcost)
            val btncompleted:Button=view.findViewById(R.id.btncompleted)
            val linearfeed:LinearLayout=view.findViewById(R.id.linearfeed)
           val  ratingbar:RatingBar=view.findViewById(R.id.rating)
           val  tvfeedback:TextView=view.findViewById(R.id.tvfeedback)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.carddocappointment, parent, false)
            return DataViewHolder(view)
        }

        override fun onBindViewHolder(holder: DataViewHolder, @SuppressLint("RecyclerView") position:Int) {
            holder.apply {
                listdata.get(position).apply {
                    tvappointid.text=appointid
                    tvdate.text=date
                    tvhours.text=hours
                    tvsatuts.text=status
                    tvreason.text=reason
                    tvdname.text=uname
                    tvemail.text=uemail
                    tvnum.text=unum
                    tvsatuts1.text=pstatus
                    tvcost.text=cost
                    linearuser.visibility=View.GONE
                    linearaccepted.visibility=View.GONE
                    linearfeed.visibility=View.GONE
                    tvfeedback.text=feedback

                    var floaft=0.0f
                    rating.forEach {
                        if(it!=' '&&it.isDigit()||it=='.'){
                            floaft=it.toFloat()
                        }
                    }
                    ratingbar.isIndeterminate=true
                    ratingbar.rating=floaft

                    if(feedback.isEmpty()){
                        linearfeed.visibility=View.GONE
                    }else{
                        linearfeed.visibility=View.VISIBLE
                    }
                    if(status=="Accepted"||status=="Completed"){
                        linearaccepted.visibility=View.VISIBLE
                    }else{
                        linearaccepted.visibility=View.GONE
                    }

                    if(pstatus=="Accepted"){
                        btncompleted.visibility=View.VISIBLE
                    }else{
                        btncompleted.visibility=View.GONE
                    }

                    tvuserdetails.setOnClickListener {
                        linearuser.visibility = View.VISIBLE
                        Handler().postDelayed({
                            linearuser.visibility = View.GONE
                        }, 3500)
                    }

                    btncompleted.setOnClickListener {
                        CoroutineScope(Dispatchers.IO).launch {
                            RetrofitClient.instance.doccompletedstatus("Completed","Completed",id,"doccompletedstatus")
                                .enqueue(object: retrofit2.Callback<DefaultResponse> {
                                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {

                                    }
                                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                                        Toast.makeText(context,"Successfully Noted your response ", Toast.LENGTH_SHORT).show()
                                    }
                                })
                        }
                    }


                    itemView.setOnClickListener {
                        if(status=="Rejected"||status=="Completed"){
                            Toast.makeText(context, "Already ${status}", Toast.LENGTH_SHORT).show()
                        }else{
                            val alertdialog= AlertDialog.Builder(context)
                            alertdialog.setTitle("Accept/Reject")
                            alertdialog.setIcon(R.drawable.log)
                            alertdialog.setCancelable(false)
                            alertdialog.setMessage("Do you Want to Accept or reject the Appointment?")
                            alertdialog.setPositiveButton("Yes"){ alertdialog, which->
                                updatestatus("Accepted",id)
                                alertdialog.dismiss()
                            }
                            alertdialog.setNegativeButton("No"){alertdialog,which->
                                updatestatus("Rejected",id)
                                alertdialog.dismiss()
                            }
                            alertdialog.show()
                        }

                    }
                }

            }

        }

        private fun updatestatus(status: String, id: Int) {
            if(status=="Accepted"){
                val alertDialogBuilder = AlertDialog.Builder(context)
                alertDialogBuilder.setTitle("Enter The cost")
                alertDialogBuilder.setCancelable(false)
                val editText = EditText(context)
                editText.inputType=android.text.InputType.TYPE_CLASS_NUMBER
                editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(4))
                val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
                editText.layoutParams = layoutParams
                alertDialogBuilder.setView(editText)
                alertDialogBuilder.setPositiveButton("yes") { _, _ ->
                    val cost= editText.text.toString()
                    if (cost.isEmpty()){
                        editText.error="Enter the cost"
                    }else{
                        CoroutineScope(Dispatchers.IO).launch {
                            RetrofitClient.instance.docstatus(cost,status,id,"docstatus")
                                .enqueue(object: retrofit2.Callback<DefaultResponse> {
                                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {

                                    }
                                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                                        Toast.makeText(context,"Successfully Noted your response ", Toast.LENGTH_SHORT).show()
                                    }
                                })
                        }
                    }

                }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }else{
                CoroutineScope(Dispatchers.IO).launch {
                    RetrofitClient.instance.docstatus("",status,id,"docstatus")
                        .enqueue(object: retrofit2.Callback<DefaultResponse> {
                            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {

                            }
                            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                                Toast.makeText(context,"Successfully Noted your response ", Toast.LENGTH_SHORT).show()
                            }
                        })
                }
            }

        }


        override fun getItemCount() = listdata.size
    }
}