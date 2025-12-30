package com.example.appsmovie.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appsmovie.Api.MovieResult

@Dao
interface MovieDao {
    // untuk insert movie ke database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(movie: Movie)

    // get all movie untuk ditampilin
    @Query("SELECT * FROM favorite_movies_table")
    fun getAllFavoriteMovies(): LiveData<List<Movie>>

    // nge delete movie dari database
    @Query("DELETE FROM favorite_movies_table WHERE id = :movieId")
    suspend fun removeFromFavorite(movieId: String)

    // cek apakah movie ada di database
    @Query("SELECT EXISTS (SELECT 1 FROM favorite_movies_table WHERE id = :movieId)")
    suspend fun isFavorite(movieId: String): Boolean
}
