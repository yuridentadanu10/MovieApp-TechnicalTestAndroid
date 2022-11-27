package com.pastitechnicaltest.pastimovieapp.presentation.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.pastitechnicaltest.pastimovieapp.core.data.lib.ApiResponse
import com.pastitechnicaltest.pastimovieapp.core.domain.MovieUseCase
import com.pastitechnicaltest.pastimovieapp.core.domain.model.PaginationItem
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Review

class ReviewViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    var page = 1
    var hasMore = false

    fun addPage() {
        page += 1
    }

    fun getReviewMovie(movieId: Int, page: Int): LiveData<ApiResponse<PaginationItem<Review>>> {
        return movieUseCase.getReviewMovie(movieId, page).asLiveData()
    }

}