package com.example.appsmovie.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.appsmovie.ApiOffline.RoomApi
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    // untuk insert movie ke database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(movie: Movie)

    // get all movie untuk ditampilin
    @Query("SELECT * FROM favorite_movies_table WHERE email = :email")
    fun getAllFavoriteMovies(email: String): Flow<List<Movie>>

    // nge delete movie dari database
    @Query("DELETE FROM favorite_movies_table WHERE id = :movieId")
    suspend fun removeFromFavorite(movieId: String)

    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    fun getFavoriteMovies(): LiveData<List<RoomApi>> // Gunakan LiveData agar otomatis update

    // cek apakah movie ada di database
    @Query("SELECT EXISTS (SELECT 1 FROM favorite_movies_table WHERE id = :movieId)")
    suspend fun isFavorite(movieId: String): Boolean

    @Update
    suspend fun updateMovie(movie: RoomApi)
}
