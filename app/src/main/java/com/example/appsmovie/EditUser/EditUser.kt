package com.example.appsmovie.EditUser

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.example.appsmovie.R
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.appsmovie.DetailUser.DetailUser
import com.example.appsmovie.RoomDatabase.AppDatabase
import com.example.appsmovie.SharedPreferences.SharedPreferences
import com.example.appsmovie.ViewModelFactory


class EditUser : AppCompatActivity() {
    private lateinit var viewModel: EditUserVM
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnUpdate: Button
    private lateinit var ivBack: ImageView
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edituser)

        etEmail = findViewById(R.id.etuseremail)
        etPassword = findViewById(R.id.etuserpass)
        btnUpdate = findViewById(R.id.save)
        ivBack = findViewById(R.id.iv_balik)
        progressBar = findViewById(R.id.loding)


        val database = AppDatabase.getInstance(applicationContext)
        val viewModelFactory = ViewModelFactory(database, applicationContext)
        viewModel = ViewModelProvider(this, viewModelFactory)[EditUserVM::class.java]

        intent.getStringExtra("userEmail")?.let { email ->
            viewModel.loadUser(email)
            setupListeners()
            observeViewModel()
        } ?: run {
            Toast.makeText(this, "Error: ID Userr tidak valid.", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupListeners(){
        etEmail.addTextChangedListener {
            viewModel.onEmailChanged(it.toString())
        }
        etPassword.addTextChangedListener {
            viewModel.onPasswordBaruChanged(it.toString())
        }
        btnUpdate.setOnClickListener {
            viewModel.onUpdateClicked()
        }
        ivBack.setOnClickListener {
            finish()
        }
    }

    private fun observeViewModel(){
        viewModel.uiState.observe(this) { state ->
            if (etEmail.text.toString() != state.email) {
                etEmail.setText(state.email)
            }
            if (etPassword.text.toString() != state.passwordBaru) {
                etPassword.setText(state.passwordBaru)
            }
            progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

            btnUpdate.isEnabled = !state.isLoading

            state.error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }

            if (state.updateSuccess) {
                val sharedPrefs = SharedPreferences(this)
                sharedPrefs.saveLoginDetails(
                    email = state.email,
                    password = state.passwordBaru
                )
                setResult(RESULT_OK)
                finish()
            }

        }
    }
}