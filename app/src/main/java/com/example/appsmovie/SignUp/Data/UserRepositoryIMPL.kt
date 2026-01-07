package com.example.appsmovie.SignUp.Data

import com.example.appsmovie.RoomDatabase.User
import com.example.appsmovie.RoomDatabase.UserDao
import com.example.appsmovie.SignUp.Domain.Repository.UserRepository
import javax.inject.Inject

class UserRepositoryIMPL @Inject constructor(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun getUserByEmail(email: String): User? {
        return userDao.get(email)
    }

    override suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    override suspend fun findUserByEmail(email: String): User? {
        return userDao.findByEmail(email)
    }

    override suspend fun insertUser(user: User) {
        userDao.insertdata(user)
    }
}