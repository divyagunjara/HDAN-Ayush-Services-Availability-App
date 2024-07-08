package com.example.hdan_ayushservicesavailabilityapp.Admin

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
import com.ymts0579.model.model.DefaultResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class Adminfeedbacks : AppCompatActivity() {
    lateinit var listfeedbacks: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminfeedbacks)
        listfeedbacks=findViewById(R.id.listfeed)
        listfeedbacks.layoutManager = LinearLayoutManager(this)
        listfeedbacks.setHasFixedSize(true)


        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.instance.getappointments()
                .enqueue(object :retrofit2.Callback<Appointmentresponse>{
                    override fun onResponse(call: Call<Appointmentresponse>, response: Response<Appointmentresponse>) {
                       listfeedbacks.adapter=adminfeedadapter(this@Adminfeedbacks,response.body()!!.user)
                    }

                    override fun onFailure(call: Call<Appointmentresponse>, t: Throwable) {

                    }
                })

        }
    }


    class adminfeedadapter(var context: Context, var listdata: ArrayList<Appointment>):
        RecyclerView.Adapter<adminfeedadapter.DataViewHolder>(){
        var id=0
        class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

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

            val linearfeed: LinearLayout =view.findViewById(R.id.linearfeed)
            val  ratingbar: RatingBar =view.findViewById(R.id.rating)
            val  tvfeedback: TextView =view.findViewById(R.id.tvfeedback)

            val tvuserdetails1:TextView=view.findViewById(R.id.tvuserdetails1)
            val linearuser1:LinearLayout=view.findViewById(R.id.linearuser1)
            val tvdname1:TextView=view.findViewById(R.id.tvdname1)
            val tvemail1:TextView=view.findViewById(R.id.tvemail1)
            val tvnum1:TextView=view.findViewById(R.id.tvnum1)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.cardadminfeed, parent, false)
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
                    linearuser1.visibility=View.GONE
                    tvdname1.text=dname
                    tvemail1.text=demail
                    tvnum1.text=dnum
                    linearuser.visibility= View.GONE
                    linearaccepted.visibility= View.GONE
                    linearfeed.visibility= View.GONE
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
                        linearfeed.visibility= View.GONE
                    }else{
                        linearfeed.visibility= View.VISIBLE
                    }
                    if(status=="Accepted"||status=="Completed"){
                        linearaccepted.visibility= View.VISIBLE
                    }else{
                        linearaccepted.visibility= View.GONE
                    }

                    tvuserdetails.setOnClickListener {
                        linearuser.visibility = View.VISIBLE
                        Handler().postDelayed({
                            linearuser.visibility = View.GONE
                        }, 3500)
                    }


                    tvuserdetails1.setOnClickListener {
                        linearuser1.visibility = View.VISIBLE
                        Handler().postDelayed({
                            linearuser1.visibility = View.GONE
                        }, 3500)
                    }

                }

            }

        }



        override fun getItemCount() = listdata.size
    }
}