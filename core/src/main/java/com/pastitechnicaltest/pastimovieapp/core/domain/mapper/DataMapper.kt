package com.pastitechnicaltest.pastimovieapp.core.domain.mapper

import com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response.GenreItemResponse
import com.pastitechnicaltest.pastimovieapp.core.data.lib.MovieApiResponse
import com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response.AuthorItem
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Cast
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Movie
import com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response.CastItem
import com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response.MovieDetailResponse
import com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response.MovieItem
import com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response.ReviewItem
import com.pastitechnicaltest.pastimovieapp.core.data.movie.model.response.VideoItemResponse
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Author
import com.pastitechnicaltest.pastimovieapp.core.domain.model.DetailMovie
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Genre
import com.pastitechnicaltest.pastimovieapp.core.domain.model.PaginationItem
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Review
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Video
import com.pastitechnicaltest.pastimovieapp.core.utils.orFalse
import com.pastitechnicaltest.pastimovieapp.core.utils.orZero

fun MovieItem.mapToMovie(): Movie =
    Movie(
        id = this.id.orZero(),
        title = this.title.orEmpty(),
        overview = this.overview.orEmpty(),
        posterPath = this.posterPath,
        voteAverage = this.voteAverage.orZero(),
        adult = this.adult.orFalse(),
        releaseDate = this.releaseDate.orEmpty()
    )

fun CastItem.mapToCast(): Cast =
    Cast(
        id = this.id.orZero(),
        name = this.name.orEmpty(),
        character = this.character,
        profilePath = this.profilePath
    )

fun MovieApiResponse<MovieItem>.mapToMoviePagination(): PaginationItem<Movie> {
    val moviesResult = mutableListOf<Movie>()
    this.results.forEach { genreResponse ->
        moviesResult.add(genreResponse.mapToMovie())
    }

    return PaginationItem(
        page = this.page.orZero(),
        totalPages = this.totalPages.orZero(),
        results = moviesResult
    )
}

fun ReviewItem.mapToReview(): Review =
    Review(
        author_details = this.author_details?.mapToAuthor() ?: Author(),
        content = this.content.orEmpty()
    )

fun AuthorItem.mapToAuthor(): Author =
    Author(
        name = this.name.orEmpty(),
        username = this.username.orEmpty(),
        avatarPath = this.avatarPath.orEmpty(),
        rating = this.rating.orZero()
    )

fun MovieApiResponse<ReviewItem>.mapToReviewPagination(): PaginationItem<Review> {
    val reviewResult = mutableListOf<Review>()

    this.results.forEach { reviewResponse ->
        reviewResult.add(reviewResponse.mapToReview())
    }

    return PaginationItem(
        page = this.page.orZero(),
        totalPages = this.totalPages.orZero(),
        results = reviewResult
    )
}


fun GenreItemResponse.mapToGenre(): Genre =
    Genre(
        id = this.id.orZero(),
        name = this.name.orEmpty()
    )

fun VideoItemResponse.mapToVideo(): Video =
    Video(
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        key = this.key.orEmpty()
    )

fun MovieDetailResponse.mapToDetailMovie(): DetailMovie {
    val genres = ArrayList<String>()
    this.genres?.forEach { genreResponse ->
        genres.add(genreResponse.name.orEmpty())
    }

    return DetailMovie(
        genres = genres,
        overview = this.overview.orEmpty(),
        popularity = this.popularity.orZero(),
        voteAverage = this.voteAverage.orZero(),
        runtime = this.runtime.orZero()
    )
}

