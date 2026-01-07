package com.example.appsmovie.DetailTicket2

import android.app.Application
import androidx.activity.result.launch
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsmovie.DetailTicket2.Domain.Usecase.GetBookingDetailUC
import com.example.appsmovie.RoomDatabase.AppDatabase
import com.example.appsmovie.RoomDatabase.BookingHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailTicket2VM @Inject constructor (
    private val getBookingDetailUC: GetBookingDetailUC
) : ViewModel() {
    private val _ticketDetails = MutableLiveData<BookingHistory?>()
    val ticketDetails: LiveData<BookingHistory?> = _ticketDetails

    fun loadBookingDetails(id: Int) {
        viewModelScope.launch {
            val details = getBookingDetailUC(id)
            _ticketDetails.postValue(details)
        }
    }
}
