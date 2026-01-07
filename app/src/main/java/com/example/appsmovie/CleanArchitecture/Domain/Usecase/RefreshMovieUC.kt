package com.example.appsmovie.CleanArchitecture.Domain.Usecase

import com.example.appsmovie.CleanArchitecture.Data.MovieRepositoryIMPL
import com.example.appsmovie.CleanArchitecture.Domain.Repository.MovieRepository
import javax.inject.Inject

class RefreshMovieUC @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke() {
        repository.refreshMovies()
    }
}