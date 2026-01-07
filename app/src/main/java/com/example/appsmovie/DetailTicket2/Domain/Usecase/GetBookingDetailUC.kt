package com.example.appsmovie.DetailTicket2.Domain.Usecase

import com.example.appsmovie.DetailTicket2.Domain.Repository.DetailTicketRepository
import com.example.appsmovie.RoomDatabase.BookingHistory
import javax.inject.Inject

class GetBookingDetailUC @Inject constructor(
    private val repository: DetailTicketRepository
) {
    suspend operator fun invoke(id: Int): BookingHistory? {
        return repository.getBookingById(id)
    }
}