package com.example.movies_mvi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.movies_mvi.model.Movie
import com.example.movies_mvi.util.*
import kotlinx.coroutines.*

abstract class NetworkBoundResource<ResponseObject, ViewStateType> {

    // MediatorLiveData can be CHANGED, set and looked at
    // while LiveData can only be set and looked at
    // MediatorLiveData gives the option to interact with LiveData
    protected val result = MediatorLiveData<DataState<ViewStateType>>()

    init {
        result.value = DataState.loading(true)

        // coroutines
        GlobalScope.launch(Dispatchers.IO){
            delay(1000)

            withContext(Dispatchers.Main){
                val apiResponse = createCall()
                result.addSource(apiResponse) { response ->
                    result.removeSource(apiResponse)

                    handleNetworkCall(response)
                }
            }
        }
    }

    private fun handleNetworkCall(response: GenericApiResponse<ResponseObject>){

        println("DEBUG ovde $response")

        when(response){
            is ApiSuccessResponse ->{
                println("DEBUG: NetworkBoundResource: ApiSuccessResponse: $response")
                handleApiSuccessResponse(response)
            }
            is ApiErrorResponse ->{
                // expect to get an error at this point
                println("DEBUG: NetworkBoundResource: ${response.errorMessage}")
                onReturnError(response.errorMessage)
            }
            is ApiEmptyResponse ->{
                println("DEBUG: NetworkBoundResource: Request returned NOTHING (HTTP 204)")
                onReturnError("HTTP 204. Returned NOTHING.")
            }
        }
    }

    private fun onReturnError(message: String){
        result.value = DataState.error(message)
    }

    abstract fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>

}