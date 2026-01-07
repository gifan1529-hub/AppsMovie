package com.example.appsmovie.DetailTicket.Data

import com.example.appsmovie.DetailTicket.Domain.Repository.BookingHistoryRepository
import com.example.appsmovie.RoomDatabase.BookingHistory
import com.example.appsmovie.RoomDatabase.BookingHistoryDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookingHistoryRepositoryIMPL @Inject constructor(
    private val bookingHistoryDao: BookingHistoryDao
) : BookingHistoryRepository {
    override fun getBookingsByEmail(email: String): Flow<List<BookingHistory>> {
        return bookingHistoryDao.getBookingsByEmail(email)
    }

}