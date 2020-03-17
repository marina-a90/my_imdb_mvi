package com.example.movies_mvi.repository.movies

import androidx.lifecycle.LiveData
import com.example.movies_mvi.api.RetrofitBuilder
import com.example.movies_mvi.model.Movie
import com.example.movies_mvi.repository.NetworkBoundResource
import com.example.movies_mvi.ui.movies.state.MainViewState
import com.example.movies_mvi.util.ApiSuccessResponse
import com.example.movies_mvi.util.DataState
import com.example.movies_mvi.util.GenericApiResponse

object Repository {

    fun getMovies(
        apiKey: String,
        page: Int
    ): LiveData<DataState<MainViewState>>
    {
        return object: NetworkBoundResource<List<Movie>, MainViewState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<Movie>>) {
                result.value = DataState.data(
                    null,
                    MainViewState(
                        movies = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<Movie>>> {
                return RetrofitBuilder.apiService.getTopMovies(apiKey, page)
            }

        }.asLiveData()
    }
}