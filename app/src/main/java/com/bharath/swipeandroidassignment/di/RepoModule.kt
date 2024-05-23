package com.bharath.swipeandroidassignment.di

import com.bharath.swipeandroidassignment.data.repo.local.ProductsRepositoryImpl
import com.bharath.swipeandroidassignment.data.repo.remote.RemoteProductsRepoImpl
import com.bharath.swipeandroidassignment.data.source.local.AppDatabase
import com.bharath.swipeandroidassignment.data.source.remote.ProductsApi
import com.bharath.swipeandroidassignment.domain.repo.local.ProductsRepository
import com.bharath.swipeandroidassignment.domain.repo.remote.RemoteProductsRepo
import org.koin.dsl.module

val repoModule = module {

    single<ProductsRepository> {
        ProductsRepositoryImpl(get<AppDatabase>().productsDao)
    }

    single<RemoteProductsRepo> {
        RemoteProductsRepoImpl(get<ProductsApi>())
    }

}