package com.example.schooltasklist2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val imageView : ImageView = findViewById(R.id.imgcreate)
        imageView.setOnClickListener { viewcreate()}
        val imageView2 : ImageView = findViewById(R.id.imgtasklist)
        imageView2.setOnClickListener { viewtasklist()}
        val imageView3 : ImageView = findViewById(R.id.imgout)
        imageView3.setOnClickListener { viewout()}

    }

    private fun viewcreate() {
        val intent = Intent(this, Insert::class.java)
        startActivity(intent)
    }
    private fun viewtasklist() {
        val intent = Intent(this, Show::class.java)
        startActivity(intent)
    }
    private fun viewout() {
        val intent = Intent(this, Splashscreen::class.java)
        startActivity(intent)
    }
}