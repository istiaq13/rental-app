package com.example.rentalapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class login_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //login button on click event
        val loginButton: ImageView = findViewById(R.id.login_btn)
        loginButton.setOnClickListener {
            val intent = Intent(this, dashboard::class.java)
            startActivity(intent)
        }


        //signup page opens through this click event
        val makeAccButton: TextView = findViewById(R.id.make_acc)
        makeAccButton.setOnClickListener {
            val intent = Intent(this, sign_up_page::class.java)
            startActivity(intent)
        }
    }
}