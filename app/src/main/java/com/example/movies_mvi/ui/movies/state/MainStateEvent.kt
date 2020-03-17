package com.example.movies_mvi.ui.movies.state

import com.example.movies_mvi.api.RetrofitBuilder

// use different event classes for different packages, different view models

// Sealed classes are used for representing restricted class hierarchies,
// when a value can have one of the types from a limited set, but cannot have any other type.
sealed class MainStateEvent {

    // data models and none
    // different things that can be returned from the wrapper - all return MainStateEvent

    class GetMoviesEvent(
        var apiKey: String,
        val page: Int
    ): MainStateEvent()

    class None: MainStateEvent()

}