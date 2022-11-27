package com.pastitechnicaltest.pastimovieapp.core.domain

import com.pastitechnicaltest.pastimovieapp.core.domain.model.Cast
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Movie
import com.pastitechnicaltest.pastimovieapp.core.data.lib.ApiResponse
import com.pastitechnicaltest.pastimovieapp.core.data.movie.MovieRepository
import com.pastitechnicaltest.pastimovieapp.core.domain.model.DetailMovie
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Genre
import com.pastitechnicaltest.pastimovieapp.core.domain.model.PaginationItem
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Review
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Video
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepository: MovieRepository) : MovieUseCase {

    override fun getMovieCasts(id: Int): Flow<ApiResponse<List<Cast>>> {
        return movieRepository.getMovieCasts(id)
    }

    override fun getMovieByQuery(query: String): Flow<ApiResponse<List<Movie>>> {
        return movieRepository.getMovieByQuery(query)
    }

    override fun getMovieGenre(): Flow<ApiResponse<List<Genre>>> {
        return movieRepository.getGenre()
    }

    override fun getDiscoverMovie(
        genre: String, page: Int
    ): Flow<ApiResponse<PaginationItem<Movie>>> {
        return movieRepository.getDiscoverMovie(genre, page)
    }

    override fun getReviewMovie(
        movieId: Int,
        page: Int
    ): Flow<ApiResponse<PaginationItem<Review>>> {
        return movieRepository.getReviewMovie(movieId, page)
    }

    override fun getVideoMovie(movieId: Int): Flow<ApiResponse<Video>> {
        return movieRepository.getVideoMovie(movieId)
    }

    override fun getDetailMovie(movieId: Int): Flow<ApiResponse<DetailMovie>> {
        return movieRepository.getDetailMovie(movieId)
    }
}