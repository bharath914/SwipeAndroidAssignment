package com.bharath.swipeandroidassignment.di

import androidx.room.Room
import com.bharath.swipeandroidassignment.data.source.local.AppDatabase
import com.bharath.swipeandroidassignment.data.source.remote.ProductsApi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://app.getswipe.in/api/public/"

/**
 * [appModule] = creates a module for the application to get required dependencies.
 * Provides the Required dependencies to the application
 */
val appModule = module {
    single<ProductsApi> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductsApi::class.java)
    }
    single<AppDatabase> {
        Room.databaseBuilder(
            context = androidContext(),
            AppDatabase::class.java,
            "app_database"
        ).addMigrations()
            .build()
    }
}