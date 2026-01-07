package com.example.appsmovie.SignUp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.appsmovie.R
import com.example.appsmovie.SignIn.SignIn
import com.example.core.Database.RoomDatabase.User
import com.example.core.UseCase_Repository.SignUp.Domain.Usecase.RegistrationStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUp : AppCompatActivity() {
    private lateinit var etemail : EditText
    private lateinit var etpass : EditText
    private lateinit var btnSignUp : Button
    private lateinit var btnSignIn : TextView

    private val viewModel: SignUpVM by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.signup)
        setupViews()
        setupClickListener()
        observeViewModel()
    }

    private fun setupViews(){
        etemail = findViewById(R.id.email)
        etpass = findViewById(R.id.pass)
        btnSignUp = findViewById(R.id.signup)
        btnSignIn = findViewById(R.id.sigin)
    }

    private fun setupClickListener(){
        btnSignIn.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
        }
        btnSignUp.setOnClickListener {
            Register()
            Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, SignIn::class.java))
            finish()
        }
    }

    private fun observeViewModel(){
        viewModel.registerStatus.observe(this) { status ->
            when (status) {
                RegistrationStatus.Success-> {
                    Toast.makeText(this, "SignUp berhasil", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, SignIn::class.java))
                    finish()
                }
                RegistrationStatus.EmailExists -> {
                    Toast.makeText(this, "Email sudah terdaftar", Toast.LENGTH_SHORT).show()
                }
                is RegistrationStatus.Failure -> {
                    Toast.makeText(this, "Registrasi gagal", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    private fun Register() {
        val email = etemail.text.toString().trim()
        val password = etpass.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email dan password harus diisi", Toast.LENGTH_SHORT).show()
            return
        }
        if (password.length < 8) {
            Toast.makeText(this, "Password minimal 8 karakter", Toast.LENGTH_SHORT).show()
            return
        }
        val newUser = User(
            email = email,
            userPassword = password
        )
        viewModel.registerUser(newUser)
    }
}