package com.example.appsmovie.DetailFilm

import android.app.Application
import android.util.Log
import android.util.Log.e
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsmovie.Api.ApiClient
import com.example.appsmovie.Api.MovieResult
import com.example.appsmovie.ApiOffline.RoomApi
import com.example.appsmovie.CleanArchitecture.Domain.Repository.MovieRepository
import com.example.appsmovie.DetailFilm.Domain.Usecase.DetailResult
import com.example.appsmovie.DetailFilm.Domain.Usecase.GetMovieDetailUC
import com.example.appsmovie.DetailFilm.Domain.Usecase.ToggleFavoriteUC
import com.example.appsmovie.RoomDatabase.AppDatabase
import com.example.appsmovie.SharedPreferences.SharedPreferences
import com.example.appsmovie.data.Movie
import com.example.appsmovie.data.MovieDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailFilmVM @Inject constructor(
    private val getMovieDetail: GetMovieDetailUC,
    private val toggleFavorite: ToggleFavoriteUC,
    private val movieRepository: MovieRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _movieDetails = MutableLiveData<RoomApi?>()
    val movieDetails: LiveData<RoomApi?> = _movieDetails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun fetchMovieDetails(movieId: String) {
        if (movieId.isEmpty()) {
            _errorMessage.value = "ID Film kosong"
            return
        }
        viewModelScope.launch {
            getMovieDetail.execute(movieId).collect { result ->
                when (result) {
                    is DetailResult.Loading -> {
                        _isLoading.postValue(true)
                    }
                    is DetailResult.Success -> {
                        _isLoading.postValue(false)
                        _movieDetails.postValue(result.movie)
                        _isFavorite.postValue(result.movie.isFavorite)
                    }
                    is DetailResult.Error -> {
                        _isLoading.postValue(false)
                        _errorMessage.postValue(result.message)
                    }
                }
            }
        }
    }
    fun toggleFavorite() {
        viewModelScope.launch {
            val currentMovie = movieDetails.value ?: return@launch
            val userEmail = sharedPreferences.getUserEmail() ?: return@launch
            toggleFavorite(currentMovie, userEmail)
            val newFavoriteStatus = movieRepository.isMovieFavorite(currentMovie.id) // Anda perlu inject repository di sini
            _isFavorite.postValue(newFavoriteStatus)
            val updatedMovie = currentMovie.copy(isFavorite = newFavoriteStatus)
            _movieDetails.postValue(updatedMovie)

        }
    }
}

