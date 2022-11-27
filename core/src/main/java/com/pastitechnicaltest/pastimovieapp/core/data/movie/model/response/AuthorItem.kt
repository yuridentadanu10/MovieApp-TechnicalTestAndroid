package com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response

import com.google.gson.annotations.SerializedName

data class AuthorItem(
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("avatar_path")
    val avatarPath: String? = null,

    @field:SerializedName("rating")
    val rating: Double? = null

)