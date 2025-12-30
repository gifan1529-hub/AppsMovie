package com.example.appsmovie.DetailFilm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsmovie.Api.ApiClient
import com.example.appsmovie.Api.MovieResult
import com.example.appsmovie.RoomDatabase.AppDatabase
import com.example.appsmovie.data.Movie
import com.example.appsmovie.data.MovieDao
import kotlinx.coroutines.launch

class DetailFilmVM(application: Application) : AndroidViewModel(application){

    private val _movieDetails = MutableLiveData<MovieResult?>()
    val movieDetails: LiveData<MovieResult?> = _movieDetails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage
    private val movieDao: MovieDao
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    init {
        movieDao = AppDatabase.getInstance(application).movieDao()
    }
    fun fetchMovieDetails(movieId: String) {
        if (movieId.isEmpty()) {
            _errorMessage.value = "ID Film kosong"
            return
        }
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val details = ApiClient.create().getMovieDetails(movieId)
                _movieDetails.postValue(details)
                checkFavoriteStatus(movieId)
            } catch (e: Exception) {
                val errorMessage = "Terjadi kesalahan: ${e.message}"
                _errorMessage.postValue(errorMessage)
                Log.e("DetailFilmVM", errorMessage, e)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    private fun checkFavoriteStatus(movieId: String) {
        viewModelScope.launch {
            try {
                val isFav = movieDao.isFavorite(movieId)
                _isFavorite.postValue(isFav)
            } catch (e: Exception) {
                Log.e("DetailFilmVM", "Gagal mengecek status favorit: ${e.message}")
            }
        }
    }

    fun toggleFavorite(movie: MovieResult) {
        viewModelScope.launch {
            val movieIdString = movie.id
            if (movieIdString.isEmpty()) {
                Log.e("DetailFilmVM", "Gagal toggle favorit: ID film kosong.")
                return@launch
            }
            try {
                if (movieDao.isFavorite(movieIdString)) {
                    movieDao.removeFromFavorite(movieIdString)
                    _isFavorite.postValue(false)
                } else {
                    val movieEntity = Movie(
                        id = movieIdString,
                        title = movie.titleText,
                        poster_path = movie.primaryImage?.url,
                        rating = movie.rating?.aggregateRating
                        )
                    movieDao.addToFavorite(movieEntity)
                    _isFavorite.postValue(true)
                }
            } catch (e: Exception) {
                Log.e("DetailFilmVM", "Gagal toggle favorit: ${e.message}")
            }
        }
    }
}