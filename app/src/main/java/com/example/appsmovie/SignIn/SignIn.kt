package com.example.appsmovie.SignIn

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.appsmovie.HomeMain
import com.example.appsmovie.R
import com.example.appsmovie.RoomDatabase.AppDatabase
import com.example.appsmovie.SignUp.SignUp
import com.example.appsmovie.ViewModelFactory
import com.example.appsmovie.SharedPreferences.SharedPreferences
import kotlin.getValue

class SignIn : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val SignInVM: SignInVM by viewModels {
        val database = AppDatabase.getInstance(applicationContext)
        ViewModelFactory(database, applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.signin)

        sharedPreferences = SharedPreferences(this)

        if (sharedPreferences.isLoggedIn()) {
            val intent = Intent(this, HomeMain::class.java)
            startActivity(intent)
            finish()
            return
        }

        val etemail = findViewById<EditText>(R.id.etemail)
        val etpass = findViewById<EditText>(R.id.etpass)
        val sigin = findViewById<Button>(R.id.btnoke)
        var email : String?
        var pass : String?

        observeViewModel()



        val signup = findViewById<TextView>(R.id.tvsignup)
        signup.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        sigin.setOnClickListener {
            email = etemail.text.toString().trim()
            pass = etpass.text.toString().trim()
            SignInVM.login(email, pass)
        }
    }

    private fun observeViewModel(){
        SignInVM.loginResult.observe(this){result ->
            when (result){
                is LoginResult.Success -> {
                    val user = result.user
                    Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()

                    sharedPreferences.createLoginSession(
                        user.uid ?: 0,
                        user.email!!,
                        user.userPassword!!
                    )

//                    val editor = sharedPreferences.getEditor()
//                    editor.putString("USER_EMAIL", user.email)
//                    editor.apply()

                    val intent = Intent(this, HomeMain::class.java)
                    startActivity(intent)
                    finish()
                }

                is LoginResult.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}