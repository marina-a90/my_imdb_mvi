package com.example.movies_mvi.ui

import com.example.movies_mvi.util.DataState

interface DataStateListener {
    fun onDataStateChange(dataState: DataState<*>?)
}