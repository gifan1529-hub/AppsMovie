package com.example.core.DetailTicket.Data

import com.example.core.Database.BookingDatabase.BookingHistoryDao
import com.example.core.DetailTicket.Domain.Repository.BookingHistoryRepository
import kotlinx.coroutines.flow.Flow
import com.example.core.Database.BookingDatabase.BookingHistory
import javax.inject.Inject

class BookingHistoryRepositoryIMPL @Inject constructor(
    private val bookingHistoryDao: BookingHistoryDao
) : BookingHistoryRepository {
    override fun getBookingsByEmail(email: String): Flow<List<BookingHistory>> {
        return bookingHistoryDao.getBookingsByEmail(email)
    }

}