package com.bharath.swipeandroidassignment

import android.app.Application
import com.bharath.swipeandroidassignment.di.appModule
import com.bharath.swipeandroidassignment.di.repoModule
import com.bharath.swipeandroidassignment.di.useCaseModule
import com.bharath.swipeandroidassignment.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(appModule, repoModule, useCaseModule, viewModelModule)
            this.printLogger(level = org.koin.core.logger.Level.DEBUG)
        }
    }
}