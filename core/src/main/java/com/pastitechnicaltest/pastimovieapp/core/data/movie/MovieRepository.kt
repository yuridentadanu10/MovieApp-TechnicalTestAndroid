package com.pastitechnicaltest.pastimovieapp.core.data.movie

import com.pastitechnicaltest.pastimovieapp.core.domain.model.Cast
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Movie
import com.pastitechnicaltest.pastimovieapp.core.data.lib.ApiResponse
import com.pastitechnicaltest.pastimovieapp.core.data.movie.remote.MovieService
import com.pastitechnicaltest.pastimovieapp.core.domain.mapper.mapToCast
import com.pastitechnicaltest.pastimovieapp.core.domain.mapper.mapToDetailMovie
import com.pastitechnicaltest.pastimovieapp.core.domain.mapper.mapToGenre
import com.pastitechnicaltest.pastimovieapp.core.domain.mapper.mapToMovie
import com.pastitechnicaltest.pastimovieapp.core.domain.mapper.mapToMoviePagination
import com.pastitechnicaltest.pastimovieapp.core.domain.mapper.mapToReviewPagination
import com.pastitechnicaltest.pastimovieapp.core.domain.mapper.mapToVideo
import com.pastitechnicaltest.pastimovieapp.core.domain.model.DetailMovie
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Genre
import com.pastitechnicaltest.pastimovieapp.core.domain.model.PaginationItem
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Review
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Video
import com.pastitechnicaltest.pastimovieapp.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class MovieRepository(
    private val api: MovieService
) : IMovieRepository {

    override fun getMovieCasts(id: Int): Flow<ApiResponse<List<Cast>>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.getMovieCasts(id)
            val castList = response.cast

            if (castList.isNotEmpty()) {
                val cast = mutableListOf<Cast>()
                castList.forEach { castResponse ->
                    cast.add(castResponse.mapToCast())
                }
                emit(ApiResponse.Success(cast))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (ex: Exception) {
            emit(ApiResponse.Error(ex.toString()))
            Timber.e(ex.toString())
        }
    }

    override fun getMovieByQuery(query: String): Flow<ApiResponse<List<Movie>>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.getMovieByQuery(query)
            val movieList = response.results

            if (movieList.isNotEmpty()) {
                val movies = mutableListOf<Movie>()
                movieList.forEach { movieResponse ->
                    movies.add(movieResponse.mapToMovie())
                }
                emit(ApiResponse.Success(movies))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (ex: Exception) {
            emit(ApiResponse.Error(ex.toString()))
            Timber.e(ex.toString())
        }
    }

    override fun getGenre(): Flow<ApiResponse<List<Genre>>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.getGenre()
            val genreList = response.genres

            if (genreList.isNotEmpty()) {
                val genres = mutableListOf<Genre>()
                genreList.forEach { genreResponse ->
                    genres.add(genreResponse.mapToGenre())
                }
                emit(ApiResponse.Success(genres))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (ex: Exception) {
            emit(ApiResponse.Error(ex.toString()))
            Timber.e(ex.toString())
        }
    }

    override fun getDiscoverMovie(genre: String, page: Int): Flow<ApiResponse<PaginationItem<Movie>>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.getDiscoverMovie(page = page, withGenres =  genre)
            val genreList = response.results
            if (genreList.isNotEmpty()) {
                emit(ApiResponse.Success(response.mapToMoviePagination()))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (ex: Exception) {
            emit(ApiResponse.Error(ex.toString()))
            Timber.e(ex.toString())
        }
    }

    override fun getReviewMovie(
        movieId: Int,
        page: Int
    ): Flow<ApiResponse<PaginationItem<Review>>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.getReviewMovie(movieId, page = page)
            val genreList = response.results
            if (genreList.isNotEmpty()) {
                emit(ApiResponse.Success(response.mapToReviewPagination()))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (ex: Exception) {
            emit(ApiResponse.Error(ex.toString()))
            Timber.e(ex.toString())
        }
    }

    override fun getVideoMovie(movieId: Int): Flow<ApiResponse<Video>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.getVideoMovie(movieId)
            val videoList = response.results

            if (videoList.isNotEmpty()) {
                val videos = mutableListOf<Video>()
                videoList.forEach { videoResponse ->
                    if (videoResponse.type == TYPE_TRAILER) {
                        videos.add(videoResponse.mapToVideo())
                    }
                }
                if (videos.isEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(videos.first()))
                }
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (ex: Exception) {
            emit(ApiResponse.Error(ex.toString()))
            Timber.e(ex.toString())
        }
    }

    override fun getDetailMovie(movieId: Int): Flow<ApiResponse<DetailMovie>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.getDetailMovie(movieId)
            emit(ApiResponse.Success(response.mapToDetailMovie()))
        } catch (ex: Exception) {
            emit(ApiResponse.Error(ex.toString()))
            Timber.e(ex.toString())
        }
    }

    companion object{
        private const val TYPE_TRAILER = "Trailer"
    }
}