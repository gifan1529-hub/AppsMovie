package com.example.appsmovie.Favorite.Domain.Repository

import com.example.appsmovie.data.Movie
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun getFavoriteMovies(): Flow<List<Movie>>

    suspend fun addMovieToFavorites(movie: Movie)

    suspend fun removeMovieFromFavorites(movieId: String)

    suspend fun isFavorite(movieId: String): Boolean

}