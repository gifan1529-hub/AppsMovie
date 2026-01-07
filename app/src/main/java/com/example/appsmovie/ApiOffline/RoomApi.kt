package com.example.appsmovie.ApiOffline // <-- TAMBAHKAN BARIS INI


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class RoomApi(
    @PrimaryKey
    val id: String,
    val title: String?,
    val posterUrl: String?,
    val type: String?,
    val genre: String?,
    val releaseYear: Int?,
    val rating: Double?,
    var isFavorite: Boolean = false,
    val plot: String?,
)
