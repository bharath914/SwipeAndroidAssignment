package com.bharath.swipeandroidassignment.di

import com.bharath.swipeandroidassignment.domain.UploadWorker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.dsl.module

val workModule = module {
    workerOf(::UploadWorker)
//    single<CustomWorkerFactory>{
//        CustomWorkerFactory(get(),get())
//    }
}