package com.bharath.swipeandroidassignment.di

import com.bharath.swipeandroidassignment.domain.usecases.GetProductByIdUseCase
import com.bharath.swipeandroidassignment.domain.usecases.GetProductsUseCase
import com.bharath.swipeandroidassignment.domain.usecases.LocalProductsUseCase
import com.bharath.swipeandroidassignment.domain.usecases.PostProductUseCase
import org.koin.dsl.module


/**
 * [useCaseModule] : Provides Usecase Dependencies for ViewModels
 */
val useCaseModule = module {
    single<GetProductsUseCase> {
        GetProductsUseCase(get(), get())
    }
    single<PostProductUseCase> {
        PostProductUseCase(get(), get())
    }
    single<LocalProductsUseCase> {
        LocalProductsUseCase(get())
    }
    single<GetProductByIdUseCase> {
        GetProductByIdUseCase(get())
    }
}