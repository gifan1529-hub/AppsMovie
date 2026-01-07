package com.example.core.Database.ApiOffline

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<RoomApi>)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<RoomApi>>

    @Query("DELETE FROM movies")
    suspend fun clearAllMovies()

    @Query("SELECT * FROM movies WHERE title LIKE :query")
    suspend fun searchMoviesByTitle(query: String): List<RoomApi>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: String): RoomApi?

    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    fun getFavoriteMovies(): Flow<List<RoomApi>>

    @Query("UPDATE movies SET isFavorite = :isFavorite WHERE id = :movieId")
    suspend fun updateFavoriteStatus(movieId: String, isFavorite: Boolean)

    @Query("SELECT isFavorite FROM movies WHERE id = :movieId")
    suspend fun isMovieFavorite(movieId: String): Boolean

}