package com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response

import com.google.gson.annotations.SerializedName

data class GetCastResponse(
    @field:SerializedName("cast")
    val cast: List<CastItem> = emptyList(),

    @field:SerializedName("id")
    val id: Int? = 0
)

data class CastItem(

    @field:SerializedName("character")
    val character: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("profile_path")
    val profilePath: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,
)
