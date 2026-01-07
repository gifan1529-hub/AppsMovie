package com.example.core.UseCase_Repository.DetailTicket2.Domain.Repository

import com.example.core.Database.BookingDatabase.BookingHistory

interface DetailTicketRepository {
    suspend fun getBookingById(id: Int): BookingHistory?
}