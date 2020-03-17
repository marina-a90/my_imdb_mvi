package com.example.movies_mvi.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies_mvi.R
import com.example.movies_mvi.model.Movie
import com.example.movies_mvi.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_movies.*

@Suppress("NAME_SHADOWING")
class MoviesFragment : Fragment(), MoviesListRecyclerAdapter.Interaction {

    private val TAG: String = MoviesFragment::class.java.simpleName

    private lateinit var viewModel: MainViewModel
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

        initRecyclerView()
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
