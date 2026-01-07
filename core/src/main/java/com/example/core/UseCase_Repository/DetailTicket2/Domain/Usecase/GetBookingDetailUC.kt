package com.example.core.UseCase_Repository.DetailTicket2.Domain.Usecase

import com.example.core.Database.BookingDatabase.BookingHistory
import com.example.core.UseCase_Repository.DetailTicket2.Domain.Repository.DetailTicketRepository
import javax.inject.Inject

class GetBookingDetailUC @Inject constructor(
    private val repository: DetailTicketRepository
) {
    suspend operator fun invoke(id: Int): BookingHistory? {
        return repository.getBookingById(id)
    }
}