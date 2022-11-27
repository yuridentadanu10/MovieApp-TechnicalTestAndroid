package com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @field:SerializedName("results")
    val results: List<VideoItemResponse> = emptyList()
)