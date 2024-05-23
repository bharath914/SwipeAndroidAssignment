package com.bharath.swipeandroidassignment.presentation.fragments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharath.swipeandroidassignment.data.common.Resource
import com.bharath.swipeandroidassignment.data.entity.local.ProductEntity
import com.bharath.swipeandroidassignment.domain.usecases.GetProductsUseCase
import com.bharath.swipeandroidassignment.presentation.states.ListState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProductsUseCase: GetProductsUseCase,
) : ViewModel() {

    private val _productsListState = MutableStateFlow(ListState<ProductEntity>())
    val productsListState = _productsListState.asStateFlow()

    fun getData(isRefresh: Boolean = false) {
        viewModelScope.launch(IO) {
            getProductsUseCase(isRefresh).onEach { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _productsListState.update {
                            ListState(error = resource.message)
                        }
                    }

                    is Resource.Loading -> {
                        _productsListState.update {
                            ListState(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        _productsListState.update {
                            Log.d("Products", "getData: ${resource.data} ")
                            ListState(list = resource.data ?: emptyList())
                        }
                    }
                }
            }.collect()
        }
    }
}