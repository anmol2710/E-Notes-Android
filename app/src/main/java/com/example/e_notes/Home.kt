package com.example.e_notes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.activity.ComponentActivity
import com.example.nsut_connect.R

class Home : ComponentActivity() {
    lateinit var SubjectArray: ArrayList<Subject>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val listview = findViewById<ListView>(R.id.listview);
        val logout = findViewById<Button>(R.id.logout);

        SubjectArray = ArrayList()
        SubjectArray.add(Subject("Principles of Electrical Engineering (PEE)" , "PEE" , "Lab-PEE"))
        SubjectArray.add(Subject("Electrical Measurements (EM)" , "EM" , "Lab-EM"))
        SubjectArray.add(Subject("              Mathematics-II            " , "Maths2" , "Lab-Maths2"))
        SubjectArray.add(Subject("Introduction to Electromagnetic Theory (EMT)" , "EMT" , "Lab-EMT"))
        SubjectArray.add(Subject("Electronics Devices and Circuits (EDC)" , "EDC" , "Lab-EDC"))
        SubjectArray.add(Subject("Computer Programming (CP)" , "CP" , "Lab-CP"))

        listview.adapter = MyAdapter(this , SubjectArray);

        logout.setOnClickListener{
            val preferences = applicationContext.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            preferences.edit().clear().apply()
            val intent = Intent(applicationContext , SignIn::class.java);
            startActivity(intent)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finishAffinity()
        super.onBackPressed()
    }
}
