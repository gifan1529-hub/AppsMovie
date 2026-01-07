package com.example.core.UseCase_Repository.SignUp.Domain.Repository

import com.example.core.Database.RoomDatabase.User

interface UserRepository {

    suspend fun getUserByEmail(email: String): User?
    suspend fun updateUser(user: User)
    suspend fun findUserByEmail(email: String): User?
    suspend fun insertUser(user: User)

}