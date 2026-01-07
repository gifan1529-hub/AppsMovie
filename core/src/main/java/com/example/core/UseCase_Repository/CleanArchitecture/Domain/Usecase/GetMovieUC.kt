package com.example.core.CleanArchitecture.Domain.Usecase

import com.example.core.CleanArchitecture.Domain.Repository.MovieRepository
import com.example.core.Database.ApiOffline.RoomApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieUC @Inject constructor (
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<List<RoomApi>> {
        return repository.getMoviesFromLocal()
    }
}