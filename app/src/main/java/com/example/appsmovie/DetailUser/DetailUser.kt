package com.example.appsmovie.DetailUser

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.navigation
import com.example.appsmovie.EditUser.EditUser
import com.example.appsmovie.HomeMain
import com.example.appsmovie.R
import com.example.appsmovie.SharedPreferences.SharedPreferences
import com.example.appsmovie.SignIn.SignIn
import com.example.appsmovie.databinding.DetailuserBinding

class DetailUser : AppCompatActivity() {

    private lateinit var binding: DetailuserBinding
    private val viewModel: DetailUserVM by viewModels()

    private val editProfileLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            refreshUserData()
            Toast.makeText(this, "Profil berhasil diperbarui.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = DetailuserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = SharedPreferences(this)
        val userEmail = sharedPreferences.getUserEmail()
        binding.email.text = userEmail ?: "Email tidak ditemukan"

        binding.linearLayout.setOnClickListener {
            val intent = Intent(this, HomeMain::class.java).apply {
                putExtra("navigateTo", 2)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }

        binding.linearLayout2.setOnClickListener {
            val intent = Intent(this, HomeMain::class.java).apply {
                putExtra("navigateTo", 1)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }

        binding.logout.setOnClickListener {
            viewModel.logout()
        }

       binding.ivBak.setOnClickListener {
            finish()
       }

       binding.edit.setOnClickListener {
           val userEmail = sharedPreferences.getUserEmail()
           if (userEmail != null) {
               val intent = Intent(this, EditUser::class.java)
               intent.putExtra("userEmail", userEmail)
               editProfileLauncher.launch(intent)
           } else {
               Toast.makeText(this, "Error: ID User tidak valid.", Toast.LENGTH_LONG).show()
           }
       }

       observeLogoutEvent()
    }



    private fun refreshUserData() {
        val sharedPreferences = SharedPreferences(this)
        val updatedUserEmail = sharedPreferences.getUserEmail()
        binding.email.text = updatedUserEmail ?: "Email tidak ditemukan"
    }

    private fun observeLogoutEvent() {
        viewModel.logoutEvent.observe(this) { hasLoggedOut ->
            if (hasLoggedOut) {
                 val intent = Intent(this, SignIn::class.java).apply {
                   flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
                finish()
            }
        }
    }
}
