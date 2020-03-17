package com.example.movies_mvi.ui.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.movies_mvi.model.Movie
import com.example.movies_mvi.repository.movies.Repository
import com.example.movies_mvi.ui.movies.state.MainStateEvent
import com.example.movies_mvi.ui.movies.state.MainStateEvent.GetMoviesEvent
import com.example.movies_mvi.ui.movies.state.MainStateEvent.None
import com.example.movies_mvi.ui.movies.state.MainViewState
import com.example.movies_mvi.util.AbsentLiveData
import com.example.movies_mvi.util.DataState

class MainViewModel: ViewModel() {

    private val TAG: String = MainViewModel::class.java.simpleName

    // only 2 live data objects in view model
    // for the state events and for data being displayed

    // handle different events
    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()

    // single mutable state object
    // the view state to be observed
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    // observe in main fragment
    val viewState: LiveData<MainViewState>
        get() = _viewState
    // same as writing
    //    fun observeViewState(): LiveData<MainViewState> {
    //        return _viewState
    //    }

    // listening for _stateEvent MutableLiveData object
    // if it changes, the switchmap will detect the change and execute the code
    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent){stateEvent ->
            stateEvent?.let {
                handleStateEvent(stateEvent)
            }
        }

    private fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>>? {
        return when(stateEvent) {

            is GetMoviesEvent -> {
                Log.d("viewmodel", "get movies")
                Repository.getMovies(stateEvent.apiKey, stateEvent.page)
            }

            is None ->{
                AbsentLiveData.create()
            }
        }
    }

    private fun getCurrentViewStateOrNew(): MainViewState {
        return viewState.value?.let {
            it
        }?: MainViewState()
    }

    // set state event - to trigger the switch map
    fun setStateEvent(event: MainStateEvent){
        _stateEvent.value = event
        Log.d(TAG, "set state $event")
    }

    fun setMovieListData(movies: List<Movie>){
        val update = getCurrentViewStateOrNew()
        update.movies = movies
        _viewState.value = update
    }

}