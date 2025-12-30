
package com.example.appsmovie.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies_table")
data class Movie (
    @PrimaryKey
    val id: String,
    val title: String?,
    val poster_path: String?,
    val overview: String? = null,
    val rating: Double? = null
)
