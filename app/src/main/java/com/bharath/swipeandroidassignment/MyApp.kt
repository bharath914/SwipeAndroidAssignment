package com.bharath.swipeandroidassignment

import android.app.Application
import com.bharath.swipeandroidassignment.di.appModule
import com.bharath.swipeandroidassignment.di.repoModule
import com.bharath.swipeandroidassignment.di.useCaseModule
import com.bharath.swipeandroidassignment.di.viewModelModule
import com.bharath.swipeandroidassignment.di.workModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // create koin dependencies.
            androidContext(this@MyApp)
            modules(appModule, repoModule, useCaseModule, viewModelModule, workModule)
            this.printLogger(level = org.koin.core.logger.Level.DEBUG)
            workManagerFactory()
        }

    }


}

