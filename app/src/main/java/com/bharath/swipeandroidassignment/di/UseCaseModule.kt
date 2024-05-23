package com.bharath.swipeandroidassignment.di

import com.bharath.swipeandroidassignment.domain.usecases.GetProductsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single<GetProductsUseCase> {
        GetProductsUseCase(get(), get())
    }
}