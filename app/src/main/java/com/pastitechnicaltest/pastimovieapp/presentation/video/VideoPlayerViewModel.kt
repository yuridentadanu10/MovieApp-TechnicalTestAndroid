package com.pastitechnicaltest.pastimovieapp.presentation.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.pastitechnicaltest.pastimovieapp.core.data.lib.ApiResponse
import com.pastitechnicaltest.pastimovieapp.core.domain.MovieUseCase
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Cast
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Video

class VideoPlayerViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun getVideoTrailer(movieId: Int): LiveData<ApiResponse<Video>> {
        return movieUseCase.getVideoMovie(movieId).asLiveData()
    }

    fun getMovieCasts(id: Int) : LiveData<ApiResponse<List<Cast>>> {
        return movieUseCase.getMovieCasts(id).asLiveData()
    }

}