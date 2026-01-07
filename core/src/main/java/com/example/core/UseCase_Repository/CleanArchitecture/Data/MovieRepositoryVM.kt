package com.example.core.CleanArchitecture.Data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.CleanArchitecture.Domain.Usecase.GetMovieUC
import com.example.core.CleanArchitecture.Domain.Usecase.RefreshMovieUC
import com.example.core.Database.ApiOffline.RoomApi
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