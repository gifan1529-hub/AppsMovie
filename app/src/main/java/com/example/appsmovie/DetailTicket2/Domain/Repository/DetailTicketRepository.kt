package com.example.appsmovie.DetailTicket2.Domain.Repository

import com.example.appsmovie.RoomDatabase.BookingHistory

interface DetailTicketRepository {
    suspend fun getBookingById(id: Int): BookingHistory?
}