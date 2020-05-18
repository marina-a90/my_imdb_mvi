package com.example.movies_mvi.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.movies_mvi.R
import com.example.movies_mvi.api.RetrofitBuilder
import com.example.movies_mvi.ui.DataStateListener
import com.example.movies_mvi.ui.movies.state.MainStateEvent.GetMoviesEvent
import com.example.movies_mvi.util.DataState
import kotlinx.android.synthetic.main.button_load_movies.*
import kotlinx.android.synthetic.main.progress_bar.*

class MoviesActivity : AppCompatActivity(), DataStateListener {

    private val TAG: String = MoviesActivity::class.java.simpleName

    lateinit var viewModel: MainViewModel
    private lateinit var dataStateHandler: DataStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        showMoviesFragment()

        setDataStateHandler()

        subscribeObservers()

        button_load_movies.setOnClickListener {
            Log.d(TAG, "clicked on button")
            loadMovies()
        }
    }

    private fun setDataStateHandler() {
        try{
            dataStateHandler = this // context
        }catch(e: ClassCastException){
            println("$this must implement DataStateListener")
        }
    }

    private fun loadMovies() {
        viewModel.setStateEvent(GetMoviesEvent(RetrofitBuilder.getApiKey(), 1))
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->

            // Handle Loading and Message
            dataStateHandler.onDataStateChange(dataState)

            // handle Data<T>
            dataState.data?.let { mainViewState ->

                println("DEBUG: DataState: $mainViewState")

                mainViewState.getContentIfNotHandled()?.let{ mainViewState ->
                    mainViewState.movies?.let{
                        viewModel.setMovieListData(it)
                    }
                }

            }
        })
    }

    private fun showMoviesFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.frame_movies,
                MoviesFragment(),
                "MainFragment")
            .commit()
    }

    override fun onDataStateChange(dataState: DataState<*>?) {
        handleDataStateChange(dataState)
    }

    private fun handleDataStateChange(dataState: DataState<*>?){
        dataState?.let{
            // Handle loading
            showProgressBar(dataState.loading)

            Log.d(TAG, "DEBUG: loading ${dataState.loading}")

            // Handle Message
            dataState.message?.let{ event ->
                event.getContentIfNotHandled()?.let { message ->
                    showToast(message)
                }
            }
        }
    }

    private fun showProgressBar(isVisible: Boolean){
        if (isVisible) {
            progress_bar.visibility = View.VISIBLE
        }
        else{
            progress_bar.visibility = View.INVISIBLE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
