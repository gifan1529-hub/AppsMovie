package com.example.appsmovie.Payment

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appsmovie.RoomDatabase.AppDatabase
import com.example.appsmovie.RoomDatabase.BookingHistory
import com.example.appsmovie.SharedPreferences.SharedPreferences
import com.example.appsmovie.Ticket.BookingData
import kotlinx.coroutines.launch

class PaymentFragmentVM(application: Application) : AndroidViewModel(application) {

    private val bookingDao = AppDatabase.getInstance(application).bookingHistoryDao()
    private val sharedPreferences = SharedPreferences(application)

   private val _navigateToResult = MutableLiveData<Boolean>()
    val navigateToResult: LiveData<Boolean> = _navigateToResult
    private val _showNotificationEvent = MutableLiveData<BookingHistory?>()
    val showNotificationEvent: LiveData<BookingHistory?> = _showNotificationEvent
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    fun onPaymentConfirmed(bookingData: BookingData?, selectedPaymentMethodId: Int) {
        if (bookingData == null) {
            _toastMessage.value = "Data pemesanan tidak ditemukan"
            return
        }

        val loggedInUserEmail = sharedPreferences.getUserEmail()
        if (loggedInUserEmail == null) {
            _toastMessage.value = "Error: Pengguna tidak ditemukan!"
            return
        }

       val paymentMethod = when (selectedPaymentMethodId) {
            com.example.appsmovie.R.id.rb_bank_transfer -> "Bank Transfer"
            com.example.appsmovie.R.id.rb_cash -> "Cash"
            else -> "Belum Dipilih"
        }

        if (paymentMethod == "Belum Dipilih") {
            _toastMessage.value = "Silakan pilih metode pembayaran"
            return
        }

        val newBooking = BookingHistory(
            email = loggedInUserEmail,
            movieTitle = bookingData.movieTitle ?: "Tidak ada judul",
            theater = bookingData.theater ?: "Tidak ada teater",
            session = bookingData.session ?: "Tidak ada sesi",
            moviePosterUrl = bookingData.moviePosterUrl,
            buffetItems = if (bookingData.selectedBuffet.isNullOrEmpty()) "Tidak ada" else bookingData.selectedBuffet!!,
            adultTickets = bookingData.adultTickets,
            childTickets = bookingData.childTickets,
            seatIds = bookingData.selectedSeats.toList(),
            paymentMethod = paymentMethod,
            paymentStatus = "LUNAS",
            totalPrice = bookingData.totalPrice.toLong()
        )
        saveBookingToDatabase(newBooking)
    }

    private fun saveBookingToDatabase(booking: BookingHistory) {
        viewModelScope.launch {
            try {
                val newGeneratedId = bookingDao.insertBooking(booking)
                val bookingForNotif = booking.copy(id = newGeneratedId.toInt())
                _showNotificationEvent.value = bookingForNotif
                _navigateToResult.value = true
            } catch (e: Exception) {
                _toastMessage.value = "Gagal menyimpan riwayat: ${e.message}"
            }
        }
    }

    fun onNotificationShown() {
        _showNotificationEvent.value = null
    }
    fun onNavigationComplete() {
        _navigateToResult.value = false
    }
}
