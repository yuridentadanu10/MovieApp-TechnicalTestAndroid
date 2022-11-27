package com.pastitechnicaltest.pastimovieapp

import android.app.Application
import com.pastitechnicaltest.pastimovieapp.core.di.networkModule
import com.pastitechnicaltest.pastimovieapp.core.di.repositoryModule
import com.pastitechnicaltest.pastimovieapp.di.useCaseModule
import com.pastitechnicaltest.pastimovieapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level.NONE
import timber.log.Timber

class MovieApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger(NONE)
            androidContext(this@MovieApplication)
            modules(
                listOf(
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                    networkModule
                )
            )
        }
    }

}