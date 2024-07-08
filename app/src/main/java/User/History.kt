package com.example.hdan_ayushservicesavailabilityapp.User

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hdan_ayushservicesavailabilityapp.Doctor.ViewAppointments
import com.example.hdan_ayushservicesavailabilityapp.R
import com.example.hdan_ayushservicesavailabilityapp.RetrofitClient
import com.example.hdan_ayushservicesavailabilityapp.model.Appointment
import com.example.hdan_ayushservicesavailabilityapp.model.Appointmentresponse
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ymts0579.model.model.DefaultResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class History : AppCompatActivity() {
    lateinit var listhistory:RecyclerView
    var email=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        listhistory=findViewById(R.id.listhistory)
        listhistory.layoutManager = LinearLayoutManager(this)
        listhistory.setHasFixedSize(true)


        getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).apply {

            email=(getString("email", "").toString())

        }



        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.instance.userappointment(email,"user")
                .enqueue(object :retrofit2.Callback<Appointmentresponse>{
                    override fun onResponse(call: Call<Appointmentresponse>, response: Response<Appointmentresponse>) {
                          listhistory.adapter=historyappointadapter(this@History,response.body()!!.user)
                    }

                    override fun onFailure(call: Call<Appointmentresponse>, t: Throwable) {

                    }
                })

        }




    }

    class historyappointadapter(var context: Context, var listdata: ArrayList<Appointment>):
        RecyclerView.Adapter<historyappointadapter.DataViewHolder>(){
        var id=0
        inner class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            val  tvappointid: TextView =view.findViewById(R.id.tvappointid)
            val tvdate: TextView =view.findViewById(R.id.tvdate)
            val  tvhours: TextView =view.findViewById(R.id.tvhours)
            val  tvsatuts: TextView =view.findViewById(R.id.tvsatuts)
            val tvreason: TextView =view.findViewById(R.id.tvreason)
            val tvuserdetails: TextView =view.findViewById(R.id.tvuserdetails)
            val tvdname: TextView =view.findViewById(R.id.tvdname)
            val tvemail: TextView =view.findViewById(R.id.tvemail)
            val tvnum: TextView =view.findViewById(R.id.tvnum)
            val linearuser: LinearLayout =view.findViewById(R.id.linearuser)
            val linearaccepted: LinearLayout =view.findViewById(R.id.linearaccepted)
            val tvsatuts1: TextView =view.findViewById(R.id.tvsatuts1)
            val  tvcost: TextView =view.findViewById(R.id.tvcost)
            val btnfeedback:Button=view.findViewById(R.id.btnfeedback)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.cardhistory, parent, false)
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
                    tvdname.text=dname
                    tvemail.text=demail
                    tvnum.text=dnum
                    tvsatuts1.text=pstatus
                    tvcost.text=cost
                    linearuser.visibility= View.GONE
                    linearaccepted.visibility= View.GONE
                    if(status=="Accepted"||status=="Completed"){
                        linearaccepted.visibility= View.VISIBLE
                    }else{
                        linearaccepted.visibility= View.GONE
                    }


                    if(pstatus=="Completed"){
                        if (feedback.isEmpty()){
                            btnfeedback.visibility=View.VISIBLE
                        }else{
                            btnfeedback.visibility= View.GONE
                        }
                    }else{
                        btnfeedback.visibility= View.GONE
                    }
                    tvuserdetails.setOnClickListener {
                        linearuser.visibility= View.VISIBLE
                        Handler().postDelayed({
                            linearuser.visibility= View.GONE
                        },3500)
                    }


                    btnfeedback.setOnClickListener {
                        val dd=BottomSheetDialog(context)
                        dd.setContentView(R.layout.cardfeedback)
                       val etfeedback=dd.findViewById<EditText>(R.id.etfeedback)!!
                       val btnsubmit=dd.findViewById<Button>(R.id.btnsubmit)!!
                        val rating=dd.findViewById<RatingBar>(R.id.rating)!!
                        btnsubmit.setOnClickListener {
                            val rate=rating.rating.toString()
                            val feed=etfeedback.text.toString().trim()
                            if(rate=="0.0"){
                                Toast.makeText(context, "give your rating", Toast.LENGTH_SHORT).show()
                            }else if(feed.isEmpty()){
                                Toast.makeText(context, "Enter your Feedback", Toast.LENGTH_SHORT).show()
                            }else{
                                CoroutineScope(Dispatchers.IO).launch {
                                    RetrofitClient.instance.userfeedback(rate,feed,id,"userfeedback")
                                        .enqueue(object: retrofit2.Callback<DefaultResponse> {
                                            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {

                                            }
                                            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                                                Toast.makeText(context,"Successfully Noted your response ", Toast.LENGTH_SHORT).show()
                                                dd.dismiss()
                                            }
                                        })
                                }
                            }

                        }

                        dd.show()
                    }


                    itemView.setOnClickListener {
                        if(status=="Rejected"||status=="Completed"){
                            Toast.makeText(context, "Already ${status}", Toast.LENGTH_SHORT).show()
                        }else{
                            val alertdialog= AlertDialog.Builder(context)
                            alertdialog.setTitle("Accept/Reject")
                            alertdialog.setIcon(R.drawable.log)
                            alertdialog.setCancelable(false)
                            alertdialog.setMessage("Do you Want to Accept or reject the Doctor Cost?")
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
            CoroutineScope(Dispatchers.IO).launch {
                RetrofitClient.instance.userstatus(status,id,"userstatus")
                    .enqueue(object: retrofit2.Callback<DefaultResponse> {
                        override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {

                        }
                        override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                            Toast.makeText(context,"Successfully Noted your response ", Toast.LENGTH_SHORT).show()
                        }
                    })
            }

        }


        override fun getItemCount() = listdata.size
    }
}