package com.example.appsmovie.Favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.core.CleanArchitecture.Domain.Repository.MovieRepository
import com.example.core.Database.MovieDatabase.Movie
import com.example.core.SharedPreferences.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
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
