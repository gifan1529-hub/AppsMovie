package com.example.core

import android.content.Context
import androidx.room.Room
import com.example.core.Api.ApiClient
import com.example.core.Api.ApiService
import com.example.core.Database.ApiOffline.RoomDao
import com.example.core.Database.BookingDatabase.BookingHistoryDao
import com.example.core.Database.MovieDatabase.MovieDao
import com.example.core.Database.RoomDatabase.AppDatabase
import com.example.core.Database.RoomDatabase.UserDao
import com.example.core.SharedPreferences.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return ApiClient.create()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "user_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideRoomDao(appDatabase: AppDatabase): RoomDao {
        return appDatabase.roomDao()
    }

//    @Provides
//    @Singleton
//    fun provideMovieRepository(
//        apiService: ApiService,
//        roomDao: RoomDao,
//        @ApplicationContext context: Context
//    ): MovieRepository {
//        return MovieRepositoryIMPL(apiService, roomDao, context)
//    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

//    @Provides
//    @Singleton
//    fun provideUserRepository(db: AppDatabase): EditUserRepository {
//        return EditUserRepositoryIMPL(db as UserDao)
//    }

    @Provides
    @Singleton
    fun provideBookingHistoryDao(appDatabase: AppDatabase): BookingHistoryDao {
        return appDatabase.bookingHistoryDao()
    }

//    @Provides
//    @Singleton
//    fun provideBookingHistoryRepository(dao: BookingHistoryDao): BookingHistoryRepository {
//        return BookingHistoryRepositoryIMPL(dao)
//    }
//
//    @Provides
//    @Singleton
//    fun provideTicketDetailRepository(dao: BookingHistoryDao): DetailTicketRepository {
//        return DetailTicketIMPL(dao)
//    }

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }

//    @Provides
//    @Singleton
//    fun provideFavoriteRepository(movieDao: MovieDao): FavoriteRepository {
//        return FavoriteRepositoryIMPL(movieDao)
//    }


    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return SharedPreferences(context)
    }
}