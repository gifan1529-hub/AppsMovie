package com.example.appsmovie.Favorite.Domain.Usecase

import com.example.appsmovie.Favorite.Domain.Repository.FavoriteRepository
import com.example.appsmovie.data.Movie
import javax.inject.Inject

class ToggleFavoriteUC @Inject constructor(
    private val repository: FavoriteRepository
)  {
    suspend operator fun invoke(movie: Movie): Boolean {
        return if (repository.isFavorite(movie.id)) {
            repository.removeMovieFromFavorites(movie.id)
            false
        } else {
            repository.addMovieToFavorites(movie)
            true
        }
    }
}