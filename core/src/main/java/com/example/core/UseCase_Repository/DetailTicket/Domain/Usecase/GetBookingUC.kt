package com.example.core.DetailTicket.Domain.Usecase

import com.example.core.Database.BookingDatabase.BookingHistory
import com.example.core.DetailTicket.Domain.Repository.BookingHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookingUC @Inject constructor(
    private val bookingHistoryRepository: BookingHistoryRepository
) {
    operator fun invoke(email: String): Flow<List<BookingHistory>> {
        return bookingHistoryRepository.getBookingsByEmail(email)
    }
}