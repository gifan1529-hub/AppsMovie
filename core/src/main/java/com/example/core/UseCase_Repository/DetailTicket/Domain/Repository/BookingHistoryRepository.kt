package com.example.core.DetailTicket.Domain.Repository

import com.example.core.Database.BookingDatabase.BookingHistory
import kotlinx.coroutines.flow.Flow

interface BookingHistoryRepository {

    fun getBookingsByEmail(email: String): Flow<List<BookingHistory>>
}