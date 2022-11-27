package com.pastitechnicaltest.pastimovieapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.pastitechnicaltest.pastimovieapp.core.data.lib.ApiResponse
import com.pastitechnicaltest.pastimovieapp.core.domain.MovieUseCase
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Genre
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Movie
import com.pastitechnicaltest.pastimovieapp.core.domain.model.PaginationItem
import com.pastitechnicaltest.pastimovieapp.core.utils.mutableLiveDataOf

class HomeViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    var choosenGenre = mutableLiveDataOf<String>()
    var page = 1
    var hasMore = false

    fun addPage() {
        page += 1
    }

    fun resetPage() {
        page = 1
    }

    fun getGenre(): LiveData<ApiResponse<List<Genre>>> {
        return movieUseCase.getMovieGenre().asLiveData()
    }

    fun getDiscoverMovie(genre: String = "", page: Int): LiveData<ApiResponse<PaginationItem<Movie>>> {
        return movieUseCase.getDiscoverMovie(genre, page).asLiveData()
    }

}