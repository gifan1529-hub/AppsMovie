package com.example.core.UseCase_Repository.DetailTicket2.Data

import com.example.core.Database.BookingDatabase.BookingHistory
import com.example.core.Database.BookingDatabase.BookingHistoryDao
import com.example.core.UseCase_Repository.DetailTicket2.Domain.Repository.DetailTicketRepository
import javax.inject.Inject

class DetailTicketIMPL @Inject constructor(
    private val bookingHistoryDao: BookingHistoryDao
) : DetailTicketRepository {

    override suspend fun getBookingById(id: Int): BookingHistory? {
        return bookingHistoryDao.getBookingById(id)
    }
}