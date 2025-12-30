package com.example.appsmovie.Favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.appsmovie.RoomDatabase.AppDatabase
import com.example.appsmovie.data.Movie
import com.example.appsmovie.data.MovieDao

class FavoriteVM(application: Application) : AndroidViewModel(application) {
    private val movieDao: MovieDao
    val favoriteMovies: LiveData<List<Movie>>

    init {
        val database = AppDatabase.getInstance(application)
        movieDao = database.movieDao()
        favoriteMovies = movieDao.getAllFavoriteMovies()
    }
}
