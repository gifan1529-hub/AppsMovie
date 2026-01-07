package com.example.core.UseCase_Repository.Favorite.Domain.Usecase


import com.example.core.Database.MovieDatabase.Movie
import com.example.core.UseCase_Repository.Favorite.Domain.Repository.FavoriteRepository
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