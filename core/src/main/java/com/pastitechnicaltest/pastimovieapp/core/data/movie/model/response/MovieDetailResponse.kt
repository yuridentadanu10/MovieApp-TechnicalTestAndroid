package com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @field:SerializedName("genres")
    val genres: List<GenreItemResponse>? = null,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("popularity")
    val popularity: Double? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("runtime")
    val runtime: Int? = null
)
