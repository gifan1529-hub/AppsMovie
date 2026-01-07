package com.example.appsmovie.DetailFilm.Domain.Usecase

import com.example.appsmovie.ApiOffline.RoomApi
import com.example.appsmovie.CleanArchitecture.Domain.Repository.MovieRepository
import com.example.appsmovie.RoomDatabase.AppDatabase
import com.example.appsmovie.data.Movie
import com.example.appsmovie.data.MovieDao
import javax.inject.Inject

class ToggleFavoriteUC @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(movie: RoomApi, email: String) {
        val isCurrentlyFavorite = movieRepository.isMovieFavorites(movie.id, email)
        val movieToToggle = Movie(
            id = movie.id,
            title = movie.title,
            posterUrl = movie.posterUrl,
            plot = movie.plot,
            rating = movie.rating,
            email = email
        )
        if (isCurrentlyFavorite) {
            movieRepository.removeMovieFromFavorites(movieToToggle)
        } else {
            movieRepository.addMovieToFavorites(movieToToggle)
        }
        val updatedRoomApi = movie.copy(isFavorite = !isCurrentlyFavorite)
        movieRepository.updateMovie(updatedRoomApi)

    }
}