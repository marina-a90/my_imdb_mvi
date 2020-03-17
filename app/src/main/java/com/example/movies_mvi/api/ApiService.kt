package com.example.movies_mvi.api

import androidx.lifecycle.LiveData
import com.example.movies_mvi.model.MoviesResponse
import com.example.movies_mvi.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/top_rated")
    fun getTopMovies(
        @Query("api_key") apiKey: String?, @Query("page") page: Int
    ): LiveData<GenericApiResponse<MoviesResponse>>

}