package com.example.appsmovie.DetailUser.Domain.Usecase

import com.example.appsmovie.SharedPreferences.SharedPreferences
import javax.inject.Inject

class LogoutUserUc @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke() {
        sharedPreferences.logoutUser()
    }
}