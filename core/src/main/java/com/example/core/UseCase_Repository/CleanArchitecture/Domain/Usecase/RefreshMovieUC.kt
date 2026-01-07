package com.example.core.CleanArchitecture.Domain.Usecase

import com.example.core.CleanArchitecture.Domain.Repository.MovieRepository
import javax.inject.Inject

class RefreshMovieUC @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke() {
        repository.refreshMovies()
    }
}