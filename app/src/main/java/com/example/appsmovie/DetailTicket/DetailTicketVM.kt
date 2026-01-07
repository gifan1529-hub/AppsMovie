package com.example.appsmovie.DetailTicket

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.appsmovie.DetailTicket.Domain.Usecase.GetBookingUC
import com.example.appsmovie.RoomDatabase.AppDatabase
import com.example.appsmovie.RoomDatabase.BookingHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailTicketVM @Inject constructor(
    private val getBookingsByEmail : GetBookingUC,
) : ViewModel() {

     fun getTicketsByUser(email: String) = getBookingsByEmail(email).asLiveData()
}
