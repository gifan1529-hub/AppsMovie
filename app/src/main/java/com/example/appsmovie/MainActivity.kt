package com.example.appsmovie

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.appsmovie.R
import com.example.appsmovie.SignIn.SignIn
import com.example.appsmovie.SignUp.SignUp
import com.example.appsmovie.SharedPreferences.SharedPreferences


class MainActivity : AppCompatActivity() {
    lateinit var prefs: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.signin_or_signup)
        prefs = SharedPreferences(this)

        val btnsignin = findViewById<Button>(R.id.btnsignin)
        btnsignin.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }

        val btnsignup = findViewById<Button>(R.id.btnsignup)
        btnsignup.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        if (prefs.isLoggedIn()) {
            startActivity(Intent(this, HomeMain::class.java))
            finish()
        }
    }
}