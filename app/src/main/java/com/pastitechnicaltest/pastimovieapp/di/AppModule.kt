package com.pastitechnicaltest.pastimovieapp.di

import com.pastitechnicaltest.pastimovieapp.core.domain.MovieInteractor
import com.pastitechnicaltest.pastimovieapp.core.domain.MovieUseCase
import com.pastitechnicaltest.pastimovieapp.presentation.detail.DetailViewModel
import com.pastitechnicaltest.pastimovieapp.presentation.home.HomeViewModel
import com.pastitechnicaltest.pastimovieapp.presentation.review.ReviewViewModel
import com.pastitechnicaltest.pastimovieapp.presentation.search.SearchViewModel
import com.pastitechnicaltest.pastimovieapp.presentation.video.VideoPlayerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { DetailViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { VideoPlayerViewModel(get()) }
    viewModel { ReviewViewModel(get()) }
}