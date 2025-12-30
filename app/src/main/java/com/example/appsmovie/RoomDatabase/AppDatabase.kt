package com.example.appsmovie.RoomDatabase
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.appsmovie.ApiOffline.RoomApi
import com.example.appsmovie.ApiOffline.RoomDao
import com.example.appsmovie.Converter
import com.example.appsmovie.data.MovieDao
import com.example.appsmovie.data.Movie

@TypeConverters(Converter::class)
@Database(entities = [
    User::class,
    BookingHistory::class,
    Movie::class,
    RoomApi::class],
    version = 11)
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