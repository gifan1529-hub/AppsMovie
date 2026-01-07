package com.example.appsmovie.Home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.Api.ApiClient
import com.example.core.Api.MovieResult
import com.example.core.Api.UserApiResponse
import kotlinx.coroutines.launch
import java.lang.Exception
// ini udah ga kepake
class HomeVM() : ViewModel() {

    private val _movies = MutableLiveData<List<MovieResult>>()
    val movies: LiveData<List<MovieResult>> = _movies
    private val _tvSeries = MutableLiveData<List<MovieResult>>()
    val tvSeries: LiveData<List<MovieResult>> = _tvSeries
    private val _tvMiniSeries = MutableLiveData<List<MovieResult>>()
    val tvMiniSeries: LiveData<List<MovieResult>> = _tvMiniSeries
    private val _all = MutableLiveData<List<MovieResult>>()
    val all: LiveData<List<MovieResult>> = _all
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        fetchMoviesFromApi()
    }

    fun fetchMoviesFromApi() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response: UserApiResponse = ApiClient.create().getTitles()
                val moviesList = response.results
                if (!moviesList.isNullOrEmpty()) {
                    _tvMiniSeries.value = moviesList.filter { it.titleType == "tvMiniSeries" }
                    _tvSeries.value = moviesList.filter { it.titleType == "tvSeries" }
                    _movies.value = moviesList.filter { it.titleType == "movie" }
                    _all.value = moviesList
                    Log.d("HomeVM", "Sukses! Jumlah data: ${moviesList.size}")
                } else {
                    _tvMiniSeries.value = emptyList()
                    _tvSeries.value = emptyList()
                    _all.value = emptyList()
                    _movies.value = emptyList()
                    Log.w("HomeVM", "Sukses tapi data dari API kosong.")
                }
            }   catch (e: Exception) {
                val errorMsg = "Gagal memuat data : ${e.message}"
                _errorMessage.value = errorMsg
                Log.e("HomeVM", errorMsg, e)
                _movies.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
