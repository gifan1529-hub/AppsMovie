package com.example.core.UseCase_Repository.EditUser.Data

import com.example.core.Database.RoomDatabase.User
import com.example.core.Database.RoomDatabase.UserDao
import com.example.core.UseCase_Repository.EditUser.Domain.Repository.EditUserRepository
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