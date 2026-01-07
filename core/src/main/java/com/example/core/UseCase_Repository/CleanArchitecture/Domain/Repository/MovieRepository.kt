package com.example.core.CleanArchitecture.Domain.Repository


import com.example.core.Database.ApiOffline.RoomApi
import com.example.core.Database.MovieDatabase.Movie
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