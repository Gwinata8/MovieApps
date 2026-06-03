package com.example.movieapps.userreview

import android.os.Bundle
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
import com.example.movieapps.databinding.ActivityReviewListBinding
import com.example.movieapps.setEndlessScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class UserReviewList : AppCompatActivity() {
    private lateinit var binding: ActivityReviewListBinding
    private val viewModel: MovieViewModel by viewModels()
    private val userReviewListAdapter by lazy { UserReviewListAdapter() }
    private var movieId = 0
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movieId = intent.getIntExtra("movie_id", 0)
        with(binding.rvReview) {
            adapter = userReviewListAdapter
            animation = null
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setEndlessScrollListener {
                viewModel.processIntent(
                    MovieIntent.LoadMovieReviews(
                        movieId = movieId.toString(),
                        page = (++page)
                    )
                )
            }
        }
        viewModel.processIntent(MovieIntent.LoadMovieReviews(movieId = movieId.toString()))
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
            Status.SuccessLoadMovieReviews -> {
                userReviewListAdapter.submitList(state.movieReviews)
            }
            is Status.Error -> {

            }
            else -> TODO()
        }

    }
}