package com.bharath.swipeandroidassignment.di

import com.bharath.swipeandroidassignment.presentation.fragments.dialog.BottomSheetFragViewModel
import com.bharath.swipeandroidassignment.presentation.fragments.home.HomeViewModel
import com.bharath.swipeandroidassignment.presentation.fragments.productDetail.ProductDetailViewModel
import com.bharath.swipeandroidassignment.presentation.fragments.search.SearchScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
 * [viewModelModule] Provides ViewModels for the Fragments.
 */

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModel<BottomSheetFragViewModel> {
        BottomSheetFragViewModel(get())
    }
    viewModel<SearchScreenViewModel> {
        SearchScreenViewModel(get())
    }
    viewModel<ProductDetailViewModel> {
        ProductDetailViewModel(get())
    }
}