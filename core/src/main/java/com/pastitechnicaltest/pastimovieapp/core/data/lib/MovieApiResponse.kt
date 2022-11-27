package com.pastitechnicaltest.pastimovieapp.core.data.lib

import com.google.gson.annotations.SerializedName

data class MovieApiResponse<T>(
    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("total_pages")
    val totalPages: Int? = null,

    @field:SerializedName("results")
    val results: List<T>,

    @field:SerializedName("total_results")
    val totalResults: Int? = null
)
