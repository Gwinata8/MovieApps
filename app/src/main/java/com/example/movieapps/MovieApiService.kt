package com.example.movieapps

import com.example.movieapps.moviegenre.MovieGenreEntity
import com.example.movieapps.movielist.MovieListEntity
import com.example.movieapps.userreview.UserReviewListEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("genre/movie/list")
    suspend fun getMovieGenre(): MovieGenreEntity

    @GET("discover/movie")
    suspend fun getMovieList(
        @Query("with_genres") genreIds: String,
        @Query("page") page: String,
    ): MovieListEntity

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: String,
    ): MovieListEntity.Movie

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: String,
        @Query("page") page: String,
    ): UserReviewListEntity
}