package com.example.schooltasklist2

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class Adapter(val mCtx: Context, val layoutResId: Int, val list: List<Tasks> )
    : ArrayAdapter<Tasks>(mCtx,layoutResId,list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textMapel = view.findViewById<TextView>(R.id.textMapel)
        val textDeadline = view.findViewById<TextView>(R.id.textDeadline)
        val textTugas = view.findViewById<TextView>(R.id.textTugas)

        val textUpdate = view.findViewById<TextView>(R.id.textUpdate)
        val textDelete = view.findViewById<TextView>(R.id.textDelete)

        val task = list[position]

        textMapel.text = task.mapel
        textDeadline.text = task.deadline
        textTugas.text = task.tugas

        textUpdate.setOnClickListener {
            showUpdateDialog(task)
        }
        textDelete.setOnClickListener {
            Deleteinfo(task)
        }
        return view

    }

    private fun showUpdateDialog(task: Tasks) {
        val builder = AlertDialog.Builder(mCtx)

        builder.setTitle("Update")

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.update, null)

        val textMapel = view.findViewById<EditText>(R.id.etMapel)
        val textDeadline = view.findViewById<EditText>(R.id.etDeadline)
        val textTugas = view.findViewById<EditText>(R.id.etTugas)

        textMapel.setText(task.mapel)
        textDeadline.setText(task.deadline)
        textTugas.setText(task.tugas)

        builder.setView(view)

        builder.setPositiveButton("Update") { dialog, which ->

            val dbUsers = FirebaseDatabase.getInstance().getReference("TASKS")

            val mapel = textMapel.text.toString().trim()

            val deadline = textDeadline.text.toString().trim()

            val tugas = textTugas.text.toString().trim()

            if (mapel.isEmpty()){
                textMapel.error = "please enter mapel"
                textMapel.requestFocus()
                return@setPositiveButton
            }

            if (deadline.isEmpty()){
                textDeadline.error = "please enter deadline"
                textDeadline.requestFocus()
                return@setPositiveButton
            }

            if (tugas.isEmpty()){
                textTugas.error = "please enter tugas"
                textTugas.requestFocus()
                return@setPositiveButton
            }

            val task = Tasks(task.id, mapel, deadline, tugas)

            dbUsers.child(task.id).setValue(task).addOnCompleteListener {
                Toast.makeText(mCtx,"Updated",Toast.LENGTH_SHORT).show()
            }

        }

        builder.setNegativeButton("No") { dialog, which ->

        }

        val alert = builder.create()
        alert.show()

    }

    private fun Deleteinfo(task: Tasks) {
        val progressDialog = ProgressDialog(
            context,
            com.google.android.material.R.style.Theme_MaterialComponents_Light_Dialog
        )
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Deleting...")
        progressDialog.show()
        val mydatabase = FirebaseDatabase.getInstance().getReference("TASKS")
        mydatabase.child(task.id).removeValue()
        Toast.makeText(mCtx, "Deleted!!", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, Show::class.java)
        context.startActivity(intent)
    }
}