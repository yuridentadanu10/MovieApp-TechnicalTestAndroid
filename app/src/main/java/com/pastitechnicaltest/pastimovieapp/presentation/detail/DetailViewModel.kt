package com.pastitechnicaltest.pastimovieapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Cast
import com.pastitechnicaltest.pastimovieapp.core.data.lib.ApiResponse
import com.pastitechnicaltest.pastimovieapp.core.domain.MovieUseCase
import com.pastitechnicaltest.pastimovieapp.core.domain.model.DetailMovie
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Movie
import kotlinx.coroutines.launch

class DetailViewModel(private val movieUseCase: MovieUseCase): ViewModel() {

    fun getMovieDetail(movieId: Int) : LiveData<ApiResponse<DetailMovie>> {
        return movieUseCase.getDetailMovie(movieId).asLiveData()
    }

}