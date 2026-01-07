package com.example.appsmovie

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.appsmovie.databinding.NavbuttonBinding
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeMain : AppCompatActivity() {
    private lateinit var binding: NavbuttonBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()

        binding = NavbuttonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navfragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.navbutton.menu.findItem(R.id.navigation_home).isEnabled = false

        binding.navbutton.setupWithNavController(navController)

        binding.fab.setOnClickListener {
            navController.navigate(R.id.action_global_to_homeFragment)
        }

            handleIntentNavigation(intent)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val newIntent = intent
            handleIntentNavigation(newIntent)
    }

    private fun handleIntentNavigation(intent: Intent?) {
        val destinationId = intent?.getIntExtra("navigateTo", -1) ?: -1
        if ( destinationId != -1) {
            intent?.removeExtra("navigateTo")
            when(destinationId) {
               2 -> navController.navigate(R.id.action_global_to_ticketFragment)
               1 -> navController.navigate(R.id.action_global_to_favoriteFragment)
           }
        }
    }
}


