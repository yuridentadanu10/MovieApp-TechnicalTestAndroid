package com.pastitechnicaltest.pastimovieapp.core.domain.model

data class PaginationItem<T>(
    val page: Int = 0,
    val totalPages: Int = 0,
    val results: List<T>,
    val totalResults: Int = 0
)