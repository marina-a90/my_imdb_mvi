package com.example.movies_mvi.ui.movies.state

import com.example.movies_mvi.model.Movie

// packaging objects into a single class in the view
// single mutable live data object in a class that wraps everything together
data class MainViewState(
    // data models that we see in the view
    var movies: List<Movie>? = null
)