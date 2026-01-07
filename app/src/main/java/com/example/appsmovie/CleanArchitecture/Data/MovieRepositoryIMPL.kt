package com.example.appsmovie.CleanArchitecture.Data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.appsmovie.Api.ApiService
import com.example.appsmovie.ApiOffline.RoomApi
import com.example.appsmovie.ApiOffline.RoomDao
import com.example.appsmovie.CleanArchitecture.Domain.Repository.MovieRepository
import com.example.appsmovie.data.Movie
import com.example.appsmovie.data.MovieDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryIMPL @Inject constructor(
    private val apiService: ApiService,
    private val movieDao: RoomDao,
    private val dao: MovieDao,
    @ApplicationContext private val context: Context
) : MovieRepository {

    override suspend fun searchMovies(query: String): List<RoomApi> {
        return movieDao.searchMoviesByTitle("%$query%")
    }

    override fun getMoviesFromLocal(): Flow<List<RoomApi>> {
        return movieDao.getAllMovies()
    }

    override suspend fun refreshMovies() {
        if (isOnline()) {
            try {
                val response = apiService.getTitles()
                val  moviesToInsert = response.results.map { movie ->
                    RoomApi(
                        id = movie.id,
                        title = movie.titleText,
                        posterUrl = movie.primaryImage?.url,
                        type = movie.titleType,
                        genre = movie.genres?.joinToString(", "),
                        releaseYear = movie.releaseYear,
                        rating = movie.rating?.aggregateRating,
                        plot = movie.plot
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

    override fun getFavoriteMoviesFromLocal(): Flow<List<RoomApi>> {
        return movieDao.getFavoriteMovies()
    }

    override suspend fun getMovieByIdFromLocal(movieId: String): RoomApi? {
        return movieDao.getMovieById(movieId)
    }

    override suspend fun updateFavoriteStatus(movieId: String, isFavorite: Boolean) {
        movieDao.updateFavoriteStatus(movieId, isFavorite)
    }

    override suspend fun updateMovie(movie: RoomApi) {
        dao.updateMovie(movie)
    }

    override suspend fun isMovieFavorite(movieId: String): Boolean {
        return movieDao.getMovieById(movieId)?.isFavorite == true
    }

    override suspend fun addFavorite(movie: RoomApi) {
        val favoritedMovie = movie.copy(isFavorite = true)
        movieDao.insertAll(listOf(favoritedMovie))
    }

    override suspend fun removeFavorite(movie: RoomApi) {
        val unfavoritedMovie = movie.copy(isFavorite = false)
        movieDao.insertAll(listOf(unfavoritedMovie))
    }

    override suspend fun addMovieToFavorites(movie: Movie) {
        dao.addToFavorite(movie)
    }

    override suspend fun removeMovieFromFavorites(movie: Movie) {
        return dao.removeFromFavorite(movie.id)
    }

    override fun getFavoriteMovies(email: String): Flow<List<Movie>> {
        return dao.getAllFavoriteMovies(email)
    }

    override suspend fun isMovieFavorites(movieId: String, email: String): Boolean {
        return dao.isFavorite(movieId)
    }

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