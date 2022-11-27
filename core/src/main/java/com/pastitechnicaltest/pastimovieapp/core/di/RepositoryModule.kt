package com.pastitechnicaltest.pastimovieapp.core.di

import com.pastitechnicaltest.pastimovieapp.core.data.movie.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { MovieRepository(get()) }
}