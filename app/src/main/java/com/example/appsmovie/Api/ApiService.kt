package com.example.appsmovie.Api

import retrofit2.http.Query
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path

interface ApiService {
    @GET("titles")
    suspend fun getTitles(): UserApiResponse

    @GET("titles/{id}")
    suspend fun getMovieDetails(
    @Path("id") movieId: String
    ): MovieResult

    @GET("titles")
    suspend fun searchTitles(
    @Query("primaryTitle") query: String
    ): UserApiResponse

}