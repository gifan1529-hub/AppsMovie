package com.example.appsmovie.DetailUser.Domain.Usecase

import com.example.appsmovie.SharedPreferences.SharedPreferences
import javax.inject.Inject

data class UserDetails(
    val email : String
)

class GetUserDetailsUC @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(): UserDetails? {
        val userEmail = sharedPreferences.getUserEmail()

        return if (userEmail != null) {
            UserDetails(email = userEmail)
        } else {
            null
        }
    }
}