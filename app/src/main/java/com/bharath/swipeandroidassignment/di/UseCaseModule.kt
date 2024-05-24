package com.bharath.swipeandroidassignment.di

import com.bharath.swipeandroidassignment.domain.usecases.GetProductsUseCase
import com.bharath.swipeandroidassignment.domain.usecases.LocalProductsUseCase
import com.bharath.swipeandroidassignment.domain.usecases.PostProductUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single<GetProductsUseCase> {
        GetProductsUseCase(get(), get())
    }
    single<PostProductUseCase> {
        PostProductUseCase(get())
    }
    single<LocalProductsUseCase> {
        LocalProductsUseCase(get())
    }
}