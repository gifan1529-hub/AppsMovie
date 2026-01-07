package com.example.appsmovie.SignIn

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.appsmovie.HomeMain
import com.example.appsmovie.SignUp.SignUp
import com.example.appsmovie.databinding.SigninBinding
import com.example.core.SharedPreferences.SharedPreferences
import com.example.core.UseCase_Repository.SignIn.Usecase.LoginResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue
@AndroidEntryPoint
class SignIn : AppCompatActivity() {
    private lateinit var binding: SigninBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val signInVM: SignInVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = SigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = SharedPreferences(this)

        if (sharedPreferences.isLoggedIn()) {
            navigateToHome()
            return
        }

        observeViewModel()
        setupClickListener()
    }


    private fun setupClickListener() {
        binding.tvsignup.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        binding.btnoke.setOnClickListener {
            val email = binding.etemail.text.toString().trim()
            val pass = binding.etpass.text.toString().trim()
            signInVM.login(email, pass)
        }
    }

    private fun observeViewModel(){
        signInVM.loginResult.observe(this) { result ->
            when (result) {
                is LoginResult.Success -> {
                    Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()

                    lifecycleScope.launch {
                        val user = signInVM.getUserByEmail(result.email)

                        if (user != null) {
                            sharedPreferences.createLoginSession(
                                user.uid ?: 0,
                                user.email!!,
                                user.userPassword!!
                            )
                            navigateToHome()
                        } else {
                            Toast.makeText(this@SignIn, "Gagal mengambil data user.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                is LoginResult.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }

                is LoginResult.Failure -> {
                    Toast.makeText(this, "Error: ${result.error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeMain::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
