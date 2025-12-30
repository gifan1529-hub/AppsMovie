package com.example.appsmovie

import androidx.room.ColumnInfo

data class SeatData(
    @ColumnInfo(name = "selected_seats")
    val selectedSeats: String
)