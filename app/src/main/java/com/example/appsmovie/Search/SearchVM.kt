package com.example.appsmovie.Search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsmovie.Api.ApiClient
import com.example.appsmovie.Api.MovieResult
import com.example.appsmovie.ApiOffline.RoomApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(
    private val searchMovie: SearchMovieUC
) : ViewModel() {
    private val _searchResults = MutableLiveData<List<RoomApi>>()
    val searchResults: LiveData<List<RoomApi>> = _searchResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private var searchJob: Job? = null
    fun searchMovies(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            searchMovie.execute(query).collect { result ->
                when (result) {
                    is SearchResult.Loading -> {
                        _isLoading.postValue(true)
                    }

                    is SearchResult.Success -> {
                        _isLoading.postValue(false)
                        _searchResults.postValue(result.movies)
                    }

                    is SearchResult.Error -> {
                        _isLoading.postValue(false)
                        _errorMessage.postValue(result.message)
                    }

                    is SearchResult.EmptyQuery -> {
                        _isLoading.postValue(false)
                        _searchResults.postValue(emptyList())
                    }
                }
            }
        }
    }
}