package com.example.appsmovie.DetailTicket2.Data

import com.example.appsmovie.DetailTicket2.Domain.Repository.DetailTicketRepository
import com.example.appsmovie.RoomDatabase.BookingHistory
import com.example.appsmovie.RoomDatabase.BookingHistoryDao
import javax.inject.Inject

class DetailTicketIMPL @Inject constructor(
    private val bookingHistoryDao: BookingHistoryDao
) : DetailTicketRepository {

    override suspend fun getBookingById(id: Int): BookingHistory? {
        return bookingHistoryDao.getBookingById(id)
    }
}