package com.example.appsmovie.RoomDatabase

import androidx.room.Insert
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert
    suspend fun insertdata(user: User)

    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    suspend fun findByEmail(email: String): User?

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM user WHERE email = :userEmail LIMIT 1")
    suspend fun get(userEmail: String): User?



}