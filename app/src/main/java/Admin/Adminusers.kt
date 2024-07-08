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
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hdan_ayushservicesavailabilityapp.R
import com.example.hdan_ayushservicesavailabilityapp.RetrofitClient
import com.ymts0579.fooddonationapp.model.Userresponse
import com.ymts0579.model.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Adminusers : AppCompatActivity() {
    lateinit var listuser:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminusers)
        listuser=findViewById(R.id.listuser)
        listuser.layoutManager = LinearLayoutManager(this)
        listuser.setHasFixedSize(true)


        val p= ProgressDialog(this)
        p.show()
        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.instance.getusers()
                .enqueue(object : Callback<Userresponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call<Userresponse>, response: Response<Userresponse>) {


                        listuser.adapter = useradminadapter(
                            this@Adminusers,
                            response.body()!!.user
                        )

                        p.dismiss()
                    }

                    override fun onFailure(call: Call<Userresponse>, t: Throwable) {
                        Toast.makeText(this@Adminusers, "${t.message}", Toast.LENGTH_SHORT).show()

                    }

                })
        }
    }

    class useradminadapter(var context: Context, var listdata: ArrayList<User>):
        RecyclerView.Adapter<useradminadapter.DataViewHolder>(){
        var id=0
        class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            val tvfname: TextView =view.findViewById(R.id.tvfname);
            val tvfemail: TextView =view.findViewById(R.id.tvfemail);
            val tvfnum: TextView =view.findViewById(R.id.tvfnum);
            val tvfcity: TextView =view.findViewById(R.id.tvfcity);
            val tvdesc: TextView =view.findViewById(R.id.tvdesc)
            val btndelete: Button =view.findViewById(R.id.btndelete)


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
                    if(type=="User"){
                        tvdesc.visibility=View.GONE
                        btndelete.visibility=View.GONE
                    }

                }

            }




        }


        override fun getItemCount() = listdata.size
    }
}