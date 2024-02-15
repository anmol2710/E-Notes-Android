package com.example.e_notes

import android.annotation.SuppressLint
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

class SignIn : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val sharedPreferences = applicationContext.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)
        if(userId != null){
            var intent = Intent(this , Home::class.java)
            startActivity(intent)
        }

        val etRoll = findViewById<TextView>(R.id.etRoll);
        val etPassword = findViewById<TextView>(R.id.etPassword);
        val SignInbtn = findViewById<Button>(R.id.SignInbtn);
        val SignUpbtn = findViewById<Button>(R.id.SignUpbtn);
        val SignUp = findViewById<TextView>(R.id.SignUp)

        SignInbtn.setOnClickListener{
            val rollNo = etRoll.text.toString().trim();
            val password = etPassword.text.toString().trim();

            if(rollNo.length != 11){
                Toast.makeText(applicationContext , "Roll No is invalid" , Toast.LENGTH_SHORT).show();
            }
            else if(password.length < 8){
                Toast.makeText(applicationContext , "Password is too short" , Toast.LENGTH_SHORT).show();
            }
            else{
                val retrofitBuilder = Retrofit.Builder()
                    .baseUrl("https://e-notes-backend.vercel.app/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiInterface::class.java)

                val retrofitData = retrofitBuilder.login(LoginRequestData(rollNo , password))

                retrofitData.enqueue(object : Callback<MyData?> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                        val responseBody = response.body();
                        if(responseBody?.status == true){
                            val sharedPreferences = applicationContext.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()

                            editor.putString("userId", responseBody.msg.toString())
                            editor.apply()
                            startActivity(Intent(this@SignIn , Home::class.java))
                        }
                        else{
                            SignUp.text = responseBody?.msg.toString()
                            Toast.makeText(applicationContext , responseBody?.msg.toString(), Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<MyData?>, t: Throwable) {
                        SignUp.text = t.message
                        Toast.makeText(applicationContext , t.message, Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
        SignUpbtn.setOnClickListener{
            val intent = Intent(this , MainActivity::class.java);
            startActivity(intent)
        }
    }
}