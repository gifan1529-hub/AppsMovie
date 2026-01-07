package com.example.core.Database.RoomDatabase
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.Database.ApiOffline.RoomApi
import com.example.core.Database.ApiOffline.RoomDao
import com.example.core.Converter
import com.example.core.Database.BookingDatabase.BookingHistoryDao
import com.example.core.Database.BookingDatabase.BookingHistory
import com.example.core.Database.MovieDatabase.MovieDao
import com.example.core.Database.MovieDatabase.Movie


@TypeConverters(Converter::class)
@Database(entities = [
    User::class,
    BookingHistory::class,
    Movie::class,
    RoomApi::class],
    version = 14,
    exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun roomDao(): RoomDao
    abstract fun userDao(): UserDao
    abstract fun bookingHistoryDao(): BookingHistoryDao
    abstract fun movieDao(): MovieDao

    companion object{
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context : Context): AppDatabase {
             return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database"
                )
                    .fallbackToDestructiveMigration()
                    .build().also { instance = it }
            }
        }
    }
}