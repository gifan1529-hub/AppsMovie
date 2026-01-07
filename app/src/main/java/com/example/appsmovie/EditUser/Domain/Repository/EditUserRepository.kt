package com.example.appsmovie.EditUser.Domain.Repository

import com.example.appsmovie.RoomDatabase.User

interface EditUserRepository {

    suspend fun getUserByEmail(email: String): User?
    suspend fun updateUser(user: User)

}