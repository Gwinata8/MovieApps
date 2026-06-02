package com.example.movieapps.moviegenre

data class MovieGenreEntity(
    val genres: List<Genre>
) {
    class Genre(
        val id: Int,
        val name: String
    )
}