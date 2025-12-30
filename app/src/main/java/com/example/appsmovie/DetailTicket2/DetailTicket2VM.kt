package com.example.appsmovie.DetailTicket2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.appsmovie.RoomDatabase.AppDatabase
import com.example.appsmovie.RoomDatabase.BookingHistory

class DetailTicket2VM(application: Application) : AndroidViewModel(application) {

    private val bookingDao = AppDatabase.getInstance(application).bookingHistoryDao()

   fun getBookingById(id: Int): LiveData<BookingHistory?> {
        return bookingDao.getBookingById(id)
    }
}
