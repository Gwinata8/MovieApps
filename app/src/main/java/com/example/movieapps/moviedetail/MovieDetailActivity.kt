package com.example.movieapps.moviedetail

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.movieapps.MovieIntent
import com.example.movieapps.MovieState
import com.example.movieapps.MovieViewModel
import com.example.movieapps.Status
import com.example.movieapps.databinding.ActivityMovieDetailBinding
import com.example.movieapps.userreview.UserReviewList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    private val viewModel: MovieViewModel by viewModels()
    private var movieId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movieId = intent.getIntExtra("movie_id", 0)
        viewModel.processIntent(
            MovieIntent.LoadMovieDetail(movieId.toString())
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
            Status.SuccessLoadMovieDetail -> {
                binding.apply {
                    tvTitleValue.text = state.movieDetail?.original_title
                    tvOverviewValue.text = state.movieDetail?.overview
                    tvReleaseDateValue.text = state.movieDetail?.release_date
                    tvPopularityValue.text = state.movieDetail?.popularity.toString()
                    tvGenresValue.text = state.movieDetail?.genre_ids.toString()
                    btnReviews.setOnClickListener {
                        val intent = Intent(this@MovieDetailActivity, UserReviewList::class.java)
                        intent.putExtra("movie_id", movieId)
                        startActivity(intent)
                    }
                }
            }
            is Status.Error -> {

            }
            else -> TODO()
        }

    }
}