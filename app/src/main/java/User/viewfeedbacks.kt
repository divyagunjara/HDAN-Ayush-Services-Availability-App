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
import com.ymts0579.model.model.DefaultResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class viewfeedbacks : AppCompatActivity() {

    lateinit var listfeedbacks:RecyclerView
    var demail=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewfeedbacks)

        listfeedbacks=findViewById(R.id.listfeedbacks)

        listfeedbacks.layoutManager = LinearLayoutManager(this)
        listfeedbacks.setHasFixedSize(true)
        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.instance.hospital(intent.getStringExtra("demail").toString(),"hospital")
                .enqueue(object :retrofit2.Callback<Appointmentresponse>{
                    override fun onResponse(call: Call<Appointmentresponse>, response: Response<Appointmentresponse>) {
                      listfeedbacks.adapter=docsfeedbackadapter(this@viewfeedbacks,response.body()!!.user)
                    }

                    override fun onFailure(call: Call<Appointmentresponse>, t: Throwable) {

                    }
                })

        }
    }


    class docsfeedbackadapter(var context: Context, var listdata: ArrayList<Appointment>):
        RecyclerView.Adapter<docsfeedbackadapter.DataViewHolder>(){
        var id=0
        class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {


            val  ratingbar:RatingBar=view.findViewById(R.id.rating)
            val  tvfeedback: TextView =view.findViewById(R.id.tvfeedback)
            val linearfeed:LinearLayout=view.findViewById(R.id.linearfeed)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.carddocfeedback, parent, false)
            return DataViewHolder(view)
        }

        override fun onBindViewHolder(holder: DataViewHolder, @SuppressLint("RecyclerView") position:Int) {
            holder.apply {
                listdata.get(position).apply {

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





                }

            }

        }




        override fun getItemCount() = listdata.size
    }
}