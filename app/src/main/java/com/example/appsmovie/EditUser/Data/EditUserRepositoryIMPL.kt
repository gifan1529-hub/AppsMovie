package com.example.appsmovie.EditUser.Data

import com.example.appsmovie.EditUser.Domain.Repository.EditUserRepository
import com.example.appsmovie.RoomDatabase.User
import com.example.appsmovie.RoomDatabase.UserDao
import javax.inject.Inject

class EditUserRepositoryIMPL @Inject constructor(
    private val userDao: UserDao
) : EditUserRepository {
    override suspend fun getUserByEmail(email: String): User? {
        return userDao.get(email)
    }

    override suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }


}