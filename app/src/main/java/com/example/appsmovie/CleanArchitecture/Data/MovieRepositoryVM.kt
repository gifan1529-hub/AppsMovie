package com.example.appsmovie.CleanArchitecture.Data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsmovie.ApiOffline.RoomApi
import com.example.appsmovie.CleanArchitecture.Domain.Repository.MovieRepository
import com.example.appsmovie.CleanArchitecture.Domain.Usecase.GetMovieUC
import com.example.appsmovie.CleanArchitecture.Domain.Usecase.RefreshMovieUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieRepositoryVM @Inject constructor (
    private val getMovieUC : GetMovieUC,
    private val refreshMovieUC : RefreshMovieUC
): ViewModel() {

    val movies: Flow<List<RoomApi>> = getMovieUC()

    init {
        refreshData()
    }
    private fun refreshData(){
        viewModelScope.launch {
            refreshMovieUC()
        }
    }
}