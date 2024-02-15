package com.example.e_notes

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.nsut_connect.R

class MyAdapter(val context: Activity, val subjectArray: ArrayList<Subject>) :
    ArrayAdapter<Subject>(context, R.layout.each_item, subjectArray) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.each_item, parent, false)
        }

        val subject = view!!.findViewById<TextView>(R.id.subject)
        val notes = view.findViewById<Button>(R.id.notes)
        val labNotes = view.findViewById<Button>(R.id.labNotes)

        subject.text = subjectArray[position].name

        notes.setOnClickListener {
            val intent = Intent(context, Notes::class.java)
            intent.putExtra("subjectCode", subjectArray[position].subCode)
            context.startActivity(intent)
        }

        labNotes.setOnClickListener {
            val intent = Intent(context, Notes::class.java)
            intent.putExtra("subjectCode", subjectArray[position].labCode)
            context.startActivity(intent)
        }

        return view
    }

}

