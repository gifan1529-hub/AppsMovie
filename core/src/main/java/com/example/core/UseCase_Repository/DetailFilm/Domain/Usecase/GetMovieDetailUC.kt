package com.example.core.DetailFilm.Domain.Usecase

import com.example.core.CleanArchitecture.Domain.Repository.MovieRepository
import com.example.core.Database.ApiOffline.RoomApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

sealed class DetailResult {
    data class Success(val movie: RoomApi) : DetailResult()
    data class Error(val message: String) : DetailResult()
    object Loading : DetailResult()
}

class GetMovieDetailUC @Inject constructor(
    private val repository: MovieRepository
) {
    fun execute(movieId: String): Flow<DetailResult> = flow {
        emit(DetailResult.Loading)

        try {
            val movieDetail: RoomApi? = repository.getMovieByIdFromLocal(movieId)
            if (movieDetail != null) {
                emit(DetailResult.Success(movieDetail))
            } else {
                emit(DetailResult.Error("Movie not found"))
            }
        } catch (e: Exception) {
            emit(DetailResult.Error(e.message ?: "Unknown error"))
        }
    }
}