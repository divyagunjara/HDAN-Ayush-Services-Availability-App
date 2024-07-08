package com.example.hdan_ayushservicesavailabilityapp.Doctor

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hdan_ayushservicesavailabilityapp.R
import com.example.hdan_ayushservicesavailabilityapp.RetrofitClient
import com.ymts0579.model.model.DefaultResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class doctorhome : Fragment() {

     lateinit var tvdname:TextView
    lateinit var tvdstatus:TextView
    lateinit var spinnstatus: Spinner
    lateinit var btnupdate:Button
    lateinit var btnayurvedahints:Button
    lateinit var btnappointment:Button
    lateinit var btnfeedbacks:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_doctorhome, container, false)
        tvdname=view.findViewById(R.id.tvdname)
        tvdstatus=view.findViewById(R.id.tvdstatus)
        spinnstatus=view.findViewById(R.id.spinnstatus)
        btnupdate=view.findViewById(R.id.btnupdate)
        btnayurvedahints=view.findViewById(R.id.btnayurvedahints)
        btnappointment=view.findViewById(R.id.btnappointment)

        ArrayAdapter(requireActivity(),android.R.layout.simple_dropdown_item_1line, arrayOf("Available","Not Available")).apply {
            spinnstatus.adapter= this
        }

        var name=""
        var num=""
        var add=""
        var pass=""
        var city=""
        var id = 0
        var type=""
        var status=""
        var email=""
        requireActivity().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).apply {
            tvdname.setText("welcome "+getString("name", "").toString())
            tvdstatus.setText("Status "+getString("status", "").toString())
            type=getString("type","").toString()
            status=getString("status","").toString()
            name=getString("name", "").toString()
            num=getString("mob", "").toString()
            add=getString("address", "").toString()
            pass=getString("pass", "").toString()
            city=getString("city", "").toString()
            email=getString("email", "").toString()
            id = getInt("id", 0)
        }

        btnayurvedahints.setOnClickListener { startActivity(Intent(context,Ayurvedahints::class.java)) }
        btnappointment.setOnClickListener { startActivity(Intent(context,ViewAppointments::class.java)) }


        btnupdate.setOnClickListener {
            val types=spinnstatus.selectedItem.toString()
            CoroutineScope(Dispatchers.IO).launch {
                RetrofitClient.instance.updatestatus(types,id,"updatestatus")

                    .enqueue(object : Callback<DefaultResponse> {
                        override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                            t.message?.let { it1 ->
                                Toast.makeText(context,t.message, Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onResponse(
                            call: Call<DefaultResponse>,
                            response: Response<DefaultResponse>
                        ) {
                            Toast.makeText(context, response.body()!!.message, Toast.LENGTH_SHORT).show()
                            tvdstatus.setText("status "+types)
                            requireActivity().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).edit().apply {
                                putString("mob",num)
                                putString("pass",pass)
                                putString("email",email)
                                putString("name",name)
                                putString("address",add)
                                putString("city",city)
                                putString("type",type)
                                putString("status",types)
                                putInt("id",id)
                                apply()
                            }

                        }
                    })
            }
        }

        return view
    }


}