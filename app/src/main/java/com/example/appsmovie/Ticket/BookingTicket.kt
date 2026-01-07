package com.example.appsmovie.Ticket

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.appsmovie.BookingManager
import com.example.appsmovie.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingTicket : AppCompatActivity() {

    private lateinit var navController: NavController
    private val bookingViewModel: BookingTicketVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bookingtiket)
        BookingManager.viewModel = bookingViewModel
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.bookingnavhostfragment) as NavHostFragment
        navController = navHostFragment.navController

        val btnNext = findViewById<Button>(R.id.btnnextstep)
        val btnBack = findViewById<ImageView>(R.id.btnbackbooking)
        val headerLayout = findViewById<View>(R.id.header_layout)
        val footerLayout = findViewById<View>(R.id.footer_layout)
        val step1 = findViewById<TextView>(R.id.step1)
        val step2 = findViewById<TextView>(R.id.step2)
        val step3 = findViewById<TextView>(R.id.step3)
        val step4 = findViewById<TextView>(R.id.step4)

        btnNext.isEnabled = false

        bookingViewModel.isTheaterSelected.observe(this){ isSelected ->
            btnNext.isEnabled = isSelected && (bookingViewModel.isSessionSelected.value == true)
        }

        bookingViewModel.isSessionSelected.observe(this){ isSelected ->
            btnNext.isEnabled = isSelected && (bookingViewModel.isTheaterSelected.value == true)
        }

         btnNext.setOnClickListener {
             when (navController.currentDestination?.id) {
                 R.id.ticket_selection -> {
                     navController.navigate(R.id.action_ticketSelectionFragment_to_pilihSeatFragment)
                 }
                 R.id.pilih_seat-> {
                     val data = bookingViewModel.bookingData.value
                     val totalSeatsSelected = data?.selectedSeats?.size ?: 0
                     val totalTickets = (data?.adultTickets ?: 0) + (data?.childTickets ?: 0)
                     if (totalTickets > 0 && totalTickets == totalSeatsSelected) {
                         navController.navigate(R.id.action_pilihSeatFragment_to_paymentFragment)
                      } else {
                        if (totalTickets == 0) {
                             Toast.makeText(this, "Silakan pilih jumlah tiket terlebih dahulu.", Toast.LENGTH_SHORT).show()
                         } else {
                             Toast.makeText(this, "Jumlah kursi harus sama dengan jumlah tiket.", Toast.LENGTH_SHORT).show()
                         }
                     }
                 }
                 R.id.payment -> {
                     bookingViewModel.onConfirmPaymentClicked() }
             }
         }

        btnBack.setOnClickListener {
             bookingViewModel.resetBookingData()
                finish()
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            //ngubah teks di button
            when (destination.id) {
                R.id.pilih_seat -> btnNext.text = "Payment Options"
                R.id.payment -> btnNext.text = "Confirm Payment"
                else -> btnNext.text = "Next"
            }
            //ngilangin header and footer di payment result
            if (destination.id == R.id.payement_result) {
                headerLayout.visibility = View.GONE
                footerLayout.visibility = View.GONE
            } else {
                headerLayout.visibility = View.VISIBLE
                footerLayout.visibility = View.VISIBLE
            }
            // ngubah warna stepper
            step1.backgroundTintList = getColorStateList(if (destination.id == R.id.ticket_selection) R.color.ungu else R.color.abumid)
            step2.backgroundTintList = getColorStateList(if (destination.id == R.id.pilih_seat) R.color.ungu else R.color.abumid)
            step3.backgroundTintList = getColorStateList(if (destination.id == R.id.payment) R.color.ungu else R.color.abumid)
            step4.backgroundTintList = getColorStateList(if (destination.id == R.id.payement_result) R.color.ungu else R.color.abumid)
        }
    }
}