package com.example.core.UseCase_Repository.EditUser.Domain.Usecase

import com.example.core.Database.RoomDatabase.User
import com.example.core.UseCase_Repository.EditUser.Domain.Repository.EditUserRepository
import javax.inject.Inject

class GetUserUC @Inject constructor(
    private val userRepository: EditUserRepository
) {
    suspend operator fun invoke (email: String): User? {
        return userRepository.getUserByEmail(email)
    }
}