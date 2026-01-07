package com.example.core.UseCase_Repository.EditUser.Domain.Repository

import com.example.core.Database.RoomDatabase.User

interface EditUserRepository {

    suspend fun getUserByEmail(email: String): User?
    suspend fun updateUser(user: User)

}