package com.example.appsmovie.SignUp.Domain.Repository

import com.example.appsmovie.RoomDatabase.User

interface UserRepository {

    suspend fun getUserByEmail(email: String): User?
    suspend fun updateUser(user: User)
    suspend fun findUserByEmail(email: String): User?
    suspend fun insertUser(user: User)

}