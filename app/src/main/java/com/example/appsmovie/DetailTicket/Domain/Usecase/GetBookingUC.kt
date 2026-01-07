package com.example.appsmovie.DetailTicket.Domain.Usecase

import com.example.appsmovie.DetailTicket.Domain.Repository.BookingHistoryRepository
import com.example.appsmovie.RoomDatabase.BookingHistory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookingUC @Inject constructor(
    private val bookingHistoryRepository: BookingHistoryRepository
) {
    operator fun invoke(email: String): Flow<List<BookingHistory>> {
        return bookingHistoryRepository.getBookingsByEmail(email)
    }
}