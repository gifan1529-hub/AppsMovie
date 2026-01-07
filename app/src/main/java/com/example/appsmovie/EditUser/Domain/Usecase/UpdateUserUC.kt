package com.example.appsmovie.EditUser.Domain.Usecase

import com.example.appsmovie.EditUser.Domain.Repository.EditUserRepository
import com.example.appsmovie.RoomDatabase.User
import javax.inject.Inject

class UpdateUserUC @Inject constructor(
    private val userRepository: EditUserRepository
) {
    suspend operator fun invoke(user: User) {
        userRepository.updateUser(user)
    }
}