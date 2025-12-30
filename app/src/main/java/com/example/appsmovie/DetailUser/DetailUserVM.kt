package com.example.appsmovie.DetailUser

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appsmovie.SharedPreferences.SharedPreferences

class DetailUserVM(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences = SharedPreferences(application)
    private val _logoutEvent = MutableLiveData<Boolean>()
    val logoutEvent: LiveData<Boolean> =_logoutEvent

    fun logout() {
        sharedPreferences.logoutUser()
        _logoutEvent.value = true
    }
}