package com.example.hdan_ayushservicesavailabilityapp.User

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.hdan_ayushservicesavailabilityapp.R
import com.example.hdan_ayushservicesavailabilityapp.RetrofitClient
import com.example.hdan_ayushservicesavailabilityapp.login
import com.example.hdan_ayushservicesavailabilityapp.showToast
import com.ymts0579.model.model.DefaultResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class Addappointment : AppCompatActivity() {
    lateinit var tvname:TextView
    lateinit var tvnum:TextView
    lateinit var tvemail:TextView
    lateinit var tvappointid:TextView
    lateinit var etappdate:EditText
    lateinit var etapphours:EditText
    lateinit var etreason:EditText
    lateinit var btnappointmnet:Button
    var uname=""
    var unum=""
    var uemail=""
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addappointment)
        tvname = findViewById(R.id.tvname)
        tvnum = findViewById(R.id.tvnum)
        tvemail = findViewById(R.id.tvemail)
        tvappointid = findViewById(R.id.tvappointid)
        etappdate = findViewById(R.id.etappdate)
        etapphours=findViewById(R.id.etapphours)
        etreason=findViewById(R.id.etreason)
        btnappointmnet=findViewById(R.id.btnappointmnet)
        val v = (1000..9999).shuffled().last()
        val c: Calendar = Calendar.getInstance()



        tvname.text = intent.getStringExtra("dname")
        tvnum.text = intent.getStringExtra("dnum")
        tvemail.text = intent.getStringExtra("demail")
        tvappointid.text = "MED $v"


        getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).apply {

            uname=getString("name", "").toString()
            unum=(getString("mob", "").toString())
            uemail=(getString("email", "").toString())

        }

        etappdate.setOnClickListener {
            val c: Calendar = Calendar.getInstance()
            val dd= DatePickerDialog(this, { view, year, month, dayOfMonth ->
                var dt = "$dayOfMonth-${month + 1}-$year"
                TimePickerDialog(this,
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, min ->
                        dt += "  $hourOfDay:$min"
                        etappdate.setText(dt)

                    }, c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false
                ).show() }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).apply {

                this.datePicker.minDate=System.currentTimeMillis()-1000

                show()
            }
        }


        btnappointmnet.setOnClickListener {
            val date=etappdate.text.toString().trim()
            val reason=etreason.text.toString().trim()
            val hours=etapphours.text.toString().trim()
            val dname=tvname.text.toString()
            val dnum=tvnum.text.toString()
            val demail=tvemail.text.toString()
            val appointid=tvappointid.text.toString()

            if(date.isEmpty()){
                showToast("Enter the appointment Date")
            }else if(reason.isEmpty()){
                showToast("Enter the Appointment reason")
            }else if(hours.isEmpty()){
                showToast("Enter the Appointment hours")
            }else{
                CoroutineScope(Dispatchers.IO).launch {
                    RetrofitClient.instance.addappointment(date,reason,hours,dname,dnum,demail,appointid,uname,unum,uemail,""
                    ,"Pending","Pending","","","register")
                        .enqueue(object: Callback<DefaultResponse> {
                            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                                showToast(t.message)
                            }
                            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                                Toast.makeText(this@Addappointment,"${response.body()!!.message}", Toast.LENGTH_SHORT).show()

                                startActivity(Intent(this@Addappointment,History::class.java))


                            }
                        })
                }
            }
        }

    }
}