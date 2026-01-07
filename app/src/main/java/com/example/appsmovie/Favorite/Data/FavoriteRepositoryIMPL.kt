package com.example.appsmovie.Favorite.Data

import com.example.appsmovie.Favorite.Domain.Repository.FavoriteRepository
import com.example.appsmovie.SharedPreferences.SharedPreferences
import com.example.appsmovie.data.Movie
import com.example.appsmovie.data.MovieDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FavoriteRepositoryIMPL @Inject constructor(
    private val movieDao: MovieDao,
    private val sharedPreferences: SharedPreferences
) : FavoriteRepository {
    override fun getFavoriteMovies(): Flow<List<Movie>> {
        val userEmail = sharedPreferences.getUserEmail()
        return if (userEmail != null) {
            movieDao.getAllFavoriteMovies(userEmail)
        } else {
            flowOf(emptyList())
        }
    }

    override suspend fun addMovieToFavorites(movie: Movie) {
        movieDao.addToFavorite(movie)
    }

    override suspend fun removeMovieFromFavorites(movieId: String) {
        movieDao.removeFromFavorite(movieId)
    }

    override suspend fun isFavorite(movieId: String): Boolean {
        return movieDao.isFavorite(movieId)
    }
}