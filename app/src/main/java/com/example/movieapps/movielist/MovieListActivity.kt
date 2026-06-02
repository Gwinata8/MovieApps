package com.example.movieapps.movielist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapps.MovieIntent
import com.example.movieapps.MovieState
import com.example.movieapps.MovieViewModel
import com.example.movieapps.Status
import com.example.movieapps.databinding.ActivityMovieListBinding
import com.example.movieapps.moviedetail.MovieDetailActivity
import com.example.movieapps.setEndlessScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class MovieListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieListBinding
    private val viewModel: MovieViewModel by viewModels()
    private val movieListAdapter by lazy{ MovieListAdapter(
        onMovieClick = { movie ->
            val intent = Intent(
                this,
                MovieDetailActivity::class.java
            )

            intent.putExtra(
                "movie_id",
                movie.id
            )

            startActivity(intent)

        }
    ) }
    private var genreId = 0
    private var movieListPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        genreId = intent.getIntExtra(
            "genre_id",
            0
        )
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        with(binding.rvMovie) {
            adapter = movieListAdapter
            animation = null
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setEndlessScrollListener {
                viewModel.processIntent(
                    MovieIntent.LoadMovieList(
                        genreIds = genreId.toString(),
                        page = (++movieListPage).toString()
                    )
                )
            }
        }
        viewModel.processIntent(
            MovieIntent.LoadMovieList(genreIds = genreId.toString())
        )
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    render(it)
                }
            }
        }
    }

    private fun render(state: MovieState) {
        when (state.status) {
            Status.Loading -> {
                // Show loading state
            }
            Status.SuccessLoadMovieList -> {
                movieListAdapter.submitList(state.movieList)
            }
            is Status.Error -> {

            }
            else -> TODO()
        }

    }
}