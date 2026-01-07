package com.example.core.UseCase_Repository.DetailUser.Domain.Usecase

import com.example.core.SharedPreferences.SharedPreferences
import javax.inject.Inject

class LogoutUserUc @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke() {
        sharedPreferences.logoutUser()
    }
}