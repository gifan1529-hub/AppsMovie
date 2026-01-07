package com.example.appsmovie

import android.content.Context
import androidx.room.Room
import com.example.appsmovie.Api.ApiClient
import com.example.appsmovie.Api.ApiService
import com.example.appsmovie.ApiOffline.RoomDao
import com.example.appsmovie.CleanArchitecture.Data.MovieRepositoryIMPL
import com.example.appsmovie.CleanArchitecture.Domain.Repository.MovieRepository
import com.example.appsmovie.DetailTicket.Domain.Repository.BookingHistoryRepository
import com.example.appsmovie.DetailTicket.Data.BookingHistoryRepositoryIMPL
import com.example.appsmovie.DetailTicket2.Data.DetailTicketIMPL
import com.example.appsmovie.DetailTicket2.Domain.Repository.DetailTicketRepository
import com.example.appsmovie.EditUser.Domain.Repository.EditUserRepository
import com.example.appsmovie.EditUser.Data.EditUserRepositoryIMPL
import com.example.appsmovie.Favorite.Domain.Repository.FavoriteRepository
import com.example.appsmovie.Favorite.Data.FavoriteRepositoryIMPL
import com.example.appsmovie.RoomDatabase.AppDatabase
import com.example.appsmovie.RoomDatabase.BookingHistoryDao
import com.example.appsmovie.RoomDatabase.UserDao
import com.example.appsmovie.SharedPreferences.SharedPreferences
import com.example.appsmovie.SignIn.Domain.Usecase.LoginUserUC
import com.example.appsmovie.SignUp.Data.UserRepositoryIMPL
import com.example.appsmovie.SignUp.Domain.Repository.UserRepository
import com.example.appsmovie.data.MovieDao
import dagger.Binds
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