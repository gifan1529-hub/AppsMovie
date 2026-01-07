package com.example.core.Database.BookingDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: BookingHistory): Long

    @Query("SELECT * FROM booking_history WHERE email = :email ORDER BY bookingDate DESC")
    fun getBookingsByEmail(email: String): Flow<List<BookingHistory>>

    @Query("SELECT * FROM booking_history WHERE id = :id LIMIT 1")
    suspend fun getBookingById(id: Int): BookingHistory?

    @Query("SELECT seatIds FROM booking_history WHERE movieTitle = :movieId AND theater = :theater AND session = :session")
    suspend fun getTakenSeatsForShow(movieId: String, theater: String, session: String): List<String>


}
