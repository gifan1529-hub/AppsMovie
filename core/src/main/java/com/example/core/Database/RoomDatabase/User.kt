package com.example.core.Database.RoomDatabase
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity
data class User (
    @PrimaryKey(autoGenerate = true) val uid : Int? = null,
    @ColumnInfo(name = "email") val email : String,
    @ColumnInfo(name = "password") val userPassword : String
)