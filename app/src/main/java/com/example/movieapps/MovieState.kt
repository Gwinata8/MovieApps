package com.example.movieapps

import com.example.movieapps.moviegenre.MovieGenreEntity
import com.example.movieapps.movielist.MovieListEntity
import com.example.movieapps.userreview.UserReviewListEntity

data class MovieState (
    val status: Status? = null,
    val isLoading: Boolean = false,
    val genres: List<MovieGenreEntity.Genre> = emptyList(),
    val movieList: List<MovieListEntity.Movie> = emptyList(),
    val movieDetail: MovieListEntity.Movie? = null,
    val movieReviews: List<UserReviewListEntity.Result> = emptyList(),
    val error: String? = null
)

sealed class MoviePartialState {
    object Loading : MoviePartialState()
    data class SuccessLoadGenres(val genres: List<MovieGenreEntity.Genre>) : MoviePartialState()
    data class SuccessLoadMovieList(val movieList: List<MovieListEntity.Movie>) : MoviePartialState()
    data class SuccessLoadMovieDetail(val movieDetail: MovieListEntity.Movie) : MoviePartialState()
    data class SuccessLoadMovieReviews(val movieReviews: List<UserReviewListEntity.Result>) : MoviePartialState()
    data class Error(val message: String) : MoviePartialState()
}

sealed class Status {
    object Loading : Status()
    object SuccessLoadGenres : Status()
    object SuccessLoadMovieList : Status()
    object SuccessLoadMovieDetail : Status()
    object SuccessLoadMovieReviews : Status()
    data class Error(val message: String) : Status()
}
