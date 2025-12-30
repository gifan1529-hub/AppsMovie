package com.example.appsmovie.Search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsmovie.Api.ApiClient
import com.example.appsmovie.Api.MovieResult
import kotlinx.coroutines.launch

class SearchVM() : ViewModel() {
    private val _searchResults = MutableLiveData<List<MovieResult>>()
    val searchResults: LiveData<List<MovieResult>> = _searchResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun searchMovies(query: String) {
        if(query.isBlank()){
            _searchResults.value = emptyList()
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiClient.create().searchTitles(query)
                val filteredResults = response.results.filter { movie ->
                    movie.titleText?.contains(query, ignoreCase = true) == true
                }
                _searchResults.value = filteredResults
                Log.d("SearchVM", "Pencarian untuk '$query' berhasil, ditemukan: ${response.results.size} hasil.")
            } catch (e: Exception) {
                val errorMsg = "Error: ${e.message}"
                _errorMessage.value = errorMsg
                Log.e("SearchVM", errorMsg, e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}