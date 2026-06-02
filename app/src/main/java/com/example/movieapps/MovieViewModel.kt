package com.example.movieapps

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MovieState())

    val state: StateFlow<MovieState> = _state.asStateFlow()

    fun loadGenres() {
        viewModelScope.launch {
            reduce(MoviePartialState.Loading)
            try {
                val genres = repository.getMovieGenre().genres
                reduce(MoviePartialState.SuccessLoadGenres(genres))
            } catch (e: Exception) {
                reduce(MoviePartialState.Error(e.message.orEmpty()))
            }
        }
    }

    fun loadMovieList(genreIds: String, page: String?) {
        viewModelScope.launch {
            reduce(MoviePartialState.Loading)
            try {
                val movieList = repository.getMovieList(genreIds, page ?: "1").results
                reduce(MoviePartialState.SuccessLoadMovieList(movieList))
            } catch (e: Exception) {
                reduce(MoviePartialState.Error(e.message.orEmpty()))
            }
        }
    }

    fun loadMovieDetail(movieId: String) {
        viewModelScope.launch {
            reduce(MoviePartialState.Loading)
            try {
                val movieDetail = repository.getMovieDetail(movieId)

                reduce(MoviePartialState.SuccessLoadMovieDetail(movieDetail))
            } catch (e: Exception) {
                reduce(MoviePartialState.Error(e.message.orEmpty()))
            }
        }
    }

    fun loadMoviereviews(movieId: String, page: String?) {
        viewModelScope.launch {
            reduce(MoviePartialState.Loading)
            try {
                Log.d("MovieApps", "loadReviews1")
                val movieReviews = repository.getMovieReviews(movieId, page ?: "1").results
                Log.d("MovieApps", "loadReviews2: $movieReviews")
                reduce(MoviePartialState.SuccessLoadMovieReviews(movieReviews))
            } catch (e: Exception) {
                reduce(MoviePartialState.Error(e.message.orEmpty()))
            }
        }
    }

    private fun reduce(partialState: MoviePartialState) {
        _state.update { currentState ->
            when (partialState) {
                MoviePartialState.Loading -> {
                    currentState.copy(
                        status = Status.Loading,
                        isLoading = true,
                        error = null
                    )
                }
                is MoviePartialState.SuccessLoadGenres -> {
                    currentState.copy(
                        status = Status.SuccessLoadGenres,
                        isLoading = false,
                        genres = partialState.genres
                    )
                }
                is MoviePartialState.SuccessLoadMovieList -> {
                    val movieList = currentState.movieList.toMutableList()
                    movieList.addAll(partialState.movieList)
                    currentState.copy(
                        status = Status.SuccessLoadMovieList,
                        isLoading = false,
                        movieList = movieList
                    )
                }
                is MoviePartialState.SuccessLoadMovieDetail -> {
                    currentState.copy(
                        status = Status.SuccessLoadMovieDetail,
                        isLoading = false,
                        movieDetail = partialState.movieDetail
                    )
                }
                is MoviePartialState.SuccessLoadMovieReviews -> {
                    val movieReviews = currentState.movieReviews.toMutableList()
                    movieReviews.addAll(partialState.movieReviews)
                    Log.d("MovieApps", "reduce: $movieReviews")
                    currentState.copy(
                        status = Status.SuccessLoadMovieReviews,
                        isLoading = false,
                        movieReviews = movieReviews
                    )
                }
                is MoviePartialState.Error -> {
                    currentState.copy(
                        status = Status.Error(partialState.message),
                        isLoading = false,
                        error = partialState.message
                    )
                }
            }
        }
    }

    fun processIntent(
        intent: MovieIntent
    ) {
        when(intent) {
            MovieIntent.LoadGenres -> {
                loadGenres()
            }
            is MovieIntent.LoadMovieList -> {
                loadMovieList(intent.genreIds, intent.page)
            }
            is MovieIntent.LoadMovieDetail -> {
                loadMovieDetail(intent.movieId)
            }
            is MovieIntent.LoadMovieReviews -> {
                Log.d("MovieApps", "processIntent: $intent")
                Log.d("MovieApps", "processIntent: ${intent.movieId}")
                loadMoviereviews(intent.movieId, intent.page.toString())
            }
        }
    }
}