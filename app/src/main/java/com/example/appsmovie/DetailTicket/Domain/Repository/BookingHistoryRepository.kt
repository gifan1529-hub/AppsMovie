package com.example.appsmovie.DetailTicket.Domain.Repository

import com.example.appsmovie.RoomDatabase.BookingHistory
import kotlinx.coroutines.flow.Flow

interface BookingHistoryRepository {

    fun getBookingsByEmail(email: String): Flow<List<BookingHistory>>
}