package com.pastitechnicaltest.pastimovieapp.core.data.movie.remote

import com.pastitechnicaltest.pastimovieapp.core.BuildConfig
import com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response.GenreResponse
import com.pastitechnicaltest.pastimovieapp.core.data.lib.MovieApiResponse
import com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response.GetCastResponse
import com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response.MovieDetailResponse
import com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response.MovieItem
import com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response.ReviewItem
import com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCasts(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): GetCastResponse

    @GET("search/movie")
    suspend fun getMovieByQuery(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieApiResponse<MovieItem>

    //Pasti
    @GET("genre/movie/list")
    suspend fun getGenre(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): GenreResponse

    @GET("discover/movie")
    suspend fun getDiscoverMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1,
        @Query("with_genres") withGenres: String = ""
    ): MovieApiResponse<MovieItem>

    @GET("movie/{movie_id}/reviews")
    suspend fun getReviewMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1
    ): MovieApiResponse<ReviewItem>

    @GET("movie/{movie_id}/videos")
    suspend fun getVideoMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): VideoResponse

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieDetailResponse
}