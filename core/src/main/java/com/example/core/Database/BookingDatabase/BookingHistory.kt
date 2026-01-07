package com.example.core.Database.BookingDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "booking_history")
data class BookingHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val email: String,
    val movieTitle: String,
    val theater: String,
    val session: String,
    val buffetItems: String,
    val adultTickets: Int,
    val childTickets: Int,
    val seatIds: List<String>,
    val paymentMethod: String,
    val paymentStatus: String,
    val totalPrice: Long,
    val moviePosterUrl: String?,
    val bookingDate: Long = System.currentTimeMillis()
)
