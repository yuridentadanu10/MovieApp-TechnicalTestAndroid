package com.pastitechnicaltest.pastimovieapp.core.domain

import com.pastitechnicaltest.pastimovieapp.core.domain.model.Cast
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Movie
import com.pastitechnicaltest.pastimovieapp.core.data.lib.ApiResponse
import com.pastitechnicaltest.pastimovieapp.core.domain.model.DetailMovie
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Genre
import com.pastitechnicaltest.pastimovieapp.core.domain.model.PaginationItem
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Review
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    fun getMovieCasts(id: Int): Flow<ApiResponse<List<Cast>>>

    fun getMovieByQuery(query: String): Flow<ApiResponse<List<Movie>>>

    fun getMovieGenre() :Flow<ApiResponse<List<Genre>>>

    fun getDiscoverMovie(genre: String, page: Int): Flow<ApiResponse<PaginationItem<Movie>>>

    fun getReviewMovie(movieId: Int, page: Int): Flow<ApiResponse<PaginationItem<Review>>>

    fun getVideoMovie(movieId: Int): Flow<ApiResponse<Video>>

    fun getDetailMovie(movieId: Int): Flow<ApiResponse<DetailMovie>>
}