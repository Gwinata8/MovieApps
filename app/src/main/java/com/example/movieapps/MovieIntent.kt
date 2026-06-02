package com.example.movieapps

sealed interface MovieIntent {
    data object LoadGenres : MovieIntent
    data class LoadMovieList(val genreIds: String, val page: String? = null) : MovieIntent
    data class LoadMovieDetail(val movieId: String) : MovieIntent
    data class LoadMovieReviews(val movieId: String, val page: String? = null) : MovieIntent
}