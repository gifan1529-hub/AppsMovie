package com.example.appsmovie.EditUser.Domain.Usecase

import com.example.appsmovie.EditUser.Domain.Repository.EditUserRepository
import com.example.appsmovie.RoomDatabase.User
import javax.inject.Inject

class GetUserUC @Inject constructor(
    private val userRepository: EditUserRepository
) {
    suspend operator fun invoke (email: String): User? {
        return userRepository.getUserByEmail(email)
    }
}