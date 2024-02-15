package com.example.e_notes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.nsut_connect.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    data class User(
        val name: String,
        val rollNo: String,
        val email: String,
        val password: String
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = applicationContext.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)
        if(userId != null){
            val intent = Intent(this , Home::class.java)
            startActivity(intent)
        }

        val etName = findViewById<TextView>(R.id.etName);
        val etRoll = findViewById<TextView>(R.id.etRoll);
        val etEmail = findViewById<TextView>(R.id.etEmail);
        val etPassword = findViewById<TextView>(R.id.etPassword);
        val etCPassword = findViewById<TextView>(R.id.etCPassword);
        val SignUpbtn = findViewById<Button>(R.id.SignUpbtn);
        val SignInbtn = findViewById<Button>(R.id.SignInbtn)

        SignUpbtn.setOnClickListener {
            val name = etName.text.toString().trim();
            val rollNo = etRoll.text.toString().trim();
            val email = etEmail.text.toString().trim();
            val password = etPassword.text.toString().trim();
            val Cpassword = etCPassword.text.toString().trim();

            if (name.length < 3) {
                Toast.makeText(applicationContext, "Name is invalid", Toast.LENGTH_SHORT).show()
            } else if (rollNo.length != 11) {
                Toast.makeText(applicationContext, "Roll No is invalid", Toast.LENGTH_SHORT).show()
            } else if (email.isEmpty()) {
                Toast.makeText(applicationContext, "Email is invalid", Toast.LENGTH_SHORT).show()
            } else if (password.length < 8) {
                Toast.makeText(applicationContext, "Password is too short", Toast.LENGTH_SHORT)
                    .show()
            } else if (password != Cpassword) {
                Toast.makeText(applicationContext, "Password does not match", Toast.LENGTH_SHORT)
                    .show()
            } else {

                val retrofitBuilder = Retrofit.Builder()
                    .baseUrl("https://e-notes-backend.vercel.app/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiInterface::class.java)

                val retrofitData = retrofitBuilder.signUp(SignUpRequestData(name , rollNo , email , password))

                retrofitData.enqueue(object : Callback<MyData?> {
                    override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                        val responseBody = response.body()
                        if(responseBody?.status == true){
                            val sharedPreferences = applicationContext.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()

                            editor.putString("userId", responseBody.msg)
                            editor.apply()

                            Toast.makeText(applicationContext , "Success", Toast.LENGTH_LONG).show()
                        }
                        else{
                            Toast.makeText(applicationContext , responseBody?.msg, Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<MyData?>, t: Throwable) {
                        Toast.makeText(applicationContext , t.message, Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
        SignInbtn.setOnClickListener {
            val intent = Intent(this@MainActivity, SignIn::class.java);
            startActivity(intent)
        }
    }
}
