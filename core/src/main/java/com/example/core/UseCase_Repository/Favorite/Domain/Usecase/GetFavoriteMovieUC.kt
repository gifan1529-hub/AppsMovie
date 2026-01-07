package com.example.core.UseCase_Repository.Favorite.Domain.Usecase

import com.example.core.CleanArchitecture.Domain.Repository.MovieRepository
import com.example.core.Database.MovieDatabase.Movie
import kotlinx.coroutines.flow.Flow
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