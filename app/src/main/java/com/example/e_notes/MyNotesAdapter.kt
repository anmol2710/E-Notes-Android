package com.example.e_notes

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.nsut_connect.R

class MyNotesAdapter(val context: Activity, val notesArray: ArrayList<NotesData>) :
    ArrayAdapter<NotesData>(context , R.layout.each_item , notesArray){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.each_item, parent, false)
        }

        val subject = view!!.findViewById<TextView>(R.id.subject)
        val notes = view.findViewById<Button>(R.id.notes)

        subject.text = notesArray[position].name
        notes.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(notesArray[position].pdfUrl);
            context.startActivity(intent)
        }

        return view
    }

}
