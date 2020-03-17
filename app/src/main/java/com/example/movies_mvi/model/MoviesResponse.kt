package com.example.movies_mvi.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class MoviesResponse (
    @SerializedName("page")
    private val page: Int? = null,

    @SerializedName("total_results")
    private val totalResults: Int? = null,

    @SerializedName("total_pages")
    private val totalPages: Int? = null,

    @Expose
    @SerializedName("results")
    private val results: List<Movie> = ArrayList()
    )
{
    fun getMovies(): List<Movie> {
        return results
    }

    override fun toString(): String {
        return "MoviesResponse(\n page=$page,\n total results=$totalResults,\n total pages=$totalPages,\n results=$results)"
    }
}