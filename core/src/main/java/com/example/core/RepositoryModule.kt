package com.example.core

import com.example.core.CleanArchitecture.Data.MovieRepositoryIMPL
import com.example.core.CleanArchitecture.Domain.Repository.MovieRepository
import com.example.core.DetailTicket.Data.BookingHistoryRepositoryIMPL
import com.example.core.DetailTicket.Domain.Repository.BookingHistoryRepository
import com.example.core.UseCase_Repository.DetailTicket2.Data.DetailTicketIMPL
import com.example.core.UseCase_Repository.DetailTicket2.Domain.Repository.DetailTicketRepository
import com.example.core.UseCase_Repository.EditUser.Data.EditUserRepositoryIMPL
import com.example.core.UseCase_Repository.EditUser.Domain.Repository.EditUserRepository
import com.example.core.UseCase_Repository.Favorite.Data.FavoriteRepositoryIMPL
import com.example.core.UseCase_Repository.Favorite.Domain.Repository.FavoriteRepository
import com.example.core.UseCase_Repository.SignUp.Data.UserRepositoryIMPL
import com.example.core.UseCase_Repository.SignUp.Domain.Repository.UserRepository
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