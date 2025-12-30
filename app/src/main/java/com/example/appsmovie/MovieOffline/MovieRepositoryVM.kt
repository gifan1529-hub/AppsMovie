package com.example.appsmovie.MovieOffline


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsmovie.ApiOffline.RoomApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MovieRepositoryVM (private val repository: MovieRepository): ViewModel() {

    val movies: Flow<List<RoomApi>> = repository.allMovies

    init {
        refreshData()
    }
    private fun refreshData(){
        viewModelScope.launch {
            repository.refreshMovies()
        }
    }

}