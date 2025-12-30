package com.example.appsmovie

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appsmovie.Api.ApiClient

import com.example.appsmovie.DetailFilm.DetailFilmVM
import com.example.appsmovie.EditUser.EditUserVM
import com.example.appsmovie.Home.HomeVM
import com.example.appsmovie.MovieOffline.MovieRepository
import com.example.appsmovie.MovieOffline.MovieRepositoryVM
import com.example.appsmovie.RoomDatabase.AppDatabase
import com.example.appsmovie.Search.SearchVM
import com.example.appsmovie.SignIn.SignInVM
import com.example.appsmovie.SignUp.SignUpVM
import com.example.appsmovie.Ticket.BookingTicketVM
import kotlin.jvm.java
class ViewModelFactory(private val database: AppDatabase,private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieRepositoryVM::class.java) -> {
                val apiService = ApiClient.create()
                val movieDao = database.roomDao()
                val repository = MovieRepository(apiService, movieDao, context)
                MovieRepositoryVM(repository) as T
            }
            modelClass.isAssignableFrom(EditUserVM::class.java) -> {
                EditUserVM(database) as T
            }
            modelClass.isAssignableFrom(BookingTicketVM::class.java) -> {
                BookingTicketVM(database.bookingHistoryDao()) as T
            }
            modelClass.isAssignableFrom(SearchVM::class.java) -> {
                SearchVM() as T
            }
            modelClass.isAssignableFrom(SignUpVM::class.java) -> {
                SignUpVM(database) as T
            }
            modelClass.isAssignableFrom(HomeVM::class.java) -> {
                HomeVM() as T
            }
//            modelClass.isAssignableFrom(DetailFilmVM::class.java) -> {
//                DetailFilmVM(application = Application) as T
//            }
            modelClass.isAssignableFrom(SignInVM::class.java) -> {
                SignInVM(database) as T
            }
                else ->
                throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
            }
        }
    }
