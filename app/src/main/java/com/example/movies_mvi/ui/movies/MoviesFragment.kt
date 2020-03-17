package com.example.movies_mvi.ui.movies

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies_mvi.R
import com.example.movies_mvi.api.RetrofitBuilder
import com.example.movies_mvi.model.Movie
import com.example.movies_mvi.ui.DataStateListener
import com.example.movies_mvi.ui.movies.state.MainStateEvent.GetMoviesEvent
import com.example.movies_mvi.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.button_load_movies.*
import kotlinx.android.synthetic.main.fragment_movies.*

@Suppress("NAME_SHADOWING")
class MoviesFragment : Fragment(), MoviesListRecyclerAdapter.Interaction {

    private val TAG: String = MoviesFragment::class.java.simpleName

    private lateinit var viewModel: MainViewModel
    private lateinit var dataStateHandler: DataStateListener
    private lateinit var moviesAdapter: MoviesListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // let can be used instead of run
        viewModel = activity?.let{
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid activity")

        subscribeObservers()

        initRecyclerView()

        button_load_movies.setOnClickListener {
            Log.d(TAG, "clicked on button")
            loadMovies()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            dataStateHandler = context as DataStateListener
        }catch(e: ClassCastException){
            println("$context must implement DataStateListener")
        }
    }

    private fun loadMovies() {
        viewModel.setStateEvent(GetMoviesEvent(RetrofitBuilder.getApiKey(), 1))
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            Log.d(TAG, "DataState: $dataState")

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

        viewModel.viewState.observe(viewLifecycleOwner, Observer {viewState ->
            viewState.movies?.let { movies ->
                // set BlogPosts to RecyclerView
                println("DEBUG: Setting movies to RecyclerView: $viewState.movies")
                moviesAdapter.submitList(movies)
            }
        })
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)

            val topSpacingDecorator = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)

            // implement the interface for the interaction
            // MainRecyclerAdapter.Interaction
            moviesAdapter = MoviesListRecyclerAdapter(this@MoviesFragment)
            adapter = moviesAdapter
        }
    }

    override fun onItemSelected(position: Int, item: Movie) {
        Log.d(TAG, "clicked: position - $position, item - $item")
    }

}
