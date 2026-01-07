package com.example.appsmovie.SignUp.Domain.Usecase

import com.example.appsmovie.RoomDatabase.User
import com.example.appsmovie.SignUp.Domain.Repository.UserRepository
import javax.inject.Inject

sealed class RegistrationStatus {
    object Success : RegistrationStatus()
    object EmailExists : RegistrationStatus()
    data class Failure(val error: Exception) : RegistrationStatus()
}

class RegisterUserUC @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke(user: User): RegistrationStatus {
        return try {
            val existingUser = repository.findUserByEmail(user.email)
            if (existingUser == null) {
                repository.insertUser(user)
                RegistrationStatus.Success
            } else {
                RegistrationStatus.EmailExists
            }
        } catch (e: Exception) {
            RegistrationStatus.Failure(e)
        }
    }
}