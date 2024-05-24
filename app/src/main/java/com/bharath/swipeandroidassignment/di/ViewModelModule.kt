package com.bharath.swipeandroidassignment.di

import com.bharath.swipeandroidassignment.presentation.fragments.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
}