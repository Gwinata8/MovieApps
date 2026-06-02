package com.example.movieapps

import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: MovieApiService) {
    suspend fun getMovieGenre() = api.getMovieGenre()
    suspend fun getMovieList(genreIds: String, page: String) = api.getMovieList(genreIds, page)
    suspend fun getMovieDetail(movieId: String) = api.getMovieDetail(movieId)
    suspend fun getMovieReviews(movieId: String, page: String) = api.getMovieReviews(movieId, page)
}