package com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response

import com.google.gson.annotations.SerializedName

data class VideoItemResponse(
    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("key")
    val key: String? = null
)