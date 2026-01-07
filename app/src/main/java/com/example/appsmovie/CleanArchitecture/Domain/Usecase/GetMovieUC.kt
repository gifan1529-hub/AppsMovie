package com.example.appsmovie.CleanArchitecture.Domain.Usecase

import com.example.appsmovie.ApiOffline.RoomApi
import com.example.appsmovie.CleanArchitecture.Data.MovieRepositoryIMPL
import com.example.appsmovie.CleanArchitecture.Domain.Repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieUC @Inject constructor (
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<List<RoomApi>> {
        return repository.getMoviesFromLocal()
    }
}