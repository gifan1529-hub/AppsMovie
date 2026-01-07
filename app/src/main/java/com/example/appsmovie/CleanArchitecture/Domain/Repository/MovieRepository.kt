package com.example.appsmovie.CleanArchitecture.Domain.Repository

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.room.Room
import com.example.appsmovie.Api.ApiService
import com.example.appsmovie.ApiOffline.RoomApi
import com.example.appsmovie.ApiOffline.RoomDao
import com.example.appsmovie.data.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository  {
    suspend fun searchMovies(query: String): List<RoomApi>

    fun getMoviesFromLocal(): Flow<List<RoomApi>>
    suspend fun refreshMovies()

    fun getFavoriteMoviesFromLocal(): Flow<List<RoomApi>>

    suspend fun getMovieByIdFromLocal(movieId: String): RoomApi?
    suspend fun updateFavoriteStatus(movieId: String, isFavorite: Boolean)
    suspend fun updateMovie (movie: RoomApi)
    suspend fun isMovieFavorite(movieId: String): Boolean
    suspend fun addFavorite(movie: RoomApi)
    suspend fun removeFavorite(movie: RoomApi)

    suspend fun addMovieToFavorites(movie: Movie)
    suspend fun removeMovieFromFavorites(movie: Movie)
    fun getFavoriteMovies(email: String): Flow<List<Movie>>
    suspend fun isMovieFavorites(movieId: String, email: String): Boolean

}