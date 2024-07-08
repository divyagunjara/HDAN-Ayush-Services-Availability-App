package com.example.hdan_ayushservicesavailabilityapp.Doctor

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
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
import java.io.ByteArrayOutputStream

class Addhints : AppCompatActivity() {
    lateinit var imgeadd: ImageView
    lateinit var etname: EditText
    lateinit var etdes: EditText
    lateinit var btnaddhin:Button
    var encode=""
    var name=""
    var num=""
    var email=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addhints)
        imgeadd=findViewById(R.id.imgeadd)
        etname=findViewById(R.id.etname)
        etdes=findViewById(R.id.etdes)
        btnaddhin=findViewById(R.id.btnaddhin)


        getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).apply {

            name=getString("name", "").toString()
            num=getString("mob", "").toString()
            email=getString("email", "").toString()

        }


        imgeadd.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(intent,90)
        }


        btnaddhin.setOnClickListener {
            val hiname=etname.text.toString().trim()
            val hides=etdes.text.toString().trim()
            if(hiname.isEmpty()){
                showToast("Enter hint name")
            }else if(hides.isEmpty()){
                showToast("Enter hint description")
            }else if(encode==""){
                showToast("Add the image of hints")
            }else{

                CoroutineScope(Dispatchers.IO).launch {
                    RetrofitClient.instance.Addhints(name,num,email,hiname,hides,encode,"addhint")
                        .enqueue(object: Callback<DefaultResponse> {
                            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                                showToast(t.message)
                            }
                            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                                Toast.makeText(this@Addhints,"Successful added", Toast.LENGTH_SHORT).show()


                            }
                        })
                }



            }
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 90 && resultCode == RESULT_OK) {
            val tt1 = data?.data.toString()
            val uri = Uri.parse(tt1)
            imgeadd.setImageURI(uri)
            val bit= MediaStore.Images.Media.getBitmap(contentResolver,uri)
            val byte = ByteArrayOutputStream()
            bit.compress(Bitmap.CompressFormat.JPEG, 100, byte)
            val image = byte.toByteArray()
            encode = android.util.Base64.encodeToString(image, Base64.DEFAULT)

        }
    }
}