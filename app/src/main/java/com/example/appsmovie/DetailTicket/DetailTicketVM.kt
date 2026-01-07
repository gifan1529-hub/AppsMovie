package com.example.appsmovie.DetailTicket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.DetailTicket.Domain.Usecase.GetBookingUC
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailTicketVM @Inject constructor(
    private val getBookingsByEmail : GetBookingUC,
) : ViewModel() {

     fun getTicketsByUser(email: String) = getBookingsByEmail(email).asLiveData()
}
