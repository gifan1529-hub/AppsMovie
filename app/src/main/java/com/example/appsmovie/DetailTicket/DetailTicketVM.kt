package com.example.appsmovie.DetailTicket

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.appsmovie.RoomDatabase.AppDatabase
import com.example.appsmovie.RoomDatabase.BookingHistory

class DetailTicketVM(application: Application) : AndroidViewModel(application) {

     private val bookingDao = AppDatabase.getInstance(application).bookingHistoryDao()

    fun getTicketsByUser(email: String): LiveData<List<BookingHistory>> {
        return bookingDao.getBookingsByEmail(email)
    }
}
