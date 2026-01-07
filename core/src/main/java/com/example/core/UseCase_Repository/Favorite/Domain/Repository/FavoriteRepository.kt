package com.example.core.UseCase_Repository.Favorite.Domain.Repository

import com.example.core.Database.MovieDatabase.Movie
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun getFavoriteMovies(): Flow<List<Movie>>

    suspend fun addMovieToFavorites(movie: Movie)

    suspend fun removeMovieFromFavorites(movieId: String)

    suspend fun isFavorite(movieId: String): Boolean

}