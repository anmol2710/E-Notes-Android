package com.example.e_notes

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.nsut_connect.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class Notes : ComponentActivity() {
    lateinit var NotesArray: ArrayList<NotesData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        val subject = intent.getStringExtra("subjectCode")
        val db = Firebase.firestore
        val listview = findViewById<ListView>(R.id.listview);

        if (subject != null) {
            db.collection(subject)
                .get()
                .addOnSuccessListener { result ->
                    if(!result.isEmpty) {
                        NotesArray = ArrayList();
                        for (document in result) {
                            NotesArray.add(
                                NotesData(
                                    document.data["Name"].toString(),
                                    document.data["pdfUrl"].toString()
                                )
                            )
                        }

                        listview.adapter = MyNotesAdapter(this, NotesArray)
                    }
                    else{
                        Toast.makeText(applicationContext , "Notes will be uploaded Soon" , Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(applicationContext , "There is some Error in fetching the notes" , Toast.LENGTH_SHORT).show()
                }
        }
    }

}