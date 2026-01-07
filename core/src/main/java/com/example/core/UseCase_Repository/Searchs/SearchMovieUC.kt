package com.example.core.UseCase_Repository.Searchs

import com.example.core.CleanArchitecture.Domain.Repository.MovieRepository
import com.example.core.Database.ApiOffline.RoomApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

sealed class SearchResult {
    data class Success(val movies: List<RoomApi>) : SearchResult()
    data class Error(val message: String) : SearchResult()
    object Loading : SearchResult()
    object EmptyQuery : SearchResult()
}

@OptIn(FlowPreview::class)
class SearchMovieUC @Inject constructor(
    private val repository: MovieRepository
){
    fun execute(query: String): Flow<SearchResult> = flow {
        if (query.isBlank()) {
            emit(SearchResult.EmptyQuery)
            return@flow
        }
        emit(SearchResult.Loading)

        try {
            val results = repository.searchMovies(query)
            emit(SearchResult.Success(results))
        } catch (e: Exception) {
            emit(SearchResult.Error(e.message ?: "Unknown error"))
        }
    }

}