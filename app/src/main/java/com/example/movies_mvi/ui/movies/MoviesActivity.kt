package com.example.movies_mvi.ui.movies

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.movies_mvi.R
import com.example.movies_mvi.ui.DataStateListener
import com.example.movies_mvi.util.DataState
import kotlinx.android.synthetic.main.progress_bar.*

class MoviesActivity : AppCompatActivity(), DataStateListener {

    private val TAG: String = MoviesActivity::class.java.simpleName

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        showMoviesFragment()
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
