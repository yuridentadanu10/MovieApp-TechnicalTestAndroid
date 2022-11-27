package com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @field:SerializedName("genres")
    val genres: List<GenreItemResponse> = emptyList()
)