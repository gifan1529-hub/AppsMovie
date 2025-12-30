package com.example.appsmovie.ApiOffline

import com.example.appsmovie.ApiOffline.RoomApi
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<RoomApi>)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<RoomApi>>

    @Query("DELETE FROM movies")
    suspend fun clearAllMovies()

}