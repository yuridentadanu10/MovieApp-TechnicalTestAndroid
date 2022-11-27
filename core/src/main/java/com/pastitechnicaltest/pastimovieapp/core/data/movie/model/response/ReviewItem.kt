package com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response

import com.google.gson.annotations.SerializedName

data class ReviewItem(
    @field:SerializedName("author_details")
    val author_details: AuthorItem? = null,

    @field:SerializedName("content")
    val content: String? = null
)