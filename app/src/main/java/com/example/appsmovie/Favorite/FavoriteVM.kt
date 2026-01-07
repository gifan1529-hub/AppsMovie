package com.example.appsmovie.Favorite

import androidx.activity.result.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.appsmovie.ApiOffline.RoomApi
import com.example.appsmovie.CleanArchitecture.Domain.Repository.MovieRepository
import com.example.appsmovie.Favorite.Domain.Usecase.GetFavoriteMovieUC
import com.example.appsmovie.SharedPreferences.SharedPreferences
import com.example.appsmovie.data.Movie
import com.example.appsmovie.data.MovieDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteVM @Inject constructor(
    private val movieRepository: MovieRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    val favoriteMovies: LiveData<List<Movie>> = liveData {
        val userEmail = sharedPreferences.getUserEmail()
        if (userEmail != null) {
            emitSource(movieRepository.getFavoriteMovies(userEmail).asLiveData())
            // emitSource = nyambungin favoriteMovies LiveData dengan movieReposiory LiveData
        } else {
            emit(emptyList())
        }
    }
}
