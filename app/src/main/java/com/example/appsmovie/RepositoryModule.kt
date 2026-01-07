package com.example.appsmovie

import com.example.appsmovie.CleanArchitecture.Data.MovieRepositoryIMPL
import com.example.appsmovie.CleanArchitecture.Domain.Repository.MovieRepository
import com.example.appsmovie.DetailTicket.Data.BookingHistoryRepositoryIMPL
import com.example.appsmovie.DetailTicket.Domain.Repository.BookingHistoryRepository
import com.example.appsmovie.DetailTicket2.Data.DetailTicketIMPL
import com.example.appsmovie.DetailTicket2.Domain.Repository.DetailTicketRepository
import com.example.appsmovie.EditUser.Data.EditUserRepositoryIMPL
import com.example.appsmovie.EditUser.Domain.Repository.EditUserRepository
import com.example.appsmovie.Favorite.Data.FavoriteRepositoryIMPL
import com.example.appsmovie.Favorite.Domain.Repository.FavoriteRepository
import com.example.appsmovie.SignUp.Data.UserRepositoryIMPL
import com.example.appsmovie.SignUp.Domain.Repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryIMPL
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindEditUserRepository(
        editUserRepositoryIMPL: EditUserRepositoryIMPL
    ): EditUserRepository

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        movieRepositoryIMPL: MovieRepositoryIMPL
    ): MovieRepository

    @Binds
    @Singleton
    abstract fun bindBookingHistoryRepository(
        bookingHistoryRepositoryIMPL: BookingHistoryRepositoryIMPL
    ): BookingHistoryRepository

    @Binds
    @Singleton
    abstract fun bindDetailTicketRepository(
        detailTicketIMPL: DetailTicketIMPL
    ): DetailTicketRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(
        favoriteRepositoryIMPL: FavoriteRepositoryIMPL
    ): FavoriteRepository

}