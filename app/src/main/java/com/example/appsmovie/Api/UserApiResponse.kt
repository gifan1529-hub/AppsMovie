package com.example.appsmovie.Api
import android.media.Rating
import com.google.gson.annotations.SerializedName
data class UserApiResponse (

    @SerializedName("titles")
    val results: List<MovieResult>
)

data class MovieResult (

    @SerializedName("id")
    val id: String,

    @SerializedName("primaryImage")
    val primaryImage: PrimaryImage?,

    @SerializedName("type")
    val titleType: String?,

    @SerializedName("primaryTitle")
    val titleText: String?,

    @SerializedName("originalTitle")
    val originalTitle: String?,

    @SerializedName("startYear")
    val releaseYear: Int?,

    @SerializedName("plot")
    val plot: String?,

    @SerializedName("genres")
    val genres: List<String>?,

    @SerializedName("rating")
    val rating: lating?
)

data class PrimaryImage (

    @SerializedName("id")
    val id: String,

    @SerializedName("url")
    val url: String?,

    @SerializedName("caption")
    val caption: Caption?
)

data class Caption(
    @SerializedName("plainText")
    val plainText: String
)

data class lating(
    @SerializedName("aggregateRating")
    val aggregateRating: Double?,

    @SerializedName("voteCount")
    val voteCount: Int?
)




