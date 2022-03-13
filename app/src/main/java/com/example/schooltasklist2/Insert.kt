package com.example.schooltasklist2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_insert.*

class Insert : AppCompatActivity() {
    lateinit var ref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)


        ref = FirebaseDatabase.getInstance().getReference("TASKS")

        btnSave.setOnClickListener {
            savedata()

            val intent = Intent (this, Show::class.java)
            startActivity(intent)
        }
    }


    private fun savedata() {
        val mapel = etMapel.text.toString()
        val deadline = etDeadline.text.toString()
        val tugas = etTugas.text.toString()

        val taskId = ref.push().key.toString()
        val task = Tasks(taskId, mapel, deadline, tugas)

        ref.child(taskId).setValue(task).addOnCompleteListener {
            Toast.makeText(this, "Successs",Toast.LENGTH_SHORT).show()
            etMapel.setText("")
            etDeadline.setText("")
            etTugas.setText("")
        }
    }
}

