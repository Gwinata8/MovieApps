package com.example.movieapps.userreview

data class UserReviewListEntity(
    val id: Int,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
) {
    data class Result(
        val author: String,
        val content: String,
        val created_at: String
    )
}
