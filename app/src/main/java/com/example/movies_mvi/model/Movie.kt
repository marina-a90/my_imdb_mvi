package com.example.movies_mvi.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Movie (

    @Expose
    @SerializedName("id")
    val id: Int? = null,

    @Expose
    @SerializedName("title")
    var title: String? = null,

    @Expose
    @SerializedName("poster_path")
    private val posterPath: String? = null,

    @Expose
    @SerializedName("popularity")
    val popularity: Float? = null,

    @Expose
    @SerializedName("vote_count")
    val voteCount: Int? = null,

    @Expose
    @SerializedName("video")
    val video: Boolean? = null,

    @Expose
    @SerializedName("adult")
    val adult: Boolean? = null,

    @Expose
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @Expose
    @SerializedName("original_language")
    val originalLanguage: String? = null,

    @Expose
    @SerializedName("original_title")
    val originalTitle: String? = null,

    @Expose
    @SerializedName("genre_ids")
    val genreIds: List<Int> = ArrayList(),
    val genres: String? = null,

    @Expose
    @SerializedName("vote_average")
    val voteAverage: Double? = null,

    @Expose
    @SerializedName("overview")
    val overview: String? = null,

    @Expose
    @SerializedName("release_date")
    val releaseDate: String? = null
    )
{
    fun getFullPosterPath(): String? {
        val imageUrl = StringBuilder()
        imageUrl.append("http://image.tmdb.org/t/p/w500")
        imageUrl.append(posterPath)
        return imageUrl.toString()
    }

    override fun toString(): String {
        return "MoviesResponse(\n id=$id,\n title=$title,\n posterPath=$posterPath)"
    }
}