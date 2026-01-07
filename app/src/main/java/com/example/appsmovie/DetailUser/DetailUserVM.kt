package com.example.appsmovie.DetailUser

import android.app.Application
import androidx.activity.result.launch
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsmovie.DetailUser.Domain.Usecase.GetUserDetailsUC
import com.example.appsmovie.DetailUser.Domain.Usecase.LogoutUserUc
import com.example.appsmovie.DetailUser.Domain.Usecase.UserDetails
import com.example.appsmovie.SharedPreferences.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailUserVM @Inject constructor(
    private val getUser : GetUserDetailsUC,
    private val logoutUser: LogoutUserUc,
) : ViewModel() {
    private val _userDetails = MutableLiveData<UserDetails?>()
    val userDetails: LiveData<UserDetails?> = _userDetails

    private val _logoutEvent = MutableLiveData<Boolean>()
    val logoutEvent: LiveData<Boolean> = _logoutEvent

    fun loadUserDetail(){
        viewModelScope.launch {
            val details = getUser()
            _userDetails.postValue(details)
        }
    }
    fun logout() {
        viewModelScope.launch {
            logoutUser()
            _logoutEvent.postValue(true)
        }
    }
}