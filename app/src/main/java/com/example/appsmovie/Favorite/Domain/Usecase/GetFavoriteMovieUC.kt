package com.example.appsmovie.Favorite.Domain.Usecase

import com.example.appsmovie.ApiOffline.RoomApi
import com.example.appsmovie.CleanArchitecture.Domain.Repository.MovieRepository
import com.example.appsmovie.Favorite.Domain.Repository.FavoriteRepository
import com.example.appsmovie.data.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteMovieUC @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(email: String): Flow<List<Movie>> {
        return repository.getFavoriteMovies(email)
//        return repository.getFavoriteMoviesFromLocal().map { roomApiList ->
//            roomApiList.map { roomApi ->
//                Movie(
//                    id = roomApi.id,
//                    title = roomApi.title,
//                    posterUrl= roomApi.posterUrl,
//                    plot = roomApi.plot,
//                    rating = roomApi.rating
//                )
//            }
//        }
    }
}