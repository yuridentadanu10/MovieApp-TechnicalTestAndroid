package com.pastitechnicaltest.pastimovieapp.core.domain.model

data class DetailMovie(
    val genres: List<String>,
    val overview: String,
    val popularity: Double,
    val voteAverage: Double,
    val runtime: Int
)
