package com.example.schooltasklist2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.database.*

class Show : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<Tasks>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        ref = FirebaseDatabase.getInstance().getReference("TASKS")
        list = mutableListOf()
        listView = findViewById(R.id.listView)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){

                    for (h in p0.children){
                        val task = h.getValue(Tasks::class.java)
                        list.add(task!!)
                    }
                    val adapter = Adapter(this@Show,R.layout.tasks,list)
                    listView.adapter = adapter
                }
            }
        })
    }
}
