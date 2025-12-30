package com.example.appsmovie.MovieOffline


import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.appsmovie.Api.ApiService
import com.example.appsmovie.Api.MovieResult
import com.example.appsmovie.ApiOffline.RoomApi
import com.example.appsmovie.ApiOffline.RoomDao
import kotlinx.coroutines.flow.Flow

class MovieRepository (
    private val apiService: ApiService,
    private val movieDao: RoomDao,
    private val context: Context
)  {

    val allMovies: Flow<List<RoomApi>> = movieDao.getAllMovies()

    suspend fun refreshMovies() {
        if (isOnline()) {
            try {
                val response = apiService.getTitles()
                val moviesToInsert = response.results.map { movie ->
                    RoomApi(
                        id = movie.id,
                        title = movie.titleText,
                        posterUrl = movie.primaryImage?.url,
                        type = movie.titleType,
                        genre = movie.genres?.joinToString(", "),
                        releaseYear = movie.releaseYear,
                        rating = movie.rating?.aggregateRating
                    )
                }
                movieDao.insertAll(moviesToInsert)
            } catch (e: Exception) {
                Log.e("MovieRepository", "Error refreshing movies: ${e.message}")
            }
        } else {
            Log.d("MovieRepository", "Not connected to the internet. Skipping refresh.")
        }
    }
    @SuppressLint("MissingPermission")
    private fun isOnline(): Boolean {
        // ngasih informasi tentang status jaringan hp
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // cek apakah ada koneksi yang aktif
        val network = connectivityManager.activeNetwork ?: return false
        // cek kemampuan koneksi yang aktif
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}