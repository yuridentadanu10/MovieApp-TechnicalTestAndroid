package com.pastitechnicaltest.pastimovieapp.core.domain.repository

import com.pastitechnicaltest.pastimovieapp.core.domain.model.Cast
import com.pastitechnicaltest.pastimovieapp.core.data.lib.ApiResponse
import com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response.MovieDetailResponse
import com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response.ReviewItem
import com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response.VideoItemResponse
import com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response.VideoResponse
import com.pastitechnicaltest.pastimovieapp.core.domain.model.DetailMovie
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Genre
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Movie
import com.pastitechnicaltest.pastimovieapp.core.domain.model.PaginationItem
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Review
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun getMovieCasts(id: Int): Flow<ApiResponse<List<Cast>>>

    fun getMovieByQuery(query: String): Flow<ApiResponse<List<Movie>>>

    fun getGenre(): Flow<ApiResponse<List<Genre>>>

    fun getDiscoverMovie(genre: String, page: Int): Flow<ApiResponse<PaginationItem<Movie>>>

    fun getReviewMovie(movieId: Int, page: Int): Flow<ApiResponse<PaginationItem<Review>>>

    fun getVideoMovie(movieId: Int): Flow<ApiResponse<Video>>

    fun getDetailMovie(movieId: Int): Flow<ApiResponse<DetailMovie>>
}