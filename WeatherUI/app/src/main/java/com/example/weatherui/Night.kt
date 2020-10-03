package com.example.weatherui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_night.*

class Night : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_night)
        button2.setOnClickListener{
            val intent =  Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}