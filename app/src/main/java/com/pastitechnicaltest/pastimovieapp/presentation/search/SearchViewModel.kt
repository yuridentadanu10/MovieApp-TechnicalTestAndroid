package com.pastitechnicaltest.pastimovieapp.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.pastitechnicaltest.pastimovieapp.core.data.lib.ApiResponse
import com.pastitechnicaltest.pastimovieapp.core.domain.MovieUseCase
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Movie

class SearchViewModel(private val movieUseCase: MovieUseCase): ViewModel() {

    fun getMoviesByQuery(query: String): LiveData<ApiResponse<List<Movie>>> {
        return movieUseCase.getMovieByQuery(query).asLiveData()
    }

}